package com.xin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xin.bean.OrderInfoBean;
import com.xin.bean.ShInfoBean;
import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.StringsUtils;

public class AddShInfoServlet extends HttpServlet{

	private static final long serialVersionUID = -6164061478686752846L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String shInfoJson = StringsUtils.getParamToU8(req, "shInfo");

//		OrderInfoBean orderInfoBean = new OrderInfoBean(orderNo, start, end, price, shsj, scry, scsj,desc);
		ShInfoBean shInfoBean = new Gson().fromJson(shInfoJson, new ShInfoBean().getClass());
		
		ServiceDao dao = new ServiceDaoImpl();
		int result = dao.addShInfo(shInfoBean);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(result);
		out.flush();
		out.close();
	}
}
