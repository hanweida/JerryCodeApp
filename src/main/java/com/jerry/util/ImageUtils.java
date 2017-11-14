package com.jerry.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片工具类
 * 
 * @Class Name ImageUtils
 * @Author wushuo
 * @Create In Apr 19, 2012
 */
public class ImageUtils {

	private static final int WIDTH = 100;

	private static final int HEIGHT = 100;

	/**
	 * 验证图片的宽度与长度
	 * 
	 * @Methods Name validateImageWidthAndHeight
	 * @Create In Apr 23, 2012 By wushuo
	 * @param originalFile
	 * @return
	 * @throws IOException
	 *             boolean
	 */
	public static boolean validateImageWidthAndHeight(File originalFile, int widthRule, int heightRule)
			throws IOException {
		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image iamge = ii.getImage();
		int width = iamge.getWidth(null);
		int height = iamge.getHeight(null);
		if (width == widthRule && height == heightRule && widthRule != 0 && heightRule != 0) {
			return true;
		}

		return false;
	}

	/**
	 * 图片缩放
	 * 
	 * @Methods Name size
	 * @Create In Apr 23, 2012 By wushuo void
	 */
	public static String resize(int nw, int nh, File originalFile) {
		String resizeName = "";
		try {
			String basepath = originalFile.getParent();
			String fileName = originalFile.getName();

			File fo = new File(basepath + File.separator + "ea" + fileName); // 将要转换出的小图文件
			BufferedImage bis = ImageIO.read(originalFile);// 读取文件大小
			if(bis==null)  return resizeName;
			int w = bis.getWidth();// 得到文件宽度
			int h = bis.getHeight();// 得到文件高度
			AffineTransform transform = new AffineTransform();

			double ww = (double) nw / w;
			double hh = (double) nh / h;
			transform.setToScale(ww, hh);

			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "jpeg", fo);
			resizeName = fo.getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resizeName;
	}

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public static void saveToFile(String imageUrl, String filePath) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		try {
			byte[] buf = new byte[8096];
			int size = 0;
			// 建立链接
			url = new URL(imageUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			// 连接指定的资源
			httpUrl.connect();
			// 获取网络输入流
			bis = new BufferedInputStream(httpUrl.getInputStream());
			// 建立文件
			fos = new FileOutputStream(filePath);
			// 保存文件
			while ((size = bis.read(buf)) != -1){
				fos.write(buf, 0, size);
			}
		} catch (Exception e) {
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (Exception e2) {
			}
			httpUrl.disconnect();
		}
	}

	public static void main(String[] args) {
		try {

			boolean flag = ImageUtils.validateImageWidthAndHeight(new File(
					"e:\\image\\success.gif"), WIDTH, HEIGHT);
			System.out.println(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}