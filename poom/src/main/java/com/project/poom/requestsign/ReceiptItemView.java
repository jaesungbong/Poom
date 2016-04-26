package com.project.poom.requestsign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.poom.R;

public class ReceiptItemView extends FrameLayout {

	TextView category, goalamount;
	ImageView delenti;
	ReceiptData mData;
	
	public ReceiptItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ReceiptItemView(Context context) {
		super(context);
		init();
	}
	
	public interface OnItemClickListener{
		public void onDelBtnClick(View v, ReceiptData data);
	}
	
	OnItemClickListener mListener;
	public void setOnItemClickListener(OnItemClickListener listener){
		mListener=listener;
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.receipt_list_item, this);
		category=(TextView)findViewById(R.id.category);
		goalamount=(TextView)findViewById(R.id.goalamount);
		delenti=(ImageView)findViewById(R.id.del_enti);
		delenti.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onDelBtnClick(ReceiptItemView.this, mData);
				}
			}
		});
	}
	
	public void setReceiptItemData(ReceiptData data){
		mData=data;
		category.setText(data.getCategory());
		goalamount.setText(data.getGoalamount());
	}

}
