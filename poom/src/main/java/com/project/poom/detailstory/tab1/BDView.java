package com.project.poom.detailstory.tab1;

import com.project.poom.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BDView extends FrameLayout {
	
	TextView category, cost;
	BDData mData;

	public BDView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BDView(Context context) {
		super(context);
		init();
	}
	
	public void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.bd_item, this);
		category = (TextView)findViewById(R.id.bd_category);
		cost = (TextView)findViewById(R.id.bd_cost);
	}
	
	public void setBDData(BDData data){
		mData = data;
		category.setText(data.getCategory());
		cost.setText(data.getCost());
	}
}
