package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Banks;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.util.BaseDataUtil;
import com.bm.util.Constant;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 管理银行卡适配器
 * @author jsh
 *
 */
public class ManagerBankAdapter  extends BaseAd<Banks>{
	private int width;
	public ManagerBankAdapter(Context context,List<Banks> prolist,int width){
		setActivity(context, prolist);
		this.width=width;
	}
	private onRemoveListon removeListon;
	

	public void setRemoveListon(onRemoveListon removeListon) {
		this.removeListon = removeListon;
	}

	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_manager_bank, null);
			itemView.tv_bank_name = (TextView)convertView.findViewById(R.id.tv_bank_name);
			itemView.iv_bank = (ImageView)convertView.findViewById(R.id.iv_bank);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.tv_bank_code = (TextView)convertView.findViewById(R.id.tv_bank_code);
			itemView.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
			 convertView.scrollTo(0, 0);
		}
		
		Banks entity= mList.get(position);
		
		for (int i = 0; i < BaseDataUtil.yinhangList.size(); i++) {
			
			if(entity.bankType.equals(BaseDataUtil.yinhangList.get(i).storevalue)){
				itemView.tv_bank_name.setText(BaseDataUtil.yinhangList.get(i).showvalue);
			}
		}
		itemView.tv_name.setText(entity.bankUserName);
		if(entity.bankCardNo.length()==19){
			itemView.tv_bank_code.setText("**** **** **** **** "+entity.bankCardNo.substring(15, 19));
		}
		 LinearLayout.LayoutParams lp2 = new LayoutParams(width,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		 itemView.item_right.setLayoutParams(lp2);
		 itemView.item_right.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
            	 removeListon.remove(position);            	 	 
             }
         });
		
		return convertView;
	}
	class ItemView{
		private TextView tv_bank_name,tv_name,tv_bank_code;
		private ImageView iv_bank;
		RelativeLayout item_right;
	
	}
	
	public interface onRemoveListon{
		public void  remove(int position);
	}
}
