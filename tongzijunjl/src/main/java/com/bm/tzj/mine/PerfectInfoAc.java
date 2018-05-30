package com.bm.tzj.mine;

import java.util.Calendar;
import java.util.HashMap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.api.UserManager;
import com.bm.base.BaseActivity;
import com.bm.dialog.AddSelectDialog;
import com.bm.dialog.NumberPickerDialog;
import com.bm.entity.CoachInfo;
import com.bm.entity.Province;
import com.bm.entity.post.UserPost;
import com.richer.tzjjl.R;
import com.bm.util.Util;
import com.lib.http.ServiceCallback;
import com.lib.http.result.CommonListResult;
import com.lib.http.result.CommonResult;

/**
 * 完善信息
 * @author shiyt
 *
 */
public class PerfectInfoAc extends BaseActivity implements OnClickListener {
	private Context context;
	private TextView tv_sex,tv_city,tv_submit,tv_birthday;
	private EditText et_jz_name,et_phone,et_jz_lxdz,et_xl,et_byyx,et_jwjs,et_idCard;
	NumberPickerDialog dailogA;
	private String[] sexArr = new String[] { "男", "女" };
	private String sex = "男";
	AddSelectDialog diaAddr;
	private LinearLayout ll_birthday;
	String strProvinceName="",strCityName="",strAreaName="";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_perfectinfo);
		context = this;
		setTitleName("完善信息");
		initView();
	}
	
	public void initView(){
		tv_city=findTextViewById(R.id.tv_city);
		tv_sex=findTextViewById(R.id.tv_sex);
		tv_submit=findTextViewById(R.id.tv_submit);
		
		et_jz_name=findEditTextById(R.id.et_jz_name);
		et_phone=findEditTextById(R.id.et_phone);
		et_jz_lxdz=findEditTextById(R.id.et_jz_lxdz);
		
		et_xl=findEditTextById(R.id.et_xl);
		et_byyx=findEditTextById(R.id.et_byyx);
		et_jwjs=findEditTextById(R.id.et_jwjs);
		tv_birthday = findTextViewById(R.id.tv_birthday);
		et_idCard = findEditTextById(R.id.et_idCard);
		ll_birthday = findLinearLayoutById(R.id.ll_birthday);
		ll_birthday.setOnClickListener(this);
		
		tv_submit.setOnClickListener(this);
		
		tv_sex.setOnClickListener(this);
		tv_city.setOnClickListener(this);
		
		
		c = Calendar.getInstance();
		_year = c.get(Calendar.YEAR);
		_month = c.get(Calendar.MONTH);
		_day = c.get(Calendar.DAY_OF_MONTH);
		
		setDate();
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
	
	/**
	 * 设置信息
	 * 
	 * @param map
	 */
	public void setDate() {
		getAllCityInfo();
		et_phone.setText(getIntent().getStringExtra("phone").toString().trim());
		
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
	
	private void submitInfo() {
		String userName = et_jz_name.getText().toString().trim();
		String phone = et_phone.getText().toString().trim();
		String city = tv_city.getText().toString().trim();
		String jljs = et_jwjs.getText().toString().trim();
		String regionName = strCityName;
		String address = et_jz_lxdz.getText().toString().trim();
		String bbyx = et_byyx.getText().toString().trim();
		String xl = et_xl.getText().toString().trim();
		String strIdCard = et_idCard.getText().toString().trim();
		String birthday = tv_birthday.getText().toString().trim();

		if (userName.length() ==0) {
			dialogToast("教练姓名不能为空");
			return;
		}
		
		if (userName.length() > 4) {
			dialogToast("教练姓名长度不能大于4");
			return;
		}

		if (phone.length() ==0) {
			dialogToast("手机号码不能为空");
			return;
		}

		if (!Util.isMobileNO(phone)) {
			dialogToast("手机号码格式不正确");
			return;
		}
		
		if (birthday.length() == 0) {
			dialogToast("出生日期不能为空");
			return;
		}
		
		if (strIdCard.length() == 0 ) {
			dialogToast("身份证号码不能为空");
			return;
		}

		if (city.length() ==0 || city.equals("tv_city")) {
			dialogToast("所在城市不能为空");
			return;
		}
		
		if (xl.length() == 0) {
			dialogToast("学历不能为空");
			return;
		}
		if (bbyx.length() == 0) {
			dialogToast("毕业学校不能为空");
			return;
		}
		if (jljs.length() == 0) {
			dialogToast("教练介绍不能为空");
			return;
		}
		
		
//		if (sex.length() ==0) {
//			dialogToast("性别不能为空");
//			return;
//		}
		
		UserPost post = new UserPost();
		if (sex.equals("男")) {
			post.gender = "1";
		} else {
			post.gender = "2";
		}
		
		post.coachName = userName;
		post.regionName = strCityName;
		post.areaName = strAreaName;
		post.provinceName = strProvinceName;
		post.idCard = strIdCard;
		post.birthday = birthday;
		post.phone = phone;
		post.address = address;
		post.graduateInstitutions=bbyx;
		post.education=xl;
		post.password = getIntent().getStringExtra("passWord");
		post.coachIntro = jljs;
		post.sendPhone = getIntent().getStringExtra("phone");
		
		showProgressDialog();
		UserManager.getInstance().getTzjcoachRegisterCoach(context,"", post,new ServiceCallback<CommonResult<CoachInfo>>() {
			
			@Override
			public void error(String msg) {
				hideProgressDialog();
				dialogToast(msg);
			}
			
			@Override
			public void done(int what, CommonResult<CoachInfo> obj) {
				hideProgressDialog();
				finish();
				RegistreAc.intance.finish();
//				MainAc.intance.getUserInfo();
				dialogToastHandler("您的申请已提交，请耐心等待", 30000, PerfectInfoAc.this);
			}
		});
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
			submitInfo();
			break;
//		case R.id.tv_jl:
//			Intent intent = new Intent(context, MyTeachersAc.class);
//			startActivity(intent);
//			break;
		case R.id.tv_sex:
			dailogA.show();
			break;
		case R.id.tv_city:
			diaAddr.show();
			break;
		case R.id.ll_birthday:
			Dialog dialog = onCreateDialog();
			dialog.show();
			break;
		default:
			break;
		}
		
	}
}
