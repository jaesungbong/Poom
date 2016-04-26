package com.project.poom.requestsign;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.poom.R;

public class ReceiptDialog extends DialogFragment {
	
	EditText category, goalamount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.receipt_dialog_layout, container, false);
		category = (EditText)v.findViewById(R.id.ins_category);
		goalamount = (EditText)v.findViewById(R.id.ins_goalamount);
		Button btn = (Button)v.findViewById(R.id.ins_conf);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (category.getText().toString().equals("") || goalamount.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "아직 등록되지 않은 항목이 있어요", Toast.LENGTH_SHORT).show();
				}else {
					if (dListener != null) {
						dListener.OnConfirmClicked(category.getText().toString()
								, goalamount.getText().toString());
					}
					category.setText("");
					goalamount.setText("");
				}
			}
		});
		return v;
	}
	
	public interface OnDialogClickListener{
		public void OnConfirmClicked(String category, String goalamount);
	}
	
	public OnDialogClickListener dListener;
	
	public void setOnDialogClickListener(OnDialogClickListener listener){
		dListener=listener;
	}
}
