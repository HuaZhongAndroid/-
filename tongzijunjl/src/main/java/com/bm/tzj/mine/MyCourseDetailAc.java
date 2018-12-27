package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.CourseAdapter;
import com.bm.dialog.UtilDialog;
import com.bm.entity.HotGoods;
import com.bm.entity.Model;
import com.bm.tzjjl.activity.LocationMapAc;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.lib.http.result.StringResult;
import com.lib.widget.BannerView;
import com.lib.widget.BannerView.OnBannerClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 我的课程详情
 * 
 * @author shiyt
 * 
 */
public class MyCourseDetailAc extends BaseActivity implements OnClickListener {
	private Context context;
	private CourseAdapter adapter;
	private List<Model> list =new ArrayList<Model>();
	private LinearLayout ll_kc_one,ll_kc_two,ll_address;
	BannerView banner;
	private ImageView img_bg;
	private TextView tv_name,tv_price,tv_category,tv_age,tv_time,tv_address,tv_storeAddress
					 ,tv_class,tv_signup;
					// ,tv_register,tv_collet,tv_share;

	private LinearLayout  ll_map;
	private String[] picArr = new String[]{
			"http://www.totalfitness.com.cn/upfile/681x400/20120323104125_324.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/d31b0ef41bd5ad6e1e7e401f83cb39dbb7fd3cbb.jpg",
			"http://elegantliving.ceconline.com/TEAMSITE/IMAGES/SITE/ES/20080203_EL_ES_02_02.jpg"};
	/**1 户外俱乐部  2暑期大露营   3城市营地*/
	private String degree="",type="";
	private boolean isClass ;// 是否约课 0未约课 1已约课
	private WebView webview;
	HotGoods hotGoods;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_mycourse_detail);
		context = this;
		setTitleName("课程详情");
		init();
	}
	
	public void init(){
		webview = (WebView) findViewById(R.id.webview);//课程要点
		webview.setBackgroundColor(0);
		webview.getBackground().setAlpha(0);
		
		
		degree=getIntent().getStringExtra("degree");
		type=getIntent().getStringExtra("type");
		tv_signup = findTextViewById(R.id.tv_signup);
		img_bg=findImageViewById(R.id.img_bg);
		tv_class = findTextViewById(R.id.tv_class);
		tv_class.setOnClickListener(this);
		ll_map=findLinearLayoutById(R.id.ll_map);
		tv_name=findTextViewById(R.id.tv_name);
		tv_price=findTextViewById(R.id.tv_price);
		tv_category=findTextViewById(R.id.tv_category);
		tv_age=findTextViewById(R.id.tv_age);
		tv_time=findTextViewById(R.id.tv_time);
		tv_address=findTextViewById(R.id.tv_address);
		
		/**根据不同分类显示布局*/
		ll_kc_one=findLinearLayoutById(R.id.ll_kc_one);
//		ll_kc_two=findLinearLayoutById(R.id.ll_kc_two);
		ll_address = findLinearLayoutById(R.id.ll_address);
		tv_storeAddress = findTextViewById(R.id.tv_storeAddress);

		
		ll_map.setOnClickListener(this);
		
		initViewPager();
		getCourseDetial();
	}
	
	
	// 初始化viewpager
	public void initViewPager() {
		banner = (BannerView) findViewById(R.id.bannerView);
		banner.setOnBannerClickListener(new OnBannerClickListener() {
			@Override
			public void OnBannerClicked(int pos) {
				
			}
		});
		banner.update(picArr);
	}
	
	/**
	 * 获取课程详情
	 */
	public void getCourseDetial(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("baseId", getIntent().getStringExtra("goodsId"));
		if(null != App.getInstance().getCoach()){
			map.put("userId", App.getInstance().getCoach().userid);
		}
		showProgressDialog();
		UserManager.getInstance().getTzjgoodsSearchGoodsCourseInfoDetailForCoach(context, map, new ServiceCallback<CommonResult<HotGoods>>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonResult<HotGoods> obj) {
				hideProgressDialog();
				if(null !=obj.data){
					hotGoods=obj.data;
					setData(obj.data);
				}
			}
		});
	}
	
	/**
	 * 获取详情信息
	 */
	public void setData(HotGoods data){
		ImageLoader.getInstance().displayImage(data.titleMultiUrl, img_bg,App.getInstance().getAdvertisingImageOptions());
		
		if(data.goodsType.equals("1")){
			ll_address.setVisibility(View.VISIBLE);
			tv_storeAddress.setText(getNullData(data.storeName));//门店名称
		}else{
			ll_address.setVisibility(View.GONE);
		}
		
		if(data.goodsType.equals("1")){
			tv_category.setText("闹腾生存适能训练中心");// 课程分类
			ll_address.setVisibility(View.VISIBLE);
			tv_time.setText(Util.toString(getNullData(data.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm")+"—"+Util.toString(getNullData(data.endTime),"yyyy-MM-dd HH:mm:ss","HH:mm"));//时间
		}else if(data.goodsType.equals("2")){
			tv_category.setText("室内体验馆");// 课程分类
			tv_time.setText(Util.toString(getNullData(data.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")+"—"+Util.toString(getNullData(data.endTime),"yyyy-MM-dd HH:mm:ss","MM-dd"));//时间
			ll_address.setVisibility(View.GONE);
		}else{
			tv_category.setText("户外基地");// 课程分类
			ll_address.setVisibility(View.GONE);
			tv_time.setText(Util.toString(getNullData(data.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")+"—"+Util.toString(getNullData(data.endTime),"yyyy-MM-dd HH:mm:ss","MM-dd"));//时间
		}
		
		tv_name.setText(getNullData(data.goodsName));  //课程名称
		tv_price.setText(getNullData(data.goodsPrice)==""?"￥0":"￥"+data.goodsPrice);//价格
//		tv_price.setText("￥"+data.goodsPrice); // 课程价格
		if("1".equals(data.suitableAge)){
			tv_age.setText("3-4岁");// 适合年龄适合年龄 1：3-4岁  2：5-8岁  3：9岁以上
		}else if("2".equals(data.suitableAge)){
			tv_age.setText("5-8岁");
		}else if("3".equals(data.suitableAge)){
			tv_age.setText("9岁以上");
		}
//		tv_time.setText(Util.toString(getNullData(data.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm")+"—"+Util.toString(getNullData(data.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//时间

//	
		tv_address.setText(data.address);// 地址
		
		//课程要点
		if(data.coursePoints!=null){
			Util.initViewWebData(webview, data.coursePoints+"");
		}else{
			webview.setLayoutParams(new LayoutParams(0,0));
		}
		
		//是否约课  1已约课 0 未约课
		if(getNullData(data.isClass).equals("1")){
			tv_class.setText("已约课");
			tv_class.setBackgroundColor(getResources().getColor(R.color.isClass_color));
			isClass = true;
		}else {
			tv_class.setText("立即约课");
			tv_class.setBackgroundColor(getResources().getColor(R.color.txt_yellow_color));
			isClass = false;
		}
		
		if(type.equals("GrabClass")){
			tv_class.setVisibility(View.VISIBLE);
			tv_signup.setText(getNullData(data.goodsQuota)==""?"名 额 0":"名 额 "+getNullData(data.goodsQuota));  //报名人数
			FrameLayout.LayoutParams linearParams =(FrameLayout.LayoutParams) tv_signup.getLayoutParams(); //取控件textView当前的布局参数
			linearParams.height = Util.dpToPx(25, getResources());// 控件的高强制设成20
			linearParams.width = Util.dpToPx(55, getResources());;// 控件的宽强制设成30 
			tv_signup.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
		}else{
			tv_class.setVisibility(View.GONE);
			tv_signup.setText("报名 "+getNullData(data.orderNum)+"/"+getNullData(data.goodsQuota));  //报名人数
		}
		
		
	}
	
	/**
	 * 立即约课
	 */
	public void getRobgood(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("coachId", App.getInstance().getCoach().coachId);//教练ID
		map.put("goodsId", getIntent().getStringExtra("goodsId"));//课程ID
		
		showProgressDialog();
		UserManager.getInstance().getTzjcoachRobgoods(context, map, new ServiceCallback<StringResult>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				finish();
				Intent intent=new Intent(context, MainAc.class);
				intent.putExtra("tag", "MyCourseDetailAc");
				startActivity(intent);
				dialogToast("约课成功");
				MainAc.isJumpMine = 3;
//				tv_class.setText("已约课");
//				tv_class.setBackgroundColor(getResources().getColor(R.color.isClass_color));
//				isClass = true;
			}
		});
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2://确定约课
				getRobgood();
				break;
		};
		};
	};

	

	@Override
	public void onClick(View v) {
		Intent intent =null;
		switch (v.getId()) {
		case R.id.tv_class: // 约课
			if(!isClass){
				UtilDialog.dialogTwoBtnResults(context, "约课后无法轻易取消，确认约课吗？", "取消", "确定", handler, 1,2);
			}
			
			break; 
		case R.id.ll_map:  //地图
			if( hotGoods.lon.length() == 0 || hotGoods.lat.length() == 0){
				dialogToast("经纬度为空");
				return;
			}
			intent = new Intent(this,LocationMapAc.class);
			intent.putExtra("longitude", hotGoods.lon);
			intent.putExtra("latitude", hotGoods.lat);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	 
	
}
