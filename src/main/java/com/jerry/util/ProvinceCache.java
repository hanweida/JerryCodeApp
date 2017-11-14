package com.jerry.util;

import java.util.concurrent.ConcurrentHashMap;

public class ProvinceCache {
	
	private static ProvinceCache instance = new ProvinceCache();
	
	public static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
	
	public static ProvinceCache getInstance() {
		return instance;
	}
	
	static {
		cache.put("1", "北京");
		cache.put("2", "上海");
		cache.put("3", "天津");
		cache.put("4", "广东");
		cache.put("5", "福建");
		cache.put("6", "海南");
		cache.put("7", "安徽");
		cache.put("8", "贵州");
		cache.put("9", "甘肃");
		cache.put("10", "广西");
		cache.put("11", "河北");
		cache.put("12", "河南");
		cache.put("13", "黑龙江");
		cache.put("14", "湖北");
		cache.put("15", "湖南");
		cache.put("16", "吉林");
		cache.put("17", "江苏");
		cache.put("18", "江西");
		cache.put("19", "辽宁");
		cache.put("20", "内蒙古");
		cache.put("21", "宁夏");
		cache.put("22", "青海");
		cache.put("23", "山东");
		cache.put("24", "山西");
		cache.put("25", "陕西");
		cache.put("26", "四川");
		cache.put("27", "西藏");
		cache.put("28", "新疆");
		cache.put("29", "云南");
		cache.put("30", "浙江");
		cache.put("31", "重庆");
		cache.put("32", "香港");
		cache.put("33", "台湾");
		cache.put("34", "澳门");
		cache.put("35", "国外");
	}

	public static String getProvinceById(String id) {
		return cache.get(id);
	}
}
