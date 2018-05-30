package com.bm.entity;

import java.io.Serializable;

public class Banks implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 529088341724640916L;
	/**
	 * 银行卡id
	 */
	public String bankCardId;
	/**
	 * 银行卡名称
	 */
	public String bankType;
	/**
	 * 银行卡号
	 */
	public String bankCardNo;

	/**
	 * 账户余额
	 */
	public String account;
	public String bankUserName;
}
