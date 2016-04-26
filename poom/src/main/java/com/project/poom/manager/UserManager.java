package com.project.poom.manager;

import java.util.ArrayList;

public class UserManager {

	private static UserManager instance;
	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	private UserManager() {}
	
	private boolean isLogin = false;
	
	public void setLogin(boolean login) {
		if (isLogin != login) {
			isLogin = login;
			publishLoginState();
		}
	}
	
	public boolean isLogin() {
		return isLogin;
	}
	
	private int userId;
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public int getUserId(){
		return userId;
	}
	
	private String userNick;
	
	public void setUserNick(String userNick){
		this.userNick = userNick;
	}
	
	public String getUserNick(){
		return userNick;
	}
	
	private String userStat;
	
	public void setUserStat(String userStat){
		this.userStat = userStat;
	}
	
	public String getUserStat(){
		return this.userStat;
	}
	
	private int userEnergy;
	
	public void setUserEnergy(int energy){
		this.userEnergy = energy;
	}
	
	public int getUserEnergy(){
		return this.userEnergy;
	}
	
	private String images;

	public String getImages() {
		return this.images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
	public interface OnLoginChangedListener {
		public void onLoginChanged(boolean isLogin);
	}
	
	ArrayList<OnLoginChangedListener> mListeners = new ArrayList<OnLoginChangedListener>();
	public void addOnLoginChangedListener(OnLoginChangedListener listener) {
		mListeners.add(listener);
	}
	
	public void remoeOnLoginChangedListener(OnLoginChangedListener listener) {
		mListeners.remove(listener);
	}
	
	public void publishLoginState() {
		for (OnLoginChangedListener l : mListeners) {
			l.onLoginChanged(isLogin);
		}
	}
}
