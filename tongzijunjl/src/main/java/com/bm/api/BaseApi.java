package com.bm.api;


/**
 * Service基类<br />
 * 存放 API接口地址等
 * 
 * http://112.64.173.178/huafa/app/userUnRead/getAllUnreadCount.do
 */
public class BaseApi {
	

	public static final String API_HOST = "http://v250.tzj.softlst.com:8888";//正式服务器
//	public static final String API_HOST = "http://59.110.62.10:8888";//测试服务器
	//public static final String API_HOST = "http://192.168.0.107:8888";//测试服务器
//	public static final String API_HOST = "http://47.93.84.236:8888";//测试服务器

	public static String API_URL_PRE = API_HOST + "/tongZiJun/api/";
	
	//测试切换服务器用
	public static final String API_HOST1 = "http://121.40.201.137:8080";//阿里云服务器
	public static final String API_HOST2 = "http://101.201.149.186:8888";//测试服务器
	
	/** 登录接口 */
	public static final String API_USER_LOGIN =  "member/login.do";
	
	public static final String API_GETALLUNREADCOUNT =  "userUnRead/getAllUnreadCount.do";

	/**---------IM--------*/
	/**根据用户名称或手机号码查找聊天用户*/
	public static final String API_IM_SEARCHUSERLIST= "im/searchUserList.do";
	/**批量获取用户头像和昵称*/
	public static final String API_IM_USERINFOLIST= "im/getImUserInfoList.do";
	/**添加一个群*/
	public static final String API_IM_ADDGROUP= "im/addGroup.do";
	/**解散一个群*/
	public static final String API_IM_REMOVEGROUP= "im/removeGroup.do";
	/**修改一个群*/
	public static final String API_IM_UPDATEGROUPINFO= "im/updataGroupInfo.do";
	/**查看一个群信息*/
	public static final String API_IM_GROUPINFO= "im/getGroupInfo.do";
	/**搜索群*/
	public static final String API_IM_SEARCHGROUPLIST= "im/searchGroupList.do";
	/**邀请他人入群*/
	public static final String API_IM_ADDUSERLISTTOGROUP= "im/addUserListToGroup.do";
	/**退群批量*/
	public static final String API_IM_REMOVEUSERLIST= "iim/removeUserList.do";
	/**退群单人*/
	public static final String API_IM_REMOVEUSER= "im/removeUser.do";
	/**查询群成员列表*/
	public static final String API_IM_FINDGROUPUSERLIST= "im/findGroupUserList.do";
	/**群主移交群*/
	public static final String API_IM_TRANSFERGROUP= "im/transferGroup.do";
	/**查找用户的群列表*/
	public static final String API_IM_FINDUSERGROUPLIST= "im/findUserGroupList.do";
	
	/**通讯录*/
	public static final String API_IM_FINDADDRESSLIST= "im/findaddressList.do";
	
	/**
	 *验证登录信息
	 *
	 */
	public static final String API_TZJCOACH_SEARCHCOACHINFO= "tzjcoach/searchCoachInfo.do";
	/**
	 *
	 *报备-家长列表        
	 */
	public static final String API_TZJBAOB_SEARCHPARENTINFO= "casUser/getFollowupList.do";
	/**
	 *
	 *报备-新增        
	 */
	public static final String API_TZJuserFollowup= "userFollowup/add.do";
	/**
	 *
	 *报备-记录列表        
	 */
	public static final String API_TZJuserFollowuplist= "userFollowup/list.do";
	/**
	 *
	 *获取验证码
	 */
	public static final String API_TZJCAS_SENDCODE="tzjcas/sendcode.do";
	
	/**
	 *
	 *重置密码
	 */
	public static final String API_TZJCAS_UPDATEPASS= "tzjcas/updatepass.do";
	/**
	 *
	 *验证手机号和验证码
	 */
	public static final String API_TZJCAS_CHECKCODE= "tzjcas/checkcode.do";
	/**
	 *
	 *教练注册   
	 */
	public static final String API_TZJCOACH_REGISTERCOACH= "tzjcoach/registerCoach.do";
	
	/**
	 * 更新教练信息
	 */
	public static final String API_TZJCOACH_UPDATECOACH= "tzjcoach/updateUserCoachInfo.do";
	
	/**
	 *
	 *热门城市
	 */
	public static final String API_TZJTREND_HOTREGION="tzjtrend/hotregion.do";
	/**
	 *
	 *查询城市
	 */
	public static final String API_TZJTREND_TRENDREGION="tzjtrend/trendregion.do";
	/**
	 *
	 *查询最近访问城市
	 */
	public static final String API_GET_LASTCITY="tzjcas/getlastcity.do";
	/**
	 *
	 *更新最近访问城市
	 */
	public static final String API_UPDATE_LASTCITY="tzjcas/updatelastcity.do";
	
