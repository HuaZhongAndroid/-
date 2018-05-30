package com.bm.base.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseAd;
import com.bm.entity.Model;
import com.bm.entity.User;
import com.richer.tzjjl.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 勋章列表适配器
 * @author shiyt
 *
 */
public class RefusedAdapter  extends BaseAd<Model>{
	public RefusedAdapter(Context context,List<Model> prolist){
		setActivity(context, prolist);
	}
	
	@Override
	protected View setConvertView(View convertView, int position) {
		ItemView itemView = null;
		if(convertView  ==null){
			itemView = new ItemView();
			convertView = mInflater.inflate(R.layout.item_gridview_comm, null);
			itemView.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
			itemView.iv_select=(ImageView) convertView.findViewById(R.id.iv_select);
			
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		
		Model entity= mList.get(position);
		itemView.tv_content.setText(getNullData(entity.medalName));
	
		if (entity.isSelected) {
			itemView.iv_select.setImageResource(R.drawable.check_comment_on);
			
		} else {
			itemView.iv_select.setImageResource(R.drawable.check_comment_off);
		}
		
		return convertView;
	}
	
	private int selectItem = -1;

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}
	public int getSelectItem() {
		return selectItem;
	}
	
	class ItemView{
		private TextView tv_content;
		private ImageView iv_select;
	
	}
}
