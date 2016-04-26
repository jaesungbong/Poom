package com.project.poom.detailstory.tab2;

import com.project.poom.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ItemView extends FrameLayout implements Checkable{

	ImageView imageCheck;
	boolean isChecked=false;
	
	public ItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.newsalert_item_view, this);
		imageCheck=(ImageView)findViewById(R.id.image_checked);
	}

	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked	) {
			isChecked=checked;
			drawCheck();
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
		setChecked(!isChecked);
	}
	
	private void drawCheck(){
		if (isChecked) {
			imageCheck.setImageResource(android.R.drawable.checkbox_on_background);
		}else {
			imageCheck.setImageResource(android.R.drawable.checkbox_off_background);
		}
	}
}
