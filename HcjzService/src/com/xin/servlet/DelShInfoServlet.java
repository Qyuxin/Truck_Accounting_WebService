package com.xin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xin.bean.OrderInfoBean;
import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.StringsUtils;

public class DelShInfoServlet extends HttpServlet {

	private static final long serialVersionUID = -6951561548246263938L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = StringsUtils.getParamToU8(req, "id");

//		OrderInfoBean orderInfoBean = new OrderInfoBean(orderNo, start, end, price, shsj, scry, scsj,desc);
		
		ServiceDao dao = new ServiceDaoImpl();
		int result = dao.deleteShInfo(id);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(result);
		out.flush();
		out.close();
	}
}
