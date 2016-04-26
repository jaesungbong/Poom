package com.project.poom.alarm;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AlarmAdapter extends BaseAdapter {

	ArrayList<AlarmData> mItems=new ArrayList<AlarmData>();
	Context mContext;
	
	public AlarmAdapter(Context context){
		mContext=context;
	}
	
	public void add(AlarmData data){
		mItems.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<AlarmData> items){
		mItems.addAll(items);
		notifyDataSetChanged();
	}
	
	public void clear(){
		mItems.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AlarmItemView v;
		if (convertView == null) {
			v=new AlarmItemView(mContext);
		}else {
			v=(AlarmItemView)convertView;
		}
		v.setAlarmItemData(mItems.get(position));
		return v;
	}
}
