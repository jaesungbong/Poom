package com.project.poom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.internal.fi;
import com.project.poom.login.LoginActivity;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserInfoResult;
import com.project.poom.manager.UserManager;

public class LaunchActivity extends Activity {
	
	public static final int SPLASH_TIME_CHOICE = 2000;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.launch_layout);
	    
	    new Handler().postDelayed(new Runnable() {

	         @Override
	         public void run() {
	        	 
	        	 NetworkManager.getInstnace().login(LaunchActivity.this, 
	     	    		PropertyManager.getInstance().getEmail(), 
	     	    		PropertyManager.getInstance().getPassword(), 
	     	    		PropertyManager.getInstance().getRegistrationId(), new OnResultListener<UserInfoResult>() {
	     					
	     					@Override
	     					public void onSuccess(UserInfoResult result) {
	     						if (!result.error) {
	     							Intent i = new Intent(LaunchActivity.this, MainActivity.class);
	     							UserManager.getInstance().setLogin(true);
		     						UserManager.getInstance().setUserId(result.result.id);
									UserManager.getInstance().setUserNick(result.result.nick);
									UserManager.getInstance().setUserEnergy(result.result.energy);	
									UserManager.getInstance().setImages(result.result.image);
									Toast.makeText(LaunchActivity.this, "�븞�뀞�븯�꽭�슂 "+result.result.nick+"�떂!", Toast.LENGTH_SHORT).show();
									startActivity(i);
		     			            finish();
								}else {
									Intent i = new Intent(LaunchActivity.this, MainActivity.class);
									startActivity(i);
		     			            finish();
								}
	     					}
	     					
	     					@Override
	     					public void onFail(int code) {
	     						Intent i = new Intent(LaunchActivity.this, MainActivity.class);
	     						startActivity(i);
     			                finish();
	     						//Toast.makeText(LaunchActivity.this, "�옄�룞濡쒓렇�씤 �삤瑜�", Toast.LENGTH_SHORT).show();
	     					}
	     				});
	         }
	          
	       }, SPLASH_TIME_CHOICE);
	}

}
