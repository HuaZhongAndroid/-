package com.bm.tzjjl.activity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseCaptureFragmentActivity;
import com.bm.entity.CoachInfo;
import com.bm.entity.User;
import com.bm.im.api.ImApi;
import com.bm.im.tool.DemoHelper;
import com.bm.im.tool.IMTool;
import com.bm.tzj.fm.BalaFm;
import com.bm.tzj.fm.CourseFm;
import com.bm.tzj.fm.GrabClassFm;
import com.bm.tzj.fm.MineFm;
import com.bm.tzj.mine.SettingAc;
import com.bm.util.BaseDataUtil;
import com.bm.view.TabMyView;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.easemob.chat.EMMessage;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.lib.tool.SharedPreferencesHelper;
import com.lib.widget.BadgeView;
import com.richer.tzjjl.R;

/**
 *
 * 主页
 *
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class MainAc extends BaseCaptureFragmentActivity implements OnClickListener,EMEventListener {

	static final String TAG = MainAc.class.getSimpleName();

	private CourseFm indexFm; //代课
	private BalaFm messageFm;
	private MineFm mineFm;    //我的
	private GrabClassFm grabClassFm;//选课


//	private View indexLayout;
//	private View messageLayout;
//	private View meLayout;
//	private View pullulateLayout;
//
//	private ImageView iv_a;
//	private ImageView iv_b;
//	private ImageView iv_d;
//	private ImageView iv_e;

	private TabMyView[] tabs = new TabMyView[3];

	private FragmentManager fragmentManager;

	private Context context;
	public static MainAc intance=null;

	/** 每次启动程序 是否是第一次定位 */
	private boolean isFirstLocation = true;

	//判断右上角的位置
	private int position=-1;
	//判断是用户登录还是游客登陆
	private int tag=-1;

	//	private TextView tv_yqcdqr;
	BadgeView badge=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Util.isCreateRootPath(this);
		contentView(R.layout.ac_mian);
		rl_top.setVisibility(View.GONE);
		context = this;
		tag=getIntent().getIntExtra("tag", 1);
		intance=this;
		initView();

		fragmentManager = getSupportFragmentManager();
		// 第一次启动时选中第2个tab

		setTabSelection(0);
