package com.bm.tzj.mine;

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
import com.bm.base.GlobalParam;
import com.bm.base.adapter.MyCourseAdapter;
import com.bm.dialog.UtilDialog;
import com.bm.entity.CoachInfo;
import com.bm.entity.HotGoods;
import com.bm.entity.Model;
import com.bm.im.ac.ChatActivity;
import com.bm.im.api.ImApi;
import com.bm.im.tool.Constant;
import com.bm.tzj.kc.CourseCommentAc;
import com.bm.tzj.kc.SigninAc;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.google.android.gms.games.Game;
import com.google.gson.Gson;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;
import com.lib.http.result.StringResult;
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
public class MyCourseListFrameLayout extends FrameLayout implements
		MyCourseAdapter.OnSeckillClick,OnRefreshListener {
	private Context context;
	private RefreshViewPD refresh_view;
	/** 分页器 */
	public Pager pager = new Pager(10);
	private ListView lv_listCourse;
	private String state;
	private ImageView img_noData;
	private MyCourseAdapter adapter;
	private List<HotGoods> list = new ArrayList<HotGoods>();
	private SwipyRefreshLayout mSwipyRefreshLayout;
	Intent intent = null;
	private int pos=-1;
	HotGoods hotGoods;
	String[] strMembers;
	
	private String day;
	
	public MyCourseListFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public MyCourseListFrameLayout(Context context, String state) {
		super(context);
		this.context = context;
		this.state = state;
		if (state.length() == 0) {
			this.state = "";
		}

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

		adapter = new MyCourseAdapter(context, list,state);
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
		list.clear();
		getCourseList();
	}
	
	public void setDay(String day)
	{
		this.day = day;
	}
	
	private void getCourseList() {
		
		if(null == App.getInstance().getCoach()){
			return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", App.getInstance().getCoach().coachId);//教练ID
		map.put("classStatus", state);//课程状态   0 所有  1 未开始  2 进行中  3 已结束  4 待付款
		map.put("pageCount", "10");//每页展示条数
		map.put("pageNum", pager.nextPageToStr());
		map.put("day", day);
		
		MainAc.intance.showProgressDialog();
		UserManager.getInstance().getTzjgoodsGoodscourseinfo(context, map, new ServiceCallback<CommonListResult<HotGoods>>() {
			
			@Override
			public void error(String msg) {
				MainAc.intance.hideProgressDialog();
				MainAc.intance.dialogToast(msg);				
			}
			
			@Override
			public void done(int what, CommonListResult<HotGoods> obj) {
				MainAc.intance.hideProgressDialog();
				if(obj.data.size()>0){
					pager.setCurrentPage(pager.nextPage(),list.size()); 
					GlobalParam.jiaolian = obj.data.get(0).coachId;
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
			lv_listCourse.setVisibility(View.VISIBLE);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onSeckillClick(int position, int type) {
		pos=position;
		if (type == 1) {// 建群
			UtilDialog.dialogTwoBtnResults(context, "建群后，会将参与本课程的所有家长都加入群中，你可以在消息中找到群。确定建群吗？", "返回", "确定建群", handler, 1,2);
		} else if (type == 2) {// 取消课程
			UtilDialog.dialogTwoBtnResults(context, "一周内取消课程超过三次，一个月内不得再预约课程，确定取消本次课程吗？", "确定取消课程", "返回", handler, 3,4);
		} else if (type == 3) {// 签到
			intent = new Intent(context, SigninAc.class);
			intent.putExtra("degree", list.get(position).goodsType);
			intent.putExtra("states", state);
			intent.putExtra("hotGoods", list.get(position));;//课程信息
			context.startActivity(intent);
		}else if(type==4){
			intent = new Intent(context, CourseCommentAc.class);
			intent.putExtra("degree", list.get(position).typeName);
			intent.putExtra("hotGoods", list.get(position));
			context.startActivity(intent);
		}
	}
	 
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
//				if(Integer.valueOf(list.get(pos).orderNum)>0){
//					List<HotGoods> joinList = list.get(pos).baby;
//					String[] strJoinMember = new String[joinList.size()];
//					for (int i = 0; i < joinList.size(); i++) {
//						strJoinMember[i]=joinList.get(i).userId;
//					}
//					ImApi.createGroup(list.get(pos).goodsName, "",strJoinMember,handler);
//				}else{
//					ImApi.createGroup(list.get(pos).goodsName, "",null,handler);
//				}
				
				addGroupInfo();
				break;
			case 3:
				if(pos>-1){
					delCourse();
				}
				break;
			case 0://调用环信群列表成功返回值
				reFresh();
				break;
			case ImApi.CODE_CRETE://环信创建群成功返回状态值
//				System.out.println("王"+msg.obj.toString());
//				Intent intent = new Intent(context, ChatActivity.class);
//				intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
//				intent.putExtra("userId",  msg.obj.toString());
//				MainAc.intance.startActivityForResult(intent, 0);
				
				
//				addGroupInfo();
				break;
		};
	};
	};
	
	
	/**
	 * 建立群组信息
	 */
	public void addGroupInfo(/*final String groupId*/){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("groupName", list.get(pos).goodsName);//群名称
		map.put("goodsId", list.get(pos).goodsId);//课程id
		map.put("owner", App.getInstance().getCoach().coachId);//教练ID
		MainAc.intance.showProgressDialog();
		UserManager.getInstance().getImAddGroupInfo(context, map, new ServiceCallback<CommonResult<CoachInfo>>() {
			
			@Override
			public void error(String msg) {
				MainAc.intance.hideProgressDialog();
				MainAc.intance.dialogToast(msg);
			}
			
			@Override
			public void done(int what, final CommonResult<CoachInfo> obj) {
				
				
				if(null != obj.data){
					// 进入群聊
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							MainAc.intance.hideProgressDialog();
							Intent intent = new Intent(context, ChatActivity.class);
							intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
							intent.putExtra("userId",  obj.data.groupId);
//							intent.putExtra("mytag", "MyCourseListFrameLayout");//建群要发一条消息
							MainAc.intance.startActivityForResult(intent, 0);
							
						}
					}, 3000);
					
				}
				
				
			}
		});
	}
	
	/**
	 * 取消课程
	 */
	public void delCourse(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("coachId", App.getInstance().getCoach().coachId);//教练ID
		map.put("goodsId", list.get(pos).goodsId);//课程ID
		
		MainAc.intance.showProgressDialog();
		UserManager.getInstance().getTzjcoachDelgoods(context, map, new ServiceCallback<StringResult>() {
			
			@Override
			public void error(String msg) {
				MainAc.intance.hideProgressDialog();
				MainAc.intance.dialogToast(msg);
				
			}
			
			@Override
			public void done(int what, StringResult obj) {
				MainAc.intance.hideProgressDialog();
				list.remove(pos);
				adapter.notifyDataSetChanged();
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
					((MainAc) context).runOnUiThread(new Runnable() {
						@Override
						public void run() {
							pager.setFirstPage();
							list.clear();
							getCourseList();
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
							getCourseList();
							// 刷新设置
							mSwipyRefreshLayout.setRefreshing(false);
							

						}
					});
				}
			}, 2000);
		}
	}

}
