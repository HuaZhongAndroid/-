package com.bm.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.bm.entity.Banks;
import com.bm.entity.BaobeiUserinfo;
import com.bm.entity.CoachComment;
import com.bm.entity.CoachInfo;
import com.bm.entity.CoachOrderList;
import com.bm.entity.Dictionary;
import com.bm.entity.Disclaimer;
import com.bm.entity.EvaluateContent;
import com.bm.entity.HotGoods;
import com.bm.entity.MessageDetail;
import com.bm.entity.MessageList;
import com.bm.entity.Model;
import com.bm.entity.Province;
import com.bm.entity.SigninInfo;
import com.bm.entity.User;
import com.bm.entity.Version;
import com.bm.entity.post.UserPost;
import com.bm.im.entity.BaobeiFollowupEntity;
import com.bm.im.entity.BaobeijiluEntity;
import com.bm.tzj.city.AllCity;
import com.bm.tzj.city.City;
import com.lib.http.AsyncHttpHelp;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;
import com.lib.http.result.StringResult;


/**
 * UserService提供 用户类的 接口数据获取等功能
 * 驼峰命名  以Manager结尾
 *
 */
public class UserManager extends BaseApi{
	static final String TAG = UserManager.class.getSimpleName();
	private static UserManager mInstance;

	public static synchronized UserManager getInstance(){
		if(mInstance == null){
			mInstance = new UserManager();
		}
		return mInstance;
	}


