package com.bm.tzjjl.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.entity.BaobeiUserinfo;
import com.bm.im.adapter.BaobeiUserInforAdapter;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.richer.tzjjl.R;
/**
 * 个人中心-我的客户
 * @author Administrator
 *
 */
public class BaoBeiParentInforActivity extends BaseActivity{
	private ListView lvBaobei;
	private Context context;
	private List<BaobeiUserinfo> list=new ArrayList<BaobeiUserinfo>();
	private BaobeiUserInforAdapter adapter;
	private String parentName;
	private TextView tv_search;
	private EditText et_seachName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.baobei_userinfo);
		context = this;
		setTitleName("用户信息");
		initView();
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}



	private void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		String coachId= App.getInstance().getCoach().coachId;
		parentName= et_seachName.getText().toString();
		map.put("coachId", coachId);
		map.put("parentName",parentName);
		this.showProgressDialog();
		UserManager.getInstance().getTzjBaobeiParentInfo(context,map, new ServiceCallback<CommonListResult<BaobeiUserinfo>>() {

			@Override
			public void done(int what, CommonListResult<BaobeiUserinfo> obj) {
				list.clear();
				list.addAll(obj.data);
				adapter.notifyDataSetChanged();
				hideProgressDialog();
			}
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}});
			

			
			
		
			
		
	}

	private void initView() {
		lvBaobei= (ListView) findViewById(R.id.lv_baobei_userinfo);
		tv_search= (TextView) findViewById(R.id.tv_search);
		et_seachName= (EditText) findViewById(R.id.et_seachName);
		adapter= new BaobeiUserInforAdapter(context, list);
		lvBaobei.setAdapter(adapter);
		tv_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData();
			}
		});
	}
}
