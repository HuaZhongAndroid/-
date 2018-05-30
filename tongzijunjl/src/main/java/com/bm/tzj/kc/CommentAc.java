package com.bm.tzj.kc;

import java.util.ArrayList;
import java.util.List;

import net.grobas.view.PolygonImageView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.bm.app.App;
import com.bm.base.BaseActivity;
import com.bm.base.adapter.CommentAdapter;
import com.bm.entity.Model;
import com.richer.tzjjl.R;
import com.lib.widget.FuListView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 家长评价
 * @author shiyt
 *
 */
public class CommentAc extends BaseActivity {

	private ListView lv_comment;
	private Context mContext;
	private List<Model> listComment = new ArrayList<Model>();  
	private CommentAdapter commentAdapter;
	private ImageView img_pic;
	private RatingBar room_ratingbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contentView(R.layout.ac_comment);
		mContext = this;
		setTitleName("家长评价");
		init();
	}
	
	public void init(){
		img_pic=(ImageView) findViewById(R.id.img_pic);
		room_ratingbar=(RatingBar) findViewById(R.id.room_ratingbar);
		room_ratingbar.setIsIndicator(true);//禁止点击星星
		
		lv_comment=(ListView) findViewById(R.id.lv_comment);
		commentAdapter=new CommentAdapter(mContext, listComment);
		lv_comment.setAdapter(commentAdapter);
		ImageLoader.getInstance().displayImage("http://v1.qzone.cc/avatar/201403/30/09/33/533774802e7c6272.jpg!200x200.jpg", img_pic, App.getInstance().getHeadOptions());
		getData();
	}
	/**
	 * 获取数据
	 */
	public void getData(){
		for (int i = 0; i < 5; i++) {
			listComment.add(new Model());
		}
		commentAdapter.notifyDataSetChanged();
	}

}
