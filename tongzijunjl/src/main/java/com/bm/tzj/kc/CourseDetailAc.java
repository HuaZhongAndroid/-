package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.CourseAdapter;
import com.bm.base.adapter.ImagePagerAdapters280;
import com.bm.base.adapter.RegistrationAdapter;
import com.bm.entity.Model;
import com.bm.share.ShareModel;
import com.richer.tzjjl.R;
import com.bm.tzj.caledar.CaledarAc;
import com.bm.tzjjl.activity.LocationMapAc;
import com.bm.util.TestData;
import com.bm.view.FlowLayout;
import com.bm.view.TagAdapter;
import com.bm.view.TagFlowLayout;
import com.example.tzjlib.MainActivity;
import com.lib.tool.UITools;
import com.lib.widget.BannerView;
import com.lib.widget.FuGridView;
import com.lib.widget.FuListView;
import com.lib.widget.BannerView.OnBannerClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 课程详情
 * 
 * @author shiyt
 * 
 */
public class CourseDetailAc extends BaseActivity implements OnClickListener {
	private Context mContext;
	private CourseAdapter adapter;
	private RegistrationAdapter registrAdapter;
	private List<Model> list = new ArrayList<Model>();
	private List<Model> listPerson = new ArrayList<Model>();
	private FuListView lv_course;
	private FuGridView gv_person;
//	RatingBar room_ratingbar;
	private com.bm.view.RatingBar sectorView;
	private TagFlowLayout mFlowLayout;
	private LinearLayout ll_mzsm, ll_jlinfo,ll_bmrs,ll_time,ll_kc_one,ll_kc_two,ll_map_two;
	private ImageView img_course, img_head,img_collect,img_share;
	private TextView tv_name, tv_price, tv_category, tv_age, tv_time,
			tv_address, tv_coursecontent, tv_username, tv_jlage, tv_zysx,
			tv_register, tv_collet, tv_share,tv_two_address;
	BannerView banner;
	private LinearLayout ll_map;
	private String[] picArr = new String[] {
			"http://www.totalfitness.com.cn/upfile/681x400/20120323104125_324.jpg",
			"http://d.hiphotos.baidu.com/zhidao/pic/item/d31b0ef41bd5ad6e1e7e401f83cb39dbb7fd3cbb.jpg",
			"http://elegantliving.ceconline.com/TEAMSITE/IMAGES/SITE/ES/20080203_EL_ES_02_02.jpg" };
	private String[] mVals = new String[] { "上海龙之梦店", "上海中山公园店", "上海大宁店" };
	/**1 户外俱乐部  2暑期大露营   3城市营地*/
	private String degree=""; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_coursedetail);
		mContext = this;
		setTitleName("课程详情");
		degree=getIntent().getStringExtra("degree");
		init();
	}

	public void init() {
		lv_course = (FuListView) findViewById(R.id.lv_course);
		adapter = new CourseAdapter(mContext, list);
		lv_course.setAdapter(adapter);

		gv_person = (FuGridView) findViewById(R.id.gv_person);
		registrAdapter = new RegistrationAdapter(mContext, listPerson);
		gv_person.setAdapter(registrAdapter);
		sectorView=(com.bm.view.RatingBar) findViewById(R.id.sectorView);
//		room_ratingbar = (RatingBar) findViewById(R.id.room_ratingbar);
		ll_mzsm = findLinearLayoutById(R.id.ll_mzsm);
		ll_map = findLinearLayoutById(R.id.ll_map);
		img_course = findImageViewById(R.id.img_course);
		img_head = findImageViewById(R.id.img_head);
		ll_bmrs=findLinearLayoutById(R.id.ll_bmrs);
		tv_name = findTextViewById(R.id.tv_name);
		tv_price = findTextViewById(R.id.tv_price);
		ll_time=findLinearLayoutById(R.id.ll_time);
		tv_category = findTextViewById(R.id.tv_category);
		tv_age = findTextViewById(R.id.tv_age);
		tv_time = findTextViewById(R.id.tv_time);
		tv_address = findTextViewById(R.id.tv_address);
		tv_two_address = findTextViewById(R.id.tv_two_address);
		tv_coursecontent = findTextViewById(R.id.tv_coursecontent);
		tv_username = findTextViewById(R.id.tv_username);
		tv_jlage = findTextViewById(R.id.tv_jlage);
		tv_zysx = findTextViewById(R.id.tv_zysx);
		ll_jlinfo = findLinearLayoutById(R.id.ll_jlinfo);
		
		img_collect = findImageViewById(R.id.img_collect);
		img_share=findImageViewById(R.id.img_share);
		tv_register = findTextViewById(R.id.tv_register);
		/**根据不同分类显示布局*/
		ll_kc_one=findLinearLayoutById(R.id.ll_kc_one);
		ll_kc_two=findLinearLayoutById(R.id.ll_kc_two);
		ll_map_two=findLinearLayoutById(R.id.ll_map_two);
		if(degree.equals("3")){
			ll_kc_two.setVisibility(View.VISIBLE);
			ll_kc_one.setVisibility(View.GONE);
		}else{
			ll_kc_one.setVisibility(View.VISIBLE);
			ll_kc_two.setVisibility(View.GONE);
		}
		
		// int w = App.getInstance().getScreenWidth();
		// int h = (300*w)/700;
		// fm_image.setLayoutParams(new
		// LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,h));
		img_collect.setOnClickListener(this);
		img_share.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		
		ll_bmrs.setOnClickListener(this);
		ll_mzsm.setOnClickListener(this);
		
		ll_map.setOnClickListener(this);
		ll_jlinfo.setOnClickListener(this);
		ll_time.setOnClickListener(this);
		ll_map_two.setOnClickListener(this);
		getData();
		getDataPerson();
		setMD();
		initViewPager();
//		room_ratingbar.setIsIndicator(true);// 禁止点击星星
		gv_person.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, CourseCompanionAc.class);
				startActivity(intent);
			}
		});



		lv_course.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, CourseDetailAc.class);
				intent.putExtra("degree", list.get(arg2).degree);
				startActivity(intent);
			}
		});
		// 教练头像
		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", img_head,App.getInstance().getHeadOptions());
	
		setData();
	}

	// 初始化viewpager
	public void initViewPager() {
		banner = (BannerView) findViewById(R.id.bannerView);
		banner.setOnBannerClickListener(new OnBannerClickListener() {
			@Override
			public void OnBannerClicked(int pos) {
				
			}
		});
		banner.update(picArr);
	}

	/**
	 * 获取详情信息
	 */
	public void setData() {
//		tv_name.setText(""); // 课程名称
//		tv_price.setText(""); // 课程价格
//		
//		tv_age.setText("");// 适合年龄
//		tv_time.setText("");// 时间
//		tv_address.setText("");// 地址
//		tv_coursecontent.setText("");// 课程内容
//		tv_username.setText("");// 教练名
//		tv_jlage.setText("");// 教练年龄
//		tv_zysx.setText("");// 注意事项
////		room_ratingbar.setRating(Integer.valueOf("3.0"));// 星星个数
//		sectorView.setRating(5);
//		// 教练头像
//		ImageLoader.getInstance().displayImage("http://xbyx.cersp.com/xxzy/UploadFiles_7930/200808/20080810110053944.jpg", img_head,App.getInstance().getHeadOptions());

		if(degree.equals("1")){
			tv_category.setText("闹腾生存适能训练中心");// 课程分类
		}else if(degree.equals("2")){
			tv_category.setText("室内体验馆");// 课程分类
		}else{
			tv_category.setText("城市营地");// 课程分类
		}
	}

	/**
	 * 获取数据
	 */
	public void getData() {
		for (int i = 0; i < 5; i++) {
			Model model = new Model();
			if(i==0 || i==3){
				model.degree="1";  //1 户外俱乐部  2暑期大露营   3城市营地
			}else if(i==1 || i==4){
				model.degree="2";
			}else{
				model.degree="3";
			}
			
			list.add(model);
		}
		adapter.notifyDataSetChanged();
	}

	public void getDataPerson() {
		for (int i = 0; i < 5; i++) {
			listPerson.add(new Model());
		}
		registrAdapter.notifyDataSetChanged();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

		};
	};

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_mzsm:// 免责声明
			intent = new Intent(mContext, DisclaimerAc.class);
			startActivity(intent);
			break;
		case R.id.img_share: // 分享
			ShareModel model = new ShareModel();
			model.title = "测试";
			model.conent = "测试内容";
			model.urlImg = "";
			model.targetUrl = "http://www.baidu.com";
			share.shareData(model);
			break;
		case R.id.img_collect:// 收藏
			dialogToast("收藏");
			break;
		case R.id.ll_time:// 时间
			intent = new Intent(mContext, CaledarAc.class);
			startActivity(intent);
			break;
		case R.id.tv_register: // 立即报名
			intent = new Intent(mContext, PayInfoAc.class);
			startActivity(intent);
			break; 
		case R.id.ll_bmrs: // 报名人数
			intent = new Intent(mContext, CourseCompanionAc.class);
			startActivity(intent);
			break; 
		case R.id.ll_map: // 地图
			intent = new Intent(this, LocationMapAc.class);
			intent.putExtra("longitude", "121.475816");
			intent.putExtra("latitude", "31.312117");
			startActivity(intent);
			break;
		case R.id.ll_map_two: //带名店的地址
			intent = new Intent(this, LocationMapAc.class);
			intent.putExtra("longitude", "121.475816");
			intent.putExtra("latitude", "31.312117");
			startActivity(intent);
			break;
		case R.id.ll_jlinfo: // 教练信息
			intent = new Intent(mContext, CoachInformationAc.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	// 门店
	private void setMD() {
		final LayoutInflater mInflater = LayoutInflater.from(this);
		mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
		// mFlowLayout.setMaxSelectCount(3);
		TagAdapter<String> adapter;
		mFlowLayout.setAdapter(adapter=new TagAdapter<String>(mVals) {

			@Override
			public View getView(FlowLayout parent, int position, String s) {
				TextView tv = (TextView) mInflater.inflate(R.layout.tv, mFlowLayout, false);
				tv.setText(s);
				return tv;
			}
		});
		adapter.setSelectedList(0);
		mFlowLayout
				.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
					@Override
					public boolean onTagClick(View view, int position,
							FlowLayout parent) {
						
//						Toast.makeText(mContext, mVals[position],
//								Toast.LENGTH_SHORT).show();
						// view.setVisibility(View.GONE);
						return true;
					}
				});

		mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
			@Override
			public void onSelected(Set<Integer> selectPosSet) {
				tv_two_address.setText("虹口区广纪路83"+selectPosSet.toString().replace("[", "").replace("]", ""));
				// CaledarAc.this.setTitle("choose:" +
				// selectPosSet.toString());
			}
		});
	}
}
