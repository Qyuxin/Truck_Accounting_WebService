package com.xin.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class StringsUtils {
	public static String getParamToU8(HttpServletRequest req,String param){
		try {
//			System.err.println("解析url完成 参数-->"+param);
			return new String(req.getParameter(param).getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
//			System.err.println("不支持的类型 参数-->"+param);
			return null;
		} catch (NullPointerException e) {
//			System.err.println("解析url空指针 参数-->"+param);
			return null;
		}
	}
}
