package com.project.poom.detailstory.tab1.comment;

import com.google.gson.annotations.SerializedName;

public class CommentData {
	public int user_id;
	public int id;
	@SerializedName("nick")	
	public String name;
	@SerializedName("content")
	public String comment;
	@SerializedName("enroll_date")
	public String enroll_date;
	public String image;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getEnroll_date() {
		return enroll_date;
	}
	public void setEnroll_date(String enroll_date) {
		this.enroll_date = enroll_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
}
