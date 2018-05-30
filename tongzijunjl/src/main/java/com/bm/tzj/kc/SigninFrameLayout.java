package com.bm.tzj.kc;

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
import com.bm.base.adapter.CourseSignAdapter;
import com.bm.entity.SigninInfo;
import com.richer.tzjjl.R;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.lib.tool.Pager;
import com.lib.widget.RefreshViewPD;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

public class SigninFrameLayout extends FrameLayout implements CourseSignAdapter.OnSeckillClick,OnRefreshListener {
	private Context context;
	private RefreshViewPD refresh_view;
	/** 分页器 */
	public Pager pager = new Pager(10);
	private ListView lv_course_comment;
	private String state;
	private CourseSignAdapter adapter;
	private List<SigninInfo> list = new ArrayList<SigninInfo>();
	private SwipyRefreshLayout mSwipyRefreshLayout;
	private ImageView img_noData;
	Intent intent = null;
	private int pos=-1;
	String content,tag,degree,strGoodsId;
	public int strSignAllCount=0,strSignCount=0,strUnsignCount=0;//参与（宝贝人数）,已签到数,未签到数
	
	public SigninFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public SigninFrameLayout(Context context, String state,String degree,String goodsId) {
		super(context);
		this.context = context;
		this.state = state;
		this.degree=degree;
		this.strGoodsId = goodsId;
//		if (state.length() == 0) {
//			this.state = "";
//		}

		init();
	}

	public void init() {
		// instance=this;
		
		LayoutInflater.from(context).inflate(R.layout.course_comment_framelayout,
				this);
		img_noData = (ImageView) findViewById(R.id.img_noData);
		img_noData.setVisibility(View.VISIBLE);
		mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
		mSwipyRefreshLayout.setOnRefreshListener(this);
		mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
		mSwipyRefreshLayout.setColorScheme(R.color.color1, R.color.color2,R.color.color3, R.color.color4);
		
		lv_course_comment = (ListView) findViewById(R.id.lv_course_comment);

		adapter = new CourseSignAdapter(context, list);
		lv_course_comment.setAdapter(adapter);
		adapter.setOnSeckillClick(this);
	}

	public void reFresh() {
		pager.setFirstPage();
		list.clear();
		getSinginInfo();
//		setData();
	}
	
	/**
	 * 获取签到信息
	 */
	public void getSinginInfo(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", state);//查询状态   0 未签到  1 已签到  2 所有
		map.put("pageCount", "10");//每页展示条数
		map.put("pageNum", pager.nextPageToStr());
		map.put("userId", App.getInstance().getCoach().coachId);//教练ID
		map.put("baseId",strGoodsId);
		SigninAc.intance.showProgressDialog();
		UserManager.getInstance().getTzjcoachSearchSignInfo(context, map, new ServiceCallback<CommonResult<SigninInfo>>() {
			
			@Override
			public void error(String msg) {
				SigninAc.intance.hideProgressDialog();
				SigninAc.intance.dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonResult<SigninInfo> obj) {
				SigninAc.intance.hideProgressDialog();
				if(null !=obj.data){
					if(obj.data.signUserList.size()>0){
						list.addAll(obj.data.signUserList);
						pager.setCurrentPage(pager.nextPage(),list.size()); 
					}
					
					strSignAllCount = getNullIntData(obj.data.signAllCount);
					strSignCount =getNullIntData(obj.data.signCount);
					strUnsignCount =getNullIntData(obj.data.unsignCount);
					SigninAc.intance.getJoinCount(strSignAllCount,strSignCount ,strUnsignCount );
					
				
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
			lv_course_comment.setVisibility(View.VISIBLE);
		}
		adapter.notifyDataSetChanged();
	}

//	private void setData() {
//		list.clear();
//		/**0签到   1迟到   2正常*/
//		if("001".equals(state)){
//			for (int i = 0; i < 5; i++) {
//				Model info = new Model();
//				info.name="王小明"+i;
//				if(i==1 || i==4){
//					info.degree="0";  //签到
//				}else if(i==2){
//					info.degree="1";//迟到
//				}else{
//					info.degree="2";//正常
//				}
//				list.add(info);
//			}
//		}else if("002".equals(state)){
//			for (int i = 0; i < 3; i++) {
//				Model info = new Model();
//				info.name="王小明"+i;
//				 if(i==0){
//					info.degree="1";//迟到
//				}else{
//					info.degree="2";//正常
//				}
//				list.add(info);
//			}
//		}else if("003".equals(state)){
//			for (int i = 0; i < 2; i++) {
//				Model info = new Model();
//				info.name="王小明"+i;
//				info.degree="0";  //签到
//				list.add(info);
//			}
//		}
//		
//		adapter.notifyDataSetChanged();
//	}
	
	

	@Override
	public void onSeckillClick(int position, int type) {
		pos=position;
		SigninAc.intance.getCoachQd(list.get(position).userId, list.get(position).babyId,2);//宝贝ID
		
		adapter.notifyDataSetChanged();
//		SigninAc.intance.dialogToast("签到");
	}
	
	
	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if (direction == SwipyRefreshLayoutDirection.TOP) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((SigninAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							pager.setFirstPage();
							list.clear();
							getSinginInfo();
							SigninAc.intance.getCoachIsSing();
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
					((SigninAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
//							getData();
							getSinginInfo();
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
							

						}
					});
				}
			}, 2000);
		}
	}
	
	/**
	 * 判断值是否为空
	 * @param arg
	 * @return
	 */
	public static int getNullIntData(String arg) {
		if(TextUtils.isEmpty(arg)){
			return 0;
		}else {
			return Integer.parseInt(arg);
		}
	}

}