package com.project.poom.detailstory.tab2;

import com.project.poom.R;
import com.project.poom.manager.UserManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class DtailStoryTab2ViewTop extends FrameLayout {

	DtailStoryTab2Data mData;
	Button btn;
	int writtenuser_id;
	
	public DtailStoryTab2ViewTop(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public interface OnItemDataClickListener {
		public void onBtnClick(View v,DtailStoryTab2Data data);
	}
	
	OnItemDataClickListener mListener;
	public void setOnItemDataClickListener(OnItemDataClickListener listener) {
		mListener = listener;
	}

	public DtailStoryTab2ViewTop(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.dtail_tab2_listview_top_layout, this);
		btn=(Button)findViewById(R.id.newstory);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onBtnClick(DtailStoryTab2ViewTop.this, mData);
				}
			}
		});
	}

	public void setDtailStoryTab2ViewData(DtailStoryTab2Data data, int writtenuser_id){
		mData=data;
		this.writtenuser_id = writtenuser_id;
		Log.i("userid", "ds6 : "+writtenuser_id+"");
		if (writtenuser_id == UserManager.getInstance().getUserId()) {
			btn.setVisibility(View.VISIBLE);
		}else {
			btn.setVisibility(View.GONE);
		}
	}
}
