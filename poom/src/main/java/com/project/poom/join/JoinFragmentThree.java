package com.project.poom.join;

import com.project.poom.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JoinFragmentThree extends Fragment {
	
	EditText password1,password2;
	String strPassword1,strPassword2;
	TextView warning;
	ImageView delete_btn_paw1, delete_btn_paw2;
		
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.join_three, container,false);
		delete_btn_paw1 = (ImageView)v.findViewById(R.id.delete_btn_paw1);
		delete_btn_paw1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				password1.setText("");
			}
		});
		
		delete_btn_paw2 = (ImageView)v.findViewById(R.id.delete_btn_paw2);
		delete_btn_paw2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				password2.setText("");
			}
		});
		
		password1 = (EditText)v.findViewById(R.id.join_three_password);
		password1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				delete_btn_paw1.setVisibility(View.VISIBLE);
			}
		});
		
		password2 = (EditText)v.findViewById(R.id.join_three_password2);
		password2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				delete_btn_paw2.setVisibility(View.VISIBLE);
			}
		});
		
		warning = (TextView) v.findViewById(R.id.join_three_warning);
		return v;
	}
	
	
	public String getPassword1(){
			strPassword1 = password1.getText().toString();
		return strPassword1;		
	}
	
	public String getPassword2(){
		strPassword2 = password2.getText().toString();
	return strPassword2;		
}
	
	@Override
	public void onPause() {
		super.onPause();
		strPassword2 = password2.getText().toString();
	}
	
	public boolean getCheck1(){
		String pass1,pass2;
		pass1=getPassword1();
		pass2=getPassword2();
		if((pass1==null||pass1.equals(""))||(pass2==null||pass2.equals(""))){
			return false;
		}
		return true;
	}
	
	public boolean getCheck2(){
		String pass1,pass2;
		pass1=getPassword1();
		pass2=getPassword2();
		if(!pass1.equals(pass2)){
			return false;
		}
		return true;
	}
	
	public void resultCheck1(){
		warning.setVisibility(View.VISIBLE);
		warning.setText("비밀번호를 입력 하시오.");
	}
	
	public void resultCheck2(){
		warning.setText("비밀번호가 서로 다릅니다.");
	}
}
