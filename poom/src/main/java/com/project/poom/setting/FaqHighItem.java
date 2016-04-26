package com.project.poom.setting;

import com.google.android.gms.tagmanager.Container;
import com.project.poom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FaqHighItem extends FrameLayout {
	
	FaqHighItemData mData;
	TextView title;

	public FaqHighItem(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.faq_high_item, this);
		title = (TextView)findViewById(R.id.faq_high_item_title);
	}
	
	public void setFaqHighItemData(FaqHighItemData data){
		mData = data;
		title.setText(data.title);
	}

}
