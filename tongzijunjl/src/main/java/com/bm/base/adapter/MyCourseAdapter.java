package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
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
public class MyCourseAdapter  extends BaseAd<HotGoods>{
	public MyCourseAdapter(Context context,List<HotGoods> prolist,String state){
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
			itemView.tv_yjq = (TextView)convertView.findViewById(R.id.tv_yjq);
			itemView.tv_mycourse_time = (TextView)convertView.findViewById(R.id.tv_mycourse_time);
			itemView.tv_mycourse_address = (TextView)convertView.findViewById(R.id.tv_mycourse_address);
			itemView.tv_mycourse_money = (TextView)convertView.findViewById(R.id.tv_mycourse_money);
			itemView.tv_jq = (TextView)convertView.findViewById(R.id.tv_jq);
			itemView.tv_mycourse_Endstates = (TextView)convertView.findViewById(R.id.tv_mycourse_Endstates);
			itemView.tv_qd = (TextView)convertView.findViewById(R.id.tv_qd);
			itemView.tv_qxkc = (TextView)convertView.findViewById(R.id.tv_qxkc);
			itemView.tv_mycourse_club = (TextView)convertView.findViewById(R.id.tv_mycourse_club);
			itemView.tv_kcpj=(TextView) convertView.findViewById(R.id.tv_kcpj);
			itemView.tv_mycourse_Endtime = (TextView)convertView.findViewById(R.id.tv_mycourse_Endtime);
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		HotGoods entity= mList.get(position);
		ImageLoader.getInstance().displayImage(entity.titleMultiUrl, itemView.img_pic,App.getInstance().getHeadOptions());
		itemView.tv_mycourse_name.setText(getNullData(entity.goodsName));//课程名称
//		itemView.tv_mycourse_time.setText(getNullData(entity.time));//时间
		
//		String strEndTime = Util.toString(getNullData(entity.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm");
//		if(strEndTime.length()>5){
//			strEndTime = strEndTime.substring(strEndTime.length()-5, strEndTime.length());
//		} 
		itemView.tv_mycourse_time.setText(Util.toString(getNullData(entity.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//开始时间
		itemView.tv_mycourse_Endtime.setText(Util.toString(getNullData(entity.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//结束时间
		
		itemView.tv_mycourse_money.setText(getNullData(entity.goodsPrice)==""?"￥0":"￥"+entity.goodsPrice);//价格
//		itemView.tv_mycourse_club.setText(getNullData("城市营地"));//城市营地
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
		
		/**0 所有  1未开始    2进行中    3已结束   */
		//只有未开始的课程，才有取消课程              已结束的课程，有课程评价的功能没有建群功能，   未开始和进行中的有签到功能   
		if(entity.classStatus.equals("3")){
			itemView.tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
			itemView.tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab));
			itemView.tv_mycourse_Endstates.setText("已结束");
			itemView.tv_qxkc.setVisibility(View.GONE);//取消课程
			itemView.tv_qd.setVisibility(View.GONE);//签到
			itemView.tv_kcpj.setVisibility(View.VISIBLE);//评价
			itemView.tv_jq.setVisibility(View.GONE);//建群
			itemView.tv_yjq.setVisibility(View.GONE);//已建群
		}else if(entity.classStatus.equals("2")){
			itemView.tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
			itemView.tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab_two));
			itemView.tv_mycourse_Endstates.setText("进行中");
			itemView.tv_qxkc.setVisibility(View.GONE);
			itemView.tv_qd.setVisibility(View.VISIBLE);
			itemView.tv_kcpj.setVisibility(View.GONE);
			/**0未建群    1已建群*/
			if(entity.groupStatus.equals("0")){
				itemView.tv_jq.setVisibility(View.VISIBLE);
				itemView.tv_yjq.setVisibility(View.GONE);
			}else if(entity.groupStatus.equals("1")){
				itemView.tv_jq.setVisibility(View.GONE);
				itemView.tv_yjq.setVisibility(View.VISIBLE);
			}
		}else if(entity.classStatus.equals("1")){
			itemView.tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
			itemView.tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab));
			itemView.tv_mycourse_Endstates.setText("未开始");
			itemView.tv_qxkc.setVisibility(View.VISIBLE);
			itemView.tv_qd.setVisibility(View.VISIBLE);
			itemView.tv_kcpj.setVisibility(View.GONE);
			/**0未建群    1已建群*/
			if(entity.groupStatus.equals("0")){
				itemView.tv_jq.setVisibility(View.VISIBLE);
				itemView.tv_yjq.setVisibility(View.GONE);
			}else if(entity.groupStatus.equals("1")){
				itemView.tv_jq.setVisibility(View.GONE);
				itemView.tv_yjq.setVisibility(View.VISIBLE);
			}
		}else{
			if(entity.classStatus.equals("3")){
				itemView.tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
				itemView.tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab));
				itemView.tv_mycourse_Endstates.setText("已结束");
				itemView.tv_qxkc.setVisibility(View.GONE);
				itemView.tv_qd.setVisibility(View.GONE);
				itemView.tv_kcpj.setVisibility(View.VISIBLE);
				itemView.tv_jq.setVisibility(View.GONE);
				itemView.tv_yjq.setVisibility(View.GONE);//已建群
				
			}else if(entity.classStatus.equals("2")){
				itemView.tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
				itemView.tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab_two));
				itemView.tv_mycourse_Endstates.setText("进行中");
				itemView.tv_qxkc.setVisibility(View.GONE);
				itemView.tv_qd.setVisibility(View.VISIBLE);
				itemView.tv_kcpj.setVisibility(View.GONE);
				/**0未建群    1已建群*/
				if(entity.groupStatus.equals("0")){
					itemView.tv_jq.setVisibility(View.VISIBLE);
					itemView.tv_yjq.setVisibility(View.GONE);
				}else if(entity.groupStatus.equals("1")){
					itemView.tv_jq.setVisibility(View.GONE);
					itemView.tv_yjq.setVisibility(View.VISIBLE);
				}
			}else if(entity.classStatus.equals("1")){
				itemView.tv_mycourse_Endstates.setTextColor(context.getResources().getColor(R.color.white));
				itemView.tv_mycourse_Endstates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.collection_lab));
				itemView.tv_mycourse_Endstates.setText("未开始");
				itemView.tv_qxkc.setVisibility(View.VISIBLE);
				itemView.tv_qd.setVisibility(View.VISIBLE);
				itemView.tv_kcpj.setVisibility(View.GONE);
				/**0未建群    1已建群*/
				if(entity.groupStatus.equals("0")){
					itemView.tv_jq.setVisibility(View.VISIBLE);
					itemView.tv_yjq.setVisibility(View.GONE);
				}else if(entity.groupStatus.equals("1")){
					itemView.tv_jq.setVisibility(View.GONE);
					itemView.tv_yjq.setVisibility(View.VISIBLE);
				}
			}
			
		}
		
		/**
		 * 跳转详情
		 */
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,MyCourseDetailAc.class);
				intent.putExtra("degree", mList.get(position).goodsType);
				intent.putExtra("goodsId", mList.get(position).goodsId);
				intent.putExtra("type", "Course");
				context.startActivity(intent);
			}
		});
		
		//建群
		itemView.tv_jq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position,1);
			}
		});
		
		//取消课程
		itemView.tv_qxkc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position,2);
			}
		});
		
		//签到
		itemView.tv_qd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position,3);
			}
		});
		//课程评分
		itemView.tv_kcpj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position,4);
			}
		});
		
		itemView.tv_yjq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}
	
	public interface OnSeckillClick{
		void onSeckillClick(int position,int type);
	}
	class ItemView{
		private TextView tv_mycourse_time,tv_mycourse_address,tv_mycourse_money,tv_mycourse_Endtime,
		tv_mycourse_name,tv_mycourse_Endstates,tv_mycourse_club,
		tv_yjq,tv_jq,tv_qd,tv_qxkc,tv_kcpj;
		private RoundImageViewFive img_pic;
	
	}
}
