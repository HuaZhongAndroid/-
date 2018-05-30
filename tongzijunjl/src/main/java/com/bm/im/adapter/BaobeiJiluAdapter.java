package com.bm.im.adapter;

import java.util.List;

import com.baidu.location.LLSInterface;
import com.bm.im.entity.BaobeijiluEntity;




import com.richer.tzjjl.R;

import android.content.Context;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaobeiJiluAdapter extends BaseAdapter{
	private Context context;
	private List<BaobeijiluEntity> list;
	
	private int sIndex; //

	public BaobeiJiluAdapter(Context context, List<BaobeijiluEntity> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final ItemHold item;
		if(convertView==null){
			item = new ItemHold();
			convertView= LayoutInflater.from(context).inflate(R.layout.item_baobei_jilu, null);
			item.followupTime=(TextView) convertView.findViewById(R.id.tv_baobeijilu_time);
			item.ib_baobeijilu_gengduo = (ImageView)convertView.findViewById(R.id.ib_baobeijilu_gengduo);
			item.content=(TextView) convertView.findViewById(R.id.tv_baobei_content);
			item.ll_baobeijilu_gone=(LinearLayout) convertView.findViewById(R.id.ll_baobeijilu_gone);
			item.followupMode=(TextView) convertView.findViewById(R.id.tv_jilu_gtfs);
			item.followupDegree=(TextView) convertView.findViewById(R.id.tv_jilu_gtcd);
			convertView.setTag(item);
		}else{
			item=(ItemHold) convertView.getTag();
		}
		
		
		final BaobeijiluEntity entity=list.get(position);
		item.content.setText(entity.getContent());
		item.followupMode.setText(entity.getFollowupMode());
		item.followupDegree.setText(entity.getFollowupDegree());
		item.followupTime.setText(entity.getFollowupTime());
		
		if(entity.isShowContent)
		{
			item.ll_baobeijilu_gone.setVisibility(View.VISIBLE);
			item.ib_baobeijilu_gengduo.setImageResource(R.drawable.upshou);
		}else{
			item.ll_baobeijilu_gone.setVisibility(View.GONE);
			item.ib_baobeijilu_gengduo.setImageResource(R.drawable.downla);
		}

		item.ib_baobeijilu_gengduo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				entity.isShowContent = !entity.isShowContent;
				
				notifyDataSetChanged();
			}
		});

		return convertView;
	}
	class ItemHold{
		TextView followupTime;
		ImageView ib_baobeijilu_gengduo;
		TextView content;
		LinearLayout ll_baobeijilu_gone;
		TextView followupMode;
		TextView followupDegree;
	}

}
