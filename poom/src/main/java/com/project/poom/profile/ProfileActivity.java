package com.project.poom.profile;

import org.apache.http.impl.conn.Wire;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.badge.BadgeActivity;
import com.project.poom.donationact.DonationAct;
import com.project.poom.manager.CallUserInfo;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.written.WrittenActivity;

public class ProfileActivity extends Activity {

	Button btn;
	TextView name;
	TextView message;
	TextView badegeSize;
	TextView donationactSize;
	TextView letterSize;
	FrameLayout items;
	ImageView img;
	Bitmap selectedImage;
	DisplayImageOptions options;
	
	public static final String PA_TAG = "image";
	
	public static final String PROFILE_ID = "profile_id";
	public int profile_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		Intent intent = getIntent();
		profile_id=intent.getIntExtra(PROFILE_ID, 1);

		name = (TextView) findViewById(R.id.activity_profile_name);
		message = (TextView) findViewById(R.id.activity_profile_message);
		img = (ImageView)findViewById(R.id.activity_profile_img);
		badegeSize = (TextView)findViewById(R.id.activity_profile_badge_variable);
		donationactSize = (TextView)findViewById(R.id.activity_profile_donate_variable);
		letterSize = (TextView)findViewById(R.id.activity_profile_letter_variable);
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.profile_sample_image)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		initData();
		btn = (Button) findViewById(R.id.activity_profile_editbtn);
		if(profile_id!=UserManager.getInstance().getUserId()){
			btn.setVisibility(View.GONE);
		}
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
				startActivity(i);
			}
		});
		
		items = (FrameLayout)findViewById(R.id.profile_item1);
		items.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ProfileActivity.this, BadgeActivity.class);
				i.putExtra(BadgeActivity.PARAM_RESULT, profile_id);
				startActivity(i);
			}
		});
		
		items = (FrameLayout)findViewById(R.id.profile_item2);
		items.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ProfileActivity.this, DonationAct.class);
				int donate_id = profile_id;
				i.putExtra(DonationAct.DONATE_ID, donate_id);
				startActivity(i);
			}
		});
		
		items = (FrameLayout)findViewById(R.id.profile_item3);
		items.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ProfileActivity.this, WrittenActivity.class);
				int written_id = profile_id;
				i.putExtra(WrittenActivity.WRITTEN_ID, written_id);
				startActivity(i);
			}
		});

		getActionBar().setIcon(R.drawable.null_image);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setCustomView(R.layout.actionbar_profile);
	}
	
	private void initData() {
		NetworkManager.getInstnace().callUserInfo(ProfileActivity.this,profile_id, new OnResultListener<CallUserInfo>(){

					@Override
					public void onSuccess(CallUserInfo result) {
						if (!result.error) {
							ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"+result.result.get(0).image, img, options);
							name.setText(result.result.get(0).nick);
							message.setText(result.result.get(0).status);
							badegeSize.setText(Integer.toString(result.result.get(0).collect));
							donationactSize.setText(Integer.toString(result.result.get(0).donate));
							letterSize.setText(Integer.toString(result.result.get(0).enroll));
						}
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(ProfileActivity.this, "회원사진불러오기 실패", Toast.LENGTH_SHORT).show();
					}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}
}
