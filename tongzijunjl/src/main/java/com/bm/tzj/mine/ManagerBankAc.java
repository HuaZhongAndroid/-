package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.ManagerBankAdapter;
import com.bm.base.adapter.ManagerBankAdapter.onRemoveListon;
import com.bm.entity.Banks;
import com.bm.entity.CoachOrderList;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.util.Constant;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.StringResult;
import com.lib.widget.RefreshViewPD;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;

/**
 * 管理银行卡
 * 
 * @author jiangsh
 * 
 */
public class ManagerBankAc extends BaseActivity  implements onRemoveListon{
	private Context context;
	private com.lib.widget.SwipeListView lv_listMessage;
	private ImageView img_noData;
	private ManagerBankAdapter adapter;
	private List<Banks> list = new ArrayList<Banks>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_message_bank);
		context = this;
		setTitleName("管理银行卡");
		initView();
	}
	

	private void initView() {
		img_noData = findImageViewById(R.id.img_noData);
		img_noData.setVisibility(View.GONE);
		lv_listMessage = (com.lib.widget.SwipeListView) findViewById(R.id.lv_listMessage);

		
		adapter = new ManagerBankAdapter(context, list,lv_listMessage.getRightViewWidth());
		lv_listMessage.setAdapter(adapter);
		adapter.setRemoveListon(this);
		lv_listMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				openActivity(DetialMyMessageAc.class);
				if("ll_bank".equals(getIntent().getStringExtra("tag"))){
					Intent  intent=new Intent(context, BalanceWithdrawalsAc.class);
					for (int i = 0; i < Constant.yhkCode.length; i++) {
						if(list.get(arg2).bankType.equals(Constant.yhkCode[i])){
							intent.putExtra("bankName", Constant.yhkName[i]);
						}
					}
					if(list.get(arg2).bankCardNo.length()==19){
						intent.putExtra("bankNumber", list.get(arg2).bankCardNo.substring(14, 18));
					}
					intent.putExtra("bankCardId", list.get(arg2).bankCardId);
					setResult(002, intent);
					finish();
				}
			}	
		});
		getBanklist();
	}

	/**
	 * 获取银行卡
	 */
	public void getBanklist(){
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", App.getInstance().getCoach().coachId);//
		map.put("pageNum", "0");//查询页数
		map.put("pageCount", "100");//每页展示条数
		UserManager.getInstance().getBankBankCoachlist(context, map, new ServiceCallback<CommonListResult<Banks>>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonListResult<Banks> obj) {
				hideProgressDialog();
				if(obj.data.size()>0){
					list.addAll(obj.data);
				}
				setData();
			}
		});
	}
	private void setData() {
		if (list.size() == 0) {
			img_noData.setVisibility(View.VISIBLE);
		} else {
			img_noData.setVisibility(View.GONE);
			lv_listMessage.setVisibility(View.VISIBLE);
		}
		adapter.notifyDataSetChanged();
	}
	/**
	 * 删除银行卡
	 */
	public void deleteBank(int index){
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("bankCardId", list.get(index).bankCardId);//
		UserManager.getInstance().getBankDelBank(context, map, new ServiceCallback<StringResult>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				toast("删除成功");
				list.clear();
				getBanklist();
			}
		});
	}

	@Override
	public void remove(int position) {
		deleteBank(position);
	}


//	private void setData() {
//		for (int i = 0; i < 5; i++) {
//			Model info = new Model();
//			list.add(info);
//		}
//		adapter.notifyDataSetChanged();
//	}
	
	 

	 
}
