package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.List;

import net.grobas.view.PolygonImageView;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.base.BaseActivity;
import com.bm.entity.Badge;
import com.richer.tzjjl.R;
import com.bm.tzj.czzx.HonoRollAc;
import com.bm.tzj.czzx.MedalDetailAc;
import com.bm.util.SixPullulatePanel;

/**
 * 成长中心
 * @author shiyt
 *
 */
public class GrowthCenterAc extends BaseActivity implements OnClickListener {

	private Context context;
	private ListView lv_content;
	private LinearLayout ll_Msg;
	private TextView tv_get,tv_a_num,tv_b_num,tv_ry;
	private PolygonImageView iv_sixview_head;
	private RelativeLayout rl_a,rl_b,rl_c;
	private FrameLayout fm_content;
	int strPosition=-1;
	public static final int CLICK_STATES=1001;
	Intent intent;
	private String pageType;
	
	private List<Badge> list = new ArrayList<Badge>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.fm_pullulate);
		context = this;
		setTitleName("成长中心");
		pageType=getIntent().getStringExtra("pageType");
		initView();
		
	}

	/**
	 * 初始化数据
	 * @param v
	 */
	private void initView() {
		fm_content = (FrameLayout) findViewById(R.id.fm_content);
		
		tv_a_num=findTextViewById(R.id.tv_a_num);
		tv_b_num=findTextViewById(R.id.tv_b_num);
		tv_ry = findTextViewById(R.id.tv_ry);
		
		rl_a=findRelativeLayoutById(R.id.rl_a);
		rl_b=findRelativeLayoutById(R.id.rl_b);
		rl_c=findRelativeLayoutById(R.id.rl_c);
		
		tv_get=findTextViewById(R.id.tv_get);
		tv_get.setOnClickListener(this);
		
		rl_a.setOnClickListener(this);
		rl_b.setOnClickListener(this);
		rl_c.setOnClickListener(this);
		iv_sixview_head = (PolygonImageView)findViewById(R.id.iv_sixview_head);
		
		/**从荣誉榜点击某个用户进入成长中心  隐藏荣誉排行*/
		if(pageType.equals("HonoRollAc")){
			rl_b.setVisibility(View.GONE);
			tv_ry.setText("关注");
		}
//		else{
//			tv_ry.setText("荣誉榜");
//		}
		
		//ImageLoader.getInstance().displayImage("http://img1.imgtn.bdimg.com/it/u=2612160076,3558836575&fm=21&gp=0.jpg", iv_sixview_head, App.getInstance().getListViewDisplayImageOptions());
		setData();
	}
	
	private void setData() {
		for(int i = 0;i<12;i++){
			Badge  badge = new Badge();
			badge.badgeName = "badge"+i;
			badge.imageUrl  = "http://img1.imgtn.bdimg.com/it/u=2422159595,2264291448&fm=21&gp=0.jpg";
			list.add(badge);
		}
		SixPullulatePanel.setViews(fm_content, list, context,handler);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CLICK_STATES:
				strPosition = msg.arg1;
				intent = new Intent(context, MedalDetailAc.class);
				startActivity(intent);
				break;
			}
			}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_get:
			intent = new Intent(context, MedalDetailAc.class);
			startActivity(intent);
			break;
		case R.id.rl_a:
			break;
		case R.id.rl_b:
			break;
		case R.id.rl_c:
			if(!pageType.equals("HonoRollAc")){
				intent = new Intent(context, HonoRollAc.class);
				startActivity(intent);
			}
			
			
			break;
		default:
			break;
		}
	}
}
