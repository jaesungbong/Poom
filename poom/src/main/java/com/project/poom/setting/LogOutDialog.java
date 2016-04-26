package com.project.poom.setting;

import com.project.poom.R;
import com.project.poom.manager.UserManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LogOutDialog extends DialogFragment {
	
	Button btn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.logout_dialog, container, false);
		
		btn = (Button)v.findViewById(R.id.logout_dialog_cancel);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		btn = (Button)v.findViewById(R.id.logout_dialog_logout);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserManager.getInstance().setLogin(false);
				Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT);
				if(mListener!=null){
					mListener.onLogout();
				}
				dismiss();
			}
		});
		
		return v;
	}
	
	public interface onLogOutClickListener{
		public void onLogout();
	}
	
	onLogOutClickListener mListener;
	
	public void setonLogOutClickListener(onLogOutClickListener listener){
		mListener = listener;
	}

}
