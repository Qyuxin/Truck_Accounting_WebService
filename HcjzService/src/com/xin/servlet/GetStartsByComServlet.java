package com.xin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;

public class GetStartsByComServlet extends HttpServlet{
	private static final long serialVersionUID = 6990197275754737449L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String comName = new String(req.getParameter("comName").getBytes("iso-8859-1"), "utf-8"); 
		
		ServiceDao dao = new ServiceDaoImpl();
		List<String> listStarts = dao.getStartByCom(comName);
		resp.setCharacterEncoding("utf-8"); 
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.print(new Gson().toJson(listStarts));
		out.flush();
		out.close();
	}
}
