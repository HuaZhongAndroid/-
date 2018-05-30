package com.bm.im.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;

import com.bm.app.App;
import com.bm.entity.BaobeiUserinfo;
import com.bm.im.adapter.FriendAdapter.ItemView;
import com.bm.tzjjl.activity.BaoBeiActivity;
import com.bm.util.CycleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.richer.tzjjl.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaobeiUserInforAdapter extends BaseAdapter{
private Context context;
private List<BaobeiUserinfo> list;


	public BaobeiUserInforAdapter(Context context, List<BaobeiUserinfo> list) {
	super();
	this.context = context;
	this.list = list;
}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView =LayoutInflater.from(context).inflate(R.layout.item_baobei_userinfo, null);
			itemView.nickName = (TextView)convertView.findViewById(R.id.tv_userinfo_username);
			itemView.regionName = (TextView)convertView.findViewById(R.id.tv_userinfo_county);
			itemView.areaName = (TextView)convertView.findViewById(R.id.tv_userinfo_city);
			itemView.accountMoney = (TextView)convertView.findViewById(R.id.tv_userinfo_paynumber);
			itemView.followupCount = (TextView)convertView.findViewById(R.id.tv_baobei);
			itemView.avatar = (CycleImageView)convertView.findViewById(R.id.civ_baobei_cycleim);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		itemView.nickName.setText(list.get(position).getNickName());
		itemView.areaName.setText(list.get(position).getAreaName());
		itemView.regionName.setText(list.get(position).getRegionName());
		itemView.accountMoney.setText(list.get(position).getAccountMoney());
		
		if(list.get(position).getFollowupCount().equals("0")){
			itemView.followupCount.setText("报备");
		}else{
			itemView.followupCount.setText("报备"+list.get(position).getFollowupCount()+"次");
		}
		
		ImageLoader.getInstance().displayImage(list.get(position).getAvatar(), itemView.avatar,App.getInstance().getheadImage());
		
	itemView.followupCount.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context,BaoBeiActivity.class);
			String userid = list.get(position).getUserId();
			intent.putExtra("userid", userid);
			context.startActivity(intent);
		}
	});
		
		return convertView;
	}
	class ItemView {
		CycleImageView avatar;
		TextView followupCount;
		TextView regionName;
		TextView accountMoney;
		TextView nickName;
		TextView areaName;
		
		
	}

}
