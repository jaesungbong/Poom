package com.project.poom.maintab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainTab1ViewAdapter extends BaseAdapter {

	public ArrayList<MainTab1Data> mItems = new ArrayList<MainTab1Data>();
	Context mContext;

	public MainTab1ViewAdapter(Context context) {
		mContext = context;
	}

	public void add(MainTab1Data data) {
		mItems.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<MainTab1Data> datas){
		mItems.addAll(datas);
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
		MainTab1View v;
		if (convertView == null) {
			v = new MainTab1View(mContext);
		} else {
			v = (MainTab1View) convertView;
		}
		v.setMainTab1Data(mItems.get(position));
		return v;
	}
}
