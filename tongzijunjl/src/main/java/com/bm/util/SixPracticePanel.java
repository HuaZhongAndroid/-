package com.bm.util;

import java.util.List;

import net.grobas.view.PolygonImageView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bm.app.App;
import com.bm.entity.Badge;
import com.richer.tzjjl.R;
import com.bm.tzj.fm.PullulateFm;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 
 * 教练顾问的  六边形   图片显示
 * @author wanghy
 *
 */
public class SixPracticePanel {

	
	
	static Handler handler;
	public static  void  setViews(FrameLayout fm_view, List<Badge> list,Context context,Handler strHandler){
		handler = strHandler;
		 List<List<Badge>> mlist = Util.subList(list,5);
		for(int i = 0;i<mlist.size();i++){
			View vm1 = LayoutInflater.from(context).inflate(R.layout.fm_view_six_item_practice, null);
			fm_view.addView(vm1);
			setViewId(vm1,mlist.get(i));
			int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
			vm1.measure(width,height);
			int heights=vm1.getMeasuredHeight()-80; 
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) vm1.getLayoutParams();
			lp.setMargins(0, heights*i, 0, 0);
		}
		
	}
	
	
	private static void setViewId(View v,List<Badge> list){
		
		PolygonImageView iv_a,iv_b,iv_c,iv_d,iv_e;
		 
		 LinearLayout ll_left,ll_right;
		 
		 iv_a = (PolygonImageView)v.findViewById(R.id.iv_a);
		 iv_b = (PolygonImageView)v.findViewById(R.id.iv_b);
		 iv_c = (PolygonImageView)v.findViewById(R.id.iv_c);
		 iv_d = (PolygonImageView)v.findViewById(R.id.iv_d);
		 iv_e = (PolygonImageView)v.findViewById(R.id.iv_e);
		 
		 ll_left = (LinearLayout)v.findViewById(R.id.ll_left);
		 ll_right = (LinearLayout)v.findViewById(R.id.ll_right);
		 
		 
		 if(list.size()>0){
			 ImageLoader.getInstance().displayImage(list.get(0).imageUrl, iv_a,App.getInstance().getListViewDisplayImageOptions());
		     iv_a.setOnClickListener(onclickView(list.get(0)));
		 }else{
			 iv_a.setVisibility(View.GONE);
			 ll_left.setVisibility(View.INVISIBLE);
			 ll_right.setVisibility(View.INVISIBLE);
		 }
	     
		 if(list.size()>1){
			 ImageLoader.getInstance().displayImage(list.get(0).imageUrl, iv_b,App.getInstance().getListViewDisplayImageOptions());
		     iv_b.setOnClickListener(onclickView(list.get(1)));
		 }else{
			 iv_b.setVisibility(View.GONE);
			 ll_left.setVisibility(View.INVISIBLE);
			 ll_right.setVisibility(View.INVISIBLE);
		 }
	     
		 if(list.size()>2){
			 ImageLoader.getInstance().displayImage(list.get(0).imageUrl, iv_c,App.getInstance().getListViewDisplayImageOptions());
	  	     iv_c.setOnClickListener(onclickView(list.get(2)));
		 }else{
			 iv_c.setVisibility(View.GONE);
			 ll_left.setVisibility(View.INVISIBLE);
			 ll_right.setVisibility(View.INVISIBLE);
		 }
	     
		 if(list.size()>3){
			 ll_left.setVisibility(View.VISIBLE);
			 ll_right.setVisibility(View.VISIBLE);
			 ImageLoader.getInstance().displayImage(list.get(0).imageUrl, iv_d,App.getInstance().getListViewDisplayImageOptions());
			  iv_d.setOnClickListener(onclickView(list.get(3)));
		 }else{
			 iv_d.setVisibility(View.GONE);
		 }
	    
		 if(list.size()>4){
			 ImageLoader.getInstance().displayImage(list.get(0).imageUrl, iv_e,App.getInstance().getListViewDisplayImageOptions());
		     iv_e.setOnClickListener(onclickView(list.get(4)));
		 }else{
			 iv_e.setVisibility(View.GONE);
		 }
	}
	
	private static OnClickListener onclickView(final Badge badge){
		return new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Message msg = new Message();
				msg.what = PullulateFm.CLICK_STATES;
				msg.arg1 = badge.id;
				handler.sendMessage(msg);
			}
		};
	}
	
	
}
