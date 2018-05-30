package com.bm.tzj.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.base.BaseActivity;
import com.richer.tzjjl.R;

/**
 * 支付密码修改
 * 
 * @author jsh
 * 
 */
public class PwdUpAc extends BaseActivity implements OnClickListener {
	Context mContext;
	private EditText et_pwd, et_confirmpwd,et_oldpwd;
	private TextView tv_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_pwdup);
		setTitleName("修改支付密码");
		mContext = this;
		init();
	}

	public void init() {
		et_pwd = findEditTextById(R.id.et_pwd);
		et_confirmpwd = findEditTextById(R.id.et_confirmpwd);
		tv_submit = findTextViewById(R.id.tv_submit);
		et_oldpwd=findEditTextById(R.id.et_oldpwd);
		tv_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
			finish();
			break;
		default:
			break;
		}
	}
}
