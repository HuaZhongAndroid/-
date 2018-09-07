package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.BaseCaptureActivity;
import com.bm.dialog.ThreeButtonDialog;
import com.bm.entity.HotGoods;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonResult;
import com.lib.widget.RoundImageViewFive;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 课程评价
 * @author shiyt
 *
 */
public class CourseCommentAc extends BaseCaptureActivity implements OnClickListener {
	private Context context;
	private View v_1, v_2, v_3;
	private int tv_titleId[] = { R.id.tv_cjet, R.id.tv_ypf,R.id.tv_wpf};
	private TextView tv_title[] = new TextView[3];
	private TextView tv_cjet,tv_ypf,tv_wpf,tv_mycourse_name,tv_mycourse_club,tv_mycourse_address,tv_mycourse_time,tv_mycourse_money,tv_mycourse_Endtime;
	private ViewPager vPager;
	private RoundImageViewFive img_pic;
	private List<CourseCommentFrameLayout> dataList = new ArrayList<CourseCommentFrameLayout>();
	private String degree;
	static CourseCommentAc intance; 
	HotGoods hotGoods;
	private String goodsName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_courseomment);
		context = this;
		intance = this;
		setTitleName("课程评价");
		degree=getIntent().getStringExtra("degree");
		initView();
		
	}
	
	/**
	 * 清除状态
	 */
	private void clearState() {
		v_1.setVisibility(View.GONE);
		v_2.setVisibility(View.GONE);
		v_3.setVisibility(View.GONE);

		tv_cjet.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_ypf.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		tv_wpf.setTextColor(getResources().getColor(R.color.course_bg_textColor));
		
	}

	private void initView() {
		hotGoods = (HotGoods) getIntent().getSerializableExtra("hotGoods");
		tv_mycourse_name=findTextViewById(R.id.tv_mycourse_name);
		tv_mycourse_Endtime = findTextViewById(R.id.tv_mycourse_Endtime);
		tv_mycourse_club=findTextViewById(R.id.tv_mycourse_club);
		tv_mycourse_address=findTextViewById(R.id.tv_mycourse_address);
		tv_mycourse_time=findTextViewById(R.id.tv_mycourse_time);
		tv_mycourse_money=findTextViewById(R.id.tv_mycourse_money);
		img_pic=(RoundImageViewFive) findViewById(R.id.img_pic);
		
		tv_cjet = findTextViewById(R.id.tv_cjet);
		tv_ypf = findTextViewById(R.id.tv_ypf);
		tv_wpf = findTextViewById(R.id.tv_wpf);

		v_1 = findViewById(R.id.v_1);
		v_2 = findViewById(R.id.v_2);
		v_3 = findViewById(R.id.v_3);

		tv_cjet.setOnClickListener(this);
		tv_ypf.setOnClickListener(this);
		tv_wpf.setOnClickListener(this);

		vPager = (ViewPager) findViewById(R.id.vPager);
		for (int i = 0; i < tv_title.length; i++) {
			tv_title[i] = (TextView) findViewById(tv_titleId[i]);
		}
		goodsName = getNullData(hotGoods.goodsName);
		dataList.add(new CourseCommentFrameLayout(context, "2",hotGoods.goodsType,hotGoods.goodsId, hotGoods.babyId,goodsName)); // 参加儿童
		dataList.add(new CourseCommentFrameLayout(context, "1",hotGoods.goodsType,hotGoods.goodsId, hotGoods.babyId,goodsName)); // 已签到
		dataList.add(new CourseCommentFrameLayout(context, "0",hotGoods.goodsType,hotGoods.goodsId, hotGoods.babyId,goodsName)); // 未签到
		vPager.setAdapter(pagerAdapter);

		vPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				selectTieleChange(arg0);
				dataList.get(arg0).reFresh();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		setDate();
		
	}
	
	public void setDate(){
		tv_mycourse_time.setText(Util.toString(getNullData(hotGoods.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//时间
		tv_mycourse_Endtime.setText(Util.toString(getNullData(hotGoods.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
		//3 户外俱乐部  2暑期大露营   1城市营地 
		if(hotGoods.goodsType.equals("3")){
			tv_mycourse_club.setText("户外基地");
			tv_mycourse_address.setText(getNullData(hotGoods.address));
		}else if(hotGoods.goodsType.equals("2")){
			tv_mycourse_club.setText("暑期大露营");
			tv_mycourse_address.setText(getNullData(hotGoods.address));
		}else{
			tv_mycourse_club.setText("城市营地");
			tv_mycourse_address.setText(getNullData(hotGoods.storeName));
		}
		
		tv_mycourse_name.setText(goodsName);//课程名称
		ImageLoader.getInstance().displayImage(hotGoods.titleMultiUrl, img_pic,App.getInstance().getHeadOptions());
		tv_mycourse_time.setText(Util.toString(getNullData(hotGoods.startTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//开始时间
		tv_mycourse_Endtime.setText(Util.toString(getNullData(hotGoods.endTime),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));//结束时间
		tv_mycourse_money.setText(getNullData(hotGoods.goodsPrice) == "" ? "￥0" :"￥"+ hotGoods.goodsPrice);
		
		getPassCount();
		
		tv_cjet.setText("参加儿童（"+hotGoods.orderNum+"）");
		
	}
	
	
	public void getPassCount(){
		if(null == App.getInstance().getCoach()){
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", App.getInstance().getCoach().coachId);
		map.put("baseId", hotGoods.goodsId);
		
		UserManager.getInstance().getTzjgoodsPassCo(context, map, new ServiceCallback<CommonResult<Model>>() {
			
			@Override
			public void error(String msg) {
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonResult<Model> obj) {
				if(null != obj.data){
					tv_ypf.setText("已评分（"+obj.data.passCount+"）");
					tv_wpf.setText("未评分（"+obj.data.unPassCount+"）");
					tv_cjet.setText("参加儿童（"+(Integer.valueOf(obj.data.unPassCount)+Integer.valueOf(obj.data.passCount))+"）");
				}
			}
		});
		
	}

	private PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(dataList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			
			View viewClick = dataList.get(position);
			viewClick.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			container.addView(dataList.get(position));
			return dataList.get(position);
		}

	};


	private void selectTieleChange(int Index) {
		clearState();
		switch (Index) {
		case 0:
			v_1.setVisibility(View.VISIBLE);
			tv_cjet.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 1:
			v_2.setVisibility(View.VISIBLE);
			tv_ypf.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		case 2:
			v_3.setVisibility(View.VISIBLE);
			tv_wpf.setTextColor(getResources().getColor(R.color.app_dominantHue));
			break;
		default:
			break;
		}
		vPager.setCurrentItem(Index);
	}

	@Override
	public void onClick(View view) {
		clearState();
		switch (view.getId()) {
		case R.id.tv_cjet:
			selectTieleChange(0);
			vPager.setCurrentItem(0);
			break;
		case R.id.tv_ypf:
			selectTieleChange(1);
			vPager.setCurrentItem(1);
			break;
		case R.id.tv_wpf:
			selectTieleChange(2);
			vPager.setCurrentItem(2);
			
			break;
		default:
			break;
		}
	}
	
	public void getDate(){
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).reFresh();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getDate();
	}
	
	
	private List<Model> listRefu = new ArrayList<Model>();
	private GridView fgv_addImage;
	public static String tagType="1";   //1通过   0不通过
	/**
	 * 
	 * 评价弹出框1
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public void dialogTwoBtnCommResult(String content,String btnLeftName,String btnRightName,final Handler handler,final int code) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_comm_one, null);
		
		fgv_addImage = (GridView) view.findViewById(R.id.fgv_addImage);
		uploadListImg.clear();
		fgv_addImage.setAdapter(upimgAdapter);
		
		final EditText et_content = (EditText)view.findViewById(R.id.et_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		LinearLayout ll_tg=(LinearLayout) view.findViewById(R.id.ll_tg);
		LinearLayout ll_btg=(LinearLayout) view.findViewById(R.id.ll_btg);
		final ImageView img_tg=(ImageView) view.findViewById(R.id.img_tg);
		final ImageView img_btg=(ImageView) view.findViewById(R.id.img_btg);
		img_tg.setSelected(true);
		
		ll_tg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tagType="1";
				img_tg.setSelected(true);
				img_btg.setSelected(false);
			}
		});
		
		ll_btg.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View arg0) {
				img_btg.setSelected(true);
				img_tg.setSelected(false);
				tagType="0";
			}
		});
		
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		tv_name.setText(content);
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Message msg = new Message();
//				msg.what = 1;
//				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Message msg = new Message();
				msg.what = code;
				
				Model mInfo = new Model();
				mInfo.content = et_content.getText().toString().trim();
				if(mInfo.content.length()==0)
				{
					App.toast("请填写评语内容");
					return;
				}
				if(mInfo.content.length()>50)
				{
					App.toast("评语内容过长");
					return;
				}
				mInfo.degree = tagType;
				msg.obj = mInfo;
				
				handler.sendMessage(msg);
				dialog.cancel();	
				tagType="1";
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	/**
	 * 
	 * 评价弹出框2
	 * @param <RefusedAdapter>
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public void dialogTwoBtnCommTwoResult(final List<Model> list,String content,String btnLeftName,String btnRightName,final Handler handler,final int code)
	{
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_comm_two, null);
		
		final EditText et_content = (EditText)view.findViewById(R.id.et_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		
		GridView gv_comm=(GridView) view.findViewById(R.id.gv_comm);
		
		fgv_addImage = (GridView) view.findViewById(R.id.fgv_addImage);
		uploadListImg.clear();
		fgv_addImage.setAdapter(upimgAdapter);
		
		final com.bm.base.adapter.RefusedAdapter adapter = new com.bm.base.adapter.RefusedAdapter(context, list);
		gv_comm.setAdapter(adapter);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).isSelected = false;
		}
		list.get(0).isSelected = true;

		gv_comm.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if(pos == list.size() - 1){
					for(int i=0;i<list.size();i++){
						list.get(i).isSelected = true;
					}
				}else{
					if(list.get(pos).isSelected){
						adapter.setSelectItem(-1);
						if(adapter.getSelectItem() == pos){
							list.get(pos).isSelected = true;
						}else{
							list.get(pos).isSelected = false;
						}
					}else{
						adapter.setSelectItem(pos);
						if(adapter.getSelectItem() == pos){
							list.get(pos).isSelected = true;
						}else{
							list.get(pos).isSelected = false;
						}
					}
				}

				adapter.notifyDataSetChanged();
			}
		});
		
		
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		tv_name.setText(content);
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Message msg = new Message();
//				msg.what = 1;
//				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				for (int m = 0; m < list.size(); m++) {
					if (list.get(m).isSelected) {
						listRefu.add(list.get(m));
					}
				}
				
				listRefu.get(0).content = et_content.getText().toString().trim();
				if(listRefu.get(0).content.length()==0)
				{
					App.toast("请填写评语内容");
					return;
				}
				if(listRefu.get(0).content.length()>50)
				{
					App.toast("评语内容过长");
					return;
				}
				Message msg = new Message();
				msg.what = code;
				msg.obj=listRefu;
				handler.sendMessage(msg);
				dialog.cancel();	
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	
	
	public List<String> uploadListImg = new ArrayList<String>(); //要上传的图片
	private final int IMG_COUNT_MAX = 9; //最大上传图片数
	BaseAdapter upimgAdapter = new BaseAdapter() {
		@Override
		public int getCount() {
			int s  = uploadListImg.size();
			if(s < IMG_COUNT_MAX)
				s+=1;
			return s;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_send_pic_three, parent, false);
				int s = (int)(context.getResources().getDisplayMetrics().widthPixels /8.3);
				convertView.getLayoutParams().width = s;
				convertView.getLayoutParams().height = s;
			}

			ImageView iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			View iv_delete = convertView.findViewById(R.id.iv_delete);
			convertView.setOnClickListener(null);

			if(position == uploadListImg.size())
			{
				int pd = Util.dpToPx(2, getResources());
				iv_pic.setPadding(pd,pd,pd,pd);
				iv_delete.setVisibility(View.GONE);
//				iv_pic.setImageResource(R.drawable.pic_add);
				String  imgstr = "drawable://" + R.drawable.pic_add2;
				ImageLoader.getInstance().displayImage(imgstr, iv_pic, App.getInstance().getListViewDisplayImageOptions());
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ThreeButtonDialog buttonDialog = new ThreeButtonDialog(context).setFirstButtonText("拍照")
								.setBtn1Listener(new OnClickListener() {
									@Override
									public void onClick(View arg0) {
										takePhoto();
									}
								}).setThecondButtonText("从手机相册选择")
								.setBtn2Listener(new OnClickListener() {
									@Override
									public void onClick(View arg0) {
//										pickPhoto();
										pickPhotoList(IMG_COUNT_MAX-uploadListImg.size());
									}
								}).autoHide();
						buttonDialog.show();
					}
				});
			}
			else
			{
				iv_pic.setPadding(0, 0, 0, 0);
				iv_delete.setVisibility(View.VISIBLE);
				final String src = uploadListImg.get(position);
				ImageLoader.getInstance().displayImage("file://"+src, iv_pic, App.getInstance().getListViewDisplayImageOptions());
				iv_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						uploadListImg.remove(src);
						upimgAdapter.notifyDataSetChanged();
					}
				});
			}

			return convertView;
		}
	};
	
	

	@Override
	protected void onPhotoTaked(String photoPath) {
		uploadListImg.add(photoPath);
		upimgAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 照片多选以后回调
	 * @param photoPath
	 */
	protected void onPhotoListTaked(List<String> photoPath){
		uploadListImg.addAll(photoPath);
		upimgAdapter.notifyDataSetChanged();
	}
}
