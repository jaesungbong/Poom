package com.project.poom;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	private static final String PREFS_NAME = "myprefs";
	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private static final String REG_ID = "regId";
	private String regId;
	public void setRegistrationId(String regid) {
		this.regId = regid;
		mEditor.putString(REG_ID, regid);
	}
	
	public String getRegistrationId() { //단말기 고유 번호 추출 함수
		if (regId == null) {
			regId = mPrefs.getString(REG_ID, "");
		}
		return regId;
	}

	private static final String EMAIL = "email";
	private String mEmail;
	
	public void setEmail(String email){
		mEmail = email;
		mEditor.putString(EMAIL, email);
		mEditor.commit();
	}
	
	public String getEmail(){
		if (mEmail == null) {
			mEmail = mPrefs.getString(EMAIL, "");
		}
		return mEmail;
	}
	
	private static final String PASSWORD = "password";
	private String mPassword;
	
	public void setPassword(String password) {
		mPassword = password;
		mEditor.putString(PASSWORD, password);
		mEditor.commit();
	}
	
	public String getPassword() {
		if (mPassword == null) {
			mPassword = mPrefs.getString(PASSWORD, "");
		}
		return mPassword;
	}
}
