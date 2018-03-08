package com.xin.utils;

public class StringUtils {
	public static boolean isNullOrEmpty(String str){
		if (str==null || str.trim().equals("")) {
			return true;
		}
		return false;
	}
}
