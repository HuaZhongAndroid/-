package com.bm.base.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.SigninInfo;
import com.bm.im.ac.EvaluateShowAc;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.richer.tzjjl.R;

/**
 * 课程评分适配器
 * @author shiyt
 *
 */
public class CourseCommentAdapter  extends BaseAd<SigninInfo>{
	public CourseCommentAdapter(Context context,List<SigninInfo> prolist){
		setActivity(context, prolist);
	}
	
	private OnSeckillClick onSeckillClick;
	public void setOnSeckillClick(OnSeckillClick onSeckillClick){
		this.onSeckillClick = onSeckillClick;
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_coursecomm, null);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.img_head = (ImageView)convertView.findViewById(R.id.img_head);
			itemView.tv_comm=(TextView) convertView.findViewById(R.id.tv_comm);
			itemView.tv_tg=(TextView) convertView.findViewById(R.id.tv_tg);
			itemView.tv_age=(TextView) convertView.findViewById(R.id.tv_age);
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		final SigninInfo entity= mList.get(position);
		itemView.tv_name.setText(entity.realName);
		itemView.tv_age.setText(entity.babyAge==""?"年龄：0岁":"年龄："+entity.babyAge+"岁");
		
		//评分状态  0 未通过  1 已通过   2未评分
		if(entity.passStatus.equals("2")){
			itemView.tv_tg.setVisibility(View.GONE);
			itemView.tv_comm.setVisibility(View.VISIBLE);
			//评分
			itemView.tv_comm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onSeckillClick.onSeckillClick(position,1);
				}
			});
		}else if(entity.passStatus.equals("1")){
			itemView.tv_comm.setVisibility(View.GONE);
			itemView.tv_tg.setVisibility(View.VISIBLE);
			itemView.tv_tg.setText("查看评价");
		}else{
			itemView.tv_comm.setVisibility(View.GONE);
			itemView.tv_tg.setVisibility(View.VISIBLE);
			itemView.tv_tg.setText("查看评价");
		}
		
		itemView.tv_tg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent  = new Intent(context,EvaluateShowAc.class);
				intent.putExtra("hotGoods", mList.get(position));
				context.startActivity(intent);
				
			}
		});
		ImageLoader.getInstance().displayImage(entity.avatar, itemView.img_head,App.getInstance().getHeadOptions());
		
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent  = new Intent(context,EvaluateShowAc.class);
//				intent.putExtra("hotGoods", mList.get(position));
//				context.startActivity(intent);
//			}
//		});
		
		//打电话
		convertView.findViewById(R.id.btn_phone).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+entity.parentPhone));  
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			    context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	public interface OnSeckillClick{
		void onSeckillClick(int position,int type);
	}
	class ItemView{
		private TextView tv_name,tv_comm,tv_tg,tv_age;
		private ImageView img_head;
	
	}
}
