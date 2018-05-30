package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.bm.view.RatingBar;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 课程首页 gallery图片
 * @author wanghy
 *
 */
public class CourseGalleryAdapter  extends BaseAd<Model>{
	public CourseGalleryAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_gallery_course, null);
			itemView.iv_image = (ImageView)convertView.findViewById(R.id.iv_image);
			itemView.fm_layout = (FrameLayout)convertView.findViewById(R.id.fm_layout);
			itemView.sectorView=(RatingBar) convertView.findViewById(R.id.sectorView);
			itemView.ll_=(LinearLayout) convertView.findViewById(R.id.ll_);
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
//		FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams();
//		itemView.iv_image.setLayoutParams(params)
		itemView.sectorView.setRating(4);
		
		int w = App.getInstance().getScreenWidth();
		if(getCount()>2){
			itemView.iv_image.setLayoutParams(new FrameLayout.LayoutParams(w/7*3-Util.dpToPx(10, context.getResources()),LayoutParams.FILL_PARENT));
			itemView.ll_.setLayoutParams(new FrameLayout.LayoutParams(w/7*3-Util.dpToPx(10, context.getResources()),Util.dpToPx(40, context.getResources())));
			//HorizontalListView.LayoutParams laym = new HorizontalListView.LayoutParams(w/2,w+50);
			FrameLayout.LayoutParams lp = (LayoutParams)itemView.iv_image.getLayoutParams();
			lp.setMargins(20, 0, 0, 0);
			FrameLayout.LayoutParams lp2 = (LayoutParams)itemView.ll_.getLayoutParams();
			lp2.setMargins(20, 0, 0, 0);
			lp2.gravity=Gravity.BOTTOM;
		}else if(getCount()==2){
			itemView.iv_image.setLayoutParams(new FrameLayout.LayoutParams(w/2*1-40,LayoutParams.FILL_PARENT));
			itemView.ll_.setLayoutParams(new FrameLayout.LayoutParams(w/2*1-40,Util.dpToPx(40, context.getResources())));
			//HorizontalListView.LayoutParams laym = new HorizontalListView.LayoutParams(w/2,w+50);
			FrameLayout.LayoutParams lp = (LayoutParams)itemView.iv_image.getLayoutParams();
			lp.setMargins(20, 0, 20, 0);
			FrameLayout.LayoutParams lp2 = (LayoutParams)itemView.ll_.getLayoutParams();
			lp2.setMargins(20, 0, 20, 0);
			lp2.gravity=Gravity.BOTTOM;
		}else if(getCount()==1){
			itemView.iv_image.setLayoutParams(new FrameLayout.LayoutParams(w/1-40,LayoutParams.FILL_PARENT));
			itemView.ll_.setLayoutParams(new FrameLayout.LayoutParams(w/1-40,Util.dpToPx(40, context.getResources())));
			//HorizontalListView.LayoutParams laym = new HorizontalListView.LayoutParams(w/2,w+50);
			FrameLayout.LayoutParams lp = (LayoutParams)itemView.iv_image.getLayoutParams();
			lp.setMargins(20, 0, 20, 0);
			FrameLayout.LayoutParams lp2 = (LayoutParams)itemView.ll_.getLayoutParams();
			lp2.setMargins(20, 0, 20, 0);
			lp2.gravity=Gravity.BOTTOM;
		}
		
		//itemView.fm_layout.setLayoutParams(new HorizontalListView.LayoutParams(30,w+50));
		Model entity= mList.get(position);
		
		
		
		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", itemView.iv_image,App.getInstance().getListViewDisplayImageOptions());
//		itemView.tv_mycourse_name.setText(getNullData(entity.name));//课程名称
//		itemView.tv_mycourse_time.setText(getNullData(entity.time));//时间
//		itemView.tv_mycourse_address.setText(getNullData(entity.address));//地址
//		itemView.tv_mycourse_money.setText(getNullData(entity.money));//价格
		
		
	
		return convertView;
	}
	class ItemView{
		private TextView tv_mycourse_del,tv_mycourse_Endstates;
		private ImageView iv_image;
		private FrameLayout fm_layout;
		private RatingBar sectorView;
		private LinearLayout ll_;
	
	}
}
