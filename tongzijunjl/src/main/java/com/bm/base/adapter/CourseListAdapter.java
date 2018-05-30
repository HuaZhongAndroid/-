package com.bm.base.adapter;

import java.util.List;

import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 课程列表适配器
 * @author shiyt
 *
 */
public class CourseListAdapter  extends BaseAd<Model>{
	public CourseListAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, final int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_list_courselist, null);
			itemView.img_pic = (ImageView)convertView.findViewById(R.id.img_pic);
			itemView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			itemView.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
			itemView.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
			
			itemView.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
			itemView.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
			itemView.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
			itemView.tv_address = (TextView)convertView.findViewById(R.id.tv_address);
			itemView.tv_category = (TextView)convertView.findViewById(R.id.tv_category);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		//1 户外俱乐部  2暑期大露营   3城市营地
		if(entity.degree.equals("1")){
			itemView.tv_address.setVisibility(View.GONE);
			itemView.tv_category.setText("户外基地");
		}else if(entity.degree.equals("2")){
			itemView.tv_address.setVisibility(View.GONE);
			itemView.tv_category.setText("暑期大露营");
		}else{
			itemView.tv_address.setVisibility(View.VISIBLE);
			itemView.tv_category.setText("城市营地");
		}
		
//		itemView.tv_name.setText("");  //名称
//		itemView.tv_date.setText("");  //日期
//		itemView.tv_time.setText("");  //时间
//		itemView.tv_num.setText("");  //报名人数
//		itemView.tv_age.setText("");  //年龄
//		itemView.tv_price.setText("");  //价格
//		itemView.tv_address.setText("");  //地址
//		itemView.tv_category.setText(""); //分类
		
		ImageLoader.getInstance().displayImage("http://img.taopic.com/uploads/allimg/110102/292-11010221093278.jpg", itemView.img_pic,App.getInstance().getListViewDisplayImageOptions());
		return convertView;
	}
	class ItemView{
		private ImageView img_pic;
		private TextView tv_name,tv_date,tv_time,tv_num,tv_age,tv_price,tv_address,tv_category;
	}
}
