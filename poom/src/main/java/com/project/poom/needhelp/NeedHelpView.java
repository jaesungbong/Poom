package com.project.poom.needhelp;

import com.project.poom.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class NeedHelpView extends FrameLayout {

	ImageView mImageView;
	TextView mAddress, mSubject, mRatio, mIng, mMax;
	NeedHelpData mData;

	public NeedHelpView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NeedHelpView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.needhelp_list_item,
				this);
		// mImageView=(ImageView)findViewById(R.id.) 이미지 공간 따로 설정해줘야됨
		mAddress = (TextView) findViewById(R.id.nhaddress);
		mSubject = (TextView) findViewById(R.id.nhmainsubject);
		mRatio = (TextView) findViewById(R.id.nhratio);
		mIng = (TextView) findViewById(R.id.nhing);
		mMax = (TextView) findViewById(R.id.nhmax);
	}
	
	public void setNeedHelpData(NeedHelpData data){
		mData=data;
		//mImageView.setImageResource(data.getImage());
		mSubject.setText(data.getSubject());
		mAddress.setText(data.getAddress());
		mRatio.setText(data.getRatio());
		mIng.setText(data.getIng());
		mMax.setText(data.getMax());
	}
}
