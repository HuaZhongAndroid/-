package com.bm.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.bm.dialog.ToastDialog;
import com.bm.entity.CoachInfo;
import com.bm.entity.User;
import com.bm.im.tool.DemoHelper;
import com.bm.tzj.city.City;
import com.richer.tzjjl.R;
import com.lib.tool.SharedPreferencesHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Application类
 *
 */
public class App extends Application {
	static final String TAG = App.class.getSimpleName();
	private Handler handler;
	private static App instance;
	public int mode_w = 480;
	public int mode_h = 800;

	/**
	 * 列表中显示图片的选项
	 */
	private DisplayImageOptions listViewDisplayImageOptions;
	private DisplayImageOptions headImage;
	private DisplayImageOptions advertisingImageOptions;//商品详情

	/**
	 * 大图显示imageloder
	 */
	private DisplayImageOptions bigImageOptions;
	

	/**
	 * 列表中显示图片的选项
	 */
	private DisplayImageOptions headOptions;
	
	/**
	 * 群列表中显示图片的选项
	 */
	private DisplayImageOptions groupHeadOptions;
	
	
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";

	@Override
	public void onCreate() {

		//腾讯的日志收集器  和SDKInitializer 冲突了 错误 build.gradle 中的 ndk 代码
		CrashReport.initCrashReport(getApplicationContext(), "72dfd9835e", true);
		SDKInitializer.initialize(this);

		 SDKInitializer.initialize(this);
		instance = this;
		super.onCreate();
		handler = new Handler();
		// Log.i(TAG, "酒食网 启动...");
		DemoHelper.getInstance().init(this);
		initImageLoader(this);

		// 设置列表图片显示配置
		headImage = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_avatar)
				.showImageForEmptyUri(R.drawable.default_avatar)
				.showImageOnFail(R.drawable.default_avatar).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				// .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		// 群设置列表图片显示配置
		groupHeadOptions = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.ease_groups_icon)
						.showImageForEmptyUri(R.drawable.ease_groups_icon)
						.showImageOnFail(R.drawable.ease_groups_icon).cacheInMemory(true)
						.cacheOnDisk(true).considerExifParams(true)
						// .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
						.bitmapConfig(Bitmap.Config.RGB_565).build();
		// 设置广告位显示配置
		advertisingImageOptions = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.adv_default)
						.showImageForEmptyUri(R.drawable.adv_default)
						.showImageOnFail(R.drawable.adv_default).cacheInMemory(true)
						.cacheOnDisk(true).considerExifParams(true)
						// .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
						.bitmapConfig(Bitmap.Config.RGB_565).build();		
		// 设置列表图片显示配置
		listViewDisplayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defult_shape)
				.showImageForEmptyUri(R.drawable.defult_shape)
				.showImageOnFail(R.drawable.defult_shape).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				// .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		bigImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defult_shape)
				.showImageForEmptyUri(R.drawable.defult_shape)
				.showImageOnFail(R.drawable.defult_shape).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				// .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		headOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defult_shape)
		.showImageForEmptyUri(R.drawable.defult_shape)
		.showImageOnFail(R.drawable.defult_shape).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		// .displayer(new RoundedVignetteBitmapDisplayer(10, 6))
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public static App getInstance() {
		return instance;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setUser(User user) {
		SharedPreferencesHelper.saveJSON("user", user);
	}

	public User getUser() {
		User user = SharedPreferencesHelper.getJSON("user", User.class);
		return user;
	}
	
	public void setCoach(CoachInfo user) {
		SharedPreferencesHelper.saveJSON("coachInfo", user);
	}

	public CoachInfo getCoach() {
		CoachInfo user = SharedPreferencesHelper.getJSON("coachInfo", CoachInfo.class);
		return user;
	}

	public int getMode_w() {
		return mode_w;
	}

	public void setMode_w(int mode_w) {
		this.mode_w = mode_w;
	}

	public int getMode_h() {
		return mode_h;
	}

	public void setMode_h(int mode_h) {
		this.mode_h = mode_h;
	}

	private static Toast toast;

	public static void toast(int resId) {
		toast(getInstance().getString(resId), Toast.LENGTH_SHORT);
	}
	
	public DisplayImageOptions getAdvertisingImageOptions() {
		return advertisingImageOptions;
	}

	public static void toastShort(int resId) {
		toast(getInstance().getString(resId), Toast.LENGTH_SHORT);
	}

	public static void toastLong(int resId) {
		toast(getInstance().getString(resId), Toast.LENGTH_LONG);
	}

	public static void toast(String s) {
		toast(s, Toast.LENGTH_SHORT);
	}

	public static void toastShort(String s) {
		toast(s, Toast.LENGTH_SHORT);
	}

	public static void toastLong(String s) {
		toast(s, Toast.LENGTH_LONG);
	}
	/**
	 * 对话框样式的toast
	 * 
	 * @param context
	 * @param msg
	 *            内容
	 * @param ms
	 *            时间
	 */
	public static ToastDialog dialogToast(Context context, String msg, int ms) {
		return new ToastDialog(context).show(msg, ms);
	}

	private static void toast(String s, int length) {
		try {
			if (toast != null) {
				toast.setText(s);
			} else {
				toast = Toast.makeText(getInstance(), s, length);
			}
			toast.show();
		} catch (Exception e) {
		}
	}

	/**
	 * 初始化ImageLoader
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getListViewDisplayImageOptions() {
		return listViewDisplayImageOptions;
	}
	
	public DisplayImageOptions getheadImage() {
		return headImage;
	}

	public DisplayImageOptions getBigImageOptions() {
		return bigImageOptions;
	}
	
	public DisplayImageOptions getHeadOptions() {
		return headOptions;
	}
	
	public DisplayImageOptions getGroupHeadOptions() {
		return groupHeadOptions;
	}
	
	public  int getScreenWidth() {
        WindowManager wm = (WindowManager)App.instance.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
     }
	public  int getScreenHeight() {
        WindowManager wm = (WindowManager)App.instance.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
     }
	
	/**
	 *保存城市code
	 * @param code
	 */
	public void saveCityCode(City city){
		SharedPreferencesHelper.saveJSON("cityCode", city);
	}
    //删除城市code
	public void removeCityCode(){
		SharedPreferencesHelper.remove("cityCode");
	}
	//获取城市code
	public City  getCityCode(){
		City city  = SharedPreferencesHelper.getJSON("cityCode", City.class);
		return city;
	}
}
