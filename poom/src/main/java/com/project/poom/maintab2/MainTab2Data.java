package com.project.poom.maintab2;

import java.util.ArrayList;

public class MainTab2Data {
	public int sign, id;
	public String title, image, enroll_date, end_fund_date;
	
	public float getSign() {
		return (float) (sign*0.1);
	}
	public void setSign(int sign) {
		this.sign = sign;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getEnroll_date() {
		return enroll_date;
	}
	public void setEnroll_date(String enroll_date) {
		this.enroll_date = enroll_date;
	}
	public String getEnd_fund_date() {
		return end_fund_date;
	}
	public void setEnd_fund_date(String end_fund_date) {
		this.end_fund_date = end_fund_date;
	}
	
}
