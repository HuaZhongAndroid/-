package com.bm.util;


import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.richer.tzjjl.R;

/*定义一个倒计时的类*/
public class TimeCount extends CountDownTimer {

	private TextView textView,textView2;
	private Context context;

	public TimeCount(long millisInFuture, long countDownInterval,
			TextView textView,TextView textView2,Context context) {
		super(millisInFuture, countDownInterval);
		this.textView = textView;
		this.textView2 = textView2;
		this.context=context;
	}

	@Override
	public void onFinish() {
		textView.setClickable(true);
		textView2.setClickable(true);
		textView.setText("获取验证码");
		textView.setBackgroundColor(context.getResources().getColor(R.color.txt_yellow_color));
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// System.out.println("millisUntilFinished:"+millisUntilFinished);
		// 获取当前时间总秒数
		// first = millisUntilFinished / 1000;

		/**
		 * 转成小时分秒
		 */
		// textView.setText(TimeUtils.getDay(millisUntilFinished)+"天"+TimeUtils.getHour(millisUntilFinished)
		// +"小时"+TimeUtils.getMin(millisUntilFinished)+"分钟"+TimeUtils.getMs(millisUntilFinished)+"秒");
		textView.setText(TimeUtils.getMs(millisUntilFinished) + "秒重新发送");
		textView.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
		textView.setClickable(false);
		textView2.setFocusable(false);
		textView2.setFocusableInTouchMode(false);

	}
}
