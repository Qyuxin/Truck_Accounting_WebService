package com.xin.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.xin.dao.ServiceDao;
import com.xin.dao.impl.ServiceDaoImpl;
import com.xin.utils.JdbcUtils;

public class MainMethod {
	public static void main(String[] args) {
		ServiceDao dao = new ServiceDaoImpl();
//		int msg = dao.userLogin("17712286008", "123456");
		List<String> listComs = dao.getComs();
		System.out.println(listComs.toString());
	}
	
	private static Integer getAll() {
	    Connection conn = JdbcUtils.getConn();
	    String sql = "select * from bdf2_ue";
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        System.out.println("============================");
	        while (rs.next()) {
	            for (int i = 1; i <= col; i++) {
	                System.out.print(rs.getString(i) + "\t");
	                if ((i == 2) && (rs.getString(i).length() < 8)) {
	                    System.out.print("\t");
	                }
	             }
	            System.out.println("");
	        }
	            System.out.println("============================");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally{
	    	JdbcUtils.free(rs, pstmt, conn);
	    }
	    return null;
	}
}
