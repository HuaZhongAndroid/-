package com.bm.tzj.fm;

import java.util.ArrayList;
import java.util.List;

import net.grobas.view.PolygonImageView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.dialog.ThreeButtonDialog;
import com.bm.dialog.UtilDialog;
import com.bm.entity.CoachInfo;
import com.bm.im.tool.DemoHelper;
import com.bm.tzj.kc.DisclaimerAc;
import com.bm.tzj.mine.BalanceWithdrawalsAc;
import com.bm.tzj.mine.FeedBackAc;
import com.bm.tzj.mine.LoginAc;
import com.bm.tzj.mine.ManagerBankAc;
import com.bm.tzj.mine.MyIntegralAc;
import com.bm.tzj.mine.MyMessageAc;
import com.bm.tzj.mine.PersonalInformation;
import com.bm.tzj.mine.UpdatePwdAc;
import com.bm.tzj.mine.UpdateVAc;
import com.bm.tzjjl.activity.BaoBeiActivity;
import com.bm.tzjjl.activity.BaoBeiParentInforActivity;
import com.bm.tzjjl.activity.MainAc;
import com.bm.tzjjl.activity.MyWebActivity;
import com.richer.tzjjl.R;
import com.bm.util.DataCleanManager;
import com.bm.util.Util;
import com.easemob.EMCallBack;
import com.lib.tool.SharedPreferencesHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * 我的
 * 
 * @author wangqiang
 * 
 */
@SuppressLint("NewApi")
public class MineFm extends Fragment implements OnClickListener {
	private Context context;

	private PolygonImageView iv_sixview_head;
	private TextView tv_money, tv_login;
	private TextView tv_name, tv_exit;
	private LinearLayout ll_wdl, ll_dl;
	private ThreeButtonDialog buttonDialog;
	public List<String> uploadListImg = new ArrayList<String>();
	public LinearLayout ll_settlement, ll_updatePassWord, ll_message, ll_clear,
			ll_help, ll_about, ll_feedback, ll_balanceDetail, ll_personaInfo,ll_mygod,
			ll_updateVersion;
	
