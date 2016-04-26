package com.project.poom.detailstory.tab2.comment;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsPopUpView extends FrameLayout {

	public NewsPopUpView(Context context) {
		super(context);
		init();
	}
	
	ImageView contentimg;
	NewsPopUpData mData;
	DisplayImageOptions options;
	
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.newspopup_img_item, this);
		contentimg = (ImageView)findViewById(R.id.newspopup_contentimg);
	}
	
	public void setMyData(NewsPopUpData data)
	{
		mData = data;
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.login_graphic)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		ImageLoader.getInstance().displayImage("http://54.92.116.151/view/news/image/"+
	      data.getImage(), contentimg, options);
	}
	
	
	
	
}
