package com.bm.tzj.mine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.app.App;
import com.bm.base.BaseCaptureActivity;
import com.bm.base.adapter.PicAdapter;
import com.bm.dialog.AddSelectDialog;
import com.bm.dialog.NumberPickerDialog;
import com.bm.dialog.ThreeButtonDialog;
import com.bm.entity.CoachInfo;
import com.bm.entity.Province;
import com.bm.entity.post.UserPost;
import com.bm.tzjjl.activity.ImageViewActivity;
import com.bm.tzjjl.activity.MainAc;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;
import com.lib.widget.FuGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人信息
 * 
 * @author wanghy
 * 
 */
public class PersonalInformation extends BaseCaptureActivity implements OnClickListener {
	private Context context;
	private PolygonImageView iv_sixview_head;

	private LinearLayout ll_sex, ll_city, ll_babySex, ll_birthday;
	private TextView tv_sex, tv_city, tv_babySex, tv_birthday, tv_save;
	private EditText et_userName, et_telPhone, et_graduateSchool, et_education,
			et_address, et_idCard;
	NumberPickerDialog dailogA, dailogB;
	private String[] sexArr = new String[] { "男", "女" };
	private String sex = "男", imagePath = "";
	String strProvinceName="",strCityName="",strAreaName="";
	
	private PicAdapter picAdapter;
	private FuGridView fgv_zlImage;//图片
	private ThreeButtonDialog buttonDialog;
	String [] paths;
//	public List<ImageTag> listImg = new ArrayList<ImageTag>();
	public List<String> uploadListImg = new ArrayList<String>();
//	private boolean isShowDelete = false;
//	private SendNewPostPicTwoAdapter adapterPic = null;
	
	private String[] picArr = new String[]{
			"http://www.lzbs.com.cn/images/2009-02/26/r4a80D.JPG",
			"http://img0.imgtn.bdimg.com/it/u=3546235277,4259588903&fm=21&gp=0.jpg"};

	AddSelectDialog diaAddr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_personal_information);
		context = this;
		setTitleName("个人信息");
		initView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		setRightName("编辑");
