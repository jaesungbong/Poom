package com.project.poom.requestsign;

import com.project.poom.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ViewGalleryView extends FrameLayout {
	
	ViewGalleryData mData;
	ImageView image;

	public ViewGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewGalleryView(Context context) {
		super(context);
		init();
	}

	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.callgal_item, this);
		image = (ImageView)findViewById(R.id.image);
	}
	
	public void setViewGalleryData(ViewGalleryData data){
		mData = data;
		image.setImageBitmap(data.getImage());
	}
}
