package com.bm.tzj.mine;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.base.BaseActivity;
import com.richer.tzjjl.R;
import com.bm.tzj.kc.DisclaimerAc;
import com.bm.util.TimeCount;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.StringResult;

/**
 * 注册
 * @author shiyt
 *
 */
public class RegistreAc extends BaseActivity implements OnClickListener{
	private Context context;
	private LinearLayout ll_check;
	private EditText et_phone, et_code;
	private EditText et_pwd,et_confirmpwd;
	private TextView tv_next, tv_verifcode,tv_xy,tv_content;
	private ImageView img_checkbox;
	private boolean isChecked=true;
	int loginCount = 1;
	TimeCount count;
	Intent intent;
	public static RegistreAc intance ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_registre);
		context = this;
		intance = this;
		setTitleName("注册");
		initView();
	}
	
	public void initView(){
		et_pwd=findEditTextById(R.id.et_pwd);
		et_confirmpwd=findEditTextById(R.id.et_confirmpwd);
		img_checkbox=findImageViewById(R.id.img_checkbox);
		img_checkbox.setImageResource(R.drawable.login_selected);
		ll_check=findLinearLayoutById(R.id.ll_check);
		tv_next = findTextViewById(R.id.tv_next);
		tv_verifcode = findTextViewById(R.id.tv_verifcode);
		et_phone = findEditTextById(R.id.et_phone);
		et_code = findEditTextById(R.id.et_code);
		tv_xy=findTextViewById(R.id.tv_xy);
		tv_content=findTextViewById(R.id.tv_content);
		tv_next.setOnClickListener(this);
		tv_verifcode.setOnClickListener(this);
		ll_check.setOnClickListener(this);
		tv_xy.setOnClickListener(this);
		
		tv_content.setText(Html.fromHtml("<font color=\""+context.getResources().getColor(R.color.txt_name)+"\">(请在</font><font size=\"25\"  color=\""+context.getResources().getColor(R.color.txt_yellow_color)+"\">120</font><font  color=\""+context.getResources().getColor(R.color.txt_name)+"\">秒内完成验证，超时请点击重新发送)</font>"));
	}
	
	
	/**
	 * 获取验证码
	 * 
	 * @param phone 手机号码
	 *            
	 */
	public void getAuthCode(final String phone) {
		if(TextUtils.isEmpty(phone)){
			dialogToast("手机号码不能为空");
			return;
		}else{
			if (!Util.isMobileNO(phone)) {
				dialogToast("请输入正确的手机号码");
				return;
			} 
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("type", "1");//验证入口  1 注册 2 忘记密码
		UserManager.getInstance().getTzjcasSendcode(context,map,new ServiceCallback<StringResult>() {

			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				count = new TimeCount(120000, 1000, tv_verifcode, et_phone,context);
				count.start();
				et_phone.setFocusable(false);
			}

			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
		});
	}
	
	
	/**
	 * 验证手机号码和短信验证码
	 * 
	 * @param phone
	 *            手机号码
	 * @param authCode
	 *            验证码
	 */
	public void getCheckAuthCode(String phone,String authCode) {
		String pwd = et_pwd.getText().toString().trim();
		String configPwd = et_confirmpwd.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			dialogToast("手机号码不能为空");
			return;
		}else{
			if (!Util.isMobileNO(phone)) {
				dialogToast("请输入正确的手机号码");
				return;
			} 
		}
		
		if(TextUtils.isEmpty(authCode)){
			dialogToast("验证码不能为空");
			return;
		}
		
		if(TextUtils.isEmpty(pwd)){
			dialogToast("密码不能为空");
			return;
		}
		
		if(TextUtils.isEmpty(configPwd)){
			dialogToast("确认密码不能为空");
			return;
		}
		
		if(!pwd.equals(configPwd)){
			dialogToast("两次密码输入不一致");
			return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		map.put("authCode", authCode);
		map.put("type", "1");//验证入口  1 注册 2 忘记密码
		
		
		UserManager.getInstance().getTzjcasCheckcode(context,map,new ServiceCallback<StringResult>() {

			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				 intent = new Intent(context, PerfectInfoAc.class);
				 intent.putExtra("passWord", et_pwd.getText().toString().trim());
				 intent.putExtra("phone", et_phone.getText().toString().trim());
				 startActivity(intent);
			}

			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
		});
	}
	
	/**
	 * 设置密码
	 */
	public void setPwd(){
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_check:
			if(isChecked){
				img_checkbox.setImageResource(R.drawable.login_no_selected);
				isChecked = false;
			}else{
				img_checkbox.setImageResource(R.drawable.login_selected);
				isChecked = true;
			}
			
			break;
		case R.id.tv_next:  //下一步
			if(!isChecked){
				dialogToast("请勾选注册协议");
			}else{
				getCheckAuthCode(et_phone.getText().toString().trim(),et_code.getText().toString().trim());//验证手机号码和验证码是否正确
			}
			break;
		case R.id.tv_verifcode:  //获取验证码
			getAuthCode(et_phone.getText().toString().trim());
			break;
		case R.id.tv_xy:  //注册协议
			intent = new Intent(context, DisclaimerAc.class);
			intent.putExtra("pageType", "RegistrAc");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
