package com.project.poom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.login.LoginActivity;
import com.project.poom.manager.CallUserInfo;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.manager.UserManager.OnLoginChangedListener;
import com.project.poom.profile.ProfileActivity;
import com.project.poom.recharge.RechargeActivity;
import com.project.poom.requestsign.RequestSignActivity;

public class MenuFragment extends Fragment {
	
	LinearLayout when_login, when_logout;
	ImageView imageView;
	TextView poomPoint;
	TextView name;
	ImageButton loginbtn;
	Button write;
	Button myinterest;
	Button recharge;
	Button dutorial;
	Button setting;
	DisplayImageOptions options;
	
	UserManager.OnLoginChangedListener mListener = new OnLoginChangedListener() {
		
		@Override
		public void onLoginChanged(boolean isLogin) {
			initData();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.slide_menu_layout, container,false);
		when_login = (LinearLayout)v.findViewById(R.id.when_login);
		when_logout = (LinearLayout)v.findViewById(R.id.when_logout);
		imageView = (ImageView)v.findViewById(R.id.slide_profile_img);
		imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),ProfileActivity.class);
				int profile_id = UserManager.getInstance().getUserId();
				i.putExtra(ProfileActivity.PROFILE_ID, profile_id);
				startActivity(i);
			}
		});
		
		poomPoint = (TextView)v.findViewById(R.id.slide_profile_point);
		name = (TextView)v.findViewById(R.id.slide_profile_nick);
		loginbtn = (ImageButton)v.findViewById(R.id.slide_profile_login_btn);
		loginbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(),LoginActivity.class );
				startActivity(i);
			}
		});
		
		Button btn = (Button)v.findViewById(R.id.menu_write);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!UserManager.getInstance().isLogin()){
					Intent i=new Intent(getActivity(),LoginActivity.class );
					startActivity(i);
				} else{
					Intent i = new Intent(getActivity(), RequestSignActivity.class);
					startActivity(i);
				}
			}
		});
		
		btn=(Button)v.findViewById(R.id.menu_myinterest);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserManager.getInstance().isLogin()){
					Intent i=new Intent(getActivity(),LoginActivity.class );
					startActivity(i);
				} else{
					((MainActivity)getActivity()).switchFragmentWhenMyInterest();
				}
			}
		});
		
		btn=(Button)v.findViewById(R.id.menu_recharge_energy);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserManager.getInstance().isLogin()){
					Intent i=new Intent(getActivity(),LoginActivity.class );
					startActivity(i);
				} else{
					Intent i = new Intent(getActivity(), RechargeActivity.class);
					startActivity(i);
				}
			}
		});
		
		btn=(Button)v.findViewById(R.id.menu_dutorial);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!UserManager.getInstance().isLogin()){
					Intent i=new Intent(getActivity(),LoginActivity.class );
					startActivity(i);
				} else{
					((MainActivity)getActivity()).switchFragmentWhenDutorial();
				}
			}
		});
		
		btn=(Button)v.findViewById(R.id.menu_setting);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(!UserManager.getInstance().isLogin()){
//					Intent i=new Intent(getActivity(),LoginActivity.class );
//					startActivity(i);
//				} else{
					((MainActivity)getActivity()).switchFragmentWhenSetting();
//				}
			}
		});
		
		UserManager.getInstance().addOnLoginChangedListener(mListener);
		return v;
	}

	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		UserManager.getInstance().remoeOnLoginChangedListener(mListener);
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}
	
	private void initData() {
		if (!UserManager.getInstance().isLogin()) {
			when_login.setVisibility(View.GONE);
			when_logout.setVisibility(View.VISIBLE);
		} else {
			when_login.setVisibility(View.VISIBLE);
			when_logout.setVisibility(View.GONE);
			poomPoint.setText(UserManager.getInstance().getUserEnergy()+"");
			options = new DisplayImageOptions.Builder()
		      .showImageOnLoading(R.drawable.login_graphic)
		      .showImageForEmptyUri(R.drawable.ic_empty)
		      .showImageOnFail(R.drawable.profile_sample_image)
		      .cacheInMemory(true)
		      .cacheOnDisc(true)
		      .considerExifParams(true)
		      /*.displayer(new RoundedBitmapDisplayer(50))*/
		      .build();
			NetworkManager.getInstnace().callUserInfo(getActivity(), UserManager.getInstance().getUserId(), 
					new OnResultListener<CallUserInfo>() {

						@Override
						public void onSuccess(CallUserInfo result) {
							ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"+result.result.get(0).image, imageView, options);
							name.setText(result.result.get(0).nick);
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(getActivity(), "회원사진불러오기 실패", Toast.LENGTH_SHORT).show();
						}
					});
		}
	}
}
