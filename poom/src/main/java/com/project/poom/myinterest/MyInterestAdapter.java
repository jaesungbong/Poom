package com.project.poom.myinterest;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyInterestAdapter extends BaseAdapter {

	ArrayList<MyInterestItemData> items = new ArrayList<MyInterestItemData>();
	Context mContext;
	
	public MyInterestAdapter(Context context){
		mContext = context;
	}
	
	public void add(MyInterestItemData data){
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<MyInterestItemData> items){
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
	public MyInterestItemData getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyInterestItemView v;
		
		if(convertView == null){
			v= new MyInterestItemView(mContext);
		} else {
			v = (MyInterestItemView)convertView;
		}
		v.setMyInterestItemData(items.get(position));
		return v;
	}

}
