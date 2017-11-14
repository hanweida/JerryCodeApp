package com.jerry.util;

import com.easou.db.DBConnectUtil;
import com.easou.mis.dao.SpAdDao;
import com.easou.mis.pojo.AdExtends;
import com.easou.mis.pojo.Advertisment;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class SyncToRedisOnline {
	
	private static Logger errorLog = LogWriter.getErrLog();
	
	
	   
	static {
		InputStream is = null;
		try { 
			is = new FileInputStream("src/main/webapp/WEB-INF/classes/application.properties");
			Properties props = new Properties();
			props.load(is);
			String path = props.getProperty("database_config_parent_path_online");
			DBConnectUtil.init(path);
		} catch (IOException e) {
			errorLog.error("Not find application.properties");  
			e.printStackTrace();
		} finally {
			if (null != is ) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */ 
	public static void main(String[] args) {
		SyncToRedisOnline.syncADs();
		System.out.println("input done !!!");
		System.exit(0);
	}
	
	/**
	 * 从mis系统中同步广告数据并插入到Redis中。时间格式
	 * @param endDate 同步的截至日期，日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static void syncADs() {
		StringBuilder sbSuccess = new StringBuilder();
		StringBuilder sbNotIn = new StringBuilder();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SELECT_ADS = "SELECT id, title, des, url, showurl, phone, imagepath, remark, adtype, apppath, appurl,userid,des1 " + " from t_ad  WHERE adtype = 1 AND " + "DATE_FORMAT(createtime, '%Y-%m-%d %H:%i:%S') > '2013-03-21 11:00:00' AND DATE_FORMAT(createtime, '%Y-%m-%d %H:%i:%S') < '2013-03-21 13:00:00'";
				//"id>1607942";
//				"FROM t_ad where id IN (36,1050,696,705,716,733,736,739,743,796,919,927,932,947,966,980,994,1041,1069,1236,1243,1284,4787,29029,29031,29115,29118,31127,32386,33022,33359,33360,33379,34505,36436,36437,40708,41288,41577,42163,43157,43572,43997,44627,44945,50162,50841,51142,52243,53376,53434,53477,53656,53921,54032,54178,56918,57310,57850,57851,58336,58424,58425,59444,60887,61031,61036,61127,61211,61714,62887,62888,62892,62913,62915,62916,62917,62918,63433,63586,63896,65532,65717,65888,65889,65890,66407,66701,67061,67475,69208,70089,70418,70476,70477,71962,72030,72097,72278,73097,74759,74769,74771,74875,75099,76115,76123,76406,76559,77351,77399,78695,78698,79960,79965,81155,81907,82966,83304,83555,84448,84870,86249,86788,87856,138044,211363,215801,271983,277118)"; //where id=451973
//			"FROM t_ad where userid=974 limit 150000,50000";
		try {
			conn = DBConnectUtil.open("data1");
			pstmt = conn.prepareStatement(SELECT_ADS);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String desc = rs.getString(3);
				String url = rs.getString(4);
				String showUrl = rs.getString(5);
				String phone = rs.getString(6);
				String imageUpload = rs.getString(7);
				String remark = rs.getString(8);
				int adtype = rs.getInt(9);
				String appPath = rs.getString(10);
				String appUrl = rs.getString(11);
				String userid = rs.getString(12);
				String desc1 = rs.getString(13);
				byte[] bytes = null;
				byte[] str= RedisUtils.getDataByKey(String.valueOf(adtype), String.valueOf(id));
				if (str == null || str.length == 0) {
					sbNotIn.append("id:"+id+",	title:"+title+",	userid:"+userid + ",	not in redis continue\n");
					continue;
				} else {
//					sbSuccess.append("id:"+id+",	title:"+title+",	userid:"+userid + ",in redis\n");
				}
				if (StringUtils.isNotBlank(title)) {
					title = DataUtils.transToOrder(title, 1000, false);
				}
				if (StringUtils.isNotBlank(desc)) {
					desc = DataUtils.transToOrder(desc, 1000, true);
				}
				switch (adtype) {
					case 1://文本广告
						bytes = GenRedisData.saveTextAdByte(title, desc, url, showUrl, phone,desc1);
						break;
					case 2://购物广告
						bytes = GenRedisData.saveShopAdByte(title, imageUpload, url, showUrl, phone, remark, desc, "http://assets.easou.com/image");
						break;
					case 3://小说广告
						bytes = GenRedisData.saveStoryAdByte(title, url, remark);
						break;
					case 4://App广告
						bytes = GenRedisData.saveAppAdByte(title, desc, appPath, appUrl, remark);
						break;
					case 5://音乐广告
						bytes = GenRedisData.saveMusicAdByte(title, imageUpload, url, showUrl, phone, desc,desc1);
						break;
					case 7://展示广告
						SpAdDao spAdDao = SpAdDao.getInstance();
						Advertisment ad = spAdDao.findShowAdById(id);
						AdExtends adExt = spAdDao.findShowAdExtends(id);
						bytes =  GenRedisData.saveShowAdByte(ad, adExt);
						break;
					case 8://展示文本广告
						bytes =  GenRedisData.saveShowTextAdByte(title, desc, appUrl, showUrl, phone, desc1);
						break;
				}
				System.out.println(adtype + " == " + id);
				RedisUtils.saveByte(Integer.toString(adtype), Integer.toString(id), bytes);
			}
			
			System.out.println("import over");
			System.err.println(sbNotIn.toString());
			System.err.println("=======================================================");
//			System.out.println(sbSuccess.toString());
		} catch (Exception e) {
			e.printStackTrace();
			errorLog.error("getFixedRankByState:", e);
		} finally {
			DBConnectUtil.close(rs, pstmt, conn);
		}	
	}

}
