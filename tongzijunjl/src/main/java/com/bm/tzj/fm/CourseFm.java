package com.bm.tzj.fm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bm.api.BaseApi;
import com.bm.base.adapter.CourseGalleryAdapter;
import com.bm.base.adapter.CourseListAdapter;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.tzj.caledar.CaledarAc;
import com.bm.tzj.city.Activity01;
import com.bm.tzj.kc.CourseDetailAc;
import com.bm.tzj.kc.CourseListAc;
import com.bm.tzj.mine.MyCourseAc;
import com.bm.tzj.mine.MyCourseListFrameLayout;
import com.bm.tzj.mine.MyMessageAc;
import com.bm.tzjjl.activity.MainAc;
import com.bm.util.TestData;
import com.bm.util.Util;
import com.lib.tool.Pager;
import com.lib.widget.BannerView;
import com.lib.widget.BannerView.OnBannerClickListener;
import com.lib.widget.HorizontalListView;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 
 * 课程
 * 
 * @author wangqiang
 * 
 */
@SuppressLint("NewApi")
public class CourseFm extends Fragment implements OnClickListener{
	private Context context;
	private TextView tv_total, tv_notStart, tv_underway, tv_end;
	private View v_1, v_2, v_3, v_4;
	
	private int _year, _month, _day;
	private String times;

	private int tv_titleId[] = { R.id.tv_total, R.id.tv_notStart,
			R.id.tv_underway, R.id.tv_end, R.id.tv_payments };
	private TextView tv_title[] = new TextView[5];
	private ViewPager vPager;
	private List<MyCourseListFrameLayout> dataList = new ArrayList<MyCourseListFrameLayout>();
	public static MyCourseAc intance = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.fm_indexs, container,
				false);
		context = this.getActivity();
		initView(messageLayout);
		// App.toast("HealthRecordFm");
		return messageLayout;
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

	private void initView(View v) {
		
		Calendar c = Calendar.getInstance();
		_year = c.get(Calendar.YEAR);
		_month = c.get(Calendar.MONTH);
		_day = c.get(Calendar.DAY_OF_MONTH);
		times = null;
		
		tv_total = (TextView) v.findViewById(R.id.tv_total);
		tv_notStart = (TextView) v.findViewById(R.id.tv_notStart);
		tv_underway = (TextView) v.findViewById(R.id.tv_underway);
		tv_end = (TextView) v.findViewById(R.id.tv_end);

		v_1 = v.findViewById(R.id.v_1);
		v_2 = v.findViewById(R.id.v_2);
		v_3 = v.findViewById(R.id.v_3);
		v_4 = v.findViewById(R.id.v_4);

		tv_total.setOnClickListener(this);
		tv_notStart.setOnClickListener(this);
		tv_underway.setOnClickListener(this);
		tv_end.setOnClickListener(this);
		v.findViewById(R.id.tv_right).setOnClickListener(this);
		v.findViewById(R.id.ib_right).setOnClickListener(this);

		vPager = (ViewPager) v.findViewById(R.id.vPager);
		for (int i = 0; i < tv_title.length; i++) {
			tv_title[i] = (TextView) v.findViewById(tv_titleId[i]);
		}

		
		dataList.add(new MyCourseListFrameLayout(context, "1")); // 未开始
		dataList.add(new MyCourseListFrameLayout(context, "2")); // 进行中
		dataList.add(new MyCourseListFrameLayout(context, "3")); // 已结束
		dataList.add(new MyCourseListFrameLayout(context, "0")); // 全部
		
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
		
		getData();
		
		
		//切换服务器地址用的
		final SharedPreferences sp =this.context.getSharedPreferences("ssspathss", Context.MODE_PRIVATE);
		v.findViewById(R.id.tv_center).setOnClickListener(new View.OnClickListener() {
			private int i=0;
        	@Override
			public void onClick(View v) {
				i++;
				if(i>6)
				{
					final EditText et = new EditText(context);
					et.setText("http://101.201.149.186:8888/tongZiJun/api/");
					new AlertDialog.Builder(context)
					.setMessage("选择服务地址")
					.setNegativeButton("101.201.149.186:8888", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							BaseApi.API_URL_PRE = BaseApi.API_HOST2 + "/tongZiJun/api/";
							sp.edit().putString("path", BaseApi.API_URL_PRE).commit();
						}
					})
					.setPositiveButton("121.40.201.137:8080", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							BaseApi.API_URL_PRE = BaseApi.API_HOST1 + "/tongzijun/api/";
							sp.edit().putString("path", BaseApi.API_URL_PRE).commit();
						}
					})
					.setNeutralButton("手动输入的", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							BaseApi.API_URL_PRE = et.getEditableText().toString();
							sp.edit().putString("path", BaseApi.API_URL_PRE).commit();
						}
					})
					.setView(et).setCancelable(false)
					.show();
					i=0;
				}
			}
		});
	}
	
	public void getData(){
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setDay(times);
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
			tv_total.setTextColor(getResources().getColor(R.color.white));
			break;
		case 1:
			v_2.setVisibility(View.VISIBLE);
			tv_notStart.setTextColor(getResources().getColor(R.color.white));
			break;
		case 2:
			v_3.setVisibility(View.VISIBLE);
			tv_underway.setTextColor(getResources().getColor(R.color.white));
			break;
		case 3:
			v_4.setVisibility(View.VISIBLE);
			tv_end.setTextColor(getResources().getColor(R.color.white));
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View view) {
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
		case R.id.tv_right:
		case R.id.ib_right:
//			popDatePicker();
			Intent i = new Intent(this.context, CaledarAc.class);
			i.putExtra("from", "CourseFm");
			i.putExtra("classTime", times);
			this.startActivityForResult(i, 21);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 21 && resultCode == Activity.RESULT_OK)
		{
			times = data.getStringExtra("classTime");
			getData();
		}
	}

}
