package com.bm.base.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.SigninInfo;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 课程评分适配器
 * @author shiyt
 *
 */
public class CourseSignAdapter  extends BaseAd<SigninInfo>{
	public CourseSignAdapter(Context context,List<SigninInfo> prolist){
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
			convertView = mInflater.inflate(R.layout.item_list_sign, null);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.img_head = (PolygonImageView)convertView.findViewById(R.id.img_head);
			itemView.tv_sign=(TextView) convertView.findViewById(R.id.tv_sign);
			itemView.tv_age=(TextView) convertView.findViewById(R.id.tv_age);
			itemView.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		final SigninInfo entity= mList.get(position);
		itemView.tv_name.setText(entity.babyName);
		itemView.tv_age.setText(getNullData(entity.babyAge)==""?"年龄：0岁":"年龄："+entity.babyAge+"岁");
		itemView.tv_time.setText(Util.toString(entity.signDate, "yyyy-MM-dd HH:mm:ss", "HH:mm"));
		
		/**0 未签到  1 正常 2 迟到*/
		if(entity.signStatus.equals("1")){
			itemView.tv_sign.setText("正常");
			itemView.tv_sign.setBackgroundResource(R.drawable.btn_com);
			itemView.tv_time.setVisibility(View.VISIBLE);
			itemView.tv_sign.setTextColor(context.getResources().getColor(R.color.app_dominantHue));
		}else if(entity.signStatus.equals("2")){
			itemView.tv_sign.setText("迟到");
			itemView.tv_sign.setBackgroundResource(R.drawable.btn_short_gray);
			itemView.tv_time.setVisibility(View.VISIBLE);
			itemView.tv_sign.setTextColor(context.getResources().getColor(R.color.white));
		}else {
			itemView.tv_sign.setText("签到");
			itemView.tv_sign.setBackgroundResource(R.drawable.kc_pj);
			itemView.tv_sign.setTextColor(context.getResources().getColor(R.color.white));
			itemView.tv_time.setVisibility(View.GONE);
			
			itemView.tv_sign.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					onSeckillClick.onSeckillClick(position,1);
				}
			});
		}
		
		
		ImageLoader.getInstance().displayImage(entity.avatar, itemView.img_head,App.getInstance().getHeadOptions());

		//打电话
		convertView.findViewById(R.id.btn_phone).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+entity.parentPhone));
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
		private TextView tv_name,tv_sign,tv_time,tv_age;
		private PolygonImageView img_head;
	
	}
}
