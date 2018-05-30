package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.adapter.GrabClassAdapter;
import com.bm.dialog.UtilDialog;
import com.bm.entity.HotGoods;
import com.bm.tzj.city.City;
import com.bm.tzj.kc.PayInfoAc;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.tool.Pager;
import com.lib.tool.SharedPreferencesHelper;
import com.lib.widget.RefreshViewPD;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 抢课
 * 
 * @author wanghy
 * 
 */
public class GrabClassListFrameLayout extends FrameLayout implements
GrabClassAdapter.OnSeckillClick,OnRefreshListener {
	private Context context;
	private RefreshViewPD refresh_view;
	/** 分页器 */
	public Pager pager = new Pager(10);
	private ListView lv_listCourse;
	private String state;
	private ImageView img_noData;
	private GrabClassAdapter adapter;
	private List<HotGoods> list = new ArrayList<HotGoods>();
	private SwipyRefreshLayout mSwipyRefreshLayout;
	Intent intent = null;
	String strClassTime="",strSearchName="",strTag="";//上课时间,关键字,判断从哪个页面进来

	public GrabClassListFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public GrabClassListFrameLayout(Context context, String state) {
		super(context);
		this.context = context;
		this.state = state;

		init();
	}

	public void init() {
		// instance=this;
		LayoutInflater.from(context).inflate(R.layout.my_course_framelayout,
				this);
		img_noData = (ImageView) findViewById(R.id.img_noData);
		img_noData.setVisibility(View.VISIBLE);
		mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
		mSwipyRefreshLayout.setOnRefreshListener(this);
		mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipyRefreshLayout.setColorScheme(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);
		
		
		lv_listCourse = (ListView) findViewById(R.id.lv_listCourse);

		adapter = new GrabClassAdapter(context, list);
		lv_listCourse.setAdapter(adapter);
		adapter.setOnSeckillClick(this);
		// adapter.setClickDelete(new ClickDelete() {
		// @Override
		// public void delete(String id) {
		// delActivityId = id;
		// UtilDialog.dialogTwoBtnResultCode(context, "您确定要删除该条活动数据",
		// "取消","确定",handler,2);
		// // deleteAc(id);
		// }
		// });
	}

	public void reFresh() {
		pager.setFirstPage();
		list.clear();//清空数据
		getGrabClass();
	}
	
	/**
	 * 抢课数据
	 */
	public void getGrabClass(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		strClassTime = SharedPreferencesHelper.getString("classTime");
		strSearchName = SharedPreferencesHelper.getString("searchName");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId",  App.getInstance().getCoach().coachId);//教练ID
		City city = App.getInstance().getCityCode();
		
		if(null!=city&&!TextUtils.isEmpty(city.cityName)){
			map.put("cityName", city.cityName);//城市名称
			map.put("lon", city.lng);//经度
			map.put("lat",  city.lat);//纬度
		}else{
			map.put("cityName", "西安市");//城市名称
		}
		map.put("regulationSort", state);//排序规则    1 离我最近 2 最新发布 3 价格最高 4 名额最多
		map.put("classTime", strClassTime);//上课时间  默认是当天
		map.put("pageNum", pager.nextPageToStr());//查询页数
		map.put("pageCount", "10");//每页展示条数
		map.put("searchName", strSearchName);//关键字搜索
		
		MainAc.intance.showProgressDialog();
		UserManager.getInstance().getTzjgoodsSearchGoodsCourseInfoForCoach(context, map, new ServiceCallback<CommonListResult<HotGoods>>() {
			
			@Override
			public void error(String msg) {
				MainAc.intance.dialogToast(msg);
				MainAc.intance.hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonListResult<HotGoods> obj) {
				MainAc.intance.hideProgressDialog();
				if(obj.data.size()>0){
					list.addAll(obj.data);
					pager.setCurrentPage(pager.nextPage(),list.size()); 
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
			lv_listCourse.setVisibility(View.VISIBLE);
		}
		adapter.notifyDataSetChanged();
	}



	@Override
	public void onSeckillClick(int position, int type) {
		if (type == 1) {// 删除
			UtilDialog.dialogTwoBtnContentTtile(context, "确定要删除该数据？", "取消","确定","提示",handler,2);
		} else if (type == 2) {// 去评价
			intent = new Intent(context, AddCommentAc.class);
			MyCourseAc.intance.startActivity(intent);
		} else if (type == 3) {// 去付款
			intent = new Intent(context, PayInfoAc.class);
			MyCourseAc.intance.startActivity(intent);
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:// 删除事件
				break;
		};
	};
	};
	
	
	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if (direction == SwipyRefreshLayoutDirection.TOP) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((MainAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							pager.setFirstPage();
							list.clear();
							getGrabClass();
							
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
						}
					});
				}
			}, 2000);

		} else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((MainAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getGrabClass();
							
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
							

						}
					});
				}
			}, 2000);
		}
	}

}
