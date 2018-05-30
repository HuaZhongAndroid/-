package com.bm.base.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 荣誉榜适配器
 * @author shiyt
 *
 */
public class HonoRollAdapter  extends BaseAd<Model>{
	public HonoRollAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_honoroll, null);
			itemView.iv_sixview_head = (PolygonImageView)convertView.findViewById(R.id.iv_sixview_head);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
			itemView.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
			itemView.tv_category = (TextView)convertView.findViewById(R.id.tv_category);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
//		itemView.tv_name.setText("");//姓名
//		itemView.tv_age.setText(""); //年龄
//		itemView.tv_category.setText(""); //季能章
//		itemView.tv_num.setText("");//数量
		ImageLoader.getInstance().displayImage("http://v1.qzone.cc/avatar/201403/30/09/33/533774802e7c6272.jpg!200x200.jpg", itemView.iv_sixview_head,App.getInstance().getListViewDisplayImageOptions());
		return convertView;
	}
	class ItemView{
		private PolygonImageView iv_sixview_head;
		private TextView tv_name,tv_num,tv_age,tv_category;
	}
}
