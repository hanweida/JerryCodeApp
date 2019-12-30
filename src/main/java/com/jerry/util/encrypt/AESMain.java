/**
 * @Probject Name: ASPIT
 * @Path: com.easou.encrypt.AESTest.java
 * @Create By cloud
 * @Create In 2012-2-15 11:11:10
 * TODO
 */
package com.jerry.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * This program generates a AES key, retrieves its raw bytes, and
 * then reinstantiates a AES key from the key bytes.
 * The reinstantiated key is used to initialize a AES cipher for
 * encryption and decryption.
 */

/**
 * @Class Name AESTest
 * @Author cloud
 * @Create In 2012-2-15
 */
public class AESMain {

	private static Cipher cipherEn = null;
	private static Cipher cipherDe = null;
	private static String String_Key = "";
	
	public static void initKey(String pKey) {
		if (pKey != null && !String_Key.equals(pKey)) {
			byte[] rawkey = hexStrToByteArr(pKey);
			// Instantiate the cipher
			SecretKeySpec skeySpec = new SecretKeySpec(rawkey, "AES");

			try {
				cipherEn = Cipher.getInstance("AES");
				cipherEn.init(Cipher.ENCRYPT_MODE, skeySpec);
			} catch (Exception e) {
				System.out.println("Init AES ERROR:" + e);
			}

			try {
				cipherDe = Cipher.getInstance("AES");
				cipherDe.init(Cipher.DECRYPT_MODE, skeySpec);

			} catch (Exception e) {
				System.out.println("Init AES ERROR:" + e);
			}

			String_Key = pKey;
		}
	}

	public static byte[] hexStrToByteArr(String hexStr) {
		if (hexStr != null && !"".equals(hexStr)) {
			hexStr = hexStr.toUpperCase();
			int len = hexStr.length() / 2;

			byte[] result = new byte[len];
			char[] achar = hexStr.toCharArray();

			for (int i = 0; i < len; i++) {
				int pos = i * 2;
				result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
			}
			achar = null;
			return result;
		}
		return null;
	}

	public static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * Turns array of bytes into string
	 * 
	 * @param buf
	 *            Array of bytes to convert to hex string
	 * @return Generated hex string
	 */
	public static String byteArrToHexStr(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	public static void test1(String[] args) throws Exception {

		// String message = "This is just an example";

		// Get the KeyGenerator
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128); // 192 and 256 bits may not be available

		// Generate the secret key specs.
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		System.out.println("raw key:" + raw);

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		// Instantiate the cipher
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher
				.doFinal((args.length == 0 ? "This is just an example"
						: args[0]).getBytes());
		System.out.println("encrypted string: " + byteArrToHexStr(encrypted));

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(encrypted);
		String originalString = new String(original);
		System.out.println("Original string: " + originalString + " "
				+ byteArrToHexStr(original));
	}

	public static String decodeAES(String content) {
		String sRet = "";

		if (content != null && !"".equals(content)) {
			byte[] decrypted = null;
			try {
				byte[] tmp = hexStrToByteArr(content);
				decrypted = cipherDe.doFinal(tmp);
				sRet = new String(decrypted);

			} catch (Exception e) {
				System.out.println("Decrypt AES ERROR:" + e);
			}
		}

		return sRet;
	}

	public static String encodeAES(String content) {
		String sRet = "";

		if (content != null && !"".equals(content)) {
			byte[] encrypted = null;
			try {
				encrypted = cipherEn.doFinal(content.getBytes());
				sRet = byteArrToHexStr(encrypted);

			} catch (Exception e) {
				System.out.println("Encrypt AES ERROR:" + e);
			}
		}

		return sRet;
	}

	public static void main(String[] args) throws Exception {

		String message = "测试中文加密";

		// Get the KeyGenerator
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128); // 192 and 256 bits may not be available
		// Generate the secret key specs.
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		System.out.println("raw key:" + raw);

		System.out.println("message:" + message);
		String strKey = byteArrToHexStr(raw);
		System.out.println("str key:" + strKey);

		byte[] reRaw = hexStrToByteArr(strKey);
		System.out.println("reRaw key:" + reRaw);
		String reStrKey = byteArrToHexStr(reRaw);
		System.out.println("reStr key:" + reStrKey);

		AESMain.initKey(strKey);

		String encrypt = AESMain.encodeAES(message);
		System.out.println("encrypt:" + encrypt);

		String decrypt = AESMain.decodeAES(encrypt);
		System.out.println("decrypt:" + decrypt);
	}

	/**
	 * @Return the String String_Key
	 */
	public static String getString_Key() {
		return String_Key;
	}

	public static String genNewKey() throws NoSuchAlgorithmException {
		// Get the KeyGenerator
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128); // 192 and 256 bits may not be available
		// Generate the secret key specs.
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		
		String strKey = byteArrToHexStr(raw);
		return strKey;
	}
}
