package com.project.poom.maintab2;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.maintab1.DDay;

import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainTab2View extends FrameLayout {
	
	HoloCircularProgressBar progressbar;
	TextView title;
	TextView Dday;
	TextView signNumber;
	ImageView iconView;
	MainTab2Data mData;
	DisplayImageOptions options;
	
	public MainTab2View(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.main_tab2_list_item, this);
		progressbar = (HoloCircularProgressBar)findViewById(R.id.main_tab2_list_item_progress);
		title = (TextView)findViewById(R.id.main_tab2_list_item_title);
		Dday = (TextView)findViewById(R.id.main_tab2_list_item_d_day);
		iconView = (ImageView)findViewById(R.id.main_tab2_list_item_img);		
		signNumber = (TextView)findViewById(R.id.main_tab2_list_item_number_of_sign);
	}
	
	public void setMainTab2Data(MainTab2Data data){
		mData = data;
		title.setText(data.getTitle());
		progressbar.setProgress(data.getSign());
		progressbar.setProgressBackgroundColor(Color.rgb(225, 225, 225));
		progressbar.setProgressColor(Color.rgb(255, 135, 124));
		String dday = data.getEnd_fund_date();
		int year, month, day;
		year = Integer.parseInt(dday.substring(0,4));
		month = Integer.parseInt(dday.substring(5,7));
		day = Integer.parseInt(dday.substring(8,10));
		DDay d = new DDay();
		Dday.setText((d.caldate(year, month, day)*-1)+"");
		
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.ic_error)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		ImageLoader.getInstance().displayImage("http://54.92.116.151/view/story/image/"+data.getImage(), iconView, options);
		signNumber.setText(Integer.toString(data.sign));
	}
}
