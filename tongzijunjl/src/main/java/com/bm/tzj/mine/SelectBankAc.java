package com.bm.tzj.mine;

import java.util.List;

import com.bm.base.BaseActivity;
import com.bm.entity.Dictionary;
import com.bm.util.BaseDataUtil;
import com.bm.util.Util;
import com.richer.tzjjl.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectBankAc extends BaseActivity{

	private GridView gv_banks;
	
	private List<Dictionary> yinhangList;
	
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this;
		this.contentView(R.layout.ac_selectbank);
		this.setTitleName("选择银行");
		
		gv_banks = (GridView)this.findViewById(R.id.gv_banks);
		yinhangList = BaseDataUtil.yinhangList;
		
		gv_banks.setAdapter(adapter);
	}

	
	private BaseAdapter adapter = new BaseAdapter(){

		@Override
		public int getCount() {
			return yinhangList == null? 0 : yinhangList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
			}
			
			TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			
			final Dictionary yinhang = yinhangList.get(position);
			tv_name.setText(yinhang.showvalue);
			String img = "bank_logo/" + yinhang.storevalue + ".png";
			
			Bitmap bm = Util.getImageFromAssetsFile(context, img);
			iv_icon.setImageBitmap(bm);
			
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent data = new Intent();
					data.putExtra("yinhang", yinhang);
					SelectBankAc.this.setResult(SelectBankAc.RESULT_OK, data);
					finish();
				}
			});
			
			return convertView;
		}};
}
