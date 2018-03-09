package com.xin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xin.bean.OrderConditionBean;
import com.xin.bean.OrderInfoBean;
import com.xin.bean.ShInfoBean;
import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.DebugUtils;
import com.xin.utils.StringsUtils;

public class GetShInfosServlet extends HttpServlet{

	private static final long serialVersionUID = -4328138918314397813L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (DebugUtils.isDebug) {
			System.out.println("接收数据  GetShInfosServlet");
		}
		String com = StringsUtils.getParamToU8(req, "com");
		if (DebugUtils.isDebug) {
			System.out.println(com);
		}

//		OrderInfoBean orderInfoBean = new OrderInfoBean(orderNo, start, end, price, shsj, scry, scsj,desc);
		
		ServiceDao dao = new ServiceDaoImpl();
		List<ShInfoBean> result = dao.getShInfos(com);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(new Gson().toJson(result));
		out.flush();
		out.close();
	}
}
