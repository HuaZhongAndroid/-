package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bm.base.BaseActivity;
import com.bm.base.adapter.CourseListAdapter;
import com.bm.entity.Model;
import com.richer.tzjjl.R;

/**
 * 课程列表
 * @author shiyt
 *
 */
public class CourseListAc extends BaseActivity implements OnClickListener {
	private Context mContext;
	private ListView lv_course;
	private CourseListAdapter adapter;
	private List<Model> list = new ArrayList<Model>();
	private TextView tv_search;
	private EditText et_key;
	private LinearLayout ll_search;
	private String pageType;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_courselist);
		mContext = this;
		pageType=getIntent().getStringExtra("pageType");
		title=getIntent().getStringExtra("title");
		setTitleName(title);
		init();
	}
	
	public void init(){
		tv_search=findTextViewById(R.id.tv_search);
		et_key=findEditTextById(R.id.et_key);
		ll_search=findLinearLayoutById(R.id.ll_search);	
		lv_course=(ListView) findViewById(R.id.lv_course);
		adapter=new CourseListAdapter(mContext,list);
		lv_course.setAdapter(adapter);
		
		//从搜索进入显示搜索框 其他隐藏
		if("Search".equals(pageType)){
			ll_search.setVisibility(View.VISIBLE);
			setTitleName("搜索");
		}
		
		lv_course.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(mContext, CourseDetailAc.class);
				intent.putExtra("degree", list.get(position).degree);
				startActivity(intent);
			}
		});
		tv_search.setOnClickListener(this);
		getData();
	}
	
	/**
	 * 获取数据
	 */
	public void getData(){
		for (int i = 0; i < 5; i++) {
			Model model = new Model();
			if(i==0 || i==3){
				model.degree="1";  //1 户外俱乐部  2暑期大露营   3城市营地
			}else if(i==1 || i==4){
				model.degree="2";
			}else{
				model.degree="3";
			}
			
			list.add(model);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search:  //搜索
			dialogToast("搜索");
			break;

		default:
			break;
		}
		
	}
}
