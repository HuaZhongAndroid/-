package com.bm.share;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareUtil {
	public static final String DESCRIPTOR = "com.umeng.share";
	private final UMSocialService mController = UMServiceFactory.getUMSocialService(DESCRIPTOR);
	
	
	
	//配置key
	//新浪的key在  友盟的社会化组建 配置
	
	//微信
	private String wxAppid = "wx2956c52f2e36192a";
	private String wxAppSecret = "d41131df9c9f6debe3f20fcf6820d742";
	
	//qq zone
	private String qZoneAppid = "100424468";
	private String qZoneAppKey = "c7394704798a158208a74ab60104f0ba";
	
	//手机QQ   AppId:100424468  appKey:c7394704798a158208a74ab60104f0ba

	
	
	
	private Activity activity;
	
	public ShareUtil(Activity activity){
		this.activity = activity;
		AuthPlat();
	}
	
	public void AuthPlat(){
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加QQ、QZone平台
		addQQQZonePlatform();

		// 添加微信、微信朋友圈平台
		addWXPlatform();
	}
	
	
	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(activity, wxAppid, wxAppSecret);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, wxAppid, wxAppSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}
	
	
	
	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, qZoneAppid, qZoneAppKey);
		qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
		qqSsoHandler.addToSocialSDK();
		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, qZoneAppid,qZoneAppKey);
		qZoneSsoHandler.addToSocialSDK();
	}
	
	public void shareData(ShareModel share){
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA);

		SinaShareContent sinaContent = new SinaShareContent();
	    QZoneShareContent qZoneContent = new QZoneShareContent();
	    WeiXinShareContent weixinContent = new WeiXinShareContent();
	 // 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		
		if(share.title!=null){
			sinaContent.setTitle(share.title);
			qZoneContent.setTitle(share.title);
			weixinContent.setTitle(share.title);
			circleMedia.setTitle(share.title);
		}
		
		if(share.url!=null){
			sinaContent.setTargetUrl(share.url);
			qZoneContent.setTargetUrl(share.url);
			weixinContent.setTargetUrl(share.url);
			circleMedia.setTargetUrl(share.url);
		}
		
		
		if(share.conent!=null){
			sinaContent.setShareContent(share.conent);
			qZoneContent.setShareContent(share.conent);
			weixinContent.setShareContent(share.conent);
			circleMedia.setShareContent(share.conent);
		}
		if(share.localImg!=0){
			UMImage localImage = new UMImage(activity, share.localImg);
			sinaContent.setShareImage(localImage);
			qZoneContent.setShareImage(localImage);
			weixinContent.setShareImage(localImage);
			circleMedia.setShareImage(localImage);
		}
		
		if(share.urlImg!=null){
			UMImage localImage = new UMImage(activity, share.urlImg);
			sinaContent.setShareImage(localImage);
			qZoneContent.setShareImage(localImage);
			weixinContent.setShareImage(localImage);
			circleMedia.setShareImage(localImage);
		}
		
		if(share.targetUrl!=null){
			sinaContent.setTargetUrl(share.targetUrl);
			qZoneContent.setTargetUrl(share.targetUrl);
			weixinContent.setTargetUrl(share.targetUrl);
			circleMedia.setTargetUrl(share.targetUrl);
		}
		
		mController.setShareMedia(sinaContent);
		mController.setShareMedia(qZoneContent);
		mController.setShareMedia(weixinContent);
		mController.setShareMedia(circleMedia);
		mController.openShare(activity, false);
		
		//mController.shareTo(arg0, arg1, arg2, arg3)
	}
	
	
	
	
//	
//	/**
//	 * 根据不同的平台设置不同的分享内容
//	 */
//	private void setShareContent() {
//
//		// 配置SSO
//		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//		// mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity,"100424468", "c7394704798a158208a74ab60104f0ba");
//		qZoneSsoHandler.addToSocialSDK();
//		mController
//				.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能。http://www.umeng.com/social");
//
//		// RenrenSsoHandler renrenSsoHandler = new
//		// RenrenSsoHandler(getActivity(),
//		// "201874", "28401c0964f04a72a14c812d6132fcef",
//		// "3bf66e42db1e4fa9829b955cc300b737");
//		// mController.getConfig().setSsoHandler(renrenSsoHandler);
//
//		UMImage localImage = new UMImage(activity, R.drawable.device);
//		UMImage urlImage = new UMImage(activity,"http://www.umeng.com/images/pic/social/integrated_3.png");
//		// UMImage resImage = new UMImage(getActivity(), R.drawable.icon);
//
//		
//
//		WeiXinShareContent weixinContent = new WeiXinShareContent();// 设置微信平台分享的内容
//		weixinContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-微信。http://www.umeng.com/social");
//		weixinContent.setTitle("友盟社会化分享组件-微信");// 分享的标题
//		weixinContent.setTargetUrl("http://www.umeng.com/social");// 分享的地址
//		weixinContent.setShareMedia(urlImage);
//		mController.setShareMedia(weixinContent);
//
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia
//				.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-朋友圈。http://www.umeng.com/social");
//		circleMedia.setTitle("友盟社会化分享组件-朋友圈");
//		circleMedia.setShareMedia(urlImage);
//		// circleMedia.setShareMedia(uMusic);
//		// circleMedia.setShareMedia(video);
//		circleMedia.setTargetUrl("http://www.umeng.com/social");
//		mController.setShareMedia(circleMedia);
//
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent("share test");
//		qzone.setTargetUrl("http://www.umeng.com");
//		qzone.setTitle("QZone title");
//		qzone.setShareMedia(urlImage);
//		// qzone.setShareMedia(uMusic);
//		mController.setShareMedia(qzone);
//
//		
//
//		QQShareContent qqShareContent = new QQShareContent();
//		// qqShareContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能 -- QQ");
//		qqShareContent.setTitle("hello, title");
//		qqShareContent.setTargetUrl("http://www.umeng.com/social");
//		mController.setShareMedia(qqShareContent);
//
//		TencentWbShareContent tencent = new TencentWbShareContent();
//		
//		tencent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-腾讯微博。http://www.umeng.com/social");
//		// 设置tencent分享内容
//		mController.setShareMedia(tencent);
//	}
//	
	
	
	
	
}