//		setFoucs(false);//默认不能编辑
	}
	@Override
	public void clickRight() {
		super.clickRight();
		if("编辑".equals(tv_right.getText().toString())){
			setRightName("取消");
			setFoucs(true);
		}else if("取消".equals(tv_right.getText().toString())){
			setRightName("编辑");
			setFoucs(false);
		}
	}

	private void initView() {
		
		fgv_zlImage = (FuGridView) findViewById(R.id.fgv_zlImage);
		ll_sex = findLinearLayoutById(R.id.ll_sex);
		ll_sex.setOnClickListener(this);
		ll_city = findLinearLayoutById(R.id.ll_city);
		ll_city.setOnClickListener(this);
		ll_birthday = findLinearLayoutById(R.id.ll_birthday);
		ll_birthday.setOnClickListener(this);
		tv_save = findTextViewById(R.id.tv_save);
		tv_save.setOnClickListener(this);

		et_education = findEditTextById(R.id.et_education);
		et_address = findEditTextById(R.id.et_address);
		et_idCard = findEditTextById(R.id.et_idCard);

		et_userName = findEditTextById(R.id.et_userName);
		et_telPhone = findEditTextById(R.id.et_telPhone);
		et_address = findEditTextById(R.id.et_address);
		et_graduateSchool = findEditTextById(R.id.et_graduateSchool);

		tv_birthday = findTextViewById(R.id.tv_birthday);
		tv_city = findTextViewById(R.id.tv_city);
		tv_sex = findTextViewById(R.id.tv_sex);
		
		iv_sixview_head = (PolygonImageView)findViewById(R.id.iv_sixview_head);
		iv_sixview_head.setOnClickListener(this);
		buttonDialog = new ThreeButtonDialog(context).setFirstButtonText("拍照")
				.setBtn1Listener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						takePhoto();
					}
				}).setThecondButtonText("从手机相册选择")
				.setBtn2Listener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						pickPhoto();
					}
				}).autoHide();

		c = Calendar.getInstance();
		_year = c.get(Calendar.YEAR);
		_month = c.get(Calendar.MONTH);
		_day = c.get(Calendar.DAY_OF_MONTH);

		setDate();
		setRightName("编辑");
		setFoucs(false);//默认不能编辑
		
	}
	/**
	 * 获取省市区信息
	 */
	public void getAllCityInfo(){
		UserManager.getInstance().getTzjtrendAllSearchregion(context,new HashMap<String, String>(), new ServiceCallback<CommonListResult<Province>>() {
			
			@Override
			public void error(String msg) {
				
			}
			
			@Override
			public void done(int what, CommonListResult<Province> obj) {
				if(obj.data.size()>0){
					
					diaAddr = new AddSelectDialog(context, new AddSelectDialog.CancleClick() {
						
						@Override
						public void clickConform(String arg,String provinceName, String cityName, String areaName) {
							tv_city.setText(arg);
							strProvinceName = provinceName;
							strCityName = cityName;
							strAreaName = areaName;
							diaAddr.dismiss();
						}
						
						@Override
						public void click(View arg) {
							diaAddr.dismiss();
						}
					},obj.data);
				}
			}
		});
		
	}
	
	
	/**
	 * 设置焦点
	 */
	private void setFoucs(boolean flag){
		iv_sixview_head.setClickable(flag);
		
		et_userName.setEnabled(flag);
		if(flag){et_userName.setHint("请输入教练姓名");}else{et_userName.setHint("");}
		
		et_telPhone.setEnabled(flag);
		if(flag){et_telPhone.setHint("请输入手机");}else{et_telPhone.setHint("");}
		
		if(flag){tv_sex.setHint("请选择");}else{tv_sex.setHint("");}
		if(flag){tv_city.setHint("请选择");}else{tv_city.setHint("");}
		if(flag){tv_birthday.setHint("请选择");}else{tv_birthday.setHint("");}
		
		et_idCard.setEnabled(flag);
		if(flag){et_idCard.setHint("请输入身份证号码");}else{et_idCard.setHint("");}
		
		et_telPhone.setEnabled(flag);
		if(flag){et_telPhone.setHint("请输入联系电话");}else{et_telPhone.setHint("");}
		
		ll_sex.setClickable(flag);
		if(flag){ll_sex.requestFocus();}
		
		ll_city.setClickable(flag);
		if(flag){ll_sex.requestFocus();}
		
		et_address.setEnabled(flag);
		if(flag){et_address.setHint("请输入联系地址");}else{et_address.setHint("");}
		
		et_education.setEnabled(flag);
		if(flag){et_education.setHint("请输入学历");}else{et_education.setHint("");}
		
		
		et_graduateSchool.setEnabled(flag);
		if(flag){et_graduateSchool.setHint("请输毕业学校");}else{et_graduateSchool.setHint("");}
		
		
		
		
		ll_birthday.setClickable(flag);
		if(flag){ll_birthday.requestFocus();}
		
		if(flag){
			tv_save.setVisibility(View.VISIBLE);
		}else{
			tv_save.setVisibility(View.GONE);
		}
		
		CoachInfo cInfo = App.getInstance().getCoach();
		String strSex="";
		if(null !=cInfo){
			if(cInfo.gender.equals("1")){
				strSex="男";
			}else if(cInfo.gender.equals("2")){
				strSex="女";
			}else {
				strSex="未知";
			}
			strProvinceName = cInfo.provinceName;
			strCityName = cInfo.regionName;
			strAreaName = cInfo.areaName;
			
			
			tv_sex.setText(strSex);
			tv_birthday.setText(Util.toString(getNullData(cInfo.birthday),"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"));
			et_userName.setText(getNullData(cInfo.coachName));
			et_telPhone.setText(getNullData(cInfo.phone));//联系电话
			tv_city.setText(getNullData(cInfo.provinceName)+getNullData(cInfo.regionName)+getNullData(cInfo.areaName));//省市区
			et_address.setText(getNullData(cInfo.address));//联系地址
			et_education.setText(getNullData(cInfo.education));//学历
			et_graduateSchool.setText(getNullData(cInfo.graduateInstitutions));//毕业学校
			et_idCard.setText(getNullData(cInfo.idCard));//身份证
			ImageLoader.getInstance().displayImage(cInfo.avatar, iv_sixview_head, App.getInstance().getheadImage());
			
			paths=new String[cInfo.coachDiploma.size()];
			for (int i = 0; i < cInfo.coachDiploma.size(); i++) {
				paths[i]=cInfo.coachDiploma.get(i).diplomaTitleMultiUrl;
			}
			
			picAdapter = new PicAdapter(context,paths);
			fgv_zlImage.setAdapter(picAdapter);
			
			fgv_zlImage.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					Intent intent = new Intent(context,ImageViewActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("images", paths);
					bundle.putInt("position", position);
					intent.putExtras(bundle);
					context.startActivity(intent);	
				}
			});
		}
		
	}

	/**
	 * 设置信息
	 * 
	 * @param map
	 */
	public void setDate() {
		
		getAllCityInfo();
		
		
		dailogA = new NumberPickerDialog(context, sexArr, "请选择性别",
				new NumberPickerDialog.SelectValue() {

					@Override
					public void getValue(int arg) {
						sex = sexArr[arg];
					}
				}, new NumberPickerDialog.CancleClick() {

					@Override
					public void click(View arg) {
						dailogA.dismiss();
					}

					@Override
					public void clickConform(View arg) {
						tv_sex.setText(sex);
						dailogA.dismiss();

					}
				});
		
	};

	/**
	 * 时间控件
	 * 
	 * @return
	 */
	// 时间控件

	private DatePickerDialog datePicker;

	private Calendar c;
	private int _year, _month, _day;
	private String times;

	private Dialog onCreateDialog() {
		return new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				_year = year;
				_month = monthOfYear;
				_day = dayOfMonth;
				times = _year + "-" + (_month + 1) + "-" + _day;

				// if(year>)
				int day = Util.comparisonTime(times,
						Util.getCurrentDateString());
				if (day < 0) {
					dialogToast("出生日期不能是未来时间");
					tv_birthday.setText(Util.getCurrentDateString());
					return;
				}
				tv_birthday.setText(Util.setDate(times));
			}
		}, _year, _month, _day);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_sex:
			dailogA.show();
			break;
		case R.id.ll_birthday:
			Dialog dialog = onCreateDialog();
			dialog.show();
			break;
		case R.id.ll_city:
			diaAddr.show();
			break;
		case R.id.iv_sixview_head:// 拍照
			buttonDialog.show();
			break;
		case R.id.tv_save:
			submitInfo();
