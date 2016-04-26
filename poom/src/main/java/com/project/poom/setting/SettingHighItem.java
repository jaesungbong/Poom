package com.project.poom.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.appscumen.widget.MySwitch;
import com.project.poom.R;

public class SettingHighItem extends FrameLayout {
	
	TextView name; 
	MySwitch switcher;
	SettingHighItemData mData;

	public SettingHighItem(Context context) {
		super(context);
		init();
	}
	
	public interface OnItemDataSwitcherListener{
		public void onSwitcher(View v,SettingHighItemData data);
		public void onOffSwitcher(View v,SettingHighItemData data);
	}
	
	OnItemDataSwitcherListener mListener;
	
	public void setOnItemDataSwitcherLIstener(OnItemDataSwitcherListener listener){
		mListener = listener;
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.setting_high_item, this);
		name = (TextView)findViewById(R.id.setting_high_item_name);
		switcher = (MySwitch)findViewById(R.id.myswitcher);
		switcher.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if(isChecked){
					if(mListener !=null){
						mListener.onSwitcher(SettingHighItem.this, mData);
					}
				} else {
					if(mListener !=null){
						mListener.onOffSwitcher(SettingHighItem.this, mData);
					}
				}
			}
		});
	}
	
	public void setSettingHighItemData(SettingHighItemData data){
		mData	 = data;
		name.setText(data.name);
		name.setTextColor(data.color);
	}

}
