package com.bm.tzjjl.activity;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.entity.BaobeiUserinfo;
import com.bm.im.adapter.BaobeiJiluAdapter;
import com.bm.im.adapter.BaobeiUserInforAdapter;
import com.bm.im.entity.BaobeiFollowupEntity;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;
import com.lib.http.result.StringResult;
import com.richer.tzjjl.BaobeiJiluActivity;
import com.richer.tzjjl.R;
/**
 * 报备-新增
 * @author mayn
 *
 */


public class BaoBeiActivity extends BaseActivity  {
	private Context context;
	private EditText etContext;
	private RadioGroup rgGtfs;
	private RadioButton rbwx;
	private RadioButton rbphone;
	private RadioButton rbmessage;
	private RadioButton rbxianc;
	private RadioGroup rgGtcd;
	private RadioButton rbyyx;
	private RadioButton rbcb;
	private RadioButton rbsd;
	private Button btnSubmin;
	private String userId;
	private String  followupMode;
	private String followupDegree;
	private List<BaobeiFollowupEntity> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_baobei);
		context = this;
		setTitleName("报备");
		tv_right.setText("记录");
		userId = getIntent().getStringExtra("userid");
		followupMode = "1";
		followupDegree = "1";
		initView();
		initData();
	}



	private void initView(){
		etContext = findEditTextById(R.id.et_baobeicontext);
		rgGtfs = (RadioGroup) findViewById(R.id.rg_baobei_gtfs);
		rbphone =  (RadioButton) findViewById(R.id.rb_telpone);
		rbmessage =  (RadioButton) findViewById(R.id.rb_message);
		rbxianc =  (RadioButton) findViewById(R.id.rb_xianc);
		rbwx =  (RadioButton) findViewById(R.id.rb_wx);
		rgGtcd = (RadioGroup) findViewById(R.id.rg_baobei_gtcd);
		rbyyx =  (RadioButton) findViewById(R.id.rb_yyx);
		rbcb =  (RadioButton) findViewById(R.id.rb_cb);
		rbsd =  (RadioButton) findViewById(R.id.rb_sd);
		btnSubmin=findButtonById(R.id.btu_baobei_submit);
	}
	private void initData() {
		rgGtfs.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				//获取变更后的选中项的ID
				int radioButtonId = group.getCheckedRadioButtonId();
				//根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton)findViewById(radioButtonId);
				followupMode = rb.getTag().toString();
			}
		});
		rgGtcd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				//获取变更后的选中项的ID
				int radioButtonId = group.getCheckedRadioButtonId();
				//根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton)findViewById(radioButtonId);
				followupDegree = rb.getTag().toString();
			}
		});




		btnSubmin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HashMap<String, String> map = new HashMap<String, String>();
				String coachId= App.getInstance().getCoach().coachId;
				map.put("coachId", coachId);//教练id
				map.put("userId", userId);//用户id
				String content = etContext.getText().toString().trim();
				if(content.length() == 0)
				{
					dialogToast("请输入报备内容");
					return;
				}
				map.put("content", content);//沟通内容
				map.put("followupMode", followupMode);//	沟通方式：1微信 2电话 3短信 4现场
				map.put("followupDegree", followupDegree);//沟通程度：1有意向 2初步 3深度
				showProgressDialog();
				UserManager.getInstance().getTzjuserFollowup(context,map, new ServiceCallback<CommonResult<BaobeiFollowupEntity>>() {

					@Override
					public void done(int what, CommonResult<BaobeiFollowupEntity> obj) {
						hideProgressDialog();
						finish();
						Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();		
					}

					@Override
					public void error(String msg) {
						dialogToast(msg);
						hideProgressDialog();
					}



				});

			}
		});

		tv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,BaobeiJiluActivity.class); 	
				intent.putExtra("userId", userId);
				startActivity(intent);
			}
		});
	}

}
