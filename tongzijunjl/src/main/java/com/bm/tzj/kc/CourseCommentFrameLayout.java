package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.adapter.CourseCommentAdapter;
import com.bm.dialog.UtilDialog;
import com.bm.entity.HotGoods;
import com.bm.entity.Model;
import com.bm.entity.SigninInfo;
import com.richer.tzjjl.R;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;
import com.lib.tool.Pager;
import com.lib.widget.RefreshViewPD;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 我的课程
 * 
 * @author wanghy
 * 
 */
public class CourseCommentFrameLayout extends FrameLayout implements CourseCommentAdapter.OnSeckillClick,OnRefreshListener {
	private Context context;
	private RefreshViewPD refresh_view;
	/** 分页器 */
	public Pager pager = new Pager(10);
	private ListView lv_course_comment;
	private ImageView img_noData;
	private String state;
	private CourseCommentAdapter adapter;
	private List<SigninInfo> list = new ArrayList<SigninInfo>();
	private List<Model> listRefused = new ArrayList<Model>();
	private SwipyRefreshLayout mSwipyRefreshLayout;
	public String strSignAllCount="0",strSignCount="0",strUnsignCount="0";//参与（宝贝人数）,已签到数,未签到数
	Intent intent = null;
	private int pos=-1;
	String degree,strGoodsId="",strComment="",strTag="1",strMedalIds="", babyId,goodsName;
	public CourseCommentFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public CourseCommentFrameLayout(Context context, String state,String degree,String goodsId, String babayId, String goodsName) {
		super(context);
		this.context = context;
		this.state = state;
		this.degree=degree;
		this.strGoodsId = goodsId;
		this.babyId = babyId;
		this.goodsName = goodsName;
		if (state.length() == 0) {
			this.state = "";
		}

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

		adapter = new CourseCommentAdapter(context, list);
		lv_course_comment.setAdapter(adapter);
		adapter.setOnSeckillClick(this);
	}

	public void reFresh() {
		pager.setFirstPage();
		list.clear();
		getCommentInfo();
		getMedalListInfo();
	}
	
	/**
	 * 获取评论信息
	 */
	public void getCommentInfo(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", state);//查询状态   0 未评价  1 已评价  2 所有
		map.put("pageCount", "10");//每页展示条数
		map.put("pageNum", pager.nextPageToStr());
		map.put("userid", App.getInstance().getCoach().coachId);//教练ID
		map.put("baseId",strGoodsId);
		CourseCommentAc.intance.showProgressDialog();
		UserManager.getInstance().getTzjgoodsPassL(context, map, new ServiceCallback<CommonListResult<SigninInfo>>() {
			
			@Override
			public void error(String msg) {
				CourseCommentAc.intance.hideProgressDialog();
				CourseCommentAc.intance.dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonListResult<SigninInfo> obj) {
				CourseCommentAc.intance.hideProgressDialog();
				 if (obj.data.size() > 0) {
					 pager.setCurrentPage(pager.nextPage(),list.size());
					 ArrayList<SigninInfo> signininfos = obj.data;
					 for (int i = 0; i < signininfos.size(); i++) {
						 signininfos.get(i).goodsName = goodsName;
					}
					 list.addAll(signininfos);
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
	
	/**
	 * 获取勋章信息
	 */
	public void getMedalListInfo(){
		listRefused.clear();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("baseId", strGoodsId);
		UserManager.getInstance().getTzjcoachGoodsMedallist(context, map, new ServiceCallback<CommonListResult<Model>>() {
			
			@Override
			public void error(String msg) {
				CourseCommentAc.intance.dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonListResult<Model> obj) {
				if(obj.data.size()>0){
					listRefused.addAll(obj.data);
					Model info =new Model();
					info.medalId="-1";
					info.medalName="全选";
					listRefused.add(info);
				}
			}
		});
		
	}

	@Override
	public void onSeckillClick(int position, int type) {
		pos=position;
		String strName = list.get(position).realName== null?"未知人":list.get(position).realName;
		if (type == 1) {// 评价
			if(!"4".equals(degree)){   //城市营地类型和户外俱乐部类型：弹出评分框1      期大露营类型：弹出评分框2
				((CourseCommentAc)context).dialogTwoBtnCommResult("对"+strName+"的评价", "取消", "提交", handler, 1);
			}else{
				((CourseCommentAc)context).dialogTwoBtnCommTwoResult(listRefused,"对"+strName+"的评价", "取消", "提交", handler, 2);
			}
		} 
	}
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:	  //评分弹出框1  城市营地 户外俱乐部
				Model mInfo = (Model) msg.obj;
				strComment = mInfo.content;
				strTag = mInfo.degree;
				submitComment();
				break;
			case 2:  //评分弹出框2   暑期大陆营
				strMedalIds="";
				List<Model> lists = (List<Model>) msg.obj;
				for (int i = 0; i < lists.size(); i++) {
					if(!lists.get(i).medalId.equals("-1"))
					{
						strMedalIds += lists.get(i).medalId + ",";
					}
				}
				strComment = lists.get(0).content.toString().trim();
				submitComment();
				UtilDialog.listRefu.clear();  //清除之前选中的数据
				break;
		};
	};
	};
	
	
	/**
	 * 添加评论
	 */
	public void submitComment(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("coachId", App.getInstance().getCoach().coachId);//教练ID
		map.put("content", strComment);
		map.put("baseId", strGoodsId);//课程ID
		map.put("isPass", strTag);//是否通过
		map.put("userid", list.get(pos).userId);//评价人的ID
		map.put("goodsType", degree);
		map.put("medalIds", strMedalIds);
		map.put("babyId", list.get(pos).babyId);
		
		CourseCommentAc.intance.showProgressDialog();
		UserManager.getInstance().getTzjcoachAddPass(context, ((CourseCommentAc)context).uploadListImg, map, new ServiceCallback<CommonResult<Model>>() {
			
			@Override
			public void error(String msg) {
				CourseCommentAc.intance.dialogToast(msg);
				CourseCommentAc.intance.hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonResult<Model> obj) {
				CourseCommentAc.intance.hideProgressDialog();
				CourseCommentAc.intance.getDate();
				CourseCommentAc.intance.getPassCount();
			}
		});
		
		
		
	}
	
	
	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {
		if (direction == SwipyRefreshLayoutDirection.TOP) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// Hide the refresh after 2sec
					((CourseCommentAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							pager.setFirstPage();
							list.clear();
							getCommentInfo();
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
					((CourseCommentAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getCommentInfo();
							
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
							

						}
					});
				}
			}, 2000);
		}
	}

}
