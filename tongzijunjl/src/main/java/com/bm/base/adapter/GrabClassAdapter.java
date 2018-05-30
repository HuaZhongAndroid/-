package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.CoachInfo;
import com.bm.entity.HotGoods;
import com.bm.tzj.mine.MyCourseDetailAc;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 我的课程适配器
 * @author wanghy
 *
 */
public class GrabClassAdapter  extends BaseAd<HotGoods>{
	public GrabClassAdapter(Context context,List<HotGoods> prolist){
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
			convertView = mInflater.inflate(R.layout.item_list_mycourse, null);
			itemView.tv_mycourse_name = (TextView)convertView.findViewById(R.id.tv_mycourse_name);
			itemView.img_pic = (RoundImageViewFive)convertView.findViewById(R.id.img_pic);
			itemView.tv_mycourse_time = (TextView)convertView.findViewById(R.id.tv_mycourse_time);
			itemView.tv_mycourse_address = (TextView)convertView.findViewById(R.id.tv_mycourse_address);
			itemView.tv_mycourse_money = (TextView)convertView.findViewById(R.id.tv_mycourse_money);
			itemView.tv_mycourse_Endstates = (TextView)convertView.findViewById(R.id.tv_mycourse_Endstates);
			itemView.tv_mycourse_club = (TextView)convertView.findViewById(R.id.tv_mycourse_club);
			itemView.ll_states = (LinearLayout)convertView.findViewById(R.id.ll_states);
			itemView.tv_mycourse_distance = (TextView)convertView.findViewById(R.id.tv_mycourse_distance);
			itemView.tv_mycourse_Endtime = (TextView)convertView.findViewById(R.id.tv_mycourse_Endtime);
			
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		itemView.ll_states.setVisibility(View.GONE);
		itemView.tv_mycourse_distance.setVisibility(View.VISIBLE);
		
		HotGoods entity= mList.get(position);
		ImageLoader.getInstance().displayImage(entity.titleMultiUrl, itemView.img_pic,App.getInstance().getHeadOptions());
		itemView.tv_mycourse_name.setText(getNullData(entity.goodsName));//课程名称
		
//		String strEndTime = Util.toString(getNullData(entity.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm");
//		if(strEndTime.length()>5){
//			strEndTime = strEndTime.substring(strEndTime.length()-5, strEndTime.length());
//		} 
		itemView.tv_mycourse_time.setText(Util.toString(getNullData(entity.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//时间
		itemView.tv_mycourse_Endtime.setText(Util.toString(getNullData(entity.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
		itemView.tv_mycourse_money.setText(getNullData(entity.goodsPrice)==""?"￥0":"￥"+entity.goodsPrice);//价格
		itemView.tv_mycourse_Endstates.setText(getNullData(entity.goodsQuota)==""?"名额0":"名额 "+getNullData(entity.goodsQuota));  //报名人数
		itemView.tv_mycourse_distance.setText(getNullData(entity.distances)==""?"0km":entity.distances+"km");//地址
		 
		
		if(entity.goodsType.equals("1")){
			itemView.tv_mycourse_address.setText(getNullData(entity.storeName));//门店地址
			itemView.tv_mycourse_club.setText("闹腾生存适能");// 课程分类
//			itemView.tv_mycourse_address.setVisibility(View.GONE);
		}else if(entity.goodsType.equals("2")){
			itemView.tv_mycourse_club.setText("君昂童子军乐园");// 课程分类
//			itemView.tv_mycourse_address.setVisibility(View.VISIBLE);
			itemView.tv_mycourse_address.setText(getNullData(entity.storeName));//地址
		}else if(entity.goodsType.equals("3")){
			itemView.tv_mycourse_club.setText("周末成长营");// 课程分类
//			itemView.tv_mycourse_address.setVisibility(View.VISIBLE);
			itemView.tv_mycourse_address.setText(getNullData(entity.storeName));//地址
		}
		else{
//			itemView.tv_mycourse_address.setVisibility(View.VISIBLE);
			itemView.tv_mycourse_club.setText("暑期大露营");// 课程分类
			itemView.tv_mycourse_address.setText(getNullData(entity.storeName));//地址
		}
		
//		if(entity.states.equals("001")){
//			itemView.tv_mycourse_states.setVisibility(View.VISIBLE);
//			itemView.tv_mycourse_pay.setVisibility(View.GONE);
//			itemView.tv_mycourse_del.setVisibility(View.GONE);
//		}else if (entity.states.equals("002")){
//			itemView.tv_mycourse_pay.setVisibility(View.GONE);
//			itemView.tv_mycourse_del.setVisibility(View.GONE);
//			itemView.tv_mycourse_states.setVisibility(View.VISIBLE);
//		}else if (entity.states.equals("003")){
//			itemView.tv_mycourse_pay.setVisibility(View.GONE);
//			itemView.tv_mycourse_del.setVisibility(View.GONE);
//			itemView.tv_mycourse_states.setVisibility(View.VISIBLE);
//		}else if (entity.states.equals("004")){
//			itemView.tv_mycourse_states.setVisibility(View.GONE);
//			itemView.tv_mycourse_pay.setVisibility(View.VISIBLE);
//			itemView.tv_mycourse_del.setVisibility(View.VISIBLE);
//		}else {
//			itemView.tv_mycourse_del.setVisibility(View.GONE);
//			itemView.tv_mycourse_pay.setVisibility(View.GONE);
//			itemView.tv_mycourse_states.setVisibility(View.VISIBLE);
//		}
		
		/**
		 * 跳转详情
		 */
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,MyCourseDetailAc.class);
				intent.putExtra("goodsId", mList.get(position).goodsId);//课程ID
				intent.putExtra("degree", mList.get(position).goodsType);//类型
				intent.putExtra("type", "GrabClass");
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	public interface OnSeckillClick{
		void onSeckillClick(int position,int type);
	}
	class ItemView{
		private TextView tv_mycourse_states,tv_mycourse_time,tv_mycourse_address,tv_mycourse_Endtime,tv_mycourse_money,tv_mycourse_name,tv_mycourse_del,tv_mycourse_Endstates,tv_mycourse_pay,tv_mycourse_club,tv_mycourse_distance;
		private RoundImageViewFive img_pic;
		private LinearLayout ll_states;
	
	}
}
