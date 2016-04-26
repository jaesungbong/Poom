package com.project.poom.badge;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;

public class BadgeItemView extends FrameLayout{
	
	ImageView img;
	TextView number;
	BadgeData mData;
	DisplayImageOptions options;

	public BadgeItemView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.badge_item_layout, this);
		img = (ImageView)findViewById(R.id.medal);
		number = (TextView)findViewById(R.id.medal_content);
	}
	
	public void setBadgeData(BadgeData data){
		mData = data;
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.login_graphic)
	      .showImageOnFail(R.drawable.login_graphic)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		switch (data.getBadge_id()) {
		case 1:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 2:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 3:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 4:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 5:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 6:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 7:
			img.setImageResource(R.drawable.medal_7);
			number.setText(data.getName());
			break;
		case 8:
			img.setImageResource(R.drawable.medal_8);
			number.setText(data.getName());
			break;
		case 9:
			img.setImageResource(R.drawable.medal_9);
			number.setText(data.getName());
			break;
		case 10:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 11:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 12:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 13:
			img.setImageResource(R.drawable.medal_10);
			number.setText(data.getName());
			break;
		case 14:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 15:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 16:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 17:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 18:
			img.setImageResource(R.drawable.medal_sample2);
			number.setText(data.getName());
			break;
		case 19:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		case 20:
//			img.setImageResource(R.drawable.);
//			number.setText("");
			break;
		}
	}
}
