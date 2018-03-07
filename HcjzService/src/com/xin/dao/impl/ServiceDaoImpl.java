package com.xin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xin.bean.UpdownAndPriceBean;
import com.xin.bean.OrderConditionBean;
import com.xin.bean.OrderInfoBean;
import com.xin.dao.ServiceDao;
import com.xin.utils.JdbcUtils;

public class ServiceDaoImpl implements ServiceDao {
	/**
	 * 
	 * @param name
	 * @param pwd
	 * @return 0 --> 账号不存在 1 --> 登陆成功 -1 --> 密码错误
	 */
	public int userLogin(String username, String pwd) {
		String correctPwd = null;
		Connection conn = JdbcUtils.getConn();
		String sql = "select pwd from bdf2_ue where username = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				correctPwd = rs.getString("pwd");
			}
			if (correctPwd == null) {
				return 0;
			} else if (correctPwd.equals(pwd)) {
				return 1;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, pstmt, conn);
		}
		return 0;
	}

	// SELECT DISTINCT info_gsmc FROM bdf2_shinfo
	public List<String> getComs() {
		List<String> listComs = new ArrayList<String>();
		Connection conn = JdbcUtils.getConn();
		String sql = "SELECT DISTINCT info_gsmc FROM bdf2_shinfo";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String comName = rs.getString("info_gsmc");
				System.out.println(comName);
				listComs.add(comName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, pstmt, conn);
		}
		return listComs;
	}

	// SELECT DISTINCT info_ksdz FROM bdf2_shinfo WHERE info_gsmc = '盛世';
	public List<String> getStartByCom(String comName) {
		List<String> listStarts = new ArrayList<String>();
		Connection conn = JdbcUtils.getConn();
		String sql = "SELECT DISTINCT info_ksdz FROM bdf2_shinfo WHERE info_gsmc = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, comName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String startPoint = rs.getString("info_ksdz");
				System.out.println(startPoint);
				listStarts.add(startPoint);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, pstmt, conn);
		}
		return listStarts;
	}

	// SELECT info_jsdz,info_shjg FROM bdf2_shinfo WHERE info_ksdz = '盛世';
	public List<String> getEndsByStart(String startPoint) {
		List<String> listEnds = new ArrayList<String>();
		Connection conn = JdbcUtils.getConn();
		String sql = "SELECT info_jsdz FROM bdf2_shinfo WHERE info_ksdz = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, startPoint);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String end = rs.getString("info_jsdz");
				listEnds.add(end);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, pstmt, conn);
		}
		return listEnds;
	}

	// SELECT info_jsdz,info_shjg FROM bdf2_shinfo WHERE info_ksdz = '盛世';
	public List<UpdownAndPriceBean> getUpdownAndPriceByStartAndEnd(
			String startPoint, String endPoint) {
		List<UpdownAndPriceBean> list = new ArrayList<UpdownAndPriceBean>();
		Connection conn = JdbcUtils.getConn();
		String sql = "SELECT info_shjg , info_updown FROM bdf2_shinfo WHERE info_ksdz = ? AND info_jsdz = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, startPoint);
			pstmt.setString(2, endPoint);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String updown = rs.getString("info_updown");
				String price = rs.getString("info_shjg");
				list.add(new UpdownAndPriceBean(updown, price));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, pstmt, conn);
		}
		return list;
	}

	// SELECT info_jsdz,info_shjg FROM bdf2_shinfo WHERE info_ksdz = '盛世';
	public List<OrderInfoBean> getOrders(OrderConditionBean bean) {
		List<OrderInfoBean> listOrders = new ArrayList<OrderInfoBean>();
		Connection conn = JdbcUtils.getConn();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM bdf2_order WHERE 1 = 1 ");
		if (bean.getOrderNo() != null) {
			sql.append(" AND order_no = '").append(bean.getOrderNo())
					.append("' ");
		}
		if (bean.getCom() != null) {
			sql.append(" AND order_com = '").append(bean.getCom()).append("' ");
		}
		if (bean.getDesc() != null) {
			sql.append(" AND order_desc = '").append(bean.getDesc())
					.append("' ");
		}
		if (bean.getEnd() != null) {
			sql.append(" AND order_jsdz = '").append(bean.getEnd())
					.append("' ");
		}
		if (bean.getOrderNumber() != null) {
			sql.append(" AND order_number = '").append(bean.getOrderNumber())
					.append("' ");
		}
		if (bean.getPrice() != null) {
			sql.append(" AND order_shjg = '").append(bean.getPrice())
					.append("' ");
		}
		if (bean.getScry() != null) {
			sql.append(" AND order_scry = '").append(bean.getScry())
					.append("' ");
		}
		if (bean.getStart() != null) {
			sql.append(" AND order_ksdz = '").append(bean.getStart())
					.append("' ");
		}
		if (bean.getUpdown() != null) {
			sql.append(" AND order_updown = '").append(bean.getUpdown())
					.append("' ");
		}
		if (bean.getShrqStart() != null) {
			sql.append(" AND order_shrq >= '").append(bean.getShrqStart())
					.append("' ");
		}
		if (bean.getShrqEnd() != null) {
			sql.append(" AND order_shrq <= '").append(bean.getShrqEnd())
					.append("' ");
		}
		if (bean.getScsjStart() != null) {
			sql.append(" AND order_scsj >= '").append(bean.getScsjStart())
					.append("' ");
		}
		if (bean.getScsjEnd() != null) {
			sql.append(" AND order_scsj <= '").append(bean.getScsjEnd())
					.append("235959' ");
		}
		if (bean.getOrderHavenumber() != null) {
			sql.append(" AND order_havenumber = '")
					.append(bean.getOrderHavenumber()).append("' ");
		}
		sql.append(" order by order_shrq desc ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String orderNo = rs.getString("order_no");
				String com = rs.getString("order_com");
				String ksdz = rs.getString("order_ksdz");
				String jsdz = rs.getString("order_jsdz");
				String price = rs.getString("order_shjg");
				String shrq = rs.getString("order_shrq");
				String shsjd = rs.getString("order_shsjd");
				String scry = rs.getString("order_scry");
				String scsj = rs.getString("order_scsj");
				String desc = rs.getString("order_desc");
				String updown = rs.getString("order_updown");
				String orderHaveNumber = rs.getString("order_havenumber");
				String orderNumber = rs.getString("order_number");

				OrderInfoBean infoBean = new OrderInfoBean();
				infoBean.setOrderNo(orderNo);
				infoBean.setCom(com);
				infoBean.setStart(ksdz);
				infoBean.setEnd(jsdz);
				infoBean.setPrice(price);
				infoBean.setShrq(shrq);
				infoBean.setShsjd(shsjd);
				infoBean.setScry(scry);
				infoBean.setScsj(scsj);
				infoBean.setDesc(desc);
				infoBean.setUpdown(updown);
				infoBean.setOrderHavenumber(orderHaveNumber);
				infoBean.setOrderNumber(orderNumber);

				listOrders.add(infoBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, pstmt, conn);
		}
		return listOrders;
	}

	// SELECT info_jsdz,info_shjg FROM bdf2_shinfo WHERE info_ksdz = '盛世';
	public int saveOrder(OrderInfoBean orderInfoBean) {
		int result = 0;
		Connection conn = JdbcUtils.getConn();
		String sql = "INSERT INTO `shyl`.`bdf2_order` "
				+ "( `order_no`, `order_com`, `order_ksdz`, `order_jsdz`, `order_shjg`,"
				+ " `order_shrq`, `order_shsjd`, `order_scry`, `order_scsj`, `order_desc`,"
				+ "`order_updown`,`order_havenumber`,`order_number`) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, orderInfoBean.getOrderNo());
			pstmt.setString(2, orderInfoBean.getCom());
			pstmt.setString(3, orderInfoBean.getStart());
			pstmt.setString(4, orderInfoBean.getEnd());
			pstmt.setString(5, orderInfoBean.getPrice());
			pstmt.setString(6, orderInfoBean.getShrq());
			pstmt.setString(7, orderInfoBean.getShsjd());
			pstmt.setString(8, orderInfoBean.getScry());
			pstmt.setString(9, orderInfoBean.getScsj());
			pstmt.setString(10, orderInfoBean.getDesc());
			pstmt.setString(11, orderInfoBean.getUpdown());
			pstmt.setString(12, orderInfoBean.getOrderHavenumber());
			pstmt.setString(13, orderInfoBean.getOrderNumber());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, pstmt, conn);
		}
		return result;
	}

}
