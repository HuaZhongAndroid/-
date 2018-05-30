package com.richer.tzjjl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.entity.BaobeiUserinfo;
import com.bm.im.adapter.BaobeiJiluAdapter;
import com.bm.im.adapter.BaobeiUserInforAdapter;
import com.bm.im.entity.BaobeijiluEntity;
import com.bm.tzjjl.activity.BaoBeiActivity;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BaobeiJiluActivity extends BaseActivity {
private Context context;
private String userId;
private ListView lvBaobeijilu;
private List<BaobeijiluEntity> list=new ArrayList<BaobeijiluEntity>();
private BaobeiJiluAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.activity_baobei_jilu);
		context = this;
		setTitleName("报备记录");
		userId= getIntent().getStringExtra("userId");
		inintView();
		inintData();
	}
	private void inintView() {
		lvBaobeijilu= (ListView) findViewById(R.id.lv_baobeijilu);
		adapter= new BaobeiJiluAdapter(context, list);
		lvBaobeijilu.setAdapter(adapter);		
	}
	private void inintData() {
		HashMap<String, String> map = new HashMap<String, String>();
		String coachId= App.getInstance().getCoach().coachId;
		map.put("coachId", coachId);
		map.put("userId",userId);
		UserManager.getInstance().getTzjBaobeiuserlist(context,map, new ServiceCallback<CommonListResult<BaobeijiluEntity>>() {

			@Override
			public void done(int what, CommonListResult<BaobeijiluEntity> obj) {
				
				list.clear();
				if(obj.data.size() > 0)
				{
					list.addAll(obj.data);
					list.get(0).isShowContent = true;
				}
				adapter.notifyDataSetChanged();
			}
			@Override
			public void error(String msg) {
			}
			
		});
			

				
	}
	
	
	
}
