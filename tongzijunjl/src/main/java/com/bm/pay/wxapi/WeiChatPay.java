package com.bm.pay.wxapi;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.bm.app.App;
import com.bm.entity.Order;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiChatPay {

	
	
	// APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxd930ea5d5a258f4f";
	private static IWXAPI api;
	
	public static void pay(Context context,Order order){
		api = WXAPIFactory.createWXAPI(context, "wxb4ba3c02aa476ea1");
		
		String jsonStr = "{\"appid\":\"wxb4ba3c02aa476ea1\", \"noncestr\":\"da1dbbdc2756692e6451ba84830ccb36\", \"package\":\"Sign=WXPay\", \"partnerid\":\"10000100\", \"prepayid\":\"wx20151230132938a7ac35fbbd0529954969\", \"timestamp\":\"1451453378\", \"sign\":\"FDB908AC9D6B9606588953B86FB727E9\" }";
		//String content = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
		App.toast("获取订单中...");
        try{
				Log.e("get server pay params:",jsonStr);
	        	JSONObject json = new JSONObject(jsonStr); 
				if(null != json && !json.has("retcode") ){
					PayReq req = new PayReq();
					//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
					req.appId			= json.getString("appid");
					req.partnerId		= json.getString("partnerid");
					req.prepayId		= json.getString("prepayid");
					req.nonceStr		= json.getString("noncestr");
					req.timeStamp		= json.getString("timestamp");
					req.packageValue	= json.getString("package");
					req.sign			= json.getString("sign");
					req.extData			= "app data"; // optional
					App.toast("正常调起支付");
					// 支付 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
					api.sendReq(req);
				}else{
		        	Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
		        	App.toast("返回错误"+json.getString("retmsg"));
				}
        }catch(Exception e){
        	Log.e("PAY_GET", "异常："+e.getMessage());
        	App.toast("异常："+e.getMessage());
        }
	}
	
	
//	public static void pay(Context context,Order order){
//		api = WXAPIFactory.createWXAPI(context, "wxb4ba3c02aa476ea1");
//		
//		String json = "{\"appid\":\"wxb4ba3c02aa476ea1\", \"noncestr\":\"da1dbbdc2756692e6451ba84830ccb36\", \"package\":\"Sign=WXPay\", \"partnerid\":\"10000100\", \"prepayid\":\"wx20151230132938a7ac35fbbd0529954968\", \"timestamp\":\"1451453378\", \"sign\":\"FDB908AC9D6B9606588953B86FB727E9\" }";
//		String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
//		App.toast("获取订单中...");
//        try{
//			byte[] buf = WeiHttp.httpGet(url);
//			if (buf != null && buf.length > 0) {
//				String content = new String(buf);
//				Log.e("get server pay params:",content);
//	        	JSONObject json = new JSONObject(content); 
//				if(null != json && !json.has("retcode") ){
//					PayReq req = new PayReq();
//					//req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
//					req.appId			= json.getString("appid");
//					req.partnerId		= json.getString("partnerid");
//					req.prepayId		= json.getString("prepayid");
//					req.nonceStr		= json.getString("noncestr");
//					req.timeStamp		= json.getString("timestamp");
//					req.packageValue	= json.getString("package");
//					req.sign			= json.getString("sign");
//					req.extData			= "app data"; // optional
//					App.toast("正常调起支付");
//					// 支付 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//					api.sendReq(req);
//				}else{
//		        	Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
//		        	App.toast("返回错误"+json.getString("retmsg"));
//				}
//			}else{
//	        	Log.d("PAY_GET", "服务器请求错误");
//	        	App.toast("服务器请求错误");
//	        }
//        }catch(Exception e){
//        	Log.e("PAY_GET", "异常："+e.getMessage());
//        	App.toast("异常："+e.getMessage());
//        }
//	}
	
}
