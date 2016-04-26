package com.project.poom.setting;

import com.project.poom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SettingLowItem extends FrameLayout {
	
	TextView name;
	SettingLowItemData mData;
	CheckBox check;

	public SettingLowItem(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.setting_low_item, this);
		name = (TextView)findViewById(R.id.setting_low_item_name);
		check = (CheckBox)findViewById(R.id.setting_low_item_check);
	}
	
	public void setSettingLowItemData(SettingLowItemData data)
	{
		mData = data;
		name.setText(data.name);
		name.setTextColor(data.color);
		check.setClickable(data.check);
	}
}
