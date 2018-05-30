package com.bm.base.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.lib.widget.RoundImageView60dip;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 家长评论适配器
 * @author shiyt
 *
 */
public class CommentAdapter  extends BaseAd<Model>{
	public CommentAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_comment, null);
			itemView.iv_sixview_head = (PolygonImageView)convertView.findViewById(R.id.iv_sixview_head);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
			itemView.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		ImageLoader.getInstance().displayImage("http://v1.qzone.cc/avatar/201403/30/09/33/533774802e7c6272.jpg!200x200.jpg", itemView.iv_sixview_head,App.getInstance().getListViewDisplayImageOptions());
		return convertView;
	}
	class ItemView{
		private PolygonImageView iv_sixview_head;
		private TextView tv_name,tv_content,tv_time;
	}
}
