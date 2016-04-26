package com.project.poom.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.Session;
//import com.facebook.Session.StatusCallback;
//import com.facebook.SessionState;
import com.project.poom.PropertyManager;
import com.project.poom.R;
import com.project.poom.join.JoinActivity;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserInfoResult;
import com.project.poom.manager.UserManager;

public class LoginActivity extends Activity {

	TextView warning;
	Button btn, join;
	EditText text1, text2;
	ImageView delete_btn_email, delete_btn_password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btn = (Button) findViewById(R.id.xbox);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btn = (Button)findViewById(R.id.facebook_login_btn);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Session.openActiveSession(LoginActivity.this, true, new StatusCallback() {
//					
//					@Override
//					public void call(Session session, SessionState state, Exception exception) {
//						if (session.isOpened()) {
//							String token = session.getAccessToken();
//							Log.i("token", token);
//						}
//					}
//				});
//			}
//		});
		
		join = (Button) findViewById(R.id.join);
		join.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this, JoinActivity.class);
				startActivity(i);
			}
		});

		delete_btn_email = (ImageView)findViewById(R.id.delete_btn_email);
		delete_btn_email.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text1.setText("");
			}
		});
		delete_btn_password = (ImageView)findViewById(R.id.delete_btn_password);
		delete_btn_password.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				text2.setText("");
			}
		});
		text1 = (EditText) findViewById(R.id.email_text);
		text1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				delete_btn_email.setVisibility(View.VISIBLE);
			}
		});
		text1.setText(PropertyManager.getInstance().getEmail());
		text2 = (EditText) findViewById(R.id.password_text);
		text2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				delete_btn_password.setVisibility(View.VISIBLE);
			}
		});
		text2.setText(PropertyManager.getInstance().getPassword());
		
		warning = (TextView) findViewById(R.id.warning);

		btn = (Button) findViewById(R.id.login_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String id = text1.getText().toString();
				String password = text2.getText().toString();

				if ((id == null || id.equals(""))|| (password == null || password.equals(""))) {
					warning.setVisibility(View.VISIBLE);
					warning.setText("로그인 정보를 기입해주세요");
				} else {
					NetworkManager.getInstnace().login(LoginActivity.this, id,password, PropertyManager.getInstance().getRegistrationId(),new OnResultListener<UserInfoResult>() {
	
								@Override
								public void onSuccess(UserInfoResult result) {
									if (!result.error) {
										Toast.makeText(LoginActivity.this,result.message, Toast.LENGTH_SHORT).show();
										UserManager.getInstance().setLogin(true);
										UserManager.getInstance().setUserId(result.result.id);
										UserManager.getInstance().setUserNick(result.result.nick);
										UserManager.getInstance().setUserEnergy(result.result.energy);	
										UserManager.getInstance().setImages(result.result.image);
										
										PropertyManager.getInstance().setEmail(text1.getText().toString());
										PropertyManager.getInstance().setPassword(text2.getText().toString());
										finish();
									} else {
										Toast.makeText(LoginActivity.this,result.message,Toast.LENGTH_SHORT).show();
										finish();
									}
									
								}
								
								@Override
								public void onFail(int code) {
									Toast.makeText(LoginActivity.this,"서벼 연결 실패", Toast.LENGTH_SHORT).show();
								}
							});
				}
			}
		});
	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (Session.getActiveSession() != null) {
//			Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//		}
//	}
}
