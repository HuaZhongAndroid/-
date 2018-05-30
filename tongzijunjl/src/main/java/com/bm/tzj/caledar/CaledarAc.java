package com.bm.tzj.caledar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.base.BaseActivity;
import com.bm.base.adapter.CaledarTimeListAdapter;
import com.bm.entity.Model;
import com.bm.im.tool.Constant;
import com.bm.tzj.caledar.CalendarView.OnCalendarViewListener;
import com.bm.tzj.city.Activity01;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.tool.SharedPreferencesHelper;
import com.lib.widget.FuGridView;

public class CaledarAc extends BaseActivity {

	private Context context ;
	private CaledarTimeListAdapter adapter ;
	private List<Model> list = new ArrayList<Model>();
	
	private FuGridView gv_explore_time;
	private TextView tv_submit;
	String classTime ="";//上课时间
//	private Calendar c;
//	private int _year, _month, _day;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_acledar);
		setTitleName("选择时间");
		context = this;
		init();
	}

	private void init() {
		
//		c = Calendar.getInstance();
//		_year = c.get(Calendar.YEAR);
//		_month = c.get(Calendar.MONTH);
//		_day = c.get(Calendar.DAY_OF_MONTH);
//		classTime = _year +"-"+(_month+1) +"-"+_day;
		
		classTime = this.getIntent().getStringExtra("classTime");
		if(classTime == null)
			classTime = Util.getCurrentDateString();//默认当前日期
		
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//		Date date = null;
//		try {
//			date = format.parse(classTime);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		if(date == null)
//			date = new Date();
		//组件不支持定义当前选择的日期,只能默认当天.......先不处理了
		
		
		// 在代码中
		CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
		// 设置标注日期
		List<Date> markDates = new ArrayList<Date>();
		markDates.add(new Date());
		calendarView.setMarkDates(markDates);
		
		tv_submit=findTextViewById(R.id.tv_submit);
		// 设置点击操作
		calendarView.setOnCalendarViewListener(new OnCalendarViewListener() {

			@Override
			public void onCalendarItemClick(CalendarView view, Date date) {
				classTime = format.format(date);//当前选中日期
			}
		});
		
		tv_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if("CourseFm".equals(CaledarAc.this.getIntent().getStringExtra("from")))
				{
					Intent intent = new Intent();
					intent.putExtra("classTime", classTime);
					setResult(RESULT_OK, intent);
					finish();
					return;
				}
				
				SharedPreferencesHelper.saveString("classTime", classTime);//上课时间
				Intent intent = new Intent();
				intent.setClass(CaledarAc.this, MainAc.class);
				setResult(4, intent);
				finish();
			}
		});
		
		if("CourseFm".equals(CaledarAc.this.getIntent().getStringExtra("from")))
		{
			this.findViewById(R.id.tv_clear).setVisibility(View.VISIBLE);
			this.findViewById(R.id.tv_clear).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
//					intent.putExtra("classTime", "");
					setResult(RESULT_OK, intent);
					finish();
					return;
				}});
		}
	}
}
