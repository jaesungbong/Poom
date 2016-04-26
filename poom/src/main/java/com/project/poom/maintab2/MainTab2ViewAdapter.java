package com.project.poom.maintab2;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainTab2ViewAdapter extends BaseAdapter {

	ArrayList<MainTab2Data> items = new ArrayList<MainTab2Data>();
	Context mContext;
	
	public MainTab2ViewAdapter(Context context){
		mContext = context;
	}
	
	public void add(MainTab2Data data) {
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<MainTab2Data> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void remove(MainTab2Data data) {
		items.remove(data);
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
	public MainTab2Data getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainTab2View v;
		
		if (convertView == null) {
			v = new MainTab2View(mContext);
		} else {
			v = (MainTab2View)convertView;
		}		
		v.setMainTab2Data(items.get(position));
		return v;
	}

}
