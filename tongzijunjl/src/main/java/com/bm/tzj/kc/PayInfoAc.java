package com.bm.tzj.kc;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.dialog.UtilDialog;
import com.bm.entity.Order;
import com.bm.pay.alipay.AlipayUtil;
import com.bm.pay.wxapi.WeiChatPay;
import com.richer.tzjjl.R;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 支付方式
 * 
 * @author shiyt
 * 
 */
public class PayInfoAc extends BaseActivity implements OnClickListener,IWXAPIEventHandler {
	private Context mContext;
	private RoundImageViewFive img_pic;
	private TextView tv_next, tv_course_name, tv_course_money, tv_balance;
	private LinearLayout ll_alipay, ll_wexin, ll_unio, ll_balance;
	private ImageView img_balance,img_unio,img_alipay,img_wexin;
	private ImageView[] tab_tvs = new ImageView[4]; // 第部三个按钮切换颜色
	
	/**
	 * 支付方式 1 支付宝 2 微信 3 网银 4 账户余额
	 */
	int payType=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_payinfo);
		mContext = this;
		setTitleName("支付");
		init();
		
		initWeiChat();
	}

	public void init() {
		img_pic = (RoundImageViewFive) findViewById(R.id.img_pic);
		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", img_pic,App.getInstance().getListViewDisplayImageOptions());
		tv_next = findTextViewById(R.id.tv_next);
		tv_course_name = findTextViewById(R.id.tv_course_name);
		tv_course_money = findTextViewById(R.id.tv_course_money);
		tv_balance = findTextViewById(R.id.tv_balance);

		ll_alipay = findLinearLayoutById(R.id.ll_alipay);
		ll_wexin = findLinearLayoutById(R.id.ll_wexin);
		ll_unio = findLinearLayoutById(R.id.ll_unio);
		ll_balance = findLinearLayoutById(R.id.ll_balance);
		
		img_balance=findImageViewById(R.id.img_balance);
		img_unio=findImageViewById(R.id.img_unio);
		img_alipay=findImageViewById(R.id.img_alipay);
		img_wexin=findImageViewById(R.id.img_wexin);
		
		tab_tvs[0]=img_alipay;
		tab_tvs[1]=img_wexin;
		tab_tvs[2]=img_unio;
		tab_tvs[3]=img_balance;
		img_alipay.setSelected(true);
		
		ll_balance.setOnClickListener(this);
		ll_wexin.setOnClickListener(this);
		ll_alipay.setOnClickListener(this);
		ll_unio.setOnClickListener(this);
		tv_next.setOnClickListener(this);
	}

	private void switchTvsTo(int pos) {
		for (int i = 0; i < tab_tvs.length; i++) {
			tab_tvs[i].setSelected(pos == i);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_alipay:  //支付宝
			switchTvsTo(0);
			payType = 1;
//			alipay();
			break;
		case R.id.ll_wexin: //微信
			switchTvsTo(1);
			payType = 2;
//			weichatPay();
			break;
		case R.id.ll_unio:  //银联
			switchTvsTo(2);
			payType = 3;
			break;
		case R.id.ll_balance:  //余额
			switchTvsTo(3);
			payType = 4;
			break;
		case R.id.tv_next:
			UtilDialog.dialogPay(mContext,handler);
			getSubmit();
			break;
		default:
			break;
		}
	}
	
	private void getSubmit(){
		if(payType == 1){
			alipay();
		}else if (payType == 2){
			weichatPay();
		}else if (payType == 3){
		}else if (payType == 4){
		}
	}
	
	private void alipay(){
		Order order = new Order();
		order.bookId = "2015023459678445";
		order.productName = "电脑";
		order.amount = "0.01";
		AlipayUtil.pay(order, this);
	}
	
	private IWXAPI api;
	private void initWeiChat(){
		api = WXAPIFactory.createWXAPI(this, WeiChatPay.APP_ID, false);
		api.registerApp(WeiChatPay.APP_ID);
//		api.handleIntent(getIntent(), this);
	}
    
	private void weichatPay(){
		  // 通过WXAPIFactory工厂，获取IWXAPI的实例
		
		WeiChatPay.pay(this,new Order());
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResp(BaseResp arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	Handler handler=new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if(TextUtils.isEmpty(((EditText)msg.obj).getText())){
					dialogToast("请输入支付密码");
					return;
				}
				finish();
				break;

			default:
				break;
			}
		}
	};
	
	
}
