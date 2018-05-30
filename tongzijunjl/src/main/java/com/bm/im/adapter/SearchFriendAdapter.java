package com.bm.im.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.CoachInfo;
import com.richer.tzjjl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 新朋友适配器
 * @author shiyt
 *
 */
public class SearchFriendAdapter  extends BaseAd<CoachInfo>{
	public SearchFriendAdapter(Context context,List<CoachInfo> prolist){
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
			convertView = mInflater.inflate(R.layout.item_list_search_friend, null);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.img_head = (PolygonImageView)convertView.findViewById(R.id.img_head);
			itemView.tv_add=(TextView) convertView.findViewById(R.id.tv_add);
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		final CoachInfo entity= mList.get(position);
		itemView.tv_name.setText(entity.nickname);
		
		
		//是否是好友关系 2加好友 1是好友关系 0同意好友 3已申请
		if(entity.friStatus.equals("2")){
			itemView.tv_add.setText("加好友");
			itemView.tv_add.setBackgroundResource(R.drawable.btn_short);
		} else if(entity.friStatus.equals("3")){
			itemView.tv_add.setText("已申请");
			itemView.tv_add.setBackgroundResource(R.drawable.btn_short_gray);
		}else{
			itemView.tv_add.setText("已添加");
			itemView.tv_add.setBackgroundResource(R.drawable.btn_short_gray);
		}
		ImageLoader.getInstance().displayImage(entity.avatar, itemView.img_head,App.getInstance().getheadImage());
		
		//同意
		itemView.tv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(entity.friStatus.equals("2")){
					onSeckillClick.onSeckillClick(position);
				}
			}
		});
		return convertView;
	}
	
	public interface OnSeckillClick{
		void onSeckillClick(int position);
	}
	class ItemView{
		private TextView tv_name,tv_add;
		private PolygonImageView img_head;
	
	}
}
