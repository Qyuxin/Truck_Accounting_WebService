package com.xin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xin.bean.ShInfoBean;
import com.xin.bean.UpdownAndPriceBean;
import com.xin.bean.OrderConditionBean;
import com.xin.bean.OrderInfoBean;
import com.xin.dao.ServiceDao;
import com.xin.utils.DateUtils;
import com.xin.utils.JdbcUtils;
import com.xin.utils.StringUtils;

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
		sql.append(" AND order_isdel = '0' ");//未被删除的订单
		if (!StringUtils.isNullOrEmpty(bean.getOrderNo())) {
			sql.append(" AND order_no = '").append(bean.getOrderNo())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getCom())) {
			if (bean.getCom().equals("other")) {
				sql.append(" AND order_com != '盛世' AND order_com != '华光' ");
			} else {
				sql.append(" AND order_com = '").append(bean.getCom())
						.append("' ");
			}
		}
		if (!StringUtils.isNullOrEmpty(bean.getDesc())) {
			sql.append(" AND order_desc = '").append(bean.getDesc())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getEnd())) {
			sql.append(" AND order_jsdz = '").append(bean.getEnd())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getOrderNumber())) {
			sql.append(" AND order_number = '").append(bean.getOrderNumber())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getPrice())) {
			sql.append(" AND order_shjg = '").append(bean.getPrice())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getScry())) {
			sql.append(" AND order_scry = '").append(bean.getScry())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getStart())) {
			sql.append(" AND order_ksdz = '").append(bean.getStart())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getUpdown())) {
			sql.append(" AND order_updown = '").append(bean.getUpdown())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getShrqStart())) {
			sql.append(" AND order_shrq >= '").append(bean.getShrqStart())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getShrqEnd())) {
			sql.append(" AND order_shrq <= '").append(bean.getShrqEnd())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getScsjStart())) {
			sql.append(" AND order_scsj >= '").append(bean.getScsjStart())
					.append("' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getScsjEnd())) {
			sql.append(" AND order_scsj <= '").append(bean.getScsjEnd())
					.append("235959' ");
		}
		if (!StringUtils.isNullOrEmpty(bean.getOrderHavenumber())) {
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
	public int addOrder(OrderInfoBean orderInfoBean) {
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

	public int updateOrder(OrderInfoBean orderInfoBean) {
		int result = 0;
		Connection conn = JdbcUtils.getConn();
		String sql = "UPDATE `shyl`.`bdf2_order` "
				+ "SET  `order_com`=?, `order_ksdz`=?, "
				+ "`order_jsdz`=?, `order_shjg`=?, `order_shrq`=?, `order_shsjd`=?, "
				+ "`order_updateuser`=?, `order_updatetime`=?, `order_desc`=?, `order_updown`=?, "
				+ "`order_havenumber`=?, `order_number`=? "
				+ "WHERE (`order_no`=?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, orderInfoBean.getCom());
			pstmt.setString(2, orderInfoBean.getStart());
			pstmt.setString(3, orderInfoBean.getEnd());
			pstmt.setString(4, orderInfoBean.getPrice());
			pstmt.setString(5, orderInfoBean.getShrq());
			pstmt.setString(6, orderInfoBean.getShsjd());
			pstmt.setString(7, orderInfoBean.getScry());
			pstmt.setString(8, orderInfoBean.getScsj());
			pstmt.setString(9, orderInfoBean.getDesc());
			pstmt.setString(10, orderInfoBean.getUpdown());
			pstmt.setString(11, orderInfoBean.getOrderHavenumber());
			pstmt.setString(12, orderInfoBean.getOrderNumber());

			pstmt.setString(13, orderInfoBean.getOrderNo());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, pstmt, conn);
		}
		return result;
	}

	// delete from shyl.bdf2_order where order_no = '' ;
	public int deleteOrder(String user, String orderNo) {
		int result = 0;
		Connection conn = JdbcUtils.getConn();
		String sql = "update shyl.bdf2_order set order_updatetime = ? , order_updateuser = ? , order_isdel = '1' where order_no = ? ";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, DateUtils.getStringYMDHMS(DateUtils.getYMDHMS()));
			pstmt.setString(2, user);
			pstmt.setString(3, orderNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, pstmt, conn);
		}
		return result;
	}

	// SELECT info_jsdz,info_shjg FROM bdf2_shinfo WHERE info_ksdz = '盛世';
	public List<ShInfoBean> getShInfos(String comInfo) {
		List<ShInfoBean> listOrders = new ArrayList<ShInfoBean>();
		Connection conn = JdbcUtils.getConn();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM bdf2_shinfo WHERE 1 = 1 ");
		if (!StringUtils.isNullOrEmpty(comInfo)) {
			sql.append(" AND info_gsmc = '").append(comInfo).append("' ");
		}
		sql.append(" order by info_gsmc desc ");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String com = rs.getString("info_gsmc");
				String ksdz = rs.getString("info_ksdz");
				String jsdz = rs.getString("info_jsdz");
				String price = rs.getString("info_shjg");
				String updown = rs.getString("info_updown");
				String haveNumber = rs.getString("info_havenumber");

				ShInfoBean infoBean = new ShInfoBean();
				infoBean.setId(id);
				infoBean.setCom(com);
				infoBean.setStart(ksdz);
				infoBean.setEnd(jsdz);
				infoBean.setPrice(price);
				infoBean.setUpdown(updown);
				infoBean.setHavenumber(haveNumber);

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
	public int addShInfo(ShInfoBean infoBean) {
		int result = 0;
		Connection conn = JdbcUtils.getConn();
		String sql = "INSERT INTO `shyl`.`bdf2_shinfo` "
				+ "(`info_gsmc`, `info_ksdz`, `info_jsdz`, `info_shjg`, `info_updown`, `info_havanumber` ) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, infoBean.getCom());
			pstmt.setString(2, infoBean.getStart());
			pstmt.setString(3, infoBean.getEnd());
			pstmt.setString(4, infoBean.getPrice());
			pstmt.setString(5, infoBean.getUpdown());
			pstmt.setString(6, infoBean.getHavenumber());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, pstmt, conn);
		}
		return result;
	}

	public int updateShInfo(ShInfoBean infoBean) {
		int result = 0;
		Connection conn = JdbcUtils.getConn();
		String sql = "UPDATE `shyl`.`bdf2_shinfo` "
				+ "SET  `info_gsmc`=?, `info_ksdz`=?, `info_jsdz`=?,  "
				+ "`info_shjg`=?, `info_updown`=?, `info_havanumber`= ? "
				+ " WHERE (`id`= ? );";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, infoBean.getCom());
			pstmt.setString(2, infoBean.getStart());
			pstmt.setString(3, infoBean.getEnd());
			pstmt.setString(4, infoBean.getPrice());
			pstmt.setString(5, infoBean.getUpdown());
			pstmt.setString(6, infoBean.getHavenumber());
			pstmt.setString(7, infoBean.getId());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, pstmt, conn);
		}
		return result;
	}

	// delete from shyl.bdf2_order where order_no = '' ;
	public int deleteShInfo(String id) {
		int result = 0;
		Connection conn = JdbcUtils.getConn();
		String sql = "delete from shyl.bdf2_shinfo where order_no = ? '";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, id);
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
