package com.project.poom.maintab1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;

public class MainTab1View extends FrameLayout {
	
	ImageView mImageView;
	TextView d_day;
	TextView subject;
	TextView fund_number;
	TextView progress_message;
	TextView cheerup_number;
	TextView goal_number, current_percent;
	LinearLayout textpercentage;
	SeekBar current_fund;
	MainTab1Data mData;
	DisplayImageOptions options;
	int max, current, percentage;

	public MainTab1View(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MainTab1View(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.main_tab1_list_item, this);
		mImageView = (ImageView)findViewById(R.id.main_tab1_list_item_img);
		d_day = (TextView)findViewById(R.id.main_tab1_list_item_day);
		subject = (TextView)findViewById(R.id.main_tab1_list_item_mainsubject);
		fund_number=(TextView)findViewById(R.id.main_tab1_list_item_fund_number);
		progress_message = (TextView)findViewById(R.id.main_tab1_list_item_progressmessage);
		cheerup_number = (TextView)findViewById(R.id.main_tab1_list_item_cheerup_number);
		goal_number = (TextView)findViewById(R.id.main_tab1_list_item_goal);
		current_fund = (SeekBar)findViewById(R.id.main_tab1_list_item_progressBar);
//		current_fund.setEnabled(false);
		current_percent = (TextView)findViewById(R.id.current_percent);
		textpercentage = (LinearLayout)findViewById(R.id.textpercentage);
	}
	
	public void setMainTab1Data(MainTab1Data data){
		mData=data;
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.ic_error)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		ImageLoader.getInstance().displayImage("http://54.92.116.151/view/story/image/"+data.getImage(), mImageView, options);
		
		String dday = data.getEnd_fund_date();
		int year, month, day;
		year = Integer.parseInt(dday.substring(0,4));
		month = Integer.parseInt(dday.substring(5,7));
		day = Integer.parseInt(dday.substring(8,10));
		DDay d = new DDay();
		d_day.setText((d.caldate(year, month, day)*-1)+"");
		
		subject.setText(data.getTitle().toString());
		fund_number.setText(data.getAttend()+"");
		
		cheerup_number.setText(data.getDonator()+"");
		
		String temp = data.getGoal_fund()+"";
		if (temp.length() > 6) {
			int index = temp.length()%3;
			StringBuffer sb = new StringBuffer(temp);
			sb.insert(index, ",");
			index+=4;
			sb.insert(index, ",");
			goal_number.setText(sb.toString());
		}else if (temp.length() > 3) {
			int index = temp.length()%3+3;
			StringBuffer sb = new StringBuffer(temp);
			sb.insert(index, ",");
			goal_number.setText(sb.toString());
		}else{
			goal_number.setText(data.getGoal_fund()+"");
		}
		
		max = data.getGoal_fund();
		current = data.getCurrent_fund();
		percentage = (int)((double)current / (double)max * 100.0);
		current_fund.setMax(max);
		current_fund.setProgress(current);
		current_percent.setText(percentage+"");
		
		if (percentage<25) {
        	textpercentage.setVisibility(View.GONE);
			Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to25);
			current_fund.setThumb(dd);
			progress_message.setText("힘이 안나요 도와주세요~");
		}else if (percentage < 50) {
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(50+percentage, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
		    Log.i("udy", percentage+"");
		    current_percent.setLayoutParams(llp);
			textpercentage.setVisibility(View.VISIBLE);
			Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to25);
			current_fund.setThumb(dd);
			progress_message.setText("더 많은 힘이 필요해요");
		}else if (percentage < 75) {
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(percentage, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
		    Log.i("udy", percentage+"");
		    current_percent.setLayoutParams(llp);
			textpercentage.setVisibility(View.VISIBLE);
			Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to50);
			current_fund.setThumb(dd);
			progress_message.setText("도움을 주세요!");
		}else if (percentage < 100) {
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(218+percentage, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
		    Log.i("udy", percentage+"");
		    current_percent.setLayoutParams(llp);
			textpercentage.setVisibility(View.VISIBLE);
			Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to75);
			current_fund.setThumb(dd);
			progress_message.setText("조금만 더! 거의 다 왔어요");
		}else{
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(295, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
		    current_percent.setLayoutParams(llp);
			textpercentage.setVisibility(View.VISIBLE);
			Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to100);
			current_fund.setThumb(dd);
			progress_message.setText("펀딩이 완료되었습니다");
		}
		
		current_fund.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int origin;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				origin = current;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				seekBar.setProgress(current);
			}
		});
	}
}
