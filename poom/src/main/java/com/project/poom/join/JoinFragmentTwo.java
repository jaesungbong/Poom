package com.project.poom.join;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.manager.CheckEmailDuplication;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;

public class JoinFragmentTwo extends Fragment {
	
	EditText mail;
	String strMail;
	TextView warning;
	ImageView delete_btn_Iemail;
	Button btn;
	
	int IS_CHECKED_DUPLICATION = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.join_two, container,false);
		delete_btn_Iemail = (ImageView)v.findViewById(R.id.delete_btn_Iemail);
		delete_btn_Iemail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mail.setText("");
			}
		});
		mail = (EditText)v.findViewById(R.id.join_two_email);
		mail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				delete_btn_Iemail.setVisibility(View.VISIBLE);
			}
		});
		
		warning = (TextView) v.findViewById(R.id.join_two_warning);
		btn = (Button)v.findViewById(R.id.join_two_button);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String email = getEmail();
				
				NetworkManager.getInstnace().checkEmailDuplication(getActivity(), email, new OnResultListener<CheckEmailDuplication>() {
					
					@Override
					public void onSuccess(CheckEmailDuplication result) {
						if(!result.error){
							IS_CHECKED_DUPLICATION = 1;
							Toast.makeText(getActivity(), "사용할 수 있는 이메일 입니다.", Toast.LENGTH_SHORT).show();
						} else{
							IS_CHECKED_DUPLICATION = 0;
							Toast.makeText(getActivity(), "이미 사용중인 이메일 입니다.", Toast.LENGTH_SHORT).show();
						}
					}
					
					@Override
					public void onFail(int code) {
						IS_CHECKED_DUPLICATION = 0;
						Toast.makeText(getActivity(), "서버와 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		return v;
	}
	
	public String getEmail(){
		strMail = mail.getText().toString();
		return strMail;
	}
	
	public boolean getCheck1(){
		String mail = getEmail();
		if(mail == null || mail.equals("")){
			return false;
		} else{
			return true;
		}
	}
	
	public void resultCheck1(){
		warning.setVisibility(View.VISIBLE);
		warning.setText("메일을 입력 하세요.");
	}
	
	public boolean getCheck2(){
		if(IS_CHECKED_DUPLICATION==0){
			return false;
		} else {
			return true;
		}
	}
	
	public void resultCheck2(){
		warning.setVisibility(View.VISIBLE);
		warning.setText("이메일 중복체크를 해 주세요.");
	}
	@Override
	public void onPause() {
		super.onPause();
		strMail = mail.getText().toString();
	}
}
