package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.MyAccountBalanceAdapter;
import com.bm.dialog.NumberPickerDialog;
import com.bm.entity.Dictionary;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.util.Constant;
import com.lib.http.ServiceCallback;
import com.lib.http.result.StringResult;
import com.lib.tool.Pager;
import com.lib.widget.refush.SwipyRefreshLayout;
import com.lib.widget.refush.SwipyRefreshLayout.OnRefreshListener;
import com.lib.widget.refush.SwipyRefreshLayoutDirection;

/**
 * 新增银行卡
 * 
 * @author wanghy
 * 
 */
public class AddCardAc extends BaseActivity implements OnClickListener {

	private Context context;
	private TextView tv_add;
	private EditText et_cardName,et_card,et_name;
//	private NumberPickerDialog dailogA;
//	private String yhkName="";
//	private int index=0;//标记第几个
	
	private Dictionary yinhang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_addcard);
		context = this;
		setTitleName("新增银行卡");
		initView();
		
	}

	private void initView() {
		et_cardName = findEditTextById(R.id.et_cardName);
		et_card = findEditTextById(R.id.et_card);
		et_name=findEditTextById(R.id.et_name);
		
		if(!TextUtils.isEmpty(App.getInstance().getCoach().coachName)){
			et_name.setText(App.getInstance().getCoach().coachName);
		}
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_add.setOnClickListener(this);
		et_cardName.setOnClickListener(this);
//		setDate();
//		TextPaint tp = tv_money.getPaint();
//		tp.setFakeBoldText(true);
//		tv_recharge.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_add://添加
			addBank();
			
			break;
		case R.id.et_cardName://银行卡姓名
//			dailogA.show();
			
			this.startActivityForResult(new Intent(this, SelectBankAc.class), 1);
			
			break;
		default:
			break;
		}
	}
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 1)  //选择银行
		{
			if(resultCode == Activity.RESULT_OK)
			{
				yinhang = (Dictionary)data.getSerializableExtra("yinhang");
				et_cardName.setText(yinhang.showvalue);
			}
		}
	}

	/**
	 * 添加银行卡
	 */
	public void addBank(){
		if(TextUtils.isEmpty(et_cardName.getText())){
			dialogToast("银行卡名称不能为空");
			return;
		}
		if(TextUtils.isEmpty(et_card.getText())){
			dialogToast("银行卡卡号不能为空");
			return;
		}
		if(et_card.getText().toString().trim().length()!=19){
			dialogToast("请输入正确的银行卡号");
			return;
		}
		if(TextUtils.isEmpty(et_name.getText())){
			dialogToast("姓名不能为空");
			return;
		}
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", App.getInstance().getCoach().coachId);//
		map.put("bankType", yinhang.storevalue);//银行卡类别
		map.put("bankNumber", et_card.getText().toString().trim());//银行卡号码
		map.put("bankUserName", et_name.getText().toString().trim());//姓名
		UserManager.getInstance().getBankAddBank(context, map, new ServiceCallback<StringResult>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				toast("银行卡添加成功");
				finish();
			}
		});
	}
//	public void setDate() {
//		dailogA = new NumberPickerDialog(context, Constant.yhkName, "请选择银行卡名称",
//				new NumberPickerDialog.SelectValue() {
//
//					@Override
//					public void getValue(int arg) {
//						yhkName = Constant.yhkName[arg];
//						index=arg;
//					}
//				}, new NumberPickerDialog.CancleClick() {
//
//					@Override
//					public void click(View arg) {
//						dailogA.dismiss();
//					}
//
//					@Override
//					public void clickConform(View arg) {
//						et_cardName.setText(yhkName);
//						dailogA.dismiss();
//
//					}
//				});
//	}

}
