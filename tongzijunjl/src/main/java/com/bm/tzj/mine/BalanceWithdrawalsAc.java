package com.bm.tzj.mine;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.StringResult;

/**
 * 余额结算
 * 
 * @author wanghy
 * 
 */
public class BalanceWithdrawalsAc extends BaseActivity implements
		OnClickListener {

	private Context context;
	private TextView tv_add, tv_addCard, tv_administrationCard,tv_money,tv_bank_message;

	private EditText et_money;
	private LinearLayout ll_bank;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_balance_withdrawals);
		context = this;
		setTitleName("余额结算");
		initView();

	}

	private void initView() {
		tv_bank_message=findTextViewById(R.id.tv_bank_message);
		ll_bank=findLinearLayoutById(R.id.ll_bank);
		ll_bank.setOnClickListener(this);
		et_money=findEditTextById(R.id.et_money);
		tv_add = findTextViewById(R.id.tv_add);
		tv_add.setOnClickListener(this);
		tv_addCard = findTextViewById(R.id.tv_addCard);
		tv_addCard.setOnClickListener(this);
		tv_administrationCard = findTextViewById(R.id.tv_administrationCard);
		tv_administrationCard.setOnClickListener(this);
		
		tv_money = findTextViewById(R.id.tv_money);

		TextPaint tp = tv_money.getPaint();
		tp.setFakeBoldText(true);
		tv_money.setOnClickListener(this);
		
		
		if(null != App.getInstance().getCoach()){
			tv_money.setText(getNullData(App.getInstance().getCoach().account)==""?"￥0":"￥"+Util.toNumber("0.00",Float.parseFloat(App.getInstance().getCoach().account)));//余额
		}
//		Util.setPricePoint(et_money);//设置小数点2为
	}

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.tv_add:// 保存
			if(TextUtils.isEmpty(et_money.getText())){
				dialogToast("请输入金额");
				return;
			}
			if(Float.valueOf(et_money.getText().toString().trim())>Float.valueOf(getNullData(App.getInstance().getCoach().account)==""?"0":App.getInstance().getCoach().account)){
				dialogToast("您的余额不够");
				return;
			}
			if(TextUtils.isEmpty(tv_bank_message.getText())||"请选择".equals(tv_bank_message.getText().toString())){
				dialogToast("请选择银行卡");
				return;
			}
			getMoney();
			
			break;
		case R.id.ll_bank:// 提现选择银行卡
			intent = new Intent(this,ManagerBankAc.class);
			intent.putExtra("tag", "ll_bank");
			startActivityForResult(intent, 001);
			break;
		case R.id.tv_administrationCard:// 管理银行卡
			intent = new Intent(this,ManagerBankAc.class);
			startActivity(intent);
			break;
		case R.id.tv_addCard:// 新增银行卡
			intent = new Intent(this,AddCardAc.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	String bankCardId="";
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(null!=data){
			bankCardId=data.getStringExtra("bankCardId");
			tv_bank_message.setText(data.getStringExtra("bankName")+"("+data.getStringExtra("bankNumber")+")");
		}
	}
	/**
	 *提现
	 */
	private  void getMoney(){
		showProgressDialog();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("bankCardId", bankCardId);//
		map.put("balancePrice", et_money.getText().toString());//
		map.put("userid", App.getInstance().getCoach().coachId);
		UserManager.getInstance().addDeduct(context, map, new ServiceCallback<StringResult>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
				hideProgressDialog();
			}
			
			@Override
			public void done(int what, StringResult obj) {
				hideProgressDialog();
				MainAc.intance.getCoachInfo();//刷新教练数据
				toast("操作成功");
				finish(); 
			}
		});
	}

}
