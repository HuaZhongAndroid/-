package com.bm.entity;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 用户数据
 *
 */
public class User implements Serializable{
    private static final long serialVersionUID = -7819498102619483872L;
    
    public String topImage;
    public String userId;
    /**
     * 
     * 用户id
     * 
     */
    public String userid;
    
    /**
     * 
     * 用户姓名
     * 
     */
    public String username;
    
    /**
     * 
     * 手机号
     * 
     */
    public String phone;
    
    
    /**
     * 密码
     * 
     */
    public String password;
    
    /**
     * 
     * 昵称
     */
    public String nickname;
    
    
    /**
     * 性别
     * 
     */
    public String gender;
    /**
     * 城市
     */
    public String regionName;
    /**
     *地址
     */
    public String address;
    /**
     *宝贝姓名
     */
    public String babyName;
    /**
     *宝贝性别
     */
    public String babyGender;
    /**
     *宝贝出生日期
     */
    public String birthday;
    /**
     *教练ID
     */
    public String coachId;
    /**
     *教练名称
     */
    public String coachName;
    /**
     *账户余额
     */
    public String account;
    /**
     *是否有支付密码
     */
    public String paymentPassword;
    /**
     *荣誉榜
     */
    public String honourSort;
    /**
     *已获勋章
     */
    public String medalNum;
    /**
     *用户头像
     */
    public String avatar;
    /**
     *宝贝年龄
     */
    public String babyAge;
    /**
     *用户积分
     */
    public String score;
    
    public String signDate;//签到时间
    public String signStatus;//签到状态     0 未签到  1 正常 2 迟到
    /**
     * 0:非好友关系  1:好友关系
     */
    public String friStatus;
    
    public String friendUserId;
    
    /**
     * 群信息
     */
    /**
     * 群Id
     */
    public String groupId;
    /**
     * 群头像
     */
    public String groupPic;
    /**
     * 群昵称
     */
    public String groupName;
    /**
     * 群英会数量
     */
    public String userCount;
    /**
     * 群用户
     */
    public ArrayList<User> groupUserList;
}
