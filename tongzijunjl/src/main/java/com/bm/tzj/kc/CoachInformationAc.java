package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.CertificateAdapter;
import com.bm.base.adapter.CommentAdapter;
import com.bm.base.adapter.CourseAdapter;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.lib.widget.FuGridView;
import com.lib.widget.FuListView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 教练信息
 * @author shiyt
 *
 */
public class CoachInformationAc extends BaseActivity implements OnClickListener {
	private Context mContext;
	private FuListView lv_course,lv_comment;
	private FuGridView gv_certificate;
	
	private List<Model> listCourse = new ArrayList<Model>();  //带过的课
	private List<Model> listComment = new ArrayList<Model>();  //评论
	private List<Model> listCertificate = new ArrayList<Model>();  //证书
	private CourseAdapter courseAdapter;
	private CertificateAdapter certiAdapter;
	private CommentAdapter commentAdapter;
	private ImageView img_pic;
//	private RatingBar room_ratingbar;
	private com.bm.view.RatingBar room_ratingbar;
	private TextView tv_jcbd;
	private LinearLayout ll_comment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_coachinformation);
		mContext = this;
		setTitleName("顾问教练");
		init();
	}
	
	public void init(){
		ll_comment=findLinearLayoutById(R.id.ll_comment);
		tv_jcbd=findTextViewById(R.id.tv_jcbd);
		img_pic=(ImageView) findViewById(R.id.img_pic);
//		room_ratingbar=(RatingBar) findViewById(R.id.room_ratingbar);
//		room_ratingbar.setIsIndicator(true);//禁止点击星星

		room_ratingbar=(com.bm.view.RatingBar) findViewById(R.id.room_ratingbar);
		
		lv_course=(FuListView) findViewById(R.id.lv_course);
		lv_comment=(FuListView) findViewById(R.id.lv_comment);
		gv_certificate=(FuGridView) findViewById(R.id.gv_certificate);
		
		courseAdapter=new CourseAdapter(mContext, listCourse);
		lv_course.setAdapter(courseAdapter);
		
		certiAdapter=new CertificateAdapter(mContext, listCertificate);
		gv_certificate.setAdapter(certiAdapter);
		
		commentAdapter=new CommentAdapter(mContext, listComment);
		lv_comment.setAdapter(commentAdapter);
		
		tv_jcbd.setOnClickListener(this);
		ll_comment.setOnClickListener(this);
		ImageLoader.getInstance().displayImage("http://v1.qzone.cc/avatar/201403/30/09/33/533774802e7c6272.jpg!200x200.jpg", img_pic, App.getInstance().getHeadOptions());
		getData();
	}
	
	/**
	 * 获取数据
	 */
	public void getData(){
		for (int i = 0; i < 5; i++) {
			Model model = new Model();
			if(i==0 || i==3){
				model.degree="1";  //1 户外俱乐部  2暑期大露营   3城市营地
			}else if(i==1 || i==4){
				model.degree="2";
			}else{
				model.degree="3";
			}
			
			listCourse.add(model);
		}
		courseAdapter.notifyDataSetChanged();
		
		for (int i = 0; i < 5; i++) {
			listCertificate.add(new Model());
		}
		certiAdapter.notifyDataSetChanged();
		
		for (int i = 0; i < 5; i++) {
			listComment.add(new Model());
		}
		
		room_ratingbar.setRating(5);
		commentAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.tv_jcbd:
//			intent = new Intent(mContext, NoTeacherAc.class);
//			startActivity(intent);
			break;
		case R.id.ll_comment:
			intent = new Intent(mContext, CommentAc.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}
