package com.bm.tzj.ts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.MyExploreReplayAdapter;
import com.bm.base.adapter.PicAdapter;
import com.bm.dialog.UtilDialog;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.tzjjl.activity.ImageViewActivity;
import com.lib.tool.Pager;
import com.lib.widget.FuGridView;
import com.lib.widget.RefreshViewPD;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 探索详情
 * @author wanghy
 *
 */
public class ExploreDetailAc extends BaseActivity implements OnClickListener{
	private Context context;
	private TextView tv_exploreName,tv_exploreTime,tv_explore_content,tv_explore_chart,tv_explore_zan,tv_explore_count,tv_explore_zanName,tv_exploreDel;
	private ImageView tv_exploreImg;
	private FuGridView gv_explore_pic;
	private ListView lv_listReplay;
	private MyExploreReplayAdapter replayAdapter;
	private PicAdapter picAdapter;
	private List<Model> list = new ArrayList<Model>();
	
	//判断是从我的探索进来还是探索进来
	String strType = "";
	
	private RefreshViewPD refresh_view;
	/** 分页器 */
	public Pager pager = new Pager(10);
	
	private ListView lv_exploreDetail;
	
	private String[] picArr = new String[]{
			"http://www.totalfitness.com.cn/upfile/681x400/20120323104125_324.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/d31b0ef41bd5ad6e1e7e401f83cb39dbb7fd3cbb.jpg",
			"http://elegantliving.ceconline.com/TEAMSITE/IMAGES/SITE/ES/20080203_EL_ES_02_02.jpg"};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_exploredetail);
		context = this;
		
		initView();
	}
	
	private void initView(){
		strType = getIntent().getStringExtra("type");
		refresh_view = (RefreshViewPD) findViewById(R.id.refresh_view);
		lv_exploreDetail = findListViewById(R.id.lv_exploreDetail);
		initHeadView();
		
		replayAdapter = new MyExploreReplayAdapter(context,list);
		lv_exploreDetail.setAdapter(replayAdapter);
		
		if(strType.equals("FindFm")){
			setTitleName("探索详情");
		}else{
			setTitleName("我的探索详情");
		}
		
		setData();
		
	}
	
	private void setData() {
		for (int i = 0; i < 5; i++) {
			Model info = new Model();
			info.name= "丽萨" + (i + 3) +":";
			info.content="宝贝很棒哦"+(i+4);
			list.add(info);
		}
		replayAdapter.notifyDataSetChanged();
		tv_exploreName.setText(getNullData("嘻嘻"));//课程名称
		tv_exploreTime.setText(getNullData("2015.8.12 15:00"));//时间
		tv_explore_content.setText(getNullData("今天上课非常开心，学到了很多的知识宝宝很勇敢。谢谢教练"));//评论内容
		tv_explore_chart.setText("(30)");
		tv_explore_zan.setText(getNullData("(15)"));//点赞人数
		tv_explore_count.setText(getNullData("(20)"));//评论数
		tv_explore_zanName.setText(getNullData("安妮,爱探险的朵拉"));//点赞名单
		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", tv_exploreImg,App.getInstance().getHeadOptions());
		
	}
	
	/**
	 * 初始化HeadView信息
	 */
	public void initHeadView(){
		View headView = getLayoutInflater().inflate(R.layout.ac_head_explore, null);
		tv_exploreName = (TextView)headView.findViewById(R.id.tv_exploreName);
		tv_exploreImg = (ImageView)headView.findViewById(R.id.tv_exploreImg);
		tv_exploreTime = (TextView)headView.findViewById(R.id.tv_exploreTime);
		tv_explore_content = (TextView)headView.findViewById(R.id.tv_explore_content);
		gv_explore_pic= (FuGridView)headView.findViewById(R.id.gv_explore_pic);
		tv_explore_chart = (TextView)headView.findViewById(R.id.tv_explore_chart);
		tv_explore_zan = (TextView)headView.findViewById(R.id.tv_explore_zan);
		tv_explore_count = (TextView)headView.findViewById(R.id.tv_explore_count);
		tv_explore_zanName = (TextView)headView.findViewById(R.id.tv_explore_zanName);
		tv_exploreDel = (TextView) headView.findViewById(R.id.tv_exploreDel);
		tv_exploreDel.setVisibility(View.GONE);
		
		tv_explore_count.setOnClickListener(this);
		tv_explore_chart.setOnClickListener(this);
		
		picAdapter = new PicAdapter(context,picArr);
		gv_explore_pic.setAdapter(picAdapter);
		
		gv_explore_pic.setOnItemClickListener(new OnItemClickListener() {
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
		
		lv_exploreDetail.addHeaderView(headView);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_explore_count:
			UtilDialog.dialogPromtMessage(context,handler);
		break;
		case R.id.tv_explore_chart:
			UtilDialog.dialogTwoBtnContentTtile(context, "确定举报该条信息", "取消","确定","提示",handler,2);
			break;

		default:
			break;
		}
	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:// 确定事件
				App.toast("删除");
				break;
			}
			;
		};
	};
}
