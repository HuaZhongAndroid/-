package com.bm.tzjjl.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.baidu.location.BDLocation;
import com.bm.api.BaseApi;
import com.bm.app.App;
import com.bm.tzj.city.City;
import com.bm.util.BDLocationHelper;
import com.bm.util.BDLocationHelper.LocationInfo;
import com.bm.util.BDLocationHelper.MyLocationListener;
import com.richer.tzjjl.R;

/**
 * 启动页
 * @author wanghy
 *
 */
public class StartAc extends Activity{
	/**
	 * 该类是进入app的第一个界面，显示开启app的动画
	 * 
	 * @author LiXuetao
	 */
	private Context context;
	public static final int SKIP_GUIDE = 0x001;
	public static final int SKIP_MAIN = 0x002;
	SharedPreferences sharedPreferences;
	// 该线程用于延迟跳转activity
	Thread thread;
	// 判断是否第一次打开该应用
	Boolean b;
	
	
//	 private AnimationDrawable animationDrawable;  
//	 private ImageView animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_start);
		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		context=this;
		loacationInfo();
		/**
		 * context.getgetSharedPreferences(String name, int mode)
		 * 获取sharedPreference对象 name是sharedPreference生成的xml文件的名字
		 */
		sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
		thread = new Thread(runnable);
		thread.start();
		
		//加载服务器地址
		final SharedPreferences sp =this.getSharedPreferences("ssspathss", MODE_PRIVATE);
		String bpath = sp.getString("path", null);
		if(bpath != null)
			BaseApi.API_URL_PRE = bpath;
	}
	 public void loacationInfo() {
		 LocationInfo info = BDLocationHelper.getCacheLocation();
			 if(info != null){
				 App.getInstance().saveCityCode(null);
				 City city = new City();
				 city.lat = info.lat+"";
				 city.lng = info.lng+"";
				 city.address = info.address;
				 city.cityName = info.city;
				 System.out.println("cityName"+city.cityName);
				 App.getInstance().saveCityCode(city);
			 }else{
				// 手机定位一次
			 		BDLocationHelper.locate(context, new MyLocationListener() {
						
						@Override
						public void success(BDLocation location) {
							
							
							
							LocationInfo info = BDLocationHelper.getCacheLocation();
			 				 if(info != null){
			 					 App.getInstance().saveCityCode(null);
			 					 City city = new City();
								 city.lat = info.lat+"";
								 city.lng = info.lng+"";
								 city.address = info.address;
								 city.cityName = info.city;
								 System.out.println("cityName"+city.cityName);
								 App.getInstance().saveCityCode(city);
			 				 }
							
						}
					}); 
			 }

	 	}
	
	// 此handler用于处理界面的变换（跳转activity）
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			
			case SKIP_GUIDE:

				Intent guideIntent = new Intent(StartAc.this,GuideAc.class);
				startActivity(guideIntent);
				finish();
				break;
			case SKIP_MAIN:
//				animationDrawable = (AnimationDrawable)animation.getBackground();  
//				animationDrawable.stop();
				if(null != App.getInstance().getCoach() ){
					Intent intent  = new Intent(context, MainAc.class);
					intent.putExtra("tag", 2);
					startActivity(intent);
				}else{
					Intent intent  = new Intent(context, com.bm.tzj.mine.LoginAc.class);
					startActivity(intent);
				}
				finish();
				break;
			}
		};
	};

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				// getBoolean(String key, boolean defValue)
				// 获取键isFirst的值，若无此键则获取默认值（第一次打开程序的时候没有isFirst这个键）
				b = sharedPreferences.getBoolean("isFirst", true);
				Message msg = handler.obtainMessage();
				if (b) {
					// Editor对象用于修改sharedpreference对象,修改完后必须提交事务，才能完成修改（参考数据库的事务处理）
					Editor editor = sharedPreferences.edit();
					editor.putBoolean("isFirst", false);
					editor.commit();
					msg.what = SKIP_GUIDE;
				} else {
					msg.what = SKIP_MAIN;
				}
				// 休眠3s后，将信息发给handler，由handler来跳转activity
				Thread.sleep(2000);
				handler.sendMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
 
}
