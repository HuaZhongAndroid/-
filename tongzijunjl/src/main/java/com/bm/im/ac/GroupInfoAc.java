package com.bm.im.ac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.IMService;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.entity.CoachInfo;
import com.bm.entity.User;
import com.bm.im.adapter.GroupInfoAdapter;
import com.bm.im.tool.Constant;
import com.richer.tzjjl.R;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 群信息
 * @author shiyt
 *
 */
public class GroupInfoAc extends BaseActivity implements OnClickListener {
	private Context mContext;
	private GridView gv_person;
	private LinearLayout ll_name;
	private List<CoachInfo> list = new ArrayList<CoachInfo>();
	GroupInfoAdapter adapter ;
	private PolygonImageView img_head;
	private TextView tv_name,tv_search,tv_newfriend;
	private EditText et_seachName;
	String groupName="",strSearch="",strPicTag="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_group);
		setTitleName("群信息");
		mContext = this;
		init();
	}
	
	/**
	 * 初始化控件
	 */
	public void init(){
		et_seachName = findEditTextById(R.id.et_seachName);
		ll_name = findLinearLayoutById(R.id.ll_name);
		ll_name.setOnClickListener(this);
		tv_search=findTextViewById(R.id.tv_search);
		tv_newfriend=findTextViewById(R.id.tv_newfriend);
		img_head=(PolygonImageView) findViewById(R.id.img_head);
		gv_person= (GridView) findViewById(R.id.gv_person);
		adapter=new GroupInfoAdapter(mContext, list);
		gv_person.setAdapter(adapter);
		tv_name=findTextViewById(R.id.tv_name);
		gv_person.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				//同步用户信息
				//ImApi.syncUserInfo(list.get(position));
//				Intent intent = new Intent(mContext, ChatActivity.class);
//				intent.putExtra("userId", list.get(position).userId);
//				intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
//				startActivity(intent);
			}
		});
		tv_search.setOnClickListener(this);
		getGroupInfo();
	}
	
	
//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		super.onRestart();
//		
//	}
	 

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_search:
			strSearch = et_seachName.getText().toString().trim();
			getGroupInfo();
			break;
		case R.id.ll_name://更改群头像
			Intent intent = new Intent(this,UpdateGroupPicAc.class);
			intent.putExtra("groupName", groupName);
			intent.putExtra("picTag", strPicTag);
			intent.putExtra("groupId", getIntent().getStringExtra("groupId"));
			startActivity(intent);
//			openActivity(UpdateGroupPicAc.class);
			break;
		default:
			break;
		}
	}
	/**
	 * 群信息
	 */
	private void getGroupInfo() {
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("groupId", getIntent().getStringExtra("groupId"));
		if(strSearch.length()>0){
			map.put("phoneOrName", strSearch);
		}
		IMService.getInstance().findGroupInfo(mContext, map, new ServiceCallback<CommonResult<CoachInfo>>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonResult<CoachInfo> obj) {
				hideProgressDialog();
				if(null!=obj.data){
					setData(obj.data);
				}
			}
		});
	}
	/**
	 * 设置数据
	 */
	private void setData(CoachInfo user){
		if(TextUtils.isEmpty(user.groupPic)){
			img_head.setImageResource(R.drawable.ease_groups_icon);
		}else{
			ImageLoader.getInstance().displayImage(user.groupPic, img_head,App.getInstance().getGroupHeadOptions());
		}
		tv_name.setText(user.groupName);
		groupName = user.groupName;
		strPicTag = getNullData(user.picTag)==""?"0":user.picTag;
		list.clear();
		if(user.groupUserList.size()>0){
			list.addAll(user.groupUserList);
			tv_newfriend.setText("群成员（"+user.userCount+"）");
		}else{
			tv_newfriend.setText("群成员（0）");
		}
		
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getGroupInfo();
	}
}
