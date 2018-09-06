package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.dialog.UtilDialog;
import com.bm.entity.HotGoods;
import com.bm.entity.User;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.bm.util.DataCleanManager;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.lib.tool.Pager;
import com.lib.widget.RoundImageViewFive;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 签到
 * @author shiyt
 *
 */
public class SigninAc  extends BaseActivity implements OnClickListener {
	private Context context;
	private View v_1, v_2, v_3;
	private int tv_titleId[] = { R.id.tv_cjet, R.id.tv_ypf,R.id.tv_wpf};
	private TextView tv_title[] = new TextView[3];
	private TextView tv_cjet,tv_ypf,tv_wpf,tv_mycourse_name,tv_mycourse_club,tv_mycourse_address
		,tv_mycourse_time,tv_mycourse_money,tv_qd,tv_time,tv_mycourse_Endstates,tv_mycourse_Endtime,tv_wqd;
	private ViewPager vPager;
	private RoundImageViewFive img_pic;
	private LinearLayout ll_yqd;
	private List<SigninFrameLayout> dataList = new ArrayList<SigninFrameLayout>();
	/** 分页器 */
	public Pager pager = new Pager(10);
	private SwipyRefreshLayout mSwipyRefreshLayout;
	private String degree,states;
	static SigninAc intance; 
	HotGoods hotGoods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_coursesignin);
		context = this;
		intance=this;
		setTitleName("课程签到");
		degree=getIntent().getStringExtra("degree");
		states=getIntent().getStringExtra("states");
		initView();
	
	}
	
	/**
	 * 清除状态
	 */
	private void clearState() {
		v_1.setVisibility(View.GONE);
		v_2.setVisibility(View.GONE);
		v_3.setVisibility(View.GONE);

		tv_cjet.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_ypf.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_wpf.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		
	}

	private void initView() {
		hotGoods = (HotGoods) getIntent().getSerializableExtra("hotGoods");
		tv_wqd = findTextViewById(R.id.tv_wqd);
		tv_wqd.setOnClickListener(this);
		ll_yqd = findLinearLayoutById(R.id.ll_yqd);
		tv_mycourse_Endtime = findTextViewById(R.id.tv_mycourse_Endtime);
		tv_time=findTextViewById(R.id.tv_time);
		tv_qd=findTextViewById(R.id.tv_qd);
		tv_mycourse_name=findTextViewById(R.id.tv_mycourse_name);
		tv_mycourse_club=findTextViewById(R.id.tv_mycourse_club);
		tv_mycourse_address=findTextViewById(R.id.tv_mycourse_address);
		tv_mycourse_time=findTextViewById(R.id.tv_mycourse_time);
		tv_mycourse_money=findTextViewById(R.id.tv_mycourse_money);
		img_pic=(RoundImageViewFive) findViewById(R.id.img_pic);
		tv_mycourse_Endstates=findTextViewById(R.id.tv_mycourse_Endstates);
		tv_cjet = findTextViewById(R.id.tv_cjet);
		tv_ypf = findTextViewById(R.id.tv_ypf);
		tv_wpf = findTextViewById(R.id.tv_wpf);

		v_1 = findViewById(R.id.v_1);
		v_2 = findViewById(R.id.v_2);
		v_3 = findViewById(R.id.v_3);

		
		if(states.equals("003")){
			tv_mycourse_Endstates.setText("已结束");
		}else if(degree.equals("002")){
			tv_mycourse_Endstates.setText("进行中");
			tv_mycourse_Endstates.setBackgroundDrawable(getResources().getDrawable(R.drawable.collection_lab_two));
		}else{
			tv_mycourse_Endstates.setText("未开始");
		}
		
		tv_cjet.setOnClickListener(this);
		tv_ypf.setOnClickListener(this);
		tv_wpf.setOnClickListener(this);

		vPager = (ViewPager) findViewById(R.id.vPager);
		for (int i = 0; i < tv_title.length; i++) {
			tv_title[i] = (TextView) findViewById(tv_titleId[i]);
		}
		dataList.add(new SigninFrameLayout(context, "2",degree,hotGoods.goodsId)); // 参加儿童
		dataList.add(new SigninFrameLayout(context, "1",degree,hotGoods.goodsId)); // 已签到
		dataList.add(new SigninFrameLayout(context, "0",degree,hotGoods.goodsId)); // 未签到
		
		
		vPager.setAdapter(pagerAdapter);

		vPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				selectTieleChange(arg0);
				dataList.get(arg0).reFresh();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		//getCoachIsSing();
		setDate();
		
		
		
		}
	/**
	 * 查询教练是否签到
	 */
	public void getCoachIsSing(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", App.getInstance().getCoach().coachId);
		map.put("baseId", hotGoods.goodsId);
		
		showProgressDialog();
		UserManager.getInstance().getTzjcoachIsSingstates(context, map, new ServiceCallback<CommonResult<User>>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonResult<User> obj) {
				hideProgressDialog();
				if(null != obj.data ){
//					if(obj.data.signStatus.equals("0"))//签到状态      0 未签到  1 正常 2 迟到
//					{
//						tv_wqd.setVisibility(View.VISIBLE);
//						ll_yqd.setVisibility(View.GONE);
//					}else if(obj.data.signStatus.equals("1")){
//						tv_qd.setText("正常");
//						tv_wqd.setVisibility(View.GONE);
//						ll_yqd.setVisibility(View.VISIBLE);
//						tv_time.setText(Util.toString(obj.data.signDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));
//					}else if(obj.data.signStatus.equals("2")){
//						tv_qd.setText("迟到");
//						tv_wqd.setVisibility(View.GONE);
//						ll_yqd.setVisibility(View.VISIBLE);
//						tv_time.setText(Util.toString(obj.data.signDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));
//					}
				}
			}
		});
	}
	
	/**
	 * 赋值数据
	 */
	public void setDate(){
		ImageLoader.getInstance().displayImage(hotGoods.titleMultiUrl, img_pic,App.getInstance().getHeadOptions());
		//3 户外俱乐部  2暑期大露营   1城市营地 
		if(hotGoods.goodsType.equals("1")){
			tv_mycourse_club.setText("城市营地");
			tv_mycourse_address.setText(getNullData(hotGoods.storeName));
		}else if(hotGoods.goodsType.equals("2")){
			tv_mycourse_club.setText("暑期大露营");
			tv_mycourse_address.setText(getNullData(hotGoods.address));
		}else{
			tv_mycourse_club.setText("户外基地");
			tv_mycourse_address.setText(getNullData(hotGoods.address));
		}
		
		tv_mycourse_time.setText(Util.toString(getNullData(hotGoods.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//时间
		tv_mycourse_Endtime.setText(Util.toString(getNullData(hotGoods.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
		tv_mycourse_name.setText(getNullData(hotGoods.goodsName));
		tv_mycourse_money.setText(getNullData(hotGoods.goodsPrice)==""?"￥0":"￥"+hotGoods.goodsPrice);
		
		if(hotGoods.classStatus.equals("2")){
			tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
			tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab_two));
			tv_mycourse_Endstates.setText("进行中");
		}else if(hotGoods.classStatus.equals("1")){
			tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
			tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab));
			tv_mycourse_Endstates.setText("未开始");
		}
	}
	
	public void getJoinCount(int signAllCount,int signCount,int unSignCount){
		tv_cjet.setText("参加儿童（"+signAllCount+"）");
		tv_ypf.setText("已签到（"+signCount+"）");
		tv_wpf.setText("未签到（"+unSignCount+"）");
	}
	
	private PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(dataList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(dataList.get(position));
			return dataList.get(position);
		}

	};


	private void selectTieleChange(int Index) {
		clearState();
		switch (Index) {
		case 0:
			v_1.setVisibility(View.VISIBLE);
			tv_cjet.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 1:
			v_2.setVisibility(View.VISIBLE);
			tv_ypf.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 2:
			v_3.setVisibility(View.VISIBLE);
			tv_wpf.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onClick(View view) {
		clearState();
		switch (view.getId()) {
		case R.id.tv_cjet:
			selectTieleChange(0);
			vPager.setCurrentItem(0);
			break;
		case R.id.tv_ypf:
			selectTieleChange(1);
			vPager.setCurrentItem(1);
			break;
		case R.id.tv_wpf:
			selectTieleChange(2);
			vPager.setCurrentItem(2);
			break;
		case R.id.tv_wqd:
			//UtilDialog.dialogTwoBtnContentTtile(context, "教练签到之后不能取消课程，确定要签到吗？", "取消","确定", "提示", handler, 1);
			break;
		default:
			break;
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				getCoachQd(App.getInstance().getCoach().coachId, null,1);
				break;
			}

		};
	};
	
	/**
	 * 教练签到
	 */
	public void getCoachQd(String id,String babyId, final int strType){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", id);//教练ID/用户ID
		map.put("baseId", hotGoods.goodsId);
		map.put("babyId", babyId);
		showProgressDialog();
		UserManager.getInstance().getTzjcoachInsertSignInfo(context, map, new ServiceCallback<CommonResult<User>>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonResult<User> obj) {
				hideProgressDialog();
				if(obj.data != null){
					
					
					if(1 == strType){//教练签到
						tv_wqd.setVisibility(View.GONE);
						ll_yqd.setVisibility(View.VISIBLE);
						tv_time.setText(Util.toString(obj.data.signDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));
						if(obj.data.signStatus.equals("1")){
							tv_qd.setText("正常");
						}else if (obj.data.signStatus.equals("2")){
							tv_qd.setText("迟到");
						}
					}else{//宝贝签到
						for (int i = 0; i < dataList.size(); i++) {
							dataList.get(i).reFresh();
						}
					}
				}
				
				
			}
		});
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).reFresh();
		}
	}
}

