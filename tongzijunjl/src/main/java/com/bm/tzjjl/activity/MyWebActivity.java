package com.bm.tzjjl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.bm.base.BaseActivity;
import com.just.agentweb.AgentWeb;
import com.richer.tzjjl.R;

/**
 * Create by zwonb on 2018/5/30
 */
public class MyWebActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.baseweblayout);
        setTitleName("asdfasdf");

        LinearLayout mLinearLayout = findLinearLayoutById(R.id.web_ll);
        String url = getIntent().getStringExtra("url");
        AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条

//                .defaultProgressBarColor() // 使用默认进度条颜色
//                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//

                .ready()
                .go(url);

    }
}