	/**
	 *
	 *验证登录信息
	 */
	 public void getTzjcoachSearchCoachInfo(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<CoachInfo>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_SEARCHCOACHINFO, map, callback);;
	 }
	 /**
	  *
	  *获取验证码
	  */
	 public void getTzjcasSendcode(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCAS_SENDCODE, map, callback);;
	 }

	 /**
	  *重置密码
	  *
	  */
	 public void getTzjcasUpdatepass(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCAS_UPDATEPASS, map, callback);;
	 }
	 /**
	  *
	  *验证手机号和验证码
	  */
	 public void getTzjcasCheckcode(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCAS_CHECKCODE, map, callback);;
	 }
	 /**
	  *教练注册
	  *
	  */
	 public void getTzjcoachRegisterCoach(Context context,String imagePath,UserPost uPost,final ServiceCallback<CommonResult<CoachInfo>> callback) {
		 List<File> files = new ArrayList<File>();
		 if (!TextUtils.isEmpty(imagePath)) {
			 files.add(new File(imagePath));
		 }
		 AsyncHttpHelp.getInstance().httpPost(context, API_TZJCOACH_REGISTERCOACH, uPost.toMap(), "avatar", files, callback);
	 }

	 /**
	  *更新教练信息
	  *
	  */
	 public void getTzjcoachUpdateCoach(Context context,String imagePath,UserPost uPost,final ServiceCallback<CommonResult<CoachInfo>> callback) {
		 List<File> files = new ArrayList<File>();
		 if (!TextUtils.isEmpty(imagePath)) {
			 files.add(new File(imagePath));
		 }
		 AsyncHttpHelp.getInstance().httpPost(context, API_TZJCOACH_UPDATECOACH, uPost.toMap(), "avatar", files, callback);
	 }

	 /**
	  *热门城市
	  *
	  */
	 public void getTzjtrendHotregion(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<City>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJTREND_HOTREGION, map, callback);;
	 }
	 /**
	  *
	  *查询城市
	  */
	 public void getTzjtrendTrendregion(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<AllCity>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJTREND_TRENDREGION, map, callback);;
	 }
	 /**
	  *
	  *查询最近访问城市
	  */
	 public void getLastCity(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_GET_LASTCITY, map, callback);;
	 }
	 /**
	  *
	  *更新最近访问城市
	  */
	 public void updateLastCity(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_UPDATE_LASTCITY, map, callback);;
	 }
	 /**
	  *
	  *查询课程
	  */
	 public void getTzjgoodsGoodscourseinfo(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<HotGoods>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJGOODS_GOODSCOURSEINFO, map, callback);;
	 }
	 /**
	  *查询签到页面的信息
	  *
	  */
	 public void getTzjcoachSearchSignInfo(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<SigninInfo>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_SEARCHSIGNINFO, map, callback);;
	 }
	 /**
	  *
	  *签到
	  */
	 public void getTzjcoachInsertSignInfo(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<User>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_INSERTSIGNINFO, map, callback);;
	 }
	 /**
	  *查询课程  抢课
	  *
	  */
	 public void getTzjgoodsSearchGoodsCourseInfoForCoach(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<HotGoods>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJGOODS_SEARCHGOODSCOURSEINFOFORCOACH, map, callback);;
	 }
	 /**
	  *
	  *查询课程详细
	  */
	 public void getTzjgoodsSearchGoodsCourseInfoDetailForCoach(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<HotGoods>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJGOODS_SEARCHGOODSCOURSEINFODETAILFORCOACH, map, callback);;
	 }
	 /**
	  *查询教练信息
	  *
	  */
	 public void getTzjcoachSearchCoachUserInfo2(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<CoachInfo>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_SEARCHCOACHUSERINFO2, map, callback);;
	 }
	 /**
	  *
	  *立即约课
	  */
	 public void getTzjcoachRobgoods(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_ROBGOODS, map, callback);;
	 }

	 /**
	  *
	  *
	  */
	 public void getImAddGroupInfo(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<CoachInfo>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_IM_ADDGROUPINFO, map, callback);;
	 }
	 /**
	  *取消课程
	  *
	  */
	 public void getTzjcoachDelgoods(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_DELGOODS, map, callback);;
	 }
	 /**
	  *
	  *查询勋章
	  */
	 public void getTzjcoachGoodsMedallist(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<Model>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_GOODSMEDALLIST, map, callback);;
	 }

	 /**
	  *评价信息查询
	  *
	  */
	 public void getTzjgoodsPassL(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<SigninInfo>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJGOODS_PASSL, map, callback);;
	 }
	 /**
	  *
	  *已评分未评分数量
	  */
	 public void getTzjgoodsPassCo(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<Model>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJGOODS_PASSCO, map, callback);;
	 }

	 /**
	  *
	  *添加评论
	  */
	 public void getTzjcoachAddPass(Context context,List<String> imagePath,HashMap<String, String> map,final ServiceCallback<CommonResult<String>> callback) {
		 
		 List<File> files = new ArrayList<File>();
			for (int i = 0; i < imagePath.size(); i++) {
				if (!TextUtils.isEmpty(imagePath.get(i))) {
					files.add(new File(imagePath.get(i)));
				}
			}
		 
		 AsyncHttpHelp.getInstance().httpPost(context, API_TZJCOACH_ADDPASS, map, "file", files, callback);
	 }



	 /**
	  *
	  *
	  */
	 //  public void getTzjgoodsSearchGoodsCourseInfoForCoach(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<Game>> callback) {
	 //      AsyncHttpHelp.getInstance().httpGet(context,API_TZJGOODS_SEARCHGOODSCOURSEINFOFORCOACH, map, callback);;
	 //  }
	 /**
	  *
	  *
	  */
	 //  public void getTzjcoachSearchCoachUserInfo2(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<Game>> callback) {
	 //      AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_SEARCHCOACHUSERINFO2, map, callback);;
	 //  }
	 /**
	  *
	  *
	  */
	 //  public void getTzjcoachRegisterCoach(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<Game>> callback) {
	 //      AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_REGISTERCOACH, map, callback);;
	 //  }
	 /**
	  *
	  *
	  */
	 public void getTzjorderCoachOrderlist(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<CoachOrderList>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJORDER_COACHORDERLIST, map, callback);;
	 }
	 /**
	  *
	  *
	  */
	 public void getBankBankCoachlist(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<Banks>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_BANK_BANKCOACHLIST, map, callback);;
	 }
	 /**
	  *
	  *
	  */
	 public void getBankAddBank(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_BANK_ADDBANK, map, callback);;
	 }
	 /**
	  *
	  *
	  */
	 public void getBankDelBank(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_BANK_DELBANK, map, callback);;
	 }
	 /**
	  *提现
	  *
	  */
	 public void addDeduct(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_BANK_ADDDEDUCT, map, callback);;
	 }

	 /**
	  *
	  *更新密码接口
	  */
	 public void getTzjaccountUpdatePassword(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJACCOUNT_UPDATEPASSWORD, map, callback);;
	 }
	 /**
	  *
	  *意见反馈
	  */
	 public void addFeedBack(Context context,HashMap<String, String> map,final ServiceCallback<StringResult> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_ADD_FEEDBACK, map, callback);;
	 }
	 /**
	  *
	  *消息查询
	  */
	 public void getTzjmessageMessagelist(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<MessageList>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJMESSAGE_MESSAGELIST, map, callback);;
	 }
	 /**
	  *消息详情
	  *
	  */
	 public void getTzjmessageMessagedetail(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<MessageDetail>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJMESSAGE_MESSAGEDETAIL, map, callback);;
	 }

	 /**
	  *查询教练是否签到过
	  *
	  */
	 public void getTzjcoachIsSingstates(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<User>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJCOACH_ISSINGSTATUS, map, callback);;
	 }

	 /**
	  *
	  *查询省市区信息
	  */
	 public void getTzjtrendAllSearchregion(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<Province>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJTREND_SEARCHTRENDREGIONALL, map, callback);;
	 }

	 /**
	  *
	  *检查版本更新
	  */
	 public void getTzjversionSearchVerson(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<Version>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJVERSION_SEARCHVERSION, map, callback);;
	 } 

	 /**
	  *
	  *注册协议 帮助 关于软件
	  */
	 public void getTzjrichRichText(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<Disclaimer>> callback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJRICH_RICHTEXT, map, callback);;
	 } 

	 /**
	  * 获取评论1
	  */
	 public void getCoachCommentByBaby(Context context,HashMap<String, String> map,final ServiceCallback<CommonResult<CoachComment>> serviceCallback){
		 AsyncHttpHelp.getInstance().httpGet(context,API_coachCommentByBaby, map, serviceCallback);;
	 }
	 /**
	  * 获取评论2
	  */
	 public void getCommentByUser(Context context,HashMap<String, String> map,final ServiceCallback<CommonListResult<EvaluateContent>> serviceCallback){
		 AsyncHttpHelp.getInstance().httpGet(context,API_tzjcomment, map, serviceCallback);;
	 }

	 /**
	  *
	  *报备-家长列表
	  */
	public void getTzjBaobeiParentInfo(Context context,
			HashMap<String, String> map,
			ServiceCallback<CommonListResult<BaobeiUserinfo>> serviceCallback) {
		 AsyncHttpHelp.getInstance().httpGet(context,API_TZJBAOB_SEARCHPARENTINFO, map, serviceCallback);;
		
	}
	/**
	  *
	  *报备-新增
	  */

	public void getTzjuserFollowup(
			Context context,
			HashMap<String, String> map,
			ServiceCallback<CommonResult<BaobeiFollowupEntity>> serviceCallback) {
		AsyncHttpHelp.getInstance().httpGet(context,API_TZJuserFollowup, map, serviceCallback);;
		
	}
	/**
	  *
	  *报备-记录列表
	  */

	public void getTzjBaobeiuserlist(Context context,
			HashMap<String, String> map,
			ServiceCallback<CommonListResult<BaobeijiluEntity>> serviceCallback) {
		AsyncHttpHelp.getInstance().httpGet(context,API_TZJuserFollowuplist, map, serviceCallback);;
		
	}
	
	

	 /**
	  * 获取城市列表
	  * @param post
	  * @param callback
	  */
	 //	public void getCities(GetCityPost post, final ServiceCallback<GetCityResult> callback){
	 //		get(API_GET_HOME_CITY, post, callback);
	 //	}
	 //	
	 //	public void getCities(final ServiceCallback<GetCityResult> callback){
	 //		get(API_GET_HOME_CITY, new GetCityPost(), callback);
	 //	}
	 /**
	  * 获取版本信息
	  * @param callback
	  *//*
	public void getVersionInfo(final ServiceCallback<CommonResult<VersionInfo>> callback){
		get(API_VERSION_INFO, new BasePostEntity(), callback);
	}

	   *//**
	   * 获取验证码
	   * @param post
	   * @param callback
	   *//*
	public void getAuthCode(GetAuthCodePost post, final ServiceCallback<StringResult> callback){
		get(API_GET_AUTH_CODE, post, callback);
	}

	    *//**
	    * 设置默认收货地址
	    * @param post
	    * @param callback
	    *//*
	public void setDefaultAddress(SetDefaultAddressPost post, final ServiceCallback<BaseResult> callback){
		get(API_SET_DEFAULT_ADDRESS, post, callback);
	}

	     *//**
	     * 用户登录
	     * @param loginPost 用户登陆提交的数据
	     * @param callback
	     *//*
	public void login(LoginPost loginPost, final ServiceCallback<CommonResult<User>> callback){
		get(API_USER_LOGIN, loginPost, callback);
	}

	      *//**
	      * 获取用户信息
	      * @param post
	      * @param callback
	      *//*
	      */




}
