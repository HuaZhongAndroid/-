package com.bm.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.app.App;
import com.bm.entity.Model;
import com.bm.entity.User;
import com.richer.tzjjl.R;


/**
 * 
 * 
 * daialog  工具类
 * @author wangqiang
 *
 */
public class UtilDialog {
	public static String tagType="1";   //1通过   0不通过
	public static List<Model> listRefu = new ArrayList<Model>();
	/**
	 * 
	 * 请假提交反馈信息
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogPromtMessage(Context context,final Handler handler) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(true);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_btncontent, null);
		
		TextView tv_cancle = (TextView)view.findViewById(R.id.tv_cancle);
		TextView tv_submit = (TextView)view.findViewById(R.id.tv_submit);
		final EditText et_content=(EditText) view.findViewById(R.id.et_content);
		tv_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message message=new Message();
				message.what=1;
				handler.dispatchMessage(message);
				dialog.cancel();
			}
		});
		tv_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	/**
	 * 
	 * 输入支付密码
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogPay(Context context,final Handler handler) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(true);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_pay, null);
		
		TextView tv_cancle = (TextView)view.findViewById(R.id.tv_cancle);
		TextView tv_submit = (TextView)view.findViewById(R.id.tv_submit);
		final EditText et_content=(EditText) view.findViewById(R.id.et_content);
		tv_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message message=new Message();
				message.what=1;
				message.obj=et_content;
				handler.dispatchMessage(message);
				dialog.cancel();
			}
		});
		tv_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	/**
	 * 
	 * 两个按钮，一个提示文字
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogTwoBtnResultCode(Context context,String content,String btnLeftName,String btnRightName,final Handler handler,final int code) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_twobtncontent, null);
		
		TextView dialog_content = (TextView)view.findViewById(R.id.tv_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		
		dialog_content.setText(content);
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 3;
				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Message msg = new Message();
				msg.what = code;
				handler.sendMessage(msg);
				dialog.cancel();				
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	/**
	 * 
	 * 新建群
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogTwoBtnGropu(Context context,final Handler handler) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_two_btncontent, null);
		
		TextView dialog_content = (TextView)view.findViewById(R.id.tv_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		TextView tv_create = (TextView) view.findViewById(R.id.tv_create);//创建
		final EditText et_groupname=(EditText) view.findViewById(R.id.et_groupname);
		
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		//确定事件
		tv_create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String groupName=et_groupname.getText().toString();
				Message msg = new Message();
				msg.obj=groupName;
				msg.what=1002;
				handler.sendMessage(msg);
				
				
//				if(groupName.length()==0){
//					GroupListAc.intance.dialogToast("请输入群名称");
//					return;
//				}
//				GroupListAc.intance.createGroup(et_groupname.getText().toString());
				dialog.cancel();				
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	/**
	 * 
	 * 两个按钮，一个提示文字
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogTwoBtnRefused(final Context context,String btnLeftName,String btnRightName,final Handler handler,final int code) {
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_refused_content, null);
		
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		
		final EditText et_content=(EditText) view.findViewById(R.id.et_desc);
		
		
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 3;
				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			  
				Message msg = new Message();
				msg.what = code;
				msg.obj=et_content.getText();
				handler.sendMessage(msg);
				dialog.cancel();				
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	/**
	 * 
	 * 两个按钮，一个提示文字   注销提示
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogTwoBtnContentTtile(Context context,String content,String btnLeftName,String btnRightName,String strTitle,final Handler handler,final int strCode) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_twobtncontent_title, null);
		
		TextView dialog_content = (TextView)view.findViewById(R.id.tv_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);//确定
		
		dialog_content.setText(content);
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		tv_title.setText(strTitle);
		
		
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 4;
				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Message msg = new Message();
				msg.what = strCode;
				handler.sendMessage(msg);
				dialog.cancel();				
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	/**
	 * 
	 * 两个按钮，一个提示文字
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogTwoBtnResults(Context context,String content,String btnLeftName,String btnRightName,final Handler handler,final int code,final int codes) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_two_msg, null);
		
		TextView dialog_content = (TextView)view.findViewById(R.id.tv_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		
		dialog_content.setText(content);
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = code;
				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Message msg = new Message();
				msg.what = codes;
				handler.sendMessage(msg);
				dialog.cancel();				
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
	
	/**
	 * 
	 * 评价弹出框1
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogTwoBtnCommResult(Context context,String content,String btnLeftName,String btnRightName,final Handler handler,final int code) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_comm_one, null);
		
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
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static <RefusedAdapter> void dialogTwoBtnCommTwoResult(Context context,final List<Model> list,String content,String btnLeftName,String btnRightName,final Handler handler,final int code) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_comm_two, null);
		
		final EditText et_content = (EditText)view.findViewById(R.id.et_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		
		GridView gv_comm=(GridView) view.findViewById(R.id.gv_comm);
		
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
	
	/**
	 * 
	 * 版本更新dialog
	 * @param context
	 * @param content
	 * @param btnName
	 * @param handler
	 */
	public static void dialogVersion(boolean isHideCancel,Context context,String content,String btnLeftName,String btnRightName,final Handler handler) {
		
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.dialog_twobtncontent_title, null);
		
		TextView dialog_content = (TextView)view.findViewById(R.id.tv_content);
		TextView tv_cancel = (TextView) view.findViewById(R.id.btn_Cancel);
		TextView tv_Determine = (TextView) view.findViewById(R.id.btn_Determine);//确定
		View v_line = (View)view.findViewById(R.id.v_line);//确定
		dialog_content.setText(content);
		tv_cancel.setText(btnLeftName);
		tv_Determine.setText(btnRightName);
		
		if(isHideCancel){
			v_line.setVisibility(View.GONE);
			tv_cancel.setVisibility(View.GONE);
		}else{
			v_line.setVisibility(View.VISIBLE);
			tv_cancel.setVisibility(View.VISIBLE);
		}
		
		
		//取消事件
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 100;
				handler.sendMessage(msg);
				dialog.cancel();
			}
		});
		//确定事件
		tv_Determine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Message msg = new Message();
				msg.what = 101;
				handler.sendMessage(msg);
				dialog.cancel();				
			}
		});
		
		dialog.setContentView(view);
		dialog.setTitle(null);
		dialog.show();
	}
}
