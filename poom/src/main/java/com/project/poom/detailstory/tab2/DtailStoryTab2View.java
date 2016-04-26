package com.project.poom.detailstory.tab2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.manager.NetworkManager;

public class DtailStoryTab2View extends FrameLayout {
	
	ImageView img;
	FrameLayout frame;
	TextView date, clock, content, imgcount;
	DtailStoryTab2Data mData;
	DisplayImageOptions options;

	public DtailStoryTab2View(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.dtail_tab2_listview_layout, this);
		img = (ImageView)findViewById(R.id.donate_news_img);
		frame = (FrameLayout)findViewById(R.id.frame);
		date = (TextView)findViewById(R.id.donate_news_ymd);
		clock = (TextView)findViewById(R.id.donate_news_time);
		content = (TextView)findViewById(R.id.donate_news_content);
		imgcount = (TextView)findViewById(R.id.donate_news_imgcount);
	}
	
	public void setDtailStoryTab2ViewData(DtailStoryTab2Data data){
		mData = data;
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.ic_error)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		if (data.getImage() != null) {
			ImageLoader.getInstance().displayImage("http://54.92.116.151/view/news/image/"+data.getImage(), img, options);
			frame.setVisibility(View.VISIBLE);
		}else {
			frame.setVisibility(View.GONE);
		}
		
		
		String temp = data.getEnroll_date();
		Log.i("temp", temp);
		String year, month, day, time;
		year = temp.substring(0, 4);
		month = temp.substring(5, 7);
		day = temp.substring(8, 10);
		StringBuilder sb = new StringBuilder();
		sb.append(year).append(".").append(month).append(".").append(day);
		time = temp.substring(11, 16);
		
		date.setText(sb.toString());
		clock.setText(time);
		content.setText(data.getContent());
		imgcount.setText(data.getCnt()+"");
	}

}
