package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.bm.base.BaseActivity;
import com.bm.base.adapter.CourseCompanionAdapter;
import com.bm.entity.Model;
import com.richer.tzjjl.R;

/**
 * 课程旅伴
 * @author Administrator
 *
 */
public class CourseCompanionAc extends BaseActivity implements CourseCompanionAdapter.OnSeckillClick {
	private Context mContext;
	private ListView lv_course;
	private List<Model> list = new ArrayList<Model>();
	private CourseCompanionAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_coursecompanion);
		mContext = this;
		setTitleName("课程玩伴");
		init();
	}
	
	public void init(){
		lv_course=findListViewById(R.id.lv_course);
		adapter=new CourseCompanionAdapter(mContext, list);
		lv_course.setAdapter(adapter);
		adapter.setOnSeckillClick(this);
		getData();
	}
	
	/**
	 * 获取数据
	 */
	public void getData(){
		for (int i = 0; i < 5; i++) {
			list.add(new Model());
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onSeckillClick(int position) {
		Intent intent = new Intent(mContext, GrowthCenterAc.class);
		intent.putExtra("pageType", "HonoRollAc");
		startActivity(intent);
	}
}
