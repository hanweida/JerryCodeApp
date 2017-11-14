package com.jerry.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

	/**
	 * 
	 * @Title: transToOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param
	 * @param str
	 *            字符串
	 * @param
	 * @param length
	 *            长度
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String transToOrder(String str, int length, boolean remarkflag) {
		// 过滤掉特殊字符
		str = str.trim().replaceAll("'", "").replaceAll("\"", "").replaceAll("\r", "")
				.replaceAll("\t", "").replaceAll("\\*", "").replaceAll("\\|", "");
		if(!remarkflag){//当不是remark字段时滤除\N，否则不滤除
			str = str.replaceAll("\n", "");
		}
		try {
			// 如果超过字符长度
			if (isNotOverBytesLength(str, length) == false) {
				char[] newcharlist = new char[length];
				char[] charlist = str.toCharArray();
				int n = 0;
				int i = 0;
				for (i = 0; i < length; i++) {
					char charstr = charlist[i];
					String charlen = String.valueOf(charstr);
					n = n + charlen.getBytes().length;
					newcharlist[i] = charstr;
					if (n > length-3) {
						i = i - 1;
						break;
					}
				}
				str = String.valueOf(newcharlist, 0, i) + "...";

			}
		} catch (Exception e) {
			str = str.substring(0, length / 3);
		}
		return str;
	}

	/**
	 * 是否是中文
	 * 
	 * @Methods Name isChinese
	 * @Create In Jun 20, 2012 By 005
	 * @param str
	 * @return boolean
	 */
	public static boolean isChinese(String str) {
		String reg = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否超过字符数限制 一个汉字两个字符 一个英文一个字符
	 * 
	 * @Methods Name isOverBytesLength
	 * @Create In Jun 27, 2012 By 005
	 * @param str
	 * @param length
	 * @return boolean
	 */
	public static boolean isNotOverBytesLength(String str, int length) {
		if (str.getBytes().length > length) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * @Methods Name formatString
	 * @Create In Aug 8, 2012 By zhenyu
	 * @param str
	 * @return String
	 */
	public static String formatString(String str) {
		// 过滤掉特殊字符
		str = str.replaceAll("\r", "").replaceAll("\t", "").replaceAll("\n", "").replaceAll(" {2,}", " ").replaceAll("　{2,}", " ");	
		str = str.replaceAll(" {2,}", " ");
		str = str.trim();
		return str;
	}

    /**
     * 将已用“，”分隔的字符串转化为SQL查询的字符串
     * 例如：ecom1,ecom2 转化为 'ecom1','ecom2'
     * @param str
     * @return
     */
    public static String formatToSQLString(String str){
        if(str.startsWith(",")){
            str = str.substring(1);
        }
        if(str.endsWith(",")){
            str = str.substring(0,(str.length()-1));
        }
        str = "'"+str.replaceAll(",","','")+"'";
        return str;
    }

	public static void main(String[] args) {
//		String resTR = "＄％21６发达省\n\t\r防守对方打伤份但是书fdsfds@#432卡的发放的423卡4$#$%$^%$^%$的fdsfd方fds法dfsfdsfdsfdsfdsfdsfdsfdsfdsfdsfdsffdsfddsfdsfdsf34243243fdsfd我却惹我v发生发生大幅度大幅度2佛挡杀范德萨佛32佛发生大幅度挡杀佛对E方房贷首付的R佛挡杀佛e'r是否r打算3fds康第三方fdsfd看对方是否sfds2132132复'rewr读32132132rewr'erwerewrewrewrewrewr康康2313213213213";
//		System.out.println("OLD -------resTR:" + resTR + "length:"
//				+ resTR.getBytes().length);
//		resTR = DataUtils.transToOrder(resTR, 60,false);
//		System.out.println("NEW -------resTR:" + resTR + "length:"
//				+ resTR.getBytes().length);
		System.out.println(formatString("	分 多钟   　　　\r\t\n放 到 "));
	}
}
