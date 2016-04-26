package com.project.poom.maintab1;

import android.widget.ImageView;
import android.widget.TextView;

public class MainTab1Data {
	public int id;
	public String title;
	public int goal_fund;
	public int current_fund;
	public String end_fund_date;
	public String image;
	public int attend;
	public int donator;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getGoal_fund() {
		return goal_fund;
	}
	public void setGoal_fund(int goal_fund) {
		this.goal_fund = goal_fund;
	}
	public int getCurrent_fund() {
		return current_fund;
	}
	public void setCurrent_fund(int current_fund) {
		this.current_fund = current_fund;
	}
	public String getEnd_fund_date() {
		return end_fund_date;
	}
	public void setEnd_fund_date(String end_fund_date) {
		this.end_fund_date = end_fund_date;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getAttend() {
		return attend;
	}
	public void setAttend(int attend) {
		this.attend = attend;
	}
	public int getDonator() {
		return donator;
	}
	public void setDonator(int donator) {
		this.donator = donator;
	}
}
