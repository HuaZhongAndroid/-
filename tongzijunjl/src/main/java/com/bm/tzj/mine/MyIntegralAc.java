package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.MyAccountBalanceAdapter;
import com.bm.entity.CoachOrderList;
import com.bm.entity.HotGoods;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.tool.Pager;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 我的积分
 * 
 * @author shiyt
 * 
 */
public class MyIntegralAc extends BaseActivity implements OnRefreshListener {
	private Context context;
	private ListView lv_integral;
	private List<CoachOrderList> list = new ArrayList<CoachOrderList>();
	private MyAccountBalanceAdapter adapter;
	private SwipyRefreshLayout mSwipyRefreshLayout;
	public Pager pager = new Pager(10);
	private ImageView img_noData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_integral);
		context = this;
		setTitleName("余额明细");
		initView();
	}

	private void initView() {
		img_noData = findImageViewById(R.id.img_noData);
		img_noData.setVisibility(View.GONE);
		mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
		mSwipyRefreshLayout.setOnRefreshListener(this);
		mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipyRefreshLayout.setColorScheme(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);

		lv_integral = findListViewById(R.id.lv_integral);
		adapter = new MyAccountBalanceAdapter(context, list);
		lv_integral.setAdapter(adapter);
		
		getCoachOrderlist();
	}
	/**
	 * 获取余额明细
	 */
	private void getCoachOrderlist(){
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", App.getInstance().getCoach().coachId);//宝贝年龄
		map.put("pageNum", pager.nextPageToStr());//查询页数
		map.put("pageCount", "10");//每页展示条数
		UserManager.getInstance().getTzjorderCoachOrderlist(context, map, new ServiceCallback<CommonListResult<CoachOrderList>>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonListResult<CoachOrderList> obj) {
				hideProgressDialog();
				if(obj.data.size()>0){
					pager.setCurrentPage(pager.nextPage(),list.size()); 
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
			lv_integral.setVisibility(View.VISIBLE);
		}
		adapter.notifyDataSetChanged();
	}
//	/**
//	 * 获取数据
//	 */
//	public void getData() {
//		for (int i = 0; i < 5; i++) {
//			Model info = new Model();
//			info.name = "【购买赠送】 滚筒 " + (i + 3);
//			info.time = "2015.12." + "1" + (i + 3);
//			info.money = "9" + (i + 3);
//			info.degree=i+"";
//			list.add(info);
//		}
//		adapter.notifyDataSetChanged();
//		adapter.notifyDataSetChanged();
//	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if (direction == SwipyRefreshLayoutDirection.TOP) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((MyIntegralAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							 pager.setFirstPage();
							 list.clear();
							 getCoachOrderlist();
							 mSwipyRefreshLayout.setRefreshing(false);

							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);

							// map.put("pageNum", pager.nextPage() + "");// 页码
							// pager.setCurrentPage(pager.nextPage(),
							// list.size());
						}
					});
				}
			}, 2000);

		} else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((MyIntegralAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							 getCoachOrderlist();

							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);

						}
					});
				}
			}, 2000);
		}
	}
}