	public static MineFm instance=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.fm_mine, container,
				false);
		context = getActivity();
		instance = this;
		initView(messageLayout);
		return messageLayout;

	}

	private void initView(View view) {

		iv_sixview_head = (PolygonImageView) view.findViewById(R.id.iv_sixview_head);
		tv_money = (TextView) view.findViewById(R.id.tv_money);

		ll_wdl = (LinearLayout) view.findViewById(R.id.ll_wdl);
		ll_wdl.setOnClickListener(this);
		ll_settlement = (LinearLayout) view.findViewById(R.id.ll_settlement);
		ll_settlement.setOnClickListener(this);
		ll_updatePassWord = (LinearLayout) view
				.findViewById(R.id.ll_updatePassWord);
		ll_updatePassWord.setOnClickListener(this);
		ll_message = (LinearLayout) view.findViewById(R.id.ll_message);
		ll_message.setOnClickListener(this);
		ll_feedback = (LinearLayout) view.findViewById(R.id.ll_feedback);
		ll_feedback.setOnClickListener(this);
		ll_about = (LinearLayout) view.findViewById(R.id.ll_about);
		ll_about.setOnClickListener(this);
		ll_help = (LinearLayout) view.findViewById(R.id.ll_help);
		ll_help.setOnClickListener(this);
		ll_personaInfo = (LinearLayout) view.findViewById(R.id.ll_personaInfo);
		ll_personaInfo.setOnClickListener(this);
		ll_mygod = (LinearLayout) view.findViewById(R.id.ll_mygod);
		ll_mygod.setOnClickListener(this);

		ll_clear = (LinearLayout) view.findViewById(R.id.ll_clear);
		ll_clear.setOnClickListener(this);
		tv_exit = (TextView) view.findViewById(R.id.tv_exit);
		tv_exit.setOnClickListener(this);
		ll_dl = (LinearLayout) view.findViewById(R.id.ll_dl);
		ll_dl.setOnClickListener(this);
		ll_balanceDetail = (LinearLayout) view
				.findViewById(R.id.ll_balanceDetail);
		ll_balanceDetail.setOnClickListener(this);
		ll_updateVersion = (LinearLayout) view
				.findViewById(R.id.ll_updateVersion);
		ll_updateVersion.setOnClickListener(this);

		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_name.setOnClickListener(this);

		tv_login = (TextView) view.findViewById(R.id.tv_login);
		tv_login.setOnClickListener(this);

		buttonDialog = new ThreeButtonDialog(context).setFirstButtonText("拍照")
				.setBtn1Listener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						MainAc.intance.takep();
					}
				}).setThecondButtonText("从手机相册选择")
				.setBtn2Listener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						MainAc.intance.pickp();
					}
				}).autoHide();

		initData();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	//判断用户是否登录现实  头像信息或者用户信息
	public void hideOrView(){
		if(App.getInstance().getCoach()==null){//未登录
			ll_wdl.setVisibility(View.VISIBLE);
			ll_dl.setVisibility(View.GONE);
	    }else{
	    	ll_wdl.setVisibility(View.GONE);
			ll_dl.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(
					App.getInstance().getCoach().avatar, iv_sixview_head,
					App.getInstance().getheadImage());
	    }
	}

	public void initData() {
		CoachInfo cInfo = App.getInstance().getCoach();
		if (null == cInfo) {
			iv_sixview_head.setBackground(getResources().getDrawable(R.drawable.my_defa_head));
			tv_money.setText("0元");//余额
		}else{
			ImageLoader.getInstance().displayImage(cInfo.avatar,iv_sixview_head,App.getInstance().getheadImage());
			tv_name.setText(cInfo.coachName+"");//姓名
			tv_money.setText(cInfo.account == null?"0.00元":Util.toNumber("0.00",Float.parseFloat(App.getInstance().getCoach().account))+"元");//余额
			
//			tv_money.setText(cInfo.account == null?"0.00元":df.format(cInfo.account)+"元");//余额
			
		}
	}
	
	
	

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.ll_personaInfo:// 个人信息
			intent = new Intent(context, PersonalInformation.class);
			startActivity(intent);
			break;
		case R.id.ll_mygod:// 我的客户
			intent = new Intent(context, BaoBeiParentInforActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_b://银行卡列表
			intent = new Intent(context,ManagerBankAc.class);
			startActivity(intent);
			break;
		case R.id.ll_balanceDetail:// 收入明细
			intent = new Intent(context, MyWebActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_settlement:// 余额结算
			intent = new Intent(context, BalanceWithdrawalsAc.class);
			startActivity(intent);
			break;
		case R.id.ll_updatePassWord:// 登录密码修改
			intent = new Intent(context, UpdatePwdAc.class);
			startActivity(intent);
			break;
		case R.id.ll_message:// 消息
			intent = new Intent(context, MyMessageAc.class);
			startActivity(intent);
			break;
		case R.id.ll_feedback:// 意见反馈
			intent = new Intent(context, FeedBackAc.class);
			startActivity(intent);
			break;
		case R.id.ll_about:// 关于软件
			intent = new Intent(context, DisclaimerAc.class);
			intent.putExtra("pageType", "AboutAc");
			startActivity(intent);
			break;
		case R.id.ll_help:// 帮助
			intent = new Intent(context, DisclaimerAc.class);
			intent.putExtra("pageType", "HelpAc");
			startActivity(intent);
			break;
		case R.id.ll_clear:// 清除本地缓存
			UtilDialog.dialogTwoBtnContentTtile(context, "确定要清除本地缓存", "取消",
					"确定", "提示", handler, 4);
			break;
		case R.id.tv_exit:// 退出当前登录
			UtilDialog.dialogTwoBtnContentTtile(context, "是否要注销该账号", "否", "是",
					"提示", handler, 3);
			break;
		case R.id.ll_wdl:// 未登录
			intent = new Intent(context, LoginAc.class);
			startActivity(intent);
			break;
//		case R.id.iv_sixview_head:// 拍照
//			buttonDialog.show();
//			break;
		case R.id.ll_updateVersion:
			intent = new Intent(context, UpdateVAc.class);
			startActivity(intent);
			break;

		// case R.id.ll_money://账户余额
		// intent = new Intent(context,AccountBalanceAc.class);
		// startActivity(intent);
		// break;
		// case R.id.ll_d://我的消息
		// intent = new Intent(context,MyMessageAc.class);
		// startActivity(intent);
		// break;
		// case R.id.ll_e: //顾问教练
		// intent = new Intent(context,CoachInformationAc.class);
		// startActivity(intent);
		// break;
		// case R.id.ll_f: //意见反馈
		// intent = new Intent(context,FeedBackAc.class);
		// startActivity(intent);
		// break;
		// case R.id.ll_g: //设置
		// intent = new Intent(context,SettingAc.class);
		// startActivity(intent);
		// break;
		// case R.id.ll_integral: //积分明细
		// intent = new Intent(context,MyIntegralAc.class);
		// startActivity(intent);
		// break;
		// case R.id.tv_login:
		//
		// break;
		//
		// case R.id.tv_name:
		// case R.id.tv_message:
		// intent = new Intent(context,PersonalInformation.class);
		// startActivity(intent);
		// break;
		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 4:// 清除本地缓存
				MainAc.intance.showProgressDialog();
				DataCleanManager.cleanInternalCache(context);
				MainAc.intance.hideProgressDialog();
				break;
			case 3:// 退出当前登录
				quitIM();
				break;
			}

		};
	};
	/**
	 * 退出IM
	 */
	private void quitIM() {
		MainAc.intance.showProgressDialog();
		
		DemoHelper.getInstance().logout(false, new EMCallBack() {
			@Override
			public void onSuccess() {
				MainAc.intance.runOnUiThread(new Runnable() {
					public void run() {
						// 重新显示登陆页面
						// startActivity(new Intent(MainActivity.this,
						// LoginActivity.class));
						MainAc.intance.finish();
						App.getInstance().setCoach(null);
						SharedPreferencesHelper.remove("userId");
						SharedPreferencesHelper.remove("isAuto");
//						getActivity().stopService(new Intent(context, SingleSignOnServer.class));
						startActivity(new Intent(context, LoginAc.class));
						
						MainAc.intance.hideProgressDialog();
						
					}
				});
			}
			@Override
			public void onProgress(int progress, String status) {
			}
			@Override
			public void onError(int code, String message) {
				MainAc.intance.hideProgressDialog();
				MainAc.intance.dialogToast("退出失败");
			}
		});
	}
	// 设置图片
	public void setImage() {
		ImageLoader.getInstance().displayImage(
				"file://" + uploadListImg.get(0), iv_sixview_head,
				App.getInstance().getListViewDisplayImageOptions());
		// sendPics(uploadListImg);
	}
}
