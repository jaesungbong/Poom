package com.project.poom.join;

import com.project.poom.manager.UserManager.OnLoginChangedListener;

public class AgreementManager {
	
	private static AgreementManager instance;
	public static AgreementManager getInstance(){
		if (instance == null) {
			instance = new AgreementManager();
		}
		return instance;
	}
	
	private AgreementManager(){}
	
	private boolean userinfoisCheck = false;
	private boolean serviceisCheck= false;
	
	public void setuserinfoisCheck(boolean ischeck){
		if (ischeck == true) {
			userinfoisCheck = true;
		}else {
			userinfoisCheck = false;
		}
	}
	
	public boolean getuserinfoisCheck(){
		return userinfoisCheck;
	}
	
	public void setserviceisCheck(boolean ischeck){
		if (ischeck == true) {
			serviceisCheck = true;
		}else {
			serviceisCheck = false;
		}
	}
	
	public boolean getserviceisCheck(){
		return serviceisCheck;
	}
}
