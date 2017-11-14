package com.jerry.util;

public class UlogMoreDetailUtil {
	
	public static String getChannelStr(String source) {
		String channelStr = "";
		StringBuilder sBuilder = new StringBuilder();
		if(source.equals("0:DFT")){
			sBuilder.append("全部频道");
		}else{
			source = source.substring(source.indexOf(":")+1);
			String[] chsArray = source.split(";");
			for (String temp : chsArray) {
				if (temp.equals("3")) {
					sBuilder.append("小说频道,");
				} else if (temp.equals("22")) {
					sBuilder.append("购物频道,");
				} else if (temp.equals("2")) {
					sBuilder.append("图片频道,");
				}else if (temp.equals("10")) {
					sBuilder.append("音乐频道,");
				}else if (temp.equals("7")) {
					sBuilder.append("新闻频道,");
				}else if (temp.equals("14")) {
					sBuilder.append("网页频道,");
				}
			}
		}
		channelStr = sBuilder.toString();
		channelStr = (channelStr.endsWith(",")?channelStr.substring(0,channelStr.length()-1):channelStr);
		return channelStr;
	
	}
	
	public static String getMatchModel(int source) {
		String content = "";
		if(source==1){
			content = "精确";
		}else if(source==2){
			content = "短语";
		}else{
			content = "宽泛";
		}
		return content;
	}
	
	public static String getNetStr(String source) {
		String contentNet = "";
		StringBuilder sBuilder = new StringBuilder();
		if(source == null || source.equals("0:DFT")){
			sBuilder.append("全部协议");
		}else{
			source = source.substring(source.indexOf(":")+1);
			String[] npArray = source.split(";");
			for (String temp : npArray) {
				if (temp.equals("1")) {
					sBuilder.append("wap,");
				} else if (temp.equals("2")) {
					sBuilder.append("net,");
				} else if (temp.equals("3")) {
					sBuilder.append("wifi");
				}
			}
		}
		contentNet = sBuilder.toString();
		contentNet = (contentNet.endsWith(",")?contentNet.substring(0,contentNet.length()-1):contentNet);
		return contentNet;
	}
	
	public static String getOperatorStr(String source) {
		String contentNet = "";
		StringBuilder sBuilder = new StringBuilder();
		if(source == null || source.equals("0:DFT")){
			sBuilder.append("全部运营商");
		}else{
			source = source.substring(source.indexOf(":")+1);
			String[] opArray = source.split(";");
			for (String temp : opArray) {
				if (temp.equals("1")) {
					sBuilder.append("中国移动,");
				} else if (temp.equals("2")) {
					sBuilder.append("中国电信,");
				} else if (temp.equals("3")) {
					sBuilder.append("中国联通");
				}
			}
		}
		contentNet = sBuilder.toString();
		contentNet = (contentNet.endsWith(",")?contentNet.substring(0,contentNet.length()-1):contentNet);
		return contentNet;
	}

}
