package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.tzj.kc.GrowthCenterAc;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 *探索 评论的适配器
 * @author wanghy
 *
 */
public class MyExploreReplayAdapter  extends BaseAd<Model>{
	public MyExploreReplayAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_myexplore_replay, null);
			itemView.tv_replay_name = (TextView)convertView.findViewById(R.id.tv_replay_name);
			itemView.tv_replay_content = (TextView)convertView.findViewById(R.id.tv_replay_content);
			itemView.img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
			
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		itemView.tv_replay_name.setText(getNullData(entity.name));//姓名
		itemView.tv_replay_content.setText(getNullData(entity.content));//内容
		
		if(position==0){
			itemView.img_pic.setVisibility(View.VISIBLE);
		}else{
			itemView.img_pic.setVisibility(View.INVISIBLE);
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,GrowthCenterAc.class);
				intent.putExtra("pageType","HonoRollAc");
				context.startActivity(intent);
			}
		});
		
		
		return convertView;
	}
	class ItemView{
		private TextView tv_replay_name,tv_replay_content;
		private ImageView img_pic;
	
	}
}
