package com.jerry.util;

import com.csvreader.CsvReader;
import com.easou.db.DBConnectUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author wusuosuo
 * @version Id ExcelUtil.java
 * @since 2012-3-8     
 */
public abstract class ReadCsvFileUtil {

	private static Logger errorLog = LogWriter.getErrLog();
	//private static int count;

    public static Map<String, String> getCsvInfo(String fileName, int industry) {
		ArrayList<String[]> csvList = new ArrayList<String[]>();
		CsvReader reader;
		try {
			reader = new CsvReader(fileName, '\t', Charset.forName("Unicode"));
			reader.readHeaders(); // 跳过表头 如果需要表头的话，不要写这句。

			while (reader.readRecord()) { // 逐行读入除表头的数据
				csvList.add(reader.getValues());
			}
			reader.close();
		} catch (Exception e) {
			errorLog.error("File not Found", e);
		}

		Map<String, String> bidWordMap = new LinkedHashMap<String, String>();
		for (int row = 0; row < csvList.size(); row++) {
			String cellname = csvList.get(row)[0]; // 取得第row行第0列的数据
			String cellrbid = csvList.get(row)[5]; // 取得第row行第5列的数据
			//System.out.println(++count);
			cellname = cellname.replaceAll(" ", "");
		
			if (cellrbid.indexOf("-") != -1) {
				cellrbid = cellrbid.replaceAll("-", "0.3");
				cellrbid = cellrbid + "###" + industry;
			} else {
				cellrbid = cellrbid + "###" + industry;
			}
			
			if (!bidWordMap.containsKey(cellname)) {
				bidWordMap.put(cellname.trim(), cellrbid.trim());
			}

			if (bidWordMap.containsKey(cellname)) {
	
				String bw = bidWordMap.get(cellname);
				String[] bid_industry = bw.split("###");
				Float bwf = Float.parseFloat(bid_industry[0]);
				String[] tp = cellrbid.split("###");
				Float tpf = Float.parseFloat(tp[0]);
				if (bwf.compareTo(tpf) < 0) {
					bidWordMap.put(cellname, cellrbid);
				}
			}
		}
		return bidWordMap;
    }

