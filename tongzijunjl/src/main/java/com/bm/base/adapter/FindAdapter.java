package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.tzj.ts.ExploreDetailAc;
import com.bm.tzjjl.activity.ImageViewActivity;
import com.lib.widget.FuGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 探索适配器
 * @author jiangsh
 *
 */
public class FindAdapter  extends BaseAd<Model>{
	private PicAdapter picAdapter;
	
	private String[] picArr = new String[]{
			"http://www.totalfitness.com.cn/upfile/681x400/20120323104125_324.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/d31b0ef41bd5ad6e1e7e401f83cb39dbb7fd3cbb.jpg",
			"http://elegantliving.ceconline.com/TEAMSITE/IMAGES/SITE/ES/20080203_EL_ES_02_02.jpg"};
	
	
	public FindAdapter(Context context,List<Model> prolist){
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
			convertView = mInflater.inflate(R.layout.fm_find_list_item, null);
			itemView.tv_exploreName = (TextView)convertView.findViewById(R.id.tv_exploreName);
			itemView.tv_exploreImg = (ImageView)convertView.findViewById(R.id.tv_exploreImg);
			itemView.tv_exploreTime = (TextView)convertView.findViewById(R.id.tv_exploreTime);
			itemView.tv_explore_content = (TextView)convertView.findViewById(R.id.tv_explore_content);
			itemView.gv_explore_pic= (FuGridView)convertView.findViewById(R.id.gv_explore_pic);
			itemView.tv_explore_chart = (TextView)convertView.findViewById(R.id.tv_explore_chart);
			itemView.tv_explore_zan = (TextView)convertView.findViewById(R.id.tv_explore_zan);
			itemView.tv_explore_count = (TextView)convertView.findViewById(R.id.tv_explore_count);
			itemView.lv_listReplay = (ListView)convertView.findViewById(R.id.lv_listReplay);
			
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		final Model entity= mList.get(position);
		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", itemView.tv_exploreImg,App.getInstance().getHeadOptions());
		itemView.tv_exploreName.setText(getNullData(entity.name));//课程名称
		itemView.tv_exploreTime.setText(getNullData(entity.time));//时间
		itemView.tv_explore_content.setText(getNullData(entity.content));//评论内容
		itemView.tv_explore_chart.setText("(30)");
		itemView.tv_explore_zan.setText(getNullData("(15)"));//点赞人数
		itemView.tv_explore_count.setText(getNullData("(20)"));//评论数
		
		picAdapter = new PicAdapter(context,picArr);
		itemView.gv_explore_pic.setAdapter(picAdapter);
		
		itemView.gv_explore_pic.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent intent = new Intent(context, ImageViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("images",picArr);
				bundle.putInt("position", position);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,ExploreDetailAc.class);
				intent.putExtra("type", "FindFm");
				context.startActivity(intent);
			}
		});
		
		itemView.tv_explore_chart.setOnClickListener(new OnClickListener() {//举报
			
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position,1);
			}
		});
//		itemView.tv_explore_count.setOnClickListener(new OnClickListener() {//留言
//			
//			@Override
//			public void onClick(View v) {
//				onSeckillClick.onSeckillClick(position,3);
//			}
//		});
		itemView.tv_explore_zan.setOnClickListener(new OnClickListener() {//点赞
			
			@Override
			public void onClick(View v) {
				onSeckillClick.onSeckillClick(position,2);
			}
		});
		return convertView;
	}
	class ItemView{
		private TextView tv_exploreName,tv_exploreTime,tv_explore_content,tv_explore_chart,tv_explore_zan,tv_explore_count;
		private ImageView tv_exploreImg;
		private FuGridView gv_explore_pic;
		private ListView lv_listReplay;
	
	}
	public interface OnSeckillClick{
		void onSeckillClick(int position,int tag);//1为举报，2为点赞，3为留言
	}
}
