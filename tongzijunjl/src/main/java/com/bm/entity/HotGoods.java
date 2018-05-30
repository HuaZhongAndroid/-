package com.bm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐课程
 * @author jiangsh
 *
 */
public class HotGoods implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2954270580503532849L;
	/**
	 * 课程id
	 */
	public String goodsId;
	public String regionId;
	/**
	 * 课程名称
	 */
	public String goodsName;
	/**
	 * 课程图片 
	 */
	public String titleMultiUrl;
	
	/**
	 * 店面id
	 */
	public String storeId;
	/**
	 * 店面名称
	 */
	public String storeName;
	/**
	 * 店面地址/上课地址
	 */
	public String address;
	/**
	 * 价格
	 */
	public String goodsPrice;
	/**
	 * 类型
	 */
	public String goodsType;
	/**
	 * goodsQuota    总人数/名额
	 *orderNum    已报名人数
	 */
	public String goodsQuota;
	public String orderNum;
	/**
	 * 适合年龄段
	 */
	public String suitableAge;
	/**
	 * 上课开始时间
	 */
	public String startTime;
	/**
	 * 上课结束时间
	 */
	public String endTime;
	/**
	 * 宝贝用户id 	
	 */
	public String babyUserid;
	/**
	 * 宝贝名称
	 */
	public String babyName;
	public String babyId;
	/**
	 * 宝贝图片
	 */
	public String babyAvatar;
	/**
	 * 教练id
	 */
	public String coachId;
	/**
	 *教练名字
	 */
	public String coachName;
	/**
	 * 教练头像
	 */
	public String coachAvatar;
	/**
	 * 教练年龄
	 */
	public String coachAge;
	/**
	 * 教练评价0-5 星级
	 */
	public String coachLogistics;
	/**
	 * 课程要点
	 */
	public String coursePoints;
	/**
	 * 注意事项
	 */
	public String notice;
	/**
	 * 已报名宝贝数
	 */
	public String babyCount;
	/**
	 * 是否已买过0 未买  1 已买   如果户外和暑期大露营需要
	 */
	public String isBought;
	
	/**
	 * 群状态   1 已建群 0 未建群
	 */
	public String groupStatus;
	
	public String typeName;
	
	/**
	 * 是否约课的状态  0没有约课 1 已约课
	 */
	public String isClass;
	
	/**
	 * 课程状态  0 所有  1 未开始  2 进行中  3 已结束
	 */
	public String classStatus;
	
	/**
	 * 距离
	 */
	public String distances;
	
	/**
	 * 已报名宝贝信息
	 */
	public List<HotGoods> baby;
	public String userId;
	
	
	public String lon;
	public String lat;	
	
	
//	public String groupStatus;
}
