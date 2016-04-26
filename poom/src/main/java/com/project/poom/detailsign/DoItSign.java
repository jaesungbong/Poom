package com.project.poom.detailsign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.R.drawable;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.SignStory;
import com.project.poom.manager.UserManager;

public class DoItSign extends Activity {
	
	ImageView signarea;
	int user_id, story_id;
	public static final String DOITSIGN = "sign";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sign);
	    
	    UserManager.getInstance().getUserId();
	    Intent intent = getIntent();
		story_id = intent.getIntExtra(DOITSIGN, 1);
		user_id = UserManager.getInstance().getUserId();
	    signarea = (ImageView)findViewById(R.id.signarea);
	    signarea.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				signarea.setImageResource(R.drawable.sign_pop_foot_on);
				NetworkManager.getInstnace().signStory(DoItSign.this, story_id, user_id, new OnResultListener<SignStory>() {
					
					@Override
					public void onSuccess(SignStory result) {
						if(!result.error){
							Toast.makeText(DoItSign.this,"서명 완료!", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(DoItSign.this,"이미 서명하셨어요!", Toast.LENGTH_SHORT).show();
							finish();
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(DoItSign.this, "fail", Toast.LENGTH_SHORT).show();
						finish();
					}
				});
				
				return true;
			}
		});
	}

}
