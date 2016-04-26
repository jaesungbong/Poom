package com.project.poom.badge;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BadgeAdapter extends BaseAdapter {
	ArrayList<BadgeData> items  = new ArrayList<BadgeData>();
	Context mContext;
	
	public BadgeAdapter(Context context)
	{
		mContext = context;
	}
	
	public void add(BadgeData data){
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<BadgeData> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void clear(){
		items.clear();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public BadgeData getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BadgeItemView v;
		
		if(convertView == null){
			v = new BadgeItemView(mContext);
		} else {
			v = (BadgeItemView)convertView;
		}
		
		v.setBadgeData(items.get(position));
		return v;
	}
}