//			openActivity(MyCourseAc.class);
//			 submitInfo();
			break;
		default:
			break;
		}
	}

	private void submitInfo() {
		String strUserName = et_userName.getText().toString().trim();
		String strPhone = et_telPhone.getText().toString().trim();
		String strCity = tv_city.getText().toString().trim();
		String strIdCard = et_idCard.getText().toString().trim();
		String birthday = tv_birthday.getText().toString().trim();
		String sex = tv_sex.getText().toString().trim();
		String strAddress = et_address.getText().toString().trim();
		String strEducation = et_education.getText().toString().trim();
		String strGraduateSchool = et_graduateSchool.getText().toString().trim();
		
		
		
		if (App.getInstance().getCoach() == null) {
			dialogToast("用户信息为空，无法保存该信息");
			return;
		}

		if (strUserName.length() == 0) {
			dialogToast("教练姓名不能为空");
			return;
		}
		
		if (strUserName.length() > 4) {
			dialogToast("教练姓名长度不能大于4");
			return;
		}

		if (strPhone.length() == 0) {
			dialogToast("手机号码不能为空");
			return;
		}

		if (!Util.isMobileNO(strPhone)) {
			dialogToast("手机号码格式不正确");
			return;
		}
		
		if (strIdCard.length() == 0 ) {
			dialogToast("身份证号码不能为空");
			return;
		}

		if (birthday.length() == 0) {
			dialogToast("出生日期不能为空");
			return;
		}
		if (sex.length() == 0) {
			dialogToast("性别不能为空");
			return;
		}
		if (strCity.length() == 0) {
			dialogToast("所在城市不能为空");
			return;
		}
		if (strEducation.length() == 0) {
			dialogToast("学历不能为空");
			return;
		}
		if (strGraduateSchool.length() == 0) {
			dialogToast("毕业学校不能为空");
			return;
		}

		UserPost post = new UserPost();
		post.coachId = App.getInstance().getCoach().coachId;
		post.birthday = birthday;
		post.coachName = strUserName;
		post.idCard = strIdCard;
		post.phone = strPhone;
		post.regionName = strCityName;
		post.provinceName = strProvinceName;
		post.areaName = strAreaName;
		post.address = strAddress;
		post.education = strEducation;
		post.graduateInstitutions = strGraduateSchool;
		
		if (sex.equals("男")) {
			post.gender = "1";
		} else {
			post.gender = "2";
		}
		
		showProgressDialog();
		UserManager.getInstance().getTzjcoachUpdateCoach(context, imagePath, post, new ServiceCallback<CommonResult<CoachInfo>>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);				
			}
			
			@Override
			public void done(int what, CommonResult<CoachInfo> obj) {
				hideProgressDialog();
				finish();
				MainAc.intance.getCoachInfo();
				dialogToastHandler("修改成功", 1000, PersonalInformation.this);
			}
		});
	}

	@Override
	protected void onPhotoTaked(String photoPath) {
		imagePath = photoPath;
		ImageLoader.getInstance().displayImage("file://"+photoPath, iv_sixview_head, App.getInstance().getheadImage());
		uploadListImg.clear();
		uploadListImg.add(photoPath);
	}

	@Override
	protected void onPhotoListTaked(List<String> photoPath) {
		// TODO Auto-generated method stub
		
	}
}
