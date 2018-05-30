package com.bm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 教练信息
 *
 */
public class CoachInfo implements Serializable{
    private static final long serialVersionUID = -7819498102619483872L;
    
    /**
     *教练ID
     */
    public String coachId;
    public String userid;
    public String friendUserId;
    public String nickname;
    public String userId;
    /**
     *教练名称
     */
    public String coachName;
    /**
     * 
     * 教练年龄
     * 
     */
    public String coachAge;
    
    
    /**
     * 教练的评价
     * 
     */
    public String coachLogistics;
    
    /**
     * 
     * 教练简介
     */
    public String coachProfile;
    
    /**
     *教练头像
     */
    public String avatar;
    
    /**
     * 余额
     */
    public String account;
    
    public String areaName;//区域名称
    public String provinceName;//省
    /**
     * 城市
     */
    public String regionName;
    
    /**
     * 性别
     */
    public String gender;
    
    public String birthday;
    
    public String idCard;
    
    public String phone;
    
    public String address;
    
    public String education;
    
    public String graduateInstitutions;
    
    /**
     * 资质证书
     */
   
    public List<CoachDiploma>  coachDiploma;
    /**
     * 0:非好友关系  1:好友关系 3已申请
     */
    public String friStatus;
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
    public ArrayList<CoachInfo> groupUserList;
    
    public String picTag="0";//图片标记位
    
}
