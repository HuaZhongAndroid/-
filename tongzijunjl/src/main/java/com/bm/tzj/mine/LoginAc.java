package com.bm.tzj.mine;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.entity.CoachInfo;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.lib.tool.SharedPreferencesHelper;

/**
 * 登录
 * @author shiyt
 *
 */
public class LoginAc extends BaseActivity implements OnClickListener {  
	private Context context;
	private EditText et_phone,et_pwd;
	private TextView tv_login ,tv_forgetpwd;
	private LinearLayout ll_checkpwd;
	private ImageView img_checkbox;
	private boolean isChecked;
	int loginCount = 1;
	String strPhone="", strPassWord="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_login);
		context = this;
		setTitleName("教练登录");
		setRightName("注册");
		hideLeft();
		initView();
	}
	
	@Override
	public void clickRight() {
		// TODO Auto-generated method stub
		super.clickRight();
		Intent intent = new Intent(context, RegistreAc.class);
		startActivity(intent);
	}
	
	public void initView(){
		isChecked = true;
		et_phone=findEditTextById(R.id.et_phone);
		et_pwd=findEditTextById(R.id.et_pwd);
		tv_login=findTextViewById(R.id.tv_login);
		tv_forgetpwd=findTextViewById(R.id.tv_forgetpwd);
		img_checkbox=findImageViewById(R.id.img_checkbox);
		ll_checkpwd=findLinearLayoutById(R.id.ll_checkpwd);
		
		tv_forgetpwd.setOnClickListener(this);
		ll_checkpwd.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		if (SharedPreferencesHelper.getBoolean("isCheck")) {// 判断是否存在记住手机号和密码
			if (SharedPreferencesHelper.contain("phone")&& SharedPreferencesHelper.contain("password")) {
				strPhone = SharedPreferencesHelper.getString("phone");
				strPassWord = SharedPreferencesHelper.getString("password");
				et_phone.setText(strPhone);
				et_pwd.setText(strPassWord);
				img_checkbox.setImageResource(R.drawable.login_selected);
				isChecked = true;
				loginCount++;
			}
		}else{
			if (SharedPreferencesHelper.contain("phone")) {
				strPhone = SharedPreferencesHelper.getString("phone");
				et_phone.setText(strPhone);
			}
		}
	}
	
	/**
	 * 登录
	 */
	private void loginApp() {
		final String strPhone = et_phone.getText().toString().replace(" ", "").trim();
		final String pwd = et_pwd.getText().toString().trim();
		if (strPhone.length() == 0) {
			dialogToast("手机号码不能为空");
			return;
		} else {
			if (!Util.isMobileNO(strPhone)) {
				dialogToast("手机号码不正确");
				return;
			} 
		}

		if (pwd.length() == 0) {
			dialogToast("密码不能为空");
			return;
		}
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone", strPhone);
		map.put("password", pwd);
		map.put("deviceToken", Util.getIMEI(context));
		UserManager.getInstance().getTzjcoachSearchCoachInfo(context,map, new ServiceCallback<CommonResult<CoachInfo>>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, CommonResult<CoachInfo> obj) {
				hideProgressDialog();
				if (obj != null && obj.data != null) {
					//判断  记住密码 按钮的状态
					if (isChecked) {
						SharedPreferencesHelper.saveBoolean("isCheck", true);// 记住手机号码和密码
						SharedPreferencesHelper.saveString("phone", strPhone);//保存密码
						SharedPreferencesHelper.saveString("password", pwd);//保存密码
					}else{
						SharedPreferencesHelper.saveBoolean("isCheck", false);// 清空记住密码的信息
						SharedPreferencesHelper.saveString("phone", "");
						SharedPreferencesHelper.saveString("password", "");
							
					}
					SharedPreferencesHelper.saveString("coachId", obj.data.coachId);//保存教练ID
					SharedPreferencesHelper.saveString("passWord", pwd);//保存密码
					
					App.getInstance().setCoach(obj.data);// 存储教练信息
					Intent intent = new Intent(context, MainAc.class);
					intent.putExtra("tag", 2);
					startActivity(intent);
					finish();
				} else {
					dialogToast("登录失败");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.tv_login:
			loginApp();
//			intent = new Intent(context, MainAc.class);
//			intent.putExtra("tag", 2);
//			startActivity(intent);
//			finish();
			break;
		case R.id.tv_forgetpwd:
			intent = new Intent(context, RetrievePasswordAc.class);
			startActivity(intent);
			break;
		case R.id.ll_checkpwd:  //记住密码
			loginCount++;
			if (loginCount % 2 == 0) {
				img_checkbox.setImageResource(R.drawable.login_selected);
				isChecked = true;
			} else {
				img_checkbox.setImageResource(R.drawable.login_no_selected);
				isChecked = false;
			}
			break;
		default:
			break;
		}
	}
}