	/**
	 * 读取并去重复关键字数据
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void reduplicate(String fileNames) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Map<String,String> bidWordMap = new LinkedHashMap<String,String>();
		Future<Map<String,String>> future = null;
		
		List<File> list = new ArrayList<File>();
		File dir = new File(fileNames);
		File file[] = dir.listFiles();
		
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory()) {
				list.add(file[i]);
			}
		}
		
		File tmp;
		List<String> names = new ArrayList<String>();
		
		for (int i = 0; i < list.size(); i++) {
			tmp = list.get(i);
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				String path = null;
				for (int j = 0; j < file.length; j++) {
					if (!file[j].isDirectory()) {
						path = file[j].getAbsolutePath();
						path = path.replaceAll("\\\\", "/");
						names.add(path);
					} else if (file[j].isDirectory()) {
						File[] fileChild = file[j].listFiles();
						String pathChild = null;
						for (int k = 0; k < fileChild.length; k++) {
							if (!fileChild[k].isDirectory()) {
								pathChild = fileChild[k].getAbsolutePath();
								pathChild = pathChild.replaceAll("\\\\", "/");
								names.add(pathChild);
							}
						}
					}
				}
			}
		}
		
		for (int i = 0; i < names.size(); i++) {
			class MyCallable implements Callable<Map<String, String>> {
				private String name = "";
				MyCallable(String fileName) {
					name = fileName;
				}
			
				public Map<String, String> call() throws Exception {
						Map<String,String> bidWordMap = new LinkedHashMap<String, String>();
						String industry = name;
						int position = industry.indexOf(".");
						industry = industry.substring(0,position);
						position = industry.lastIndexOf("/");
						industry = industry.substring(position + 1);
						int industry2 = Integer.parseInt(industry);
						bidWordMap = getCsvInfo(name,industry2);
						return bidWordMap;
				}
			}
			
			future = service.submit(new MyCallable(names.get(i)));
			Map<String,String> temp = future.get();
			Set<String> key = temp.keySet();
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				String name = it.next();
				 if (!bidWordMap.containsKey(name)) {
        			 bidWordMap.put(name,temp.get(name));
        		 }
				if (bidWordMap.containsKey(name)) {
					
					String bw = bidWordMap.get(name);
					String[] bid_industry = bw.split("###");
					Float bwf = Float.parseFloat(bid_industry[0]);
					String[] tp = temp.get(name).split("###");
					Float tpf = Float.parseFloat(tp[0]);
					
					if (bwf.compareTo(tpf) < 0) {
						bidWordMap.put(name, temp.get(name));
					}
				} 
			}
		}
		try {
			reduplicateSummarizing(bidWordMap);
		} catch (Exception e) {
			errorLog.error("redulicateSummarizing",e);
		}
	}
	
	/**
	 * 汇总并保存入数据库
	 * @param maps
	 * @return
	 * @throws SQLException
	 */
	public static void reduplicateSummarizing(Map<String, String> maps) throws SQLException {
		Set<String> key = maps.keySet();
		//System.out.println(maps.size());
		Map<String, String> map = getBidWord();
		
		int id = 0;
		int flag = 0;
		boolean isCommit = false;
		Connection conn = DBConnectUtil.open("data1");
		ResultSet rs = null;
		PreparedStatement st = null;
		conn.setAutoCommit(isCommit);
		String sql = "insert into t_bidword(word,bidnum,createtime,lasttime,minbid,industry,r_bid) values(?,?,now(),now(),?,?,?)";
		
		try {
			st = conn.prepareStatement(sql);
				for (Iterator<String> it = key.iterator(); it.hasNext();) {
					
					String s = it.next();
					String[] values = maps.get(s).split("###");
					if (!map.containsKey(s)) {
						//String rbid = String.valueOf(maps.get(s));
						try {
							st.setString(1, s);
							st.setInt(2, 1);
							double r_bid = 0;
							double min_bid = Double.parseDouble(values[0]);
							min_bid =  min_bid * 100;
							r_bid = min_bid;
							min_bid = min_bid * 0.5;
							BigDecimal minbid_bd = new BigDecimal(min_bid);
							minbid_bd = minbid_bd.setScale(2, BigDecimal.ROUND_HALF_UP);
							if (minbid_bd.intValue() < 30) {
								st.setInt(3, 30);
							} else {
								st.setInt(3, minbid_bd.intValue());
							}
							
							st.setInt(4, Integer.parseInt(values[1]));
				
							BigDecimal rbid_bd = new BigDecimal(r_bid);
							rbid_bd = rbid_bd.setScale(2, BigDecimal.ROUND_HALF_UP);
							st.setInt(5, rbid_bd.intValue());
							//System.out.println(id++);
							st.addBatch();
							flag++;
							if (flag == 400 ) {
								flag = 0;
								st.executeBatch();
								conn.commit();
							}
						} catch (Exception e) {
							errorLog.error("save bidword:", e);
						}
					} else if (map.containsKey(s)) {
						double db_bidword = Double.parseDouble(map.get(s));
						double map_bidword = Float.parseFloat(values[0]);
						map_bidword =  map_bidword * 100;
						BigDecimal bidword_bd = new BigDecimal(map_bidword);
						bidword_bd = bidword_bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						if (db_bidword <  bidword_bd.intValue()) {
							//System.out.println(s + "   " + bidword_bd.intValue());
							double min_bid = Float.parseFloat(values[0]);
							min_bid =  min_bid * 100;
							min_bid = min_bid * 0.5;
							BigDecimal min_bid_bd = new BigDecimal(min_bid);
							min_bid_bd = min_bid_bd.setScale(2, BigDecimal.ROUND_HALF_UP);
							if (min_bid_bd.intValue() < 30) {
								update(s,30,bidword_bd.intValue());
							} else {
								update(s,min_bid_bd.intValue(),bidword_bd.intValue());
							}
							
						
							//System.out.println("update~~~~~~");
						}
					}
			
			}
				st.executeBatch();
				conn.commit();
		} finally {
			//isCommit = conn.getAutoCommit();
			DBConnectUtil.close(rs, st, conn);
		}
	}
	
/*	public static boolean saveBidWord(String wordName,String r_bid ,int industry, int bidnum){		
		
		String sql = "insert into t_bidword(word,bidnum,createtime,lasttime,industry,r_bid) values(?,?,now(),now(),?,?)";

		ResultSet rs = null ;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = DBConnectUtil.open("data1");
			st = conn.prepareStatement(sql);
			st.setString(1, wordName);
			st.setInt(2, bidnum);
			st.setInt(3, industry);
			st.setFloat(4, Float.parseFloat(r_bid));
			st.executeUpdate();
		} catch (Exception e) {
			errorLog.error("save bidword:", e);
			return false;
		}finally{
			DBConnectUtil.close(rs, st, conn);
		}	
		return true;	
	}
*/
	public static Map<String, String> getBidWord() {
		String sql = "select word,r_bid from t_bidword";
		ResultSet rs = null ;
		PreparedStatement st = null;
		Connection conn = null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			conn = DBConnectUtil.open("data1");
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();	
			while(rs.next()){
				String name = rs.getString("word");
				float r_bid = rs.getFloat("r_bid");
				map.put(name, String.valueOf(r_bid));
			}
		} catch (Exception e) {
			errorLog.error("getBidWord error:", e);
		}finally{
			DBConnectUtil.close(rs, st, conn);
		}	
		return map;
	}
	

	public static boolean update(String wordName, int minbid, int r_bid){
		String sql = "update t_bidword set r_bid= ?, minbid = ? where word=?";
		ResultSet rs = null ;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = DBConnectUtil.open("data1");
			st = conn.prepareStatement(sql);
			st.setInt(1, r_bid);
			st.setInt(2, minbid);
			st.setString(3, wordName);
			st.executeUpdate();
		} catch (Exception e) {
			errorLog.error("update bidword:", e);
			return false;
		}finally{
			DBConnectUtil.close(rs, st, conn);
		}	
		return true;
	}

	 public static void getCsvWordblack(String fileName) throws Exception {
			ArrayList<String[]> csvList = new ArrayList<String[]>();
			CsvReader reader;
			try {
				reader = new CsvReader(fileName, '\t', Charset.forName("gbk"));
				reader.readHeaders(); 
				while (reader.readRecord()) {
					csvList.add(reader.getValues());
				}
				reader.close();
			} catch (Exception e) {
				errorLog.error("File not Found", e);
			}

			List<String> bidWordMap = new ArrayList<String>();
			for (int row = 0; row < csvList.size(); row++) {
				String cellname = csvList.get(row)[0];
				String[] values = cellname.split(",");
				if (values[0].equals("1")) {
					bidWordMap.add(values[1]);
				}
			}
			 putInStorage(bidWordMap);
	    }


	public static void putInStorage(List<String> list) throws SQLException {
		
		int flag = 0;
		boolean isCommit = false;
		Connection conn = DBConnectUtil.open("data1");
		ResultSet rs = null;
		PreparedStatement st = null;
		conn.setAutoCommit(isCommit);
		String sql = "insert into t_sys_wordblack(word,opuserid,createtime,lasttime) values(?,?,now(),now())";

		try {
			st = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				String name = list.get(i);
				st.setString(1, name);
				st.setInt(2, 1);
				st.addBatch();
				flag++;
				if (flag == 400) {
					flag = 0;
					st.executeBatch();
					conn.commit();
				}
			}
			st.executeBatch();
			conn.commit();
			
		} catch (Exception e) {
			errorLog.error("save wordblack:", e);
		}
		finally {
			// isCommit = conn.getAutoCommit();
			DBConnectUtil.close(rs, st, conn);
		}
	}
		
}


