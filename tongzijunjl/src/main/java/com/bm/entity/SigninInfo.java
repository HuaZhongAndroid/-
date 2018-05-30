package com.bm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 测试类
 * 
 * @author wangqiang
 *
 */
public class SigninInfo implements Serializable{

	public String coachId = "";

	public String coachName;

	public String signTime = "";
	public String signDate="";
	
	/**
	 * 签到状态   0 未签到  1 正常 2 迟到
	 */
	public String signStatus ;

	/**
	 * 用户id（宝贝id）
	 */
	public String userId;
	
	public String babyId;
	
	/**
	 * 宝贝昵称
	 */
	public String babyName;
	
	/**
	 * 头像
	 */
	public String avatar;
	
	/**
	 * 宝贝年龄
	 */
	public String babyAge;
	
	/**
	 * 参与（宝贝人数）
	 */
	public String signAllCount;
	
	/**
	 * 已签到数
	 */
	public String signCount;
	
	/**
	 * 未签到数
	 */
	public String unsignCount;
	
	/**
	 * 参加儿童
	 */
	public List<SigninInfo> signUserList;
	
	//评价
	/**
	 * 评价状态   0 未通过  1 已通过   2未评分
	 */
	public String passStatus;
	
	/**
	 * 宝贝名称
	 */
	public String realName;
	
	/**
	 * 课程名称
	 */
	public String goodsId;
	
	/**
	 * 课程名称
	 */
	public String goodsName;
	
	public String parentPhone;
}
