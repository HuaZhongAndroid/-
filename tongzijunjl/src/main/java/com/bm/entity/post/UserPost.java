package com.bm.entity.post;

import com.bm.base.BasePostEntity;


/**
 * 
 * 提交用户信息
 * @author wangqiang
 *
 */
public class UserPost extends BasePostEntity{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String coachName;
	public String coachId;
	public String birthday;
	public String idCard;//身份证
	public String gender;//教练性别
	public String phone;
	
	public String education;
	public String address;//联系地址
	public String graduateInstitutions;//毕业学校
	public String coachIntro;//教练介绍
	

	public String regionName;//城市名称
	public String areaName;//区域名称
	public String provinceName;//省
	public String password;
	public String cityName;
	public String sendPhone;
	
	
	
}
