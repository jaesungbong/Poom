package com.project.poom.detailsign;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.login.LoginActivity;
import com.project.poom.maintab1.DDay;
import com.project.poom.manager.CallStory;
import com.project.poom.manager.CallUserInfo;
import com.project.poom.manager.DeleteStory;
import com.project.poom.manager.ImagePath_Bundle;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.profile.ProfileActivity;
import com.viewpagerindicator.CirclePageIndicator;

public class DetailSignActivity extends ActionBarActivity {
	
	ViewPager pager;
	MyFragmentPagerAdapter mAdapter;
	CirclePageIndicator mIndicator;
	TextView title, content, d_day, postdate,writerName, del_this_story;
	ImageView writerPhoto;
	CheckBox check;
	Bundle bundle;
	int story_id,user_id;
	int imagecount;
	DisplayImageOptions options;
	ArrayList<ImagePath_Bundle> imagepaths = new ArrayList<ImagePath_Bundle>();
	
	public static final String DETAILSIGNTAG = "id";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.sign_activity);
	    
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.login_graphic)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.login_graphic)
		.cacheInMemory(true).cacheOnDisc(true)
		.considerExifParams(true)
		/* .displayer(new RoundedBitmapDisplayer(50)) */
		.build();
	    
		Intent intent = getIntent();
		story_id = intent.getIntExtra(DETAILSIGNTAG, 1);
		
		pager = (ViewPager)findViewById(R.id.sa_pager);
		del_this_story = (TextView)findViewById(R.id.del_this_story);
		mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
		mIndicator.setStrokeColor(0xFFFF6454);
		mIndicator.setFillColor(0xFFFF6454);
		title=(TextView)findViewById(R.id.sa_title);
		d_day = (TextView)findViewById(R.id.sa_dday);
		postdate = (TextView)findViewById(R.id.postdate);
		content = (TextView)findViewById(R.id.sa_content);
		writerName = (TextView)findViewById(R.id.sign_activity_nickname);
		Button btn = (Button)findViewById(R.id.sa_share);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ShareDialogFragment f = new ShareDialogFragment();
//				f.show(getSupportFragmentManager(), "dialog");				
			}
		});
		
		btn = (Button)findViewById(R.id.sign_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!UserManager.getInstance().isLogin()) {
					Intent i = new Intent(DetailSignActivity.this, LoginActivity.class);
					startActivity(i);
				}else {
					Intent i = new Intent(DetailSignActivity.this, DoItSign.class);
					i.putExtra(DoItSign.DOITSIGN, story_id);
					startActivity(i);
				}
			}
		});
		

		
		NetworkManager.getInstnace().callStory(DetailSignActivity.this, story_id, UserManager.getInstance().getUserId(), new OnResultListener<CallStory>() {

			@Override
			public void onSuccess(CallStory result) {
				user_id = result.result.story.get(0).id;
				writerName.setText(result.result.story.get(0).nick);
				title.setText(result.result.story.get(0).title+"");
				content.setText(result.result.story.get(0).content+"");
				String dday = result.result.story.get(0).end_fund_date;
				int year, month, day;
				year = Integer.parseInt(dday.substring(0,4));
				month = Integer.parseInt(dday.substring(5,7));
				day = Integer.parseInt(dday.substring(8,10));
				DDay d = new DDay();
				d_day.setText((d.caldate(year, month, day)*-1)+"");
				//dday.setText(text);
				String ed = result.result.story.get(0).enroll_date;
				String eyear, emonth, eday;
				eyear = ed.substring(0, 4);
				emonth = ed.substring(5, 7);
				eday = ed.substring(8, 10);
				StringBuilder sb = new StringBuilder();
				sb.append(eyear).append(".").append(emonth).append(".").append(eday);
				postdate.setText(sb.toString());
				imagecount = result.result.images.size();
				imagepaths = result.result.images;
				mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), imagepaths, imagecount);
				pager.setAdapter(mAdapter);
				mIndicator.setViewPager(pager);
				NetworkManager.getInstnace().callUserInfo(DetailSignActivity.this, user_id, new OnResultListener<CallUserInfo>() {

					@Override
					public void onSuccess(CallUserInfo result) {
						ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"
								+result.result.get(0).image, writerPhoto, options);
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(DetailSignActivity.this, "회원사진 가져오기 오류", Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(DetailSignActivity.this, "fail", Toast.LENGTH_SHORT).show();
			}
		});
		
		writerPhoto = (ImageView)findViewById(R.id.sign_activity_photo);
		writerPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(DetailSignActivity.this,ProfileActivity.class);
				int profile_id = user_id;
				i.putExtra(ProfileActivity.PROFILE_ID, profile_id);
				startActivity(i);
			}
		});
		
		

		del_this_story.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().deleteStory(DetailSignActivity.this, story_id, new OnResultListener<DeleteStory>() {

					@Override
					public void onSuccess(DeleteStory result) {
						Toast.makeText(DetailSignActivity.this, "해당 사연이 정삭적으로 삭제되었습니다", Toast.LENGTH_SHORT).show();
						finish();
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(DetailSignActivity.this, "faildelete", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setCustomView(R.layout.actionbar_sign);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

}
