package com.project.poom.detailstory.tab2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.poom.R;

public class UploadNewsDialog extends DialogFragment {
	
	Button btn;
	TextView content;
	int res;
	
	public UploadNewsDialog(int res){
		this.res = res;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.uploadnewsdialog, container, false);
		content = (TextView)v.findViewById(R.id.alert_content);
		content.setBackgroundResource(res);
		btn = (Button)v.findViewById(R.id.btn_cancle);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onCancleClicked();
				}
			}
		});
		
		btn = (Button)v.findViewById(R.id.btn_confirm);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onConfirmClicked();
				}
			}
		});
		return v;
	}
	
	public interface OnUploadNewsDialogClickListener{
		public void onConfirmClicked();
		public void onCancleClicked();
	}
	
	public OnUploadNewsDialogClickListener mListener;
	
	public void setOnUploadNewsDialogClickListener(OnUploadNewsDialogClickListener listener){
		mListener = listener;
	}
}