//		if(2==tag){
//			setTabSelection(2);
//		}else if(1==tag){
//			setTabSelection(0);
//		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "1");
		map.put("cityId", "310100");
		map.put("userId", "1e55ff20a7264a0a9a3ebe02b066a5f8");

		//AsyncHttpHelp.httpPost(this,map);

		BaseDataUtil.LoadBaseData(this);// 加载基础数据
	}

	private void initView() {

		LinearLayout ll = (LinearLayout) findViewById(R.id.tabs_ll);

		String[] txts = {"选课", "代课", "我的"};
		int[] redIdN = {R.drawable.tab_0_n, R.drawable.tab_1_n, R.drawable.tab_2_n};
		int[] redIdP = {R.drawable.tab_0_p, R.drawable.tab_1_p, R.drawable.tab_2_p};

		for (int i = 0; i < ll.getChildCount(); i++) {
			TabMyView tab = ((TabMyView) ll.getChildAt(i));
			tabs[i] = tab;
			tab.setValue(txts[i], redIdN[i], redIdP[i]);
			tabs[i].setOnClickListener(this);
			tabs[i].setId(770 + i);
		}


		badge = new BadgeView(context, tabs[0]);

		badge.setBackgroundResource(R.drawable.ease_unread_count_bg);

	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
//
//		iv_a.setImageResource(R.drawable.m_3_h);
//		iv_b.setImageResource(R.drawable.m_2_h);
//		iv_d.setImageResource(R.drawable.m_4_h);
//		iv_e.setImageResource(R.drawable.m_1_h);
//
//		indexLayout.setBackgroundResource(R.color.transparent);
//		messageLayout.setBackgroundResource(R.color.transparent);
//		meLayout.setBackgroundResource(R.color.transparent);
//		pullulateLayout.setBackgroundResource(R.color.transparent);


		for (int i = 0; i < tabs.length; i++) {
			tabs[i].setSelect(false);
		}
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (indexFm != null) {
			transaction.hide(indexFm);
		}
		if (messageFm != null) {
			transaction.hide(messageFm);
		}
		if (mineFm != null) {
			transaction.hide(mineFm);
		}
		if (grabClassFm != null) {
			transaction.hide(grabClassFm);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case 770:
				setTabSelection(0);
				rl_top.setVisibility(View.GONE);
				position=0;
				hideLeft();
				break;
			case 771:
				setTabSelection(1);
				rl_top.setVisibility(View.GONE);
//			setTitleName("我的课程");
				hideLeft();
				position=1;
				break;
			case 772:
				rl_top.setVisibility(View.GONE);
				setTabSelection(2);
//			setTitleName("我的");
				hideLeft();
				position=2;
				break;
			default:
				break;
		}

	}

	@Override
	public void clickRight() {
		super.clickRight();
//		if(position==3){
//			openActivity(SendContentAc.class);
//		}else
		if(position==4){
			Intent intent=new Intent(context, SettingAc.class);
			startActivity(intent);
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 *
	 * @param index
	 *
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
			case 0:
				tabs[0].setSelect(true);
				if (grabClassFm == null) {
					grabClassFm = new GrabClassFm();
					transaction.add(R.id.content, grabClassFm);
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(grabClassFm);
					grabClassFm.getDate();
				}
				break;
			case 1:
				tabs[1].setSelect(true);

				if (indexFm == null) {
					indexFm = new CourseFm();
					transaction.add(R.id.content, indexFm);
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(indexFm);
					indexFm.getData();
				}
				break;
			case 2:
				tabs[2].setSelect(true);
				if (mineFm == null) {
					mineFm = new MineFm();
					transaction.add(R.id.content, mineFm);
				} else {
					transaction.show(mineFm);
				}
				break;
			default:
				break;
		}
		transaction.commit();
	}

	public static int isJumpMine = 0;
	@Override
	protected void onResume() {
		super.onResume();


		getCoachInfo();//获取教练信息
		// register the event listener when enter the foreground
		EMChatManager.getInstance().registerEventListener(this,new EMNotifierEvent.Event[] { EMNotifierEvent.Event.EventNewMessage ,EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventConversationListChanged});

		if(isJumpMine ==3){
			getMyCourse();
		}
	}

	public void getMyCourse(){
		hideLeft();
		setTabSelection(1);
		rl_top.setVisibility(View.GONE);
		position=1;
		isJumpMine = 0;
	}


	// 拍照
	public void takep() {
		takePhoto();
	}

	// 获取照片
	public void pickp() {
		pickPhoto();
	}

	@Override
	protected void onPhotoTaked(String photoPath) {
		if(null!=mineFm){
			mineFm.uploadListImg.add(photoPath);
			mineFm.setImage();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	private void  initIMComment(){

		if(messageFm != null) {
			messageFm.refresh();
		}

	}
	/**
	 *
	 * msg = wat = 0  login
	 *
	 * msg= wat = 2   user refush
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			System.out.println("测试"+msg.what);
			switch(msg.what){
				case 0:


					DemoHelper sdkHelper = DemoHelper.getInstance();
					sdkHelper.pushActivity(MainAc.this);

					// 注册群组和联系人监听
					DemoHelper.getInstance().registerGroupAndContactListener();
					// register the event listener when enter the foreground
					EMChatManager.getInstance().registerEventListener(MainAc.this,
							new EMNotifierEvent.Event[] { EMNotifierEvent.Event.EventNewMessage ,EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventConversationListChanged});
					initIMComment();


//				EaseUserUtils.setUserAvatar(context, username, App.getInstance().);
					break;
				case 2:
					refreshUIWithMessage();
					break;
			}


		};
	};


	/*
	 * 获取教练信息
	 */
	public void getCoachInfo() {

		//当登录后刷新进入首页
		CoachInfo coachInfo = App.getInstance().getCoach();
		HashMap<String, String> map = new HashMap<String, String>();

		if (coachInfo!= null) {

			User userPost = new User();
			if (null != coachInfo.coachId) {
				map.put("userId", coachInfo.coachId);
			}else {
				map.put("userId", SharedPreferencesHelper.getString("coachId"));
			}
			UserManager.getInstance().getTzjcoachSearchCoachUserInfo2(context, map, new ServiceCallback<CommonResult<CoachInfo>>() {

				@Override
				public void error(String msg) {
					MainAc.intance.toast(msg);
				}

				@Override
				public void done(int what, CommonResult<CoachInfo> obj) {
					if (obj != null && obj.data != null) {
						App.getInstance().setUser(null);
						App.getInstance().setCoach(obj.data);// 存储教练信息
						IMTool.loginIM(handler);  //handler  message  wat = 2
						ImApi.getAppSys(context,App.getInstance().getCoach().coachId, handler);
						//刷新个人中心的数据
						if(MineFm.instance!=null){
							MineFm.instance.hideOrView();
							MineFm.instance.initData();
						}
					} else {
						dialogToast("获取数据失败");
						MainAc.intance.toast("获取数据失败");
					}

				}
			});
		}
	}


	public void toast(String msg) {
		App.toast(msg);
	}




	/**
	 * 监听事件
	 */
	@Override
	public void onEvent(EMNotifierEvent event) {
		switch (event.getEvent()) {
			case EventNewMessage: // 普通消息
			{
				EMMessage message = (EMMessage) event.getData();
				/**
				 *
				 * handler  message  wat = 2
				 */
				ImApi.getAppSys(context,message.getUserName(),handler);
				// 提示新消息
				DemoHelper.getInstance().getNotifier().onNewMsg(message);

				refreshUIWithMessage();
				break;
			}

			case EventOfflineMessage: {
				refreshUIWithMessage();
				break;
			}

			case EventConversationListChanged: {
				refreshUIWithMessage();
				break;
			}

			default:
				break;
		}
	}
	/**
	 * 获取未读消息数
	 *
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		int chatroomUnreadMsgCount = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		for(EMConversation conversation:EMChatManager.getInstance().getAllConversations().values()){
			if(conversation.getType() == EMConversationType.ChatRoom)
				chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
		}
		return unreadMsgCountTotal-chatroomUnreadMsgCount;
	}
	private void refreshUIWithMessage() {
		runOnUiThread(new Runnable() {
			public void run() {
				// 刷新bottom bar消息未读数
				int count = getUnreadMsgCountTotal();
				if (count > 0) {
					badge.setText(""+count);
					badge.setGravity(Gravity.CENTER);
					badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
					badge.show();
				} else {
					badge.hide();
				}
				// 当前页面如果为聊天历史页面，刷新此页面
				if (messageFm != null) {
					messageFm.refresh();
				}
			}
		});
	}


	@Override
	protected void onStop() {
		EMChatManager.getInstance().unregisterEventListener(this);
		super.onStop();
	}

	private OnTabActivityResultListener onTabActivityResultListener;

	public void setOnTabActivityResultListener(
			OnTabActivityResultListener onTabActivityResultListener) {
		this.onTabActivityResultListener = onTabActivityResultListener;
	}
	public interface OnTabActivityResultListener {
		public void onTabActivityResult(int requestCode, int resultCode, Intent data);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
									Intent mIntent) {
		super.onActivityResult(requestCode, resultCode, mIntent);
		if(mIntent!=null && onTabActivityResultListener!=null){
			onTabActivityResultListener.onTabActivityResult(requestCode, resultCode, mIntent);
		}

	}


	/**
	 * 显示红点
	 * @param index
	 * @param show
	 */
	public void showRed(int index,boolean show){
		tabs[index].showRed(show);
	}
}
