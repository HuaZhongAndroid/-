package com.bm.tzj.mine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.base.BaseActivity;
import com.bm.dialog.UtilDialog;
import com.richer.tzjjl.R;

/**
 * 发表评价
 * @author shiyt
 *
 */
public class AddCommentAc extends BaseActivity implements OnClickListener {
	private Context context;
	TextView tv_submit;
	private LinearLayout ll_jl,ll_cs;
	private EditText et_commentCamp,et_commentCoach;
	int campCount=-1,coachCount=-1;
	String strCommentCamp="",strCommentCoach="";
	
	private com.bm.view.RatingBar room_ratingbar,room_ratingbars;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_addcomment);
		context = this;
		setTitleName("发表评论");
		initView();
	}
	
	
	public void initView(){
//		room_ratingbar=(RatingBar) findViewById(R.id.room_ratingbar);
//		room_ratingbars=(RatingBar) findViewById(R.id.room_ratingbars);
		
		et_commentCamp = findEditTextById(R.id.et_commentCamp);//城市营地
		et_commentCoach = findEditTextById(R.id.et_commentCoach);//教练
		ll_jl = findLinearLayoutById(R.id.ll_jl);
		ll_cs = findLinearLayoutById(R.id.ll_cs);
		
		room_ratingbar=(com.bm.view.RatingBar) findViewById(R.id.room_ratingbar);
		room_ratingbars=(com.bm.view.RatingBar) findViewById(R.id.room_ratingbars);
		
		tv_submit=findTextViewById(R.id.tv_submit);
		tv_submit.setOnClickListener(this);
		ll_jl.setVisibility(View.GONE);
		ll_cs.setVisibility(View.GONE);
		
		
		room_ratingbar.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				
				ll_jl.setVisibility(View.VISIBLE);
				coachCount=(int) room_ratingbar.getRating();
				return false;
			}
		});
		
		room_ratingbars.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				
				ll_cs.setVisibility(View.VISIBLE);
				campCount=(int) room_ratingbars.getRating();
				return false;
			}
		});
	}
	
	/**
	 * 弹出框
	 */
	public void dialogContent(){
		float start=room_ratingbar.getRating();
		float starts=room_ratingbars.getRating();
		if(start<4 && starts<4){
			//弹出框
			UtilDialog.dialogTwoBtnRefused(context,"取消", "确认", handler, 2);
		}else{
			dialogToast("提交");
		}
	}
	
	/**
	 * 提交评价
	 */
	public void submitContent(){
		strCommentCamp=et_commentCamp.getText().toString().trim();
		strCommentCoach=et_commentCoach.getText().toString().trim();
		if(coachCount<=4){
			if(strCommentCoach.length()==0){
			dialogToast("五颗星以下的教练评价内容不能为空");
			return;
			}
		}
		
		if(campCount<=4){
			if(strCommentCamp.length()==0){
			dialogToast("五颗星以下的评价城市营地内容不能为空");
			return;
			}
		}
		
		finish();
	}
	
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
				//submitContent(msg.obj.toString());
				break;
			}
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
			submitContent();
			break;
		default:
			break;
		}
	}
}
