package com.xin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.StringsUtils;

public class UserLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -3781529207291777855L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		super.doPost(req, resp);
		try {
//			String username = req.getParameter("username");
//			String pwd = req.getParameter("pwd");
			String username =StringsUtils.getParamToU8(req, "username");
			String pwd = StringsUtils.getParamToU8(req, "pwd");

			ServiceDao dao = new ServiceDaoImpl();
			int msg = dao.userLogin(username, pwd);
			
			PrintWriter out = resp.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
