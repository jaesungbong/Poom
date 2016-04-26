package com.project.poom.join;

import com.project.poom.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AgreementFragment extends Fragment {

	Button userinfo_btn1, userinfo_btn2, service_btn1, service_btn2;
	Button btn;
	boolean userinfo, service;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.agreement_layout, container, false);
		userinfo_btn1 = (Button)v.findViewById(R.id.userinfo_mark);
		userinfo_btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userinfo_btn1.setVisibility(View.GONE);
				userinfo_btn2.setVisibility(View.VISIBLE);
				AgreementManager.getInstance().setuserinfoisCheck(true);
			}
		});
		userinfo_btn2 = (Button)v.findViewById(R.id.userinfo_marking);
		userinfo_btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userinfo_btn1.setVisibility(View.VISIBLE);
				userinfo_btn2.setVisibility(View.GONE);
				AgreementManager.getInstance().setuserinfoisCheck(false);
			}
		});
		service_btn1 = (Button)v.findViewById(R.id.service_mark);
		service_btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				service_btn1.setVisibility(View.GONE);
				service_btn2.setVisibility(View.VISIBLE);
				AgreementManager.getInstance().setserviceisCheck(true	);
			}
		});
		service_btn2 = (Button)v.findViewById(R.id.service_marking);
		service_btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				service_btn1.setVisibility(View.VISIBLE);
				service_btn2.setVisibility(View.GONE);
				AgreementManager.getInstance().setserviceisCheck(false);
			}
		});
		return v;
	}
	
	public boolean getCheck1(){
		if (AgreementManager.getInstance().getuserinfoisCheck() == true && 
				AgreementManager.getInstance().getserviceisCheck() == true) {
			return true;
		}else {
			return false;
		}
	}
	
	public void resultCheck1(){
		Toast.makeText(getActivity(), "이용약관에 모두 동의하셔야 회원가입이 되요.", Toast.LENGTH_SHORT).show();
	}
}
