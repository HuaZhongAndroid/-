package com.bm.tzj.czzx;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.base.BaseActivity;
import com.bm.base.adapter.CourseAdapter;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.tzj.kc.CourseDetailAc;

/**
 * 勋章详情
 * @author shiyt
 *
 */
public class MedalDetailAc extends BaseActivity {
	private Context mContext;
	private ListView lv_course;
	private List<Model> list = new ArrayList<Model>();
	CourseAdapter adapter;
	private ImageView iv_sixview_head;
	private TextView tv_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_medaldetail);
		mContext = this;
		setTitleName("勋章详情");
		init();
	}
	
	public void init(){
		tv_name=findTextViewById(R.id.tv_name);
		iv_sixview_head=(ImageView) findViewById(R.id.iv_sixview_head);
		lv_course=findListViewById(R.id.lv_course);
		adapter = new CourseAdapter(mContext, list);
		lv_course.setAdapter(adapter);
		
//		ImageLoader.getInstance().displayImage("http://img1.imgtn.bdimg.com/it/u=2612160076,3558836575&fm=21&gp=0.jpg", iv_sixview_head, App.getInstance().getListViewDisplayImageOptions());
		
		
		lv_course.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent =new Intent(mContext, CourseDetailAc.class);
				intent.putExtra("degree", list.get(position).degree);
				startActivity(intent);
			}
		});
		getData();
	}
	
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
	
}
