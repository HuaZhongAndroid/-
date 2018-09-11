package com.bm.tzj.fm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.tzj.caledar.CaledarAc;
import com.bm.tzj.city.Activity01;
import com.bm.tzj.city.Activity02;
import com.bm.tzj.city.City;
import com.bm.tzj.mine.GrabClassListFrameLayout;
import com.bm.tzjjl.activity.MainAc;
import com.bm.tzjjl.activity.MainAc.OnTabActivityResultListener;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.tool.SharedPreferencesHelper;

/**
 * 
 * //选课
 * 
 * @author wanghy
 * 
 */
@SuppressLint("NewApi")
public class GrabClassFm extends Fragment implements OnClickListener,OnTabActivityResultListener {
	private Context context;

	private TextView tv_total, tv_notStart, tv_underway, tv_end,tv_time,tv_location,tv_seach;
	private View v_1, v_2, v_3, v_4;
	private List<GrabClassListFrameLayout> dataList = new ArrayList<GrabClassListFrameLayout>();
	private int tv_titleId[] = { R.id.tv_total, R.id.tv_notStart,
			R.id.tv_underway, R.id.tv_end };
	private TextView tv_title[] = new TextView[4];
	private ViewPager vPager;
	private ImageView iv_rl;
	String strClassTime =Util.getCurrentDateStr();//当天上课时间
	private static GrabClassFm intance;
	City city  =null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.fm_grablass, container,
				false);
		intance = this;
		context = getActivity();
		city  =App.getInstance().getCityCode();
		initView(messageLayout);
		return messageLayout;

	}

	private void initView(View v) {
		c = Calendar.getInstance();
		_year = c.get(Calendar.YEAR);
		_month = c.get(Calendar.MONTH);
		_day = c.get(Calendar.DAY_OF_MONTH);
		
		SharedPreferencesHelper.saveString("classTime",  Util.getCurrentDateString());//默认当前日期
		MainAc.intance.setOnTabActivityResultListener(this);
		tv_seach = (TextView) v.findViewById(R.id.tv_seach);
		tv_location = (TextView) v.findViewById(R.id.tv_location);
		tv_time = (TextView) v.findViewById(R.id.tv_time);
		tv_total = (TextView) v.findViewById(R.id.tv_total);
		tv_notStart = (TextView) v.findViewById(R.id.tv_notStart);
		tv_underway = (TextView) v.findViewById(R.id.tv_underway);
		tv_end = (TextView) v.findViewById(R.id.tv_end);
		iv_rl=(ImageView) v.findViewById(R.id.iv_rl);
		v_1 = v.findViewById(R.id.v_1);
		v_2 = v.findViewById(R.id.v_2);
		v_3 = v.findViewById(R.id.v_3);
		v_4 = v.findViewById(R.id.v_4);

		tv_total.setOnClickListener(this);
		tv_notStart.setOnClickListener(this);
		tv_underway.setOnClickListener(this);
		tv_end.setOnClickListener(this);
		tv_time.setOnClickListener(this);
		tv_location.setOnClickListener(this);
		iv_rl.setOnClickListener(this);
		
		
		vPager = (ViewPager) v.findViewById(R.id.vPager);
		for (int i = 0; i < tv_title.length; i++) {
			tv_title[i] = (TextView) v.findViewById(tv_titleId[i]);
		}

		dataList.add(new GrabClassListFrameLayout(context, "1")); // 离我最近
		dataList.add(new GrabClassListFrameLayout(context, "2")); // 最新发布
		dataList.add(new GrabClassListFrameLayout(context, "3")); // 价格最高
		dataList.add(new GrabClassListFrameLayout(context, "4")); // 名额最高
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
		
		tv_seach.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				
				if(keyCode==KeyEvent.KEYCODE_ENTER){//修改回车键功能
					String strSeachName = tv_seach.getText().toString().trim();
					SharedPreferencesHelper.saveString("searchName", null);
					SharedPreferencesHelper.saveString("searchName", strSeachName);
					
					for (int i = 0; i < dataList.size(); i++) {
						dataList.get(i).reFresh();
					}
					
					// 先隐藏键盘
					//((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(GrabClassFm.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});
		
		if(null!=city&&!TextUtils.isEmpty(city.cityName)){
			String strCity =city.cityName;
			tv_location.setText(city.cityName+"");//城市名称
			//包含市 则截取
			strCity=strCity.substring(strCity.length()-1,strCity.length());
			if(strCity.equals("市")){
				tv_location.setText(city.cityName.substring(0,city.cityName.length()-1));
			}
		}else{
			tv_location.setText("西安");//城市名称
		}
		
		tv_time.setText(_year+"年"+(_month + 1)+"月"+_day+"日");
		getDate();
	}
	
	public void getDate() {
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).reFresh();
		}
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
			tv_total.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 1:
			v_2.setVisibility(View.VISIBLE);
			tv_notStart.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 2:
			v_3.setVisibility(View.VISIBLE);
			tv_underway.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 3:
			v_4.setVisibility(View.VISIBLE);
			tv_end.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		
		default:
			break;
		}

		
	}

	/**
	 * 清除状态
	 */
	private void clearState() {
		v_1.setVisibility(View.GONE);
		v_2.setVisibility(View.GONE);
		v_3.setVisibility(View.GONE);
		v_4.setVisibility(View.GONE);

		tv_total.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_notStart.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_underway.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_end.setTextColor(getResources().getColor(R.color.course_bg_textColor));
	}

	@Override
	public void onClick(View view) {
		Intent intent = null;
		
		switch (view.getId()) {
		case R.id.tv_total:
			selectTieleChange(0);
			vPager.setCurrentItem(0);
			break;
		case R.id.tv_notStart:
			selectTieleChange(1);
			vPager.setCurrentItem(1);
			break;
		case R.id.tv_underway:
			selectTieleChange(2);
			vPager.setCurrentItem(2);
			break;
		case R.id.tv_end:
			selectTieleChange(3);
			vPager.setCurrentItem(3);
			break;
		case R.id.tv_time:
			Dialog dialog = onCreateDialog();
			dialog.show();
			break;
		case R.id.tv_location:
			intent = new Intent(context, Activity01.class);
			MainAc.intance.startActivityForResult(intent, 1);
			break;
		case R.id.iv_rl:  //日历
			intent = new Intent(context, CaledarAc.class);
			strClassTime = SharedPreferencesHelper.getString("classTime");
			intent.putExtra("classTime",strClassTime);
			MainAc.intance.startActivityForResult(intent, 2);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 时间控件
	 * 
	 * @return
	 */
	// 时间控件

	private DatePickerDialog datePicker;

	private Calendar c;
	private int _year, _month, _day;
	private String times;

	private Dialog onCreateDialog() {
		return new DatePickerDialog(context, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				_year = year;
				_month = monthOfYear;
				_day = dayOfMonth;
				times = _year + "年" + (_month + 1) + "月" + _day+"日";
				int day = Util.comparisonTime(_year + "-" + (_month + 1) + "-" + _day,
						Util.getCurrentDateString());
				if (day < 0) {
					App.toast("查询的日期不能是未来日期");
					tv_time.setText(Util.getCurrentDateString());
					return;
				}
				tv_time.setText(times);
			}
		}, _year, _month, _day);

	}
	
	@Override
	public void onTabActivityResult(int requestCode, int resultCode, Intent data) {
		if (5 == resultCode) {
			String cityName = data.getStringExtra("cityName"); // 城市名称
			tv_location.setText(cityName);
			String strCity = cityName.substring(cityName.length()-1,cityName.length());
			if(strCity.equals("市")){
				tv_location.setText(cityName.substring(0,cityName.length()-1));
			}
			
			City cInfo = App.getInstance().getCityCode();
			if(null != cInfo){
				cInfo.cityName = cityName;
				App.getInstance().saveCityCode(cInfo);
				city  =App.getInstance().getCityCode();
				
//				cInfo.cityName = cityName;
//				App.getInstance().saveCityCode(cInfo);
//				City city = App.getInstance().getCityCode();
			}
			
			getDate();
		}
		if(4 == resultCode){
			getDate();
		}
	}

//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case 4:// 清除本地缓存
//
//				break;
//			case 3:// 退出当前登录
//				MainAc.intance.finish();
//				break;
//			}
//
//		};
//	};
}
