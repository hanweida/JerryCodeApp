package com.jerry.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class FileTools {
	private static Log log = LogFactory.getLog("FileTools");
	
	/**
	 * copy a big file to other file
	 * 
	 * @Methods Name copyBigFile
	 * @Create In 2012-3-1 By 徐建春
	 * @param srcFullName
	 * @param destFullName
	 * @return
	 * @throws IOException boolean
	 */
	public static boolean copyBigFile(String srcFullName, String destFullName){
		boolean bRet = false;
		
		File fin = new File(srcFullName);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try{
			if(fin.exists() && fin.isFile() && fin.canRead()) {
				File fout = new File(destFullName);
				if(!fout.exists()) {
					fout.createNewFile();
				}
				fis = new FileInputStream(fin);
				fos = new FileOutputStream(fout);
				byte[] buff = new byte[4096];
				int readed = -1;
				while((readed = fis.read(buff)) > 0) {
					fos.write(buff, 0, readed);
				}				
				bRet = true;				
			} else {
				log.error("F:" + srcFullName + ", not file or cant read !!!");
			}
		}catch (Exception e) {
			log.error("copyBigFile" ,e);
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					log.error("copyBigFile" ,e);
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					log.error("copyBigFile" ,e);
				}
			}
		}
		
		return bRet;
	}
	
	/**
	 * copy a small file append to a new file line by line, return line count
	 * 
	 * @param inputName
	 * @param outputName
	 * @param charset
	 * @return line count number
	 */
	public static int copyAndAppendFile(String inputName, String outputName, String charset) {
		int nLine = -1;
		
		if(inputName == null || outputName == null || charset == null) {
			return nLine;
		}
		
		BufferedReader br = null;
		FileWriter fw = null;
		try {
			br = FileTools.readFileAsBuffReader(inputName, charset);
			String sLine = br.readLine();
			
			//file can read
			nLine = 0;
			
			List<String> list = new LinkedList<String>();
			while (sLine != null) {
				sLine = sLine.trim();
				list.add(sLine);

				nLine++;
				sLine = br.readLine();
			}

			
			File file = new File(outputName);
			if (!file.exists()) {
				file.createNewFile();
			}
			// write line per data
			fw = new FileWriter(file, true);
			// write file
			int nSize = list.size();
			for(int i=0;i<nSize;i++) {
				fw.write(list.get(i));
				fw.write('\n');
				fw.flush();
			}
			
		} catch(IOException e) {
			nLine = -1;
			log.error("copyAndAppendFile:", e);
		} catch(Exception e) {
			nLine = -1;
			log.error("copyAndAppendFile Other:", e);
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("copyAndAppendFile br close:", e);
				}
			}
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					log.error("copyAndAppendFile fw close:", e);
				}
			}
		}
		return nLine;
	}
	
	/**
	 * copy a small file to a new file line by line, return line count
	 * 
	 * @param inputName
	 * @param outputName
	 * @param charset
	 * @return line count number
	 */
	public static int copySmallFile(String inputName, String outputName, String charset) {
		int nLine = -1;
		
		if(inputName == null || outputName == null || charset == null) {
			return nLine;
		}
		
		BufferedReader br = null;
		try {
			br = FileTools.readFileAsBuffReader(inputName, charset);
			String sLine = br.readLine();
			
			//file can read
			nLine = 0;
			
			List<String> list = new LinkedList<String>();
			while (sLine != null) {
				sLine = sLine.trim();
				list.add(sLine);

				nLine++;
				sLine = br.readLine();
			}
			boolean bWrite = FileTools.buffWrite(outputName, charset, list);
			if(!bWrite) {
				nLine = -1;
			}
		} catch(IOException e) {
			log.error("copySmallFile:", e);
		} catch(Exception e) {
			log.error("copySmallFile Other:", e);
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("copySmallFile close:", e);
				}
			}
		}
		return nLine;
	}

	/**
	 * clean the file content
	 * 
	 * @param fullPath
	 * @return
	 */
	public static boolean renewFile(String fullPath) {
		boolean bRet = false;
		if(fullPath != null) {
			
			try {
				File f = new File(fullPath);
				
				if(f.canWrite()) {
					if(f.delete()) {
						bRet = f.createNewFile();
					}
				}	
			} catch(IOException e) {
				log.error("renewFile Failed:", e);
			}
		}
		return bRet;
	}
	
	/**
	 * read file as BufferedReader
	 * @param filePath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static boolean buffWrite(String filePath, String charset, List<String> content)
			throws IOException {
		boolean bRet = false;
		
		if(content == null || content.size() == 0) {
			return bRet;
		}
		
		if (charset == null || charset.equals(""))
			charset = "UTF-8";
		
		BufferedWriter bw = null;
		try {
			FileOutputStream fileOutStream = new FileOutputStream( filePath );
			OutputStreamWriter outWriter = new OutputStreamWriter( fileOutStream, charset );
			bw = new BufferedWriter(outWriter);
			
			int nSize = content.size();
			for(int i=0;i<nSize;i++) {
				String s = content.get(i);
				bw.write(s);
			}
			
			bRet = true;
		} catch (Exception e) {
			log.error("buffWrite", e);
		} finally {
			if(bw != null) {
				bw.flush();
				bw.close();	
			}
		}
		
		return bRet;
	}
	
	
	/**
	 * read file as BufferedReader
	 * @param filePath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader readFileAsBuffReader(String filePath, String charset)
			throws IOException {
		if (charset == null || charset.equals(""))
			charset = "UTF-8";
		
		BufferedReader br = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					filePath), charset);
			br = new BufferedReader(isr);
			
		} catch (FileNotFoundException e) {
			log.error("readFileAsBuffReader", e);
		}finally{
			if(br!=null){
				br.close();
			}
		}
		return br;
	}
	
	/**
	 * read file as BufferedReader
	 * @param filePath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileAsStringList(String filePath, String charset) {
		if (charset == null || charset.equals(""))
			charset = "UTF-8";
		
		List<String> list = new LinkedList<String>();
		BufferedReader br = null;
		try {
			br = readFileAsBuffReader(filePath, charset);
			String queryStr = br.readLine();
			while (queryStr != null) {
				queryStr = queryStr.trim();
				list.add(queryStr);
				queryStr = br.readLine();
			}
		} catch (Exception e) {
			log.error("readFileAsStringList", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("readFileAsStringList Close:", e);
				}
			}
		}
		return list;
	}
	
	/**
	 * read inputStream to a trim String
	 * @param is
	 * @param charset
	 * @return
	 */
	public static String readInputStreamAsStringTrim(InputStream is, String charset) {
		StringBuffer sb = new StringBuffer(1024);
		BufferedReader br = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(is, charset);
			br = new BufferedReader(isr);

			String s = br.readLine();
			while (s != null) {
				s = s.trim();
				sb.append(s);
				s = br.readLine();
			}
		} catch (Exception e) {
			log.error("readInputStreamAsStringTrim:", e);
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("readInputStreamAsStringTrim Close:", e);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * read file, from file path and encode
	 * 
	 * @param filePath
	 * @param charset
	 * @return
	 */
	public static String readFileAsStringTrim(String filePath, String charset) {
		File f = new File(filePath);
		if(!f.exists() || !f.isFile()) {
			return "";
		}
		
		if (charset == null || charset.equals(""))
			charset = "UTF-8";

		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(f), charset);
			br = new BufferedReader(isr);

			String s = br.readLine();
			while (s != null) {
				s = s.trim();
				sb.append(s);
				s = br.readLine();
			}
		} catch (FileNotFoundException e) {
			log.error("readFileAsStringTrim", e);
		} catch (IOException e) {
			log.error("readFileAsStringTrim", e);
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error("readFileAsStringTrim Close:", e);
				}
			}
		}
		return sb.toString();
	}
}
