package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 推荐课程适配器
 * @author shiyt
 *
 */
public class CourseAdapter  extends BaseAd<Model>{
	public CourseAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_course, null);
			itemView.tv_mycourse_name = (TextView)convertView.findViewById(R.id.tv_mycourse_name);
			itemView.img_pic = (RoundImageViewFive)convertView.findViewById(R.id.img_pic);
			itemView.tv_mycourse_time = (TextView)convertView.findViewById(R.id.tv_mycourse_time);
			itemView.tv_mycourse_address = (TextView)convertView.findViewById(R.id.tv_mycourse_address);
			itemView.tv_mycourse_money = (TextView)convertView.findViewById(R.id.tv_mycourse_money);
			itemView.tv_category = (TextView)convertView.findViewById(R.id.tv_category);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		//1 户外俱乐部  2暑期大露营   3城市营地
		if(entity.degree.equals("1")){
			itemView.tv_category.setText("闹腾生存适能训练中心");
			itemView.tv_mycourse_address.setVisibility(View.GONE);
		}else if(entity.degree.equals("2")){
			itemView.tv_category.setText("室内体验馆");
			itemView.tv_mycourse_address.setVisibility(View.GONE);
		}else{
			itemView.tv_mycourse_address.setVisibility(View.VISIBLE);
			itemView.tv_category.setText("城市营地");
		}
		
		
		
		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", itemView.img_pic,App.getInstance().getListViewDisplayImageOptions());
		itemView.tv_mycourse_name.setText("[晋级课程] 滚筒+鸟巢+西藏之课程");//课程名称
		itemView.tv_mycourse_time.setText("2015-12-21 10:30-11:30");//时间
		itemView.tv_mycourse_address.setText("上海龙之梦");//地址
		itemView.tv_mycourse_money.setText("￥99");//价格
		return convertView;
	}
	class ItemView{
		private TextView tv_mycourse_time,tv_mycourse_address,tv_mycourse_money,tv_mycourse_name,tv_category;
		private RoundImageViewFive img_pic;
	}
}
