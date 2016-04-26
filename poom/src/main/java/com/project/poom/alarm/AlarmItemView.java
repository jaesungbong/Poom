package com.project.poom.alarm;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.maintab1.DDay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmItemView extends FrameLayout {
	
	ImageView alarmImg;
	TextView alarmsipText, alarmDate;
	AlarmData mData;
	DisplayImageOptions options;

	public AlarmItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AlarmItemView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item,	this);
		alarmImg=(ImageView)findViewById(R.id.alarmlistimage);
		alarmsipText=(TextView)findViewById(R.id.alarm_list_item_title);
		alarmDate=(TextView)findViewById(R.id.alramlistdate);
	}

	public void setAlarmItemData(AlarmData data){
		mData=data;
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.login_graphic)	
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		ImageLoader.getInstance().displayImage("http://54.92.116.151/view/news/image/"+data.getImage(), alarmImg, options);
		
		String title = data.getTitle();
		StringBuilder ssb = new StringBuilder();
		switch (data.getType()) {
		case 1:
			ssb.append(title).append("에 새로운 글이 등록되었어요.");
			break;

		case 2:
			ssb.append(title).append("의 펀딩이 완료되었습니다!");
			break;
			
		case 3:
			ssb.append(title).append("의 서명이 완료되었습니다!");
			break;
			
		case 4:
			ssb.append("작성하신 ").append(title).append("의 서명이 완료되었습니다!");
			break;
			
		case 5:
			ssb.append("작성하신 ").append(title).append("의 기부가 시작했습니다!");
			break;
			
		case 6:
			ssb.append("작성하신 ").append(title).append("의 기부가 완료되었습니다!");
			break;
		}
		alarmsipText.setText(ssb.toString());
		
		String dday = data.getDate();
		String year, month, day, hour, minute;
		year = dday.substring(0,4);
		month = dday.substring(4,6);
		day = dday.substring(6,8);
		hour = dday.substring(8, 10);
		minute = dday.substring(10);
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("년 ").append(month).append("월 ")
				.append(day).append("일 ").append(hour+":"+minute);
		alarmDate.setText(sb.toString());
	}
}
