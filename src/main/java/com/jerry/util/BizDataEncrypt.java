package com.jerry.util;

/**
 * 业务数据加密、解密
 * 与计费系统通信
 * 与运营后台系统通信
 * Created with IntelliJ IDEA.
 * Class: BizDataEncrypt
 * User: likang
 * Date: 16-2-19
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class BizDataEncrypt {

    //与计费系统AES加密key
    private static final String CHARGING_SYSTEM_AES_KEY = "1234567812345678";
    //与计费系统AES加密iv
    private static final String CHARGING_SYSTEM_AES_IV = "1234567812345678";

    //用户密码加密 DES 算法 8个ascii字符
    private static final String USER_PASSWORD_KEY = "asdf$#12";

    //找回密码加密串key 16字节
    private static final String UESR_FIND_PWD_KEY = "alihansi$#*%2468";

    //后台一键登录加密串key 16字节
    private static final String DIRECT_LOGIN_KEY = "apojerasi&%*#$25";

    /**
     * 计费系统通信加密
     * @param data
     * @return
     */
    public static String encryptChargingSystemData(String data) {
        return AESBase64.encrypt(data,CHARGING_SYSTEM_AES_KEY,CHARGING_SYSTEM_AES_IV);
    }

    /**
     * 计费系统通信解密
     * @param data
     * @return
     */
    public static String deEncryptChargingSystemData(String data) {
        return AESBase64.deEncrypt(data,CHARGING_SYSTEM_AES_KEY,CHARGING_SYSTEM_AES_IV);
    }

    /**
     * 找回密码解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decodeFindPwd(String str) throws Exception {
        return AESBase64.deEncrypt(str, UESR_FIND_PWD_KEY,UESR_FIND_PWD_KEY);
    }

    /**
     * 找回密码加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encodeFindPwd(String str) throws Exception {
        return AESBase64.encrypt(str, UESR_FIND_PWD_KEY,UESR_FIND_PWD_KEY);
    }

    /**
     * 后台一键登录加密
     * @param str
     * @return
     */
    public static String encodeDirectLogin(String str) {
        return AESBase64.encrypt(str, DIRECT_LOGIN_KEY, DIRECT_LOGIN_KEY);
    }

    public static void main(String[] args) {
        String encrypt = encryptChargingSystemData("通信系统加密");
        System.out.println("encrypt: " +  encrypt);

        String deEntry = deEncryptChargingSystemData(encrypt);
        System.out.println("deEntry:" + deEntry);
    }

}
