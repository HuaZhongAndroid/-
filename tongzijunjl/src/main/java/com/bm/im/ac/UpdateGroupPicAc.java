package com.bm.im.ac;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.bm.api.IMService;
import com.bm.base.BaseActivity;
import com.bm.entity.Model;
import com.bm.im.adapter.GroupPicAdapter;
import com.bm.im.api.ImApi;
import com.richer.tzjjl.R;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.lib.http.ServiceCallback;
import com.lib.http.result.StringResult;

/**
 * 修改群头像
 * @author shiyt
 *
 */
public class UpdateGroupPicAc extends BaseActivity {
	private Context mContext;
	private GridView gv_pic;
	private TextView tv_submit;
	private EditText et_name;
	private List<Model> list = new ArrayList<Model>();
	GroupPicAdapter adapter;
	private EMGroup group;
	public static UpdateGroupPicAc intance;
	private String groupId="",groupName="",strPicTag="";
	public String imagePath="1";
	private int[] picArr = new int[] {
			R.drawable.puzzle_00,R.drawable.puzzle_01, R.drawable.puzzle_02,R.drawable.puzzle_03, R.drawable.puzzle_04,
			R.drawable.puzzle_05, R.drawable.puzzle_06,R.drawable.puzzle_07, R.drawable.puzzle_08,R.drawable.puzzle_09, };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_update_grouppic);
		mContext = this;
		intance=this;
		setTitleName("更改群信息");
		init();
		
	}
	public void init(){
		groupId = getIntent().getExtras().getString("groupId");
		strPicTag = getIntent().getExtras().getString("picTag");
		
		group = EMGroupManager.getInstance().getGroup(groupId);
		gv_pic=(GridView) findViewById(R.id.gv_pic);
		et_name=findEditTextById(R.id.et_name);
		tv_submit=findTextViewById(R.id.tv_submit);
		tv_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendPics();
			}
		});
		
		et_name.setText(getIntent().getStringExtra("groupName").toString().trim());
		
		getData();
		et_name.setText(getIntent().getStringExtra("groupName"));
		
		gv_pic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for(int i=0;i<list.size();i++){
					list.get(i).isSelected = false;
				}
				list.get(arg2).isSelected =true;
				imagePath = (arg2+1)+"";
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	/**
	 * 关闭页面
	 */
	public void finishPage(){
		finish();
	}
	
	/**
	 * 获取数据
	 */
	public void getData(){
		int tag = Integer.valueOf(strPicTag)-1;
		for (int i = 0; i < 10; i++) {
			Model mInfo = new Model();
			mInfo.strImageUrl = picArr[i];
			if(tag == i){
				mInfo.isSelected = true;
			}else{
				mInfo.isSelected = false;
			}
			
			mInfo.name="默认"+(i+1);
			list.add(mInfo);
		}
		if(Integer.valueOf(strPicTag)==0){
			list.get(0).isSelected = true;
		}
		
		adapter=new GroupPicAdapter(mContext, list);
		gv_pic.setAdapter(adapter);
	}
	/**
	 * 修改群头像
	 */
	private void sendPics(){
		
	    groupName=et_name.getText().toString().trim();
		if(groupName.length()==0){
			dialogToast("群名称不能为空");
			return;
		}
		if(getIntent().getStringExtra("groupId").toString() == null){
			return;
		}
		
		HashMap<String, String> map =new HashMap<String,String>();
		map.put("groupId", groupId);
		map.put("groupName", groupName);
		map.put("groupPic", imagePath);
		showProgressDialog();
		IMService.getInstance().getImUpdateGroup(mContext, map, new ServiceCallback<StringResult>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				dialogToast("修改成功");
				ImApi.changeGroupName(groupId, groupName);
				group.setGroupName(groupName); //设置群名称
				
				finish();
				
			}
		});
	}
}
