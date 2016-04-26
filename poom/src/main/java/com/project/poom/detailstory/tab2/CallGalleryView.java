package com.project.poom.detailstory.tab2;

import com.project.poom.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CallGalleryView extends FrameLayout {
	
	CallGalleryData mData;
	ImageView image;

	public CallGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CallGalleryView(Context context) {
		super(context);
		init();
	}

	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.callgal_item2, this);
		image = (ImageView)findViewById(R.id.images);
	}
	
	public void setCallGalleryData(CallGalleryData data){
		mData = data;
		image.setImageBitmap(data.getResId());
	}
}
