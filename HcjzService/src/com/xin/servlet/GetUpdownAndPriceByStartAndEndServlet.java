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
import com.xin.bean.UpdownAndPriceBean;
import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.StringsUtils;

public class GetUpdownAndPriceByStartAndEndServlet extends HttpServlet{
	private static final long serialVersionUID = 6990197275754777449L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String start = StringsUtils.getParamToU8(req, "start");
		String end = StringsUtils.getParamToU8(req, "end");

		ServiceDao dao = new ServiceDaoImpl();
		List<UpdownAndPriceBean> result = dao.getUpdownAndPriceByStartAndEnd(start, end);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(new Gson().toJson(result));
		out.flush();
		out.close();
	}
}
