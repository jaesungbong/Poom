package com.project.poom.myinterest;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyInterestItemView extends FrameLayout {
	
	MyInterestItemData mData;
	ImageView img;
	TextView content;
	ImageView sign,donate,complete;
	DisplayImageOptions options;

	public MyInterestItemView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.myinterest_item_view, this);
		img=(ImageView)findViewById(R.id.myinterest_item_view_img);
		content=(TextView)findViewById(R.id.myinterest_item_view_content);
		sign = (ImageView)findViewById(R.id.myinterest_item_view_mark_sign);
		donate=(ImageView)findViewById(R.id.myinterest_item_view_mark_donate);
		complete=(ImageView)findViewById(R.id.myinterest_item_view_mark_complete);
	}
	
	public void setMyInterestItemData(MyInterestItemData data){
		mData = data;
		content.setText(data.title);
		if(data.approve==0){
			sign.setVisibility(View.VISIBLE);
		} else if(data.approve==1&&data.progress==0){
			donate.setVisibility(View.VISIBLE);
		} else if(data.approve==1&&data.progress==1){
			complete.setVisibility(View.VISIBLE);
		}
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.login_graphic)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
		.cacheOnDisc(true).considerExifParams(true)
		/* .displayer(new RoundedBitmapDisplayer(50)) */
		.build();
		ImageLoader.getInstance().displayImage(
		"http://54.92.116.151/view/story/image/" + data.image, img, options);
	}

}
