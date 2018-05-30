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
public class Model implements Serializable{

	public String imageUrl = "";

	public String name;

	public String propertyOne = "";
	public String states = "";

	public String degree;
	public String time;
	public String address;
	public String money;
	public String zanName;
	public String content;
	
	public List<Model> mInfo;
	public boolean isSelected;
	public String comment;
	
	public int strImageUrl;
	
	/**
	 * 已评分数
	 */
	public String passCount;
	/**
	 * 未评分数
	 */
	public String unPassCount;
	
	public String medalId;//勋章ID
	
	public String medalName;//勋章名称

}
