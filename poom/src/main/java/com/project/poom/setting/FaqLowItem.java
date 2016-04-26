package com.project.poom.setting;

import com.project.poom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FaqLowItem extends FrameLayout {
	
	FaqLowItemData mData;
	TextView title;

	public FaqLowItem(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.faq_low_item, this);
		title = (TextView)findViewById(R.id.faq_low_item_content);
	}
	
	public void setFaqLowItemData(FaqLowItemData data){
		mData = data;
		title.setText(data.content);
	}

}
