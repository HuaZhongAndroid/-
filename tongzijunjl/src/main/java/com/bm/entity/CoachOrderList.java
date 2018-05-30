package com.bm.entity;

import java.io.Serializable;

public class CoachOrderList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6600825058518855380L;
	/**
	 * 订单id
	 */
	public String orderId;
	/**
	 * 商品名称
	 */
	public String goodsName;
	/**
	 * 消费时间
	 */
	public String cDate;
	/**
	 * 消费金额
	 */
	public String paymentAccount;
	/**
	 * 账户余额
	 */
	public String account;
	/**
	 * 状态
	 */
	public String changeStatus;
	 
}
