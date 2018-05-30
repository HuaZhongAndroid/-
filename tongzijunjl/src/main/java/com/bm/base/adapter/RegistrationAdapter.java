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
 * 报名人适配器
 * @author shiyt
 *
 */
public class RegistrationAdapter  extends BaseAd<Model>{
	public RegistrationAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_gridview_registration, null);
			itemView.img_head = (PolygonImageView)convertView.findViewById(R.id.img_head);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		ImageLoader.getInstance().displayImage("http://v1.qzone.cc/avatar/201403/30/09/33/533774802e7c6272.jpg!200x200.jpg", itemView.img_head,App.getInstance().getHeadOptions());
		return convertView;
	}
	class ItemView{
		private PolygonImageView img_head;
	}
}
