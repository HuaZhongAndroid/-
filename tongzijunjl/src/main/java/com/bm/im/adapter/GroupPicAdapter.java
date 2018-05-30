package com.bm.im.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;

/**
 * 修改群头像适配器
 * 
 * @author shiyt
 * 
 */
public class GroupPicAdapter extends BaseAd<Model> {
	
	public GroupPicAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}

	@Override
	protected View setConvertView(View convertView, final int position) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview_updatepic,null);
			new ItemView(convertView);
		}  
		final ItemView itemView = (ItemView) convertView.getTag();

		final Model entity = mList.get(position);
		itemView.tv_name.setText(entity.name);
		itemView.img_head.setImageResource(entity.strImageUrl);
//		ImageLoader.getInstance().displayImage("", itemView.img_head,App.getInstance().getHeadOptions());
		
		if (entity.isSelected) {
			itemView.img_status.setVisibility(View.VISIBLE);
			itemView.img_status.setImageResource(R.drawable.pay_selected);
		} else {
			itemView.img_status.setVisibility(View.GONE);
			itemView.img_status.setImageResource(R.drawable.pay_no_selected);
		}
		
		
		
//		itemView.fl_pic.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				for(int i=0;i<mList.size();i++){
//					itemView.img_status.setSelected(false);
//				}
//				itemView.img_status.setSelected(true);
//				
//			}
//		});
		return convertView;
	}

	class ItemView {
		private ImageView img_status;
		private PolygonImageView img_head;
		private FrameLayout fl_pic;
		private TextView tv_name;
		public ItemView(View view) {
			img_status = (ImageView) view.findViewById(R.id.img_status);
			img_head = (PolygonImageView) view.findViewById(R.id.img_head);
			fl_pic = (FrameLayout) view.findViewById(R.id.fl_pic);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
		}
	}
}