	/**
	 *
	 *查询课程
	 */
	public static final String API_TZJGOODS_GOODSCOURSEINFO= "tzjgoods/goodscourseinfoforcoach.do";
	/**
	 *查询签到页面的信息
	 *
	 */
	public static final String API_TZJCOACH_SEARCHSIGNINFO= "tzjcoach/searchSignInfo.do";
	/**
	 *签到
	 *
	 */
	public static final String API_TZJCOACH_INSERTSIGNINFO= "tzjcoach/insertSignInfo.do";
	/**
	 *
	 *查询课程  抢课
	 */
	public static final String API_TZJGOODS_SEARCHGOODSCOURSEINFOFORCOACH= "tzjgoods/searchGoodsCourseInfoForCoach.do";
	/**
	 *
	 *查询课程详细
	 */
	public static final String API_TZJGOODS_SEARCHGOODSCOURSEINFODETAILFORCOACH= "tzjgoods/searchGoodsCourseInfoDetailForCoach.do";
	/**
	 *
	 *查询教练信息
	 */
	public static final String API_TZJCOACH_SEARCHCOACHUSERINFO2= "tzjcoach/searchCoachUserInfo2.do";
	/**
	 *
	 *立即约课
	 */
	public static final String API_TZJCOACH_ROBGOODS= "tzjcoach/robgoods.do";
	
	
	/**
	 *
	 *建群
	 */
	public static final String API_IM_ADDGROUPINFO="im/addGroupInfo.do";
	/**
	 *取消抢课课程
	 *
	 */
	public static final String API_TZJCOACH_DELGOODS="tzjcoach/delgoods.do";
	/**
	 *
	 *查询勋章
	 */
	public static final String API_TZJCOACH_GOODSMEDALLIST="tzjmedal/goodsMedallist.do";
	
	
	/**
	 *评价信息查询
	 *
	 */
	public static final String API_TZJGOODS_PASSL="tzjgoods/passList.do";
	/**
	 *
	 *已评分未评分数量
	 */
	public static final String API_TZJGOODS_PASSCO="tzjgoods/passCount.do";
	
	
	/**
	 *
	 *添加评论
	 */
	public static final String API_TZJCOACH_ADDPASS="tzjcoach/addPass";
	
	
	/**
	 * 查看评论1
	 */
	public static final String API_coachCommentByBaby = "tzjcoach/coachCommentByBaby";
	
	/**
	 * 查看评价2
	 */
	
	public static final String API_tzjcomment = "tzjcomment/findContent.do";
	
	/**
	 *
	 *
	 */
//	public static final String API_TZJCOACH_SEARCHCOACHUSERINFO2="tzjcoach/searchCoachUserInfo2.do";
	/**
	 *
	 *
	 */
//	public static final String API_TZJCOACH_REGISTERCOACH="tzjcoach/registerCoach.do";
	/**
	 *
	 *
	 */
	public static final String API_TZJORDER_COACHORDERLIST="tzjorder/coachBalancelist.do";
	/**
	 *
	 *
	 */
	public static final String API_BANK_BANKCOACHLIST="bank/bankCoachlist.do";
	/**
	 *
	 *
	 */
	public static final String API_BANK_ADDBANK="bank/addBank.do";
	/**
	 *
	 *
	 */
	public static final String API_BANK_DELBANK="bank/delBank.do";
	/**
	 *提现
	 *
	 */
	public static final String API_BANK_ADDDEDUCT="bank/addDeduct";
	/**
	 *
	 *更新密码接口
	 */
	public static final String API_TZJACCOUNT_UPDATEPASSWORD="tzjcas/updatepassword.do";
	/**
	 *
	 *意见反馈
	 */
	public static final String API_ADD_FEEDBACK="myself/addFeedBack.do";
	/**
	 *
	 *消息查询
	 */
	public static final String API_TZJMESSAGE_MESSAGELIST="tzjmessage/messagelist.do";
	/**
	 *
	 *消息详情
	 */
	public static final String API_TZJMESSAGE_MESSAGEDETAIL="tzjmessage/messagedetail.do";
	

	/**
	 *
	 *查询教练是否签到
	 */
	public static final String API_TZJCOACH_ISSINGSTATUS="tzjcoach/issignstatus.do";
	
	/**
	 *
	 *查询省市区信息
	 */
	public static final String API_TZJTREND_SEARCHTRENDREGIONALL="tzjtrend/searchtrendregionall.do";
	
	/**
	 *
	 *版本更新
	 */
	public static final String API_TZJVERSION_SEARCHVERSION="tzjversion/searchversion.do";
	
	/**
	 *
	 *注册协议 帮助 关于软件
	 */
	public static final String API_TZJRICH_RICHTEXT="rich/richText";
	
	
	/**
	 * im start
	 */
	/**
	 * 好友-我的好友
	 */
	public static final String API_SEARCH_FRIENDLIST="im/searchFriendList.do";
	/**
	 * 好友-我的群
	 */
	public static final String API_SEARCH_GROUPLIST="im/searchGroupList.do";
	/**
	 * 查询朋友
	 */
	public static final String API_SEARCH_USERFRIENDLIST="im/searchUserFriendList.do";
	/**
	 *添加新朋友-找朋友
	 */
	public static final String API_SEARCH_USERLIST="im/searchUserList.do";
	/**
	 *添加新朋友-我的新朋友
	 */
	public static final String API_SEARCH_STATUSLIST="im/searchStatusList.do";
	/**
	 *添加好友
	 */
	public static final String API_ADD_FRIEND="im/addFriend.do";
	/**
	 *群信息
	 */
	public static final String API_SEARCH_GROUPDETAIL="im/searchGroupDetail.do";
	
	/**
	 *批量获取用户头像和昵称
	 */
	public static final String API_GETIMUSERINFOLIST =  "im/getImUserInfoList.do";

	/**
	 *建群
	 */
	public static final String API_IM_ADDGTOUPUINFO =  "im/addGroupInfo.do";
	/**
	 *修改群信息
	 */
	public static final String API_IM_UPDATEGROUP =  "im/updateGroup.do";
	
	/**
	 * 数据字典
	 */
	public static final String API_dictionary_list =  "dictionary/list";
}
