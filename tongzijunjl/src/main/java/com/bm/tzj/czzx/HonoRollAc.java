package com.bm.tzj.czzx;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.base.BaseActivity;
import com.bm.base.adapter.HonoRollAdapter;
import com.bm.entity.Model;
import com.bm.share.ShareModel;
import com.richer.tzjjl.R;
import com.bm.tzj.kc.GrowthCenterAc;

/**
 * 荣誉榜
 * 
 * @author shiyt
 * 
 */
public class HonoRollAc extends BaseActivity implements OnClickListener{
	private Context mContext;
	private List<Model> list = new ArrayList<Model>();
	private HonoRollAdapter adapter;
	private ListView lv_honoroll;
	private TextView tv_xyx,tv_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_honoroll);
		mContext = this;
		
		setTitleName("荣誉榜");
		init();
	}

	public void init() {
		tv_xyx=findTextViewById(R.id.tv_xyx);
		tv_num=findTextViewById(R.id.tv_num);
		lv_honoroll=findListViewById(R.id.lv_honoroll);
		adapter=new HonoRollAdapter(mContext, list);
		lv_honoroll.setAdapter(adapter);
		getData();
		
		tv_num.setText(Html.fromHtml("<font color=\""+mContext.getResources().getColor(R.color.txt_title_detail)+"\">第</font><font size=\"25\"  color=\""+mContext.getResources().getColor(R.color.txt_yellow_color)+"\">3</font><font  color=\""+mContext.getResources().getColor(R.color.txt_title_detail)+"\">名</font>"));
		tv_xyx.setOnClickListener(this);
		
		lv_honoroll.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, GrowthCenterAc.class);
				intent.putExtra("pageType", "HonoRollAc");
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 获取数据
	 */
	public void getData(){
		for (int i = 0; i < 8; i++) {
			list.add(new Model());
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_xyx:
			ShareModel model = new ShareModel();
			model.title = "测试";
			model.conent = "测试内容";
			model.urlImg = "";
			model.targetUrl = "http://www.baidu.com";
			share.shareData(model);
			break;

		default:
			break;
		}
		
	}
}
