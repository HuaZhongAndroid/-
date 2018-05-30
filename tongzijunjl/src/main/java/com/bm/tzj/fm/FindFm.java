package com.bm.tzj.fm;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.adapter.FindAdapter;
import com.bm.dialog.UtilDialog;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.tzj.ts.SendContentAc;
import com.lib.tool.Pager;
import com.lib.widget.RefreshViewPD;

/**
 * 
 * 探索
 * 
 * @author jiangsh
 * 
 */
@SuppressLint("NewApi")
public class FindFm extends Fragment implements OnClickListener,FindAdapter.OnSeckillClick {
	private Context context;
	private int index;//记录点击第几项回复
	private TextView tv_a;// 玩伴儿
	private TextView tv_b;// 探索
//	private TextView tv_c;// 发帖
	private ImageView img_c;//发帖

	private View v_1, v_2, v_3;
	private ListView lv_content;// 显示内容
	private RefreshViewPD refresh_view;
	/** 分页器 */
	public Pager pager = new Pager(10);

	private FindAdapter adapter;
	private List<Model> list = new ArrayList<Model>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.fm_find, container,
				false);
		
		context = this.getActivity();
		initView(messageLayout);
		isHidden();
		// App.toast("HealthRecordFm");
		return messageLayout;
	}

	/**
	 * 初始化数据
	 * 
	 * @param v
	 */
	private void initView(View v) {
		// 初始化view
		v_1 =(View) v.findViewById(R.id.v_1);
		v_2 =(View) v.findViewById(R.id.v_2);
		v_3 =(View) v.findViewById(R.id.v_3);
		tv_a = (TextView) v.findViewById(R.id.tv_a);
		tv_b = (TextView) v.findViewById(R.id.tv_b);
		img_c = (ImageView) v.findViewById(R.id.img_c);
//		tv_c = (TextView) v.findViewById(R.id.tv_c);
		
		lv_content = (ListView) v.findViewById(R.id.lv_content);
		// 初始化监听
		tv_a.setOnClickListener(this);
		tv_b.setOnClickListener(this);
		img_c.setOnClickListener(this);
//		tv_c.setOnClickListener(this);
		refresh_view = (RefreshViewPD) v.findViewById(R.id.refresh_view);

		adapter = new FindAdapter(context, list);
		lv_content.setAdapter(adapter);
		
		setData();
		
		adapter.setOnSeckillClick(this);
	}
	
	
	
	private void setData() {
		list.clear();
		for (int i = 0; i < 5; i++) {
			Model info = new Model();
			info.name = "嘻嘻妈咪" + (i + 3) ;
			info.address = "上海龙之梦" + (i + 3);
			info.time = "2015.12." + "1" + (i + 3) + " 10:30";
			info.money = "￥9" + (i + 3);
			info.zanName = "艾玛,赛丽" + (i + 3);
			info.content="今天上课非常开心，学到了很多的知识宝宝很勇敢。谢谢教练"+(i+4);
			List<Model> mList = new ArrayList<Model>();
			for(int j=0;j<2; j++){
				Model infos = new Model();
				infos.name= "丽萨" + (j + 3) +":";
				infos.content="宝贝很棒哦"+(j+4);
				mList.add(infos);
			}
			info.mInfo =mList;
			list.add(info);
		}
		adapter.notifyDataSetChanged();
	}
	
	private void setDatas() {
		list.clear();
		for (int i = 0; i < 5; i++) {
			Model info = new Model();
			info.name = "嘻嘻" + (i + 3) ;
			info.address = "上海龙之梦虹口" + (i + 3);
			info.time = "2015.12." + "1" + (i + 3) + " 10:30";
			info.money = "￥9" + (i + 3);
			info.zanName = "艾玛,赛丽" + (i + 3);
			info.content="今天上课非常开心，学到了很多的知识宝宝很勇敢。谢谢教练"+(i+4);
			List<Model> mList = new ArrayList<Model>();
			for(int j=0;j<2; j++){
				Model infos = new Model();
				infos.name= "丽萨" + (j + 3) +":";
				infos.content="宝贝很棒哦"+(j+4);
				mList.add(infos);
			}
			info.mInfo =mList;
			list.add(info);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_a:// 玩伴儿
			clearState();
			setData();
			v_1.setVisibility(View.VISIBLE);
			tv_a.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case R.id.tv_b:// 探索
			clearState();
			setDatas();
			v_2.setVisibility(View.VISIBLE);
			tv_b.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case R.id.img_c:// 发帖
			intent = new Intent(context,SendContentAc.class);
			startActivity(intent);
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

		tv_a.setTextColor(getResources().getColor(
				R.color.course_bg_textColor));
		tv_b.setTextColor(getResources().getColor(
				R.color.course_bg_textColor));
	}
	
	//清除状态
//	private void clearState(){
//		tv_a.setBackgroundColor(context.getResources().getColor(R.color.wbe));
//		tv_a.setTextColor(context.getResources().getColor(R.color.wbe_txt));
//		
//		tv_b.setBackgroundColor(context.getResources().getColor(R.color.wbe));
//		tv_b.setTextColor(context.getResources().getColor(R.color.wbe_txt));
//	}

	@Override
	public void onSeckillClick(int position, int tag) {
		index=position;
		if(tag==3){
			//UtilDialog.dialogPromtMessage(context);
		}
	}
}
