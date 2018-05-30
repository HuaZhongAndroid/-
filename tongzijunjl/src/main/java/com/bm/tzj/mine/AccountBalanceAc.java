package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.base.BaseActivity;
import com.bm.base.adapter.MyAccountBalanceAdapter;
import com.bm.entity.CoachOrderList;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.lib.tool.Pager;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 账户余额
 * 
 * @author wanghy
 * 
 */
public class AccountBalanceAc extends BaseActivity implements OnClickListener,OnRefreshListener {

	private Context context;
	/** 分页器 */
	public Pager pager = new Pager(10);
	private ListView lv_listAccount;
	private SwipyRefreshLayout mSwipyRefreshLayout;

	private MyAccountBalanceAdapter adapter;
	private List<CoachOrderList> list = new ArrayList<CoachOrderList>();
	private TextView tv_money,tv_recharge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_accountbalance);
		context = this;
		setTitleName("账号余额");
		initView();
	}

	private void initView() {
		mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
		mSwipyRefreshLayout.setOnRefreshListener(this);
		mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipyRefreshLayout.setColorScheme(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);
		
		
		lv_listAccount = (ListView) findViewById(R.id.lv_listAccount);

		adapter = new MyAccountBalanceAdapter(context, list);
		lv_listAccount.setAdapter(adapter);

		tv_recharge = findTextViewById(R.id.tv_recharge);
		tv_money = (TextView) findViewById(R.id.tv_money);
		TextPaint tp = tv_money.getPaint();
		tp.setFakeBoldText(true);
		tv_recharge.setOnClickListener(this);
		setData();
	}
	
	private void setData() {
		for (int i = 0; i < 5; i++) {
			CoachOrderList info = new CoachOrderList();
			if(i==0||i==2){
				info.changeStatus="1";  //充值
			}else{
				info.changeStatus="0";  //消费
			}
			info.goodsName = "【购买赠送】 滚筒 " + (i + 3);
			info.cDate = "2015.12." + "1" + (i + 3);
			info.account = "9" + (i + 3);
			list.add(info);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_recharge:
//			openActivity(RechargeAc.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if (direction == SwipyRefreshLayoutDirection.TOP) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((AccountBalanceAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
//							pager.setFirstPage();
//							list.clear();
//							getData();
							// mSwipyRefreshLayout.setRefreshing(false);
							
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
							
//							map.put("pageNum", pager.nextPage() + "");// 页码
//							pager.setCurrentPage(pager.nextPage(), list.size());
						}
					});
				}
			}, 2000);

		} else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((AccountBalanceAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
//							getData();
							
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
							

						}
					});
				}
			}, 2000);
		}
	}
}
