package com.project.poom.join;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.manager.CheckNickDuplication;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;

public class JoinFragmentOne extends Fragment {
	
	EditText nickname;
	String strNickName;
	TextView warning;
	ImageView delete_btn_nick;
	Button btn;
	
	int IS_CHECKED_DUPLICATION = 0; //0이면 중복체크 하지 않은 상태, 중복체크 했지만 아이디가 중복된 상태 
												//1이면 중복체크 하고 중복되지 않은 상태
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.join_one, container,false);
		nickname = (EditText) v.findViewById(R.id.nickname);
		delete_btn_nick = (ImageView)v.findViewById(R.id.delete_btn_nick);
		delete_btn_nick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nickname.setText("");
			}
		});
		nickname.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				delete_btn_nick.setVisibility(View.VISIBLE);
			}
		});
		
		warning = (TextView) v.findViewById(R.id.join_one_warning);
		btn = (Button)v.findViewById(R.id.join_one_check_duplication);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String nickname = getNickName();
				
				NetworkManager.getInstnace().checkNickDuplication(getActivity(), nickname, new OnResultListener<CheckNickDuplication>() {
					
					@Override
					public void onSuccess(CheckNickDuplication result) {
						if(!result.error){
							IS_CHECKED_DUPLICATION = 1;
							Toast.makeText(getActivity(), "사용할 수 있는 닉네임 입니다.", Toast.LENGTH_SHORT).show();
						} else{
							IS_CHECKED_DUPLICATION = 0;
							Toast.makeText(getActivity(), "이미 사용중인 닉네임 입니다.", Toast.LENGTH_SHORT).show();
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
	
	public String getNickName(){
		strNickName = nickname.getText().toString();
		return strNickName;
	}
	
	public boolean getCheck1(){
		String nickname = getNickName();
		if((nickname==null)||(nickname.equals(""))){
			return false;
		} else{
			return true;
		}
	}
	
	public void resultCheck1(){
		warning.setVisibility(View.VISIBLE);
		warning.setText("닉네임을 입력하세요.");
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
		warning.setText("닉네임 중복체크를 해 주세요.");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		strNickName = nickname.getText().toString();
	}
}
