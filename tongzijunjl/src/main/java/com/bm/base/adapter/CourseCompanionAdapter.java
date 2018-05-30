package com.bm.base.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.bm.im.adapter.FriendAdapter.OnSeckillClick;
import com.richer.tzjjl.R;
import com.lib.widget.RoundImageView60dip;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 课程旅伴适配器
 * @author shiyt
 *
 */
public class CourseCompanionAdapter  extends BaseAd<Model>{
	public CourseCompanionAdapter(Context context,List<Model> prolist){
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
			convertView = mInflater.inflate(R.layout.item_list_coursecompanion, null);
			itemView.iv_sixview_head = (PolygonImageView)convertView.findViewById(R.id.iv_sixview_head);
			itemView.tv_age=(TextView) convertView.findViewById(R.id.tv_age);
			
			itemView.tv_search=(TextView) convertView.findViewById(R.id.tv_search);
			itemView.tv_number=(TextView) convertView.findViewById(R.id.tv_number);
			itemView.tv_babyname=(TextView) convertView.findViewById(R.id.tv_babyname);
			itemView.tv_sex=(TextView) convertView.findViewById(R.id.tv_sex);
			itemView.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
//		itemView.tv_age.setText("");
//		itemView.tv_number.setText("");
//		itemView.tv_babyname.setText("");
//		itemView.tv_sex.setText("");
		itemView.tv_name.setText("杨老师"+position);
		ImageLoader.getInstance().displayImage("http://v1.qzone.cc/avatar/201403/30/09/33/533774802e7c6272.jpg!200x200.jpg", itemView.iv_sixview_head,App.getInstance().getListViewDisplayImageOptions());
		
		//查看
		itemView.tv_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position);
			}
		});
				
		return convertView;
	}
	public interface OnSeckillClick{
		void onSeckillClick(int position);
	}
	
	class ItemView{
		private PolygonImageView iv_sixview_head;
		private TextView tv_search,tv_number,tv_age,tv_babyname,tv_sex,tv_name;
	}
}
