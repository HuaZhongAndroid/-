package com.bm.tzj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.dialog.UtilDialog;
import com.bm.im.tool.DemoHelper;
import com.richer.tzjjl.R;
import com.bm.tzj.kc.DisclaimerAc;
import com.bm.tzjjl.activity.MainAc;
import com.bm.util.DataCleanManager;
import com.easemob.EMCallBack;
import com.lib.tool.SharedPreferencesHelper;

/**
 * 设置
 * 
 * @author jiangsh
 * 
 */
public class SettingAc extends BaseActivity implements OnClickListener {
	private Context context;
	private LinearLayout ll_a, ll_b, ll_c, ll_d, ll_e,ll_f;
	private TextView tv_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_setting);
		context = this;
		setTitleName("设置");
		initView();
	}

	private void initView() {
		ll_a = findLinearLayoutById(R.id.ll_a);
		ll_b = findLinearLayoutById(R.id.ll_b);
		ll_c = findLinearLayoutById(R.id.ll_c);
		ll_d = findLinearLayoutById(R.id.ll_d);
		ll_e = findLinearLayoutById(R.id.ll_e);
		ll_f=findLinearLayoutById(R.id.ll_f);
		tv_submit = findTextViewById(R.id.tv_submit);
		tv_submit.setOnClickListener(this);
		ll_a.setOnClickListener(this);
		ll_b.setOnClickListener(this);
		ll_c.setOnClickListener(this);
		ll_d.setOnClickListener(this);
		ll_e.setOnClickListener(this);
		ll_f.setOnClickListener(this);
		setData();
	}

	private void setData() {
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.tv_submit:// 退出当前用户
			UtilDialog.dialogTwoBtnContentTtile(context, "确定要退出登录么", "取消","确定","提示",handler,1);
			break;
		case R.id.ll_a:// 检查更新
			intent=new Intent(context, UpdateVAc.class);
			startActivity(intent);
			break;
		case R.id.ll_b:// 修改密码
			intent = new Intent(context, UpdatePwdAc.class);
			startActivity(intent);
			break;
		case R.id.ll_c:// 帮助中心
			intent = new Intent(context, DisclaimerAc.class);
			intent.putExtra("pageType", "HelpAc");
			startActivity(intent);
			break;
		case R.id.ll_d:// 关于软件
			intent = new Intent(context, DisclaimerAc.class);
			intent.putExtra("pageType", "AboutAc");
			startActivity(intent);
			break;
		case R.id.ll_e:// 清除本地缓存
			UtilDialog.dialogTwoBtnContentTtile(context, "确定要清除本地缓存", "取消","确定","提示",handler,4);
			break;
		case R.id.ll_f:// 设置支付密码或者是修改支付密码
			intent = new Intent(context, PwdSetAc.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
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
						App.getInstance().setUser(null);
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
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 4://清除本地缓存
				DataCleanManager.cleanExternalCache(context);
				break;
			case 1://退出登录
				quitIM();
				break;
			}
		};
	};

}
