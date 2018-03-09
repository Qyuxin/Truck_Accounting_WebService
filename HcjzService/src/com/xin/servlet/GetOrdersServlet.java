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
import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.DebugUtils;
import com.xin.utils.StringsUtils;

public class GetOrdersServlet extends HttpServlet{

	private static final long serialVersionUID = 3507277302398175229L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		String orderNo = StringsUtils.getParamToU8(req, "orderNo");
//		String start = StringsUtils.getParamToU8(req, "start");
//		String end = StringsUtils.getParamToU8(req, "end");
//		String price = StringsUtils.getParamToU8(req, "price");
//		String shsj = StringsUtils.getParamToU8(req, "shsj");
//		String scry = StringsUtils.getParamToU8(req, "scry");
//		String scsj = StringsUtils.getParamToU8(req, "scsj");
//		String desc = StringsUtils.getParamToU8(req, "desc");

		if (DebugUtils.isDebug) {
			System.out.println("接收数据");
		}
		String orderConditionJson = StringsUtils.getParamToU8(req, "orderCondition");
		if (DebugUtils.isDebug) {
			System.out.println(orderConditionJson);
		}

//		OrderInfoBean orderInfoBean = new OrderInfoBean(orderNo, start, end, price, shsj, scry, scsj,desc);
		OrderConditionBean orderConditionBean = new Gson().fromJson(orderConditionJson, OrderConditionBean.class);
		
		ServiceDao dao = new ServiceDaoImpl();
		List<OrderInfoBean> result = dao.getOrders(orderConditionBean);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(new Gson().toJson(result));
		out.flush();
		out.close();
	}
}
