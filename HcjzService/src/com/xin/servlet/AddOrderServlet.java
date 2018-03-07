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

public class AddOrderServlet extends HttpServlet{
	private static final long serialVersionUID = 6990197275754777449L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		String orderNo = new String(req.getParameter("orderNo").getBytes("iso-8859-1"), "utf-8");
//		String start = new String(req.getParameter("start").getBytes("iso-8859-1"), "utf-8");
//		String end = new String(req.getParameter("end").getBytes("iso-8859-1"), "utf-8");
//		String price = new String(req.getParameter("price").getBytes("iso-8859-1"), "utf-8");
//		String shsj = new String(req.getParameter("shsj").getBytes("iso-8859-1"), "utf-8");
//		String scry = new String(req.getParameter("scry").getBytes("iso-8859-1"), "utf-8");
//		String scsj = new String(req.getParameter("scsj").getBytes("iso-8859-1"), "utf-8");
//		String desc = new String(req.getParameter("desc").getBytes("iso-8859-1"), "utf-8");//order_desc

//		String orderNo = StringsUtils.getParamToU8(req, "orderNo");
//		String start = StringsUtils.getParamToU8(req, "start");
//		String end = StringsUtils.getParamToU8(req, "end");
//		String price = StringsUtils.getParamToU8(req, "price");
//		String shsj = StringsUtils.getParamToU8(req, "shsj");
//		String scry = StringsUtils.getParamToU8(req, "scry");
//		String scsj = StringsUtils.getParamToU8(req, "scsj");
//		String desc = StringsUtils.getParamToU8(req, "desc");
		
		String orderInfoJson = StringsUtils.getParamToU8(req, "orderInfo");

//		OrderInfoBean orderInfoBean = new OrderInfoBean(orderNo, start, end, price, shsj, scry, scsj,desc);
		OrderInfoBean orderInfoBean = new Gson().fromJson(orderInfoJson, new OrderInfoBean().getClass());
		
		ServiceDao dao = new ServiceDaoImpl();
		int result = dao.saveOrder(orderInfoBean);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(result);
		out.flush();
		out.close();
	}
}
