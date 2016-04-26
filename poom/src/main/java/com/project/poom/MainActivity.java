package com.project.poom;
import java.io.IOException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
//import com.kakao.KakaoLink;
//import com.kakao.KakaoParameterException;
import com.project.poom.alarm.AlarmFragment;
import com.project.poom.login.LoginActivity;
import com.project.poom.manager.UserManager;
import com.project.poom.menufragments.MenuDutorialFragment;
import com.project.poom.menufragments.MenuMymessageFragment;
import com.project.poom.menufragments.MenuMyrechargeFragment;
import com.project.poom.menufragments.MenuMywriteFragment;
import com.project.poom.menufragments.MenuWriteFragment;
import com.project.poom.myinterest.MenuMyInterestFragment;
import com.project.poom.setting.MenuSettingFragment;

public class MainActivity extends SlidingFragmentActivity {
	
	private Fragment mContent;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	private static final String SENDER_ID = "830539345";
//	private KakaoLink kakalink;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mContent=getSupportFragmentManager().findFragmentByTag("content");
		}
		if(mContent ==null){
			mContent=new MainFragment();
		}	
		setContentView(R.layout.activity_main);
//		try {
//			kakalink  = KakaoLink.getKakaoLink(getApplicationContext());
//			
//		} catch (KakaoParameterException e) {
//			e.printStackTrace();
//		}
		
		if (checkPlayServices()) {
			String regId = PropertyManager.getInstance().getRegistrationId();
			if (regId.equals("")) {
				registerInBackground();
			} else {}
		} else {
			finish();
		}
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, mContent, "content").commit();
		
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_container, new MenuFragment()).commit();	
		
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSupportActionBar().setHomeButtonEnabled(true);
		setSlidingActionBarEnabled(false);
		
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar); //�븸�뀡諛� �젣紐� �꽕�젙
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setIcon(R.drawable.action_btn_gnb_plus); //硫붾돱 �븘�씠肄� �뾾�븷湲�
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.donate_indicator_normal_temp);
	}
	
	private void registerInBackground() {
	    new AsyncTask<Void,Integer,String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            GoogleCloudMessaging gcm = null;
	            String regid;
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
	                }
	                regid = gcm.register(SENDER_ID);
	                PropertyManager.getInstance().setRegistrationId(regid);
	            } catch (IOException ex) {
	            }
	            return msg;
	        }
	        
	        protected void onPostExecute(String result) {
	        	Toast.makeText(MainActivity.this, "register regId", Toast.LENGTH_SHORT).show();
	        }
	    }.execute(null, null, null);
	}	
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	
//	public void switchFragmentWhenPoom(){
//		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//			getSupportFragmentManager().popBackStackImmediate();
//		}
////		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
//		showContent();
//	}
	
//	public void switchFragmentWhenMydonation(){
//		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//			getSupportFragmentManager().popBackStackImmediate();
//		}
//		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuMyDonationFragment()).addToBackStack(null).commit();
//		showContent();
//	}
	
	public void switchFragmentWhenMyInterest(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuMyInterestFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void switchFragmentWhenMymessage(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuMymessageFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void switchFragmentWhenMyRecharge(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuMyrechargeFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void switchFragmentWhenDutorial(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuDutorialFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void switchFragmentWhenSetting(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuSettingFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void switchFragmentWhenWrite(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuWriteFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void switchFragmentWhenMyWrite(){
		while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuMywriteFragment()).addToBackStack(null).commit();
		showContent();
	}
	
	public void mClickAlarmImg(){
		if(getSupportFragmentManager().getBackStackEntryCount() > 0){
			getSupportFragmentManager().popBackStackImmediate();
		}else{
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlarmFragment()).addToBackStack(null).commit();
			showContent();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;

		case R.id.github:
			if(!UserManager.getInstance().isLogin()){
				Intent i=new Intent(MainActivity.this,LoginActivity.class );
				startActivity(i);
			} else{
				mClickAlarmImg();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
