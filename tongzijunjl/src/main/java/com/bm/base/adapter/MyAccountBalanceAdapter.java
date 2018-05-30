package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.CoachOrderList;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 账户余额适配器
 * @author wanghy
 *
 */
public class MyAccountBalanceAdapter  extends BaseAd<CoachOrderList>{
	public MyAccountBalanceAdapter(Context context,List<CoachOrderList> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_myaccount, null);
			itemView.tv_accoundTitle = (TextView)convertView.findViewById(R.id.tv_accoundTitle);
			itemView.tv_accoundMoney = (TextView)convertView.findViewById(R.id.tv_accoundMoney);
			itemView.tv_accoundTime = (TextView)convertView.findViewById(R.id.tv_accoundTime);
			
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		CoachOrderList entity= mList.get(position);
		/**1充值   0消费*/
		if(entity.changeStatus.equals("0")){
			itemView.tv_accoundMoney.setText("- "+getNullData(entity.paymentAccount)==""?"0":entity.paymentAccount);//价格
		}else{
			itemView.tv_accoundMoney.setText("+ "+getNullData(entity.paymentAccount)==""?"0":entity.paymentAccount);//价格
		}
		itemView.tv_accoundTitle.setText(getNullData(entity.goodsName));//名称
		itemView.tv_accoundTime.setText(Util.toString(getNullData(entity.cDate), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));//时间
		
		
		
		
		
		return convertView;
	}
	class ItemView{
		private TextView tv_accoundTitle,tv_accoundMoney,tv_accoundTime;
	
	}
}
