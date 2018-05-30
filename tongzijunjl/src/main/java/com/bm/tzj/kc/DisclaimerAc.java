package com.bm.tzj.kc;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.bm.api.UserManager;
import com.bm.base.BaseActivity;
import com.bm.entity.Disclaimer;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;

/**
 * 免责声明
 * @author shiyt
 *
 */
public class DisclaimerAc extends BaseActivity {
	private Context mContext;
	private WebView webview;
	private String pageType;
	private TextView tv_mzsm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_disclaimer);
		mContext = this;
		pageType=getIntent().getStringExtra("pageType");
		
		
		init();
		
		if("RegistrAc".equals(pageType)){
			setTitleName("注册协议");
			getRichText(1);
		}else if("HelpAc".equals(pageType)){
			setTitleName("帮助");
			getRichText(2);
		}else if("AboutAc".equals(pageType)){
			setTitleName("关于软件");
			getRichText(3);
		}else if ("UpdateVAc".equals(pageType)){
			setTitleName("服务条款");
			getRichText(1);
		}else{
//			setTitleName("免责声明");
//			getDisclaimer();
		}
		
	}
	
	public void init(){
		tv_mzsm=findTextViewById(R.id.tv_mzsm);
		webview = (WebView) findViewById(R.id.webview);
		webview.setBackgroundColor(0);
		webview.getBackground().setAlpha(0);
		
		webview.getSettings().setJavaScriptEnabled(true);     
		webview.getSettings().setAllowFileAccess(true);  
		webview.getSettings().setDomStorageEnabled(true);
		
		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		
	}
	
	public void getRichText(final int strType){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("richType","2");//1:用户端  2:教练端
		
		showProgressDialog();
		UserManager.getInstance().getTzjrichRichText(mContext, map, new ServiceCallback<CommonResult<Disclaimer>>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonResult<Disclaimer> obj) {
				hideProgressDialog();
				if(null !=obj.data){
					String strContent = "";
					if(strType == 1){//注册协议
						strContent = obj.data.richRegister;
					}else if (strType == 2){//帮助
						strContent = obj.data.richHelp;
					}else if (strType == 3){//关于软件
						strContent = obj.data.richAbout;
					}
					
					//显示富文本信息  
					if(strContent!=null){
						Util.initViewWebData(webview, strContent+"");
					}else{
						webview.setLayoutParams(new LayoutParams(0,0));
					}
				}
				
			}
		});
	}
	
	
	/**
	 * 注册协议
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public void getRegistrationAgreement(){
		webview.setVisibility(View.VISIBLE);
		tv_mzsm.setVisibility(View.GONE);
		
		
        //找到Html文件，也可以用网络上的文件  
		webview.loadUrl("file:///android_asset/registers.htm");  
	}
	
	/**
	 * 帮助
	 */
	public void getHelp(){
		webview.setVisibility(View.VISIBLE);
		tv_mzsm.setVisibility(View.GONE);
		webview.loadUrl("file:///android_asset/help.htm");  
	}
	
	/**
	 * 关于软件
	 */
	public void getAbout(){
		webview.setVisibility(View.VISIBLE);
		tv_mzsm.setVisibility(View.GONE);
		webview.getSettings().setJavaScriptEnabled(true);  
		webview.loadUrl("file:///android_asset/about.htm");  
	}
}
