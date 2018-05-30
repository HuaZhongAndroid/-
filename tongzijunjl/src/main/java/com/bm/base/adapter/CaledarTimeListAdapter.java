package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 时间适配器
 * @author shiyt
 *
 */
public class CaledarTimeListAdapter  extends BaseAd<Model>{
	public CaledarTimeListAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_caledartime, null);
			itemView.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
			itemView.tv_count = (TextView)convertView.findViewById(R.id.tv_count);
			itemView.ll_bg = (LinearLayout)convertView.findViewById(R.id.ll_bg);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		itemView.tv_count.setText(getNullData(entity.name));
		itemView.tv_time.setText(getNullData(entity.time));
		
		
		if(position%2==0){
			itemView.tv_count.setText(getNullData(entity.name));
		}else{
			itemView.tv_count.setText("已满");
		}
		
//		if(position%3==0){
//			itemView.ll_bg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rl_selected));
//		}else{
			itemView.ll_bg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rl_noselected));
//		}
		
		return convertView;
	}
	class ItemView{
		private TextView tv_time,tv_count;
		private LinearLayout ll_bg;
	}
}
