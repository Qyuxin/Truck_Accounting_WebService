package com.xin.dao;

import java.util.List;

import com.xin.bean.UpdownAndPriceBean;
import com.xin.bean.OrderConditionBean;
import com.xin.bean.OrderInfoBean;

public interface ServiceDao {
	/**
	 * 
	 * @param name
	 * @param pwd
	 * @return 	0 --> 账号不存在
	 * 			1 --> 登陆成功
	 * 			-1 --> 密码错误
	 */
	public int userLogin(String username,String pwd);

	public List<String> getComs();
	
	public List<String> getStartByCom(String comName);

	public List<String> getEndsByStart(String startPoint);

	public List<UpdownAndPriceBean> getUpdownAndPriceByStartAndEnd(String startPoint,String endPoint);
	
	public int saveOrder(OrderInfoBean orderInfoBean);

	public List<OrderInfoBean> getOrders(OrderConditionBean orderConditionBean);
}
