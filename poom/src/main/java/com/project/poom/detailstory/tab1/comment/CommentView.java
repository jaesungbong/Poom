package com.project.poom.detailstory.tab1.comment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.maintab1.DDay;
import com.project.poom.manager.UserManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentView extends FrameLayout {

	String user_nick;
	DisplayImageOptions options;
	ImageView iconView;
	TextView nameView;
	TextView dateView;
	TextView commentView;
	ImageView delRep;
	CommentData mData;
	
	public CommentView(Context context, String nick) {
		super(context);
		user_nick = nick;
		init();
	}
	
	public interface OnItemClickListener{
		public void onDelClick(View v, CommentData data);
		public void onProfileClick(View v, CommentData data);
	}
	
	OnItemClickListener mListener;
	
	public void setOnItemClickListener(OnItemClickListener listener){
		mListener = listener;
	}
	
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.dtail_tab1_layout_comment, this);
		iconView = (ImageView)findViewById(R.id.comment_icon);
		iconView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener != null){
					mListener.onProfileClick(CommentView.this, mData);
				}
			}
		});
		nameView = (TextView)findViewById(R.id.comment_name);
		dateView = (TextView)findViewById(R.id.comment_date);
		commentView = (TextView)findViewById(R.id.comment);
		delRep = (ImageView)findViewById(R.id.del_rep);
		delRep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onDelClick(CommentView.this, mData);
				}
			}
		});		
	}
	
	public void setMyData(CommentData data)
	{
		mData = data;
		nameView.setText(data.getName()+"");
		commentView.setText(data.getComment()+"");
		String date = data.getEnroll_date();
		String eyear, emonth, eday, etime;
		eyear = date.substring(0, 4);
		emonth = date.substring(5, 7);
		eday = date.substring(8, 10);
		etime = date.substring(11, 16);
		StringBuilder sb = new StringBuilder();
		sb.append(eyear).append("년").append(" "+emonth).append("월")
				.append(" "+eday).append("일 "+etime);
		dateView.setText(sb.toString());
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.login_graphic)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"+
	      data.getImage(), iconView, options);
	}	
}
