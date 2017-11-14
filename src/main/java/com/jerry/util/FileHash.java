package com.jerry.util;


/**
 * 主要为了减少文件夹下图片数量过多的情况
 * @Class Name FileHash
 * @Author wushuo
 * @Create In Apr 19, 2012
 */
public class FileHash {
	
	/**
	 * 文件夹个数
	 * 根据文件名+UUID+上传日期hash出子目录组成<code>key</code>
	 * 根据key的hash对module取模来定位到具体的某一个文件夹
	 * 提高读取速度
	 */
	private static int moduleSize = 16;
	
	@SuppressWarnings("unused")
	public static int getFileDir(String key) {
		long hashCode = (long) key.hashCode();

		if (hashCode < 0)
			hashCode = -hashCode;

		int moudleNum = (int) hashCode % moduleSize;
		return moudleNum;
	}
	
	
//	public static void main(String[] args) throws Exception {
//		//String pathName = UUID.randomUUID().toString();
//		String realPath = "e:" + File.separator + "images";
//
//		File file = new File(realPath);
//		if (!file.exists()) {
//			file.mkdir();
//		}
//		File image = new File("e:\\success.gif");				
//		System.out.println(image.length());
//		byte[] b_images = new byte[(int)image.length()];
//		FileInputStream fis = new FileInputStream(image);
//		fis.read(b_images);
//
//
//	}
}
