package com.project.poom.detailstory.tab2.comment;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NewsPopUpAdapter extends BaseAdapter {
	
	ArrayList<NewsPopUpData> items = new ArrayList<NewsPopUpData>();
	Context mContext;
	
	public NewsPopUpAdapter(Context context)
	{
		mContext = context;
	}
	
	public void add(NewsPopUpData data)
	{
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<NewsPopUpData> items)
	{
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void remove(NewsPopUpData data) {
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
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsPopUpView  v;
		
		if(convertView == null){
			v = new NewsPopUpView(mContext);
		} else {
			v = (NewsPopUpView)convertView;
		}
		v.setMyData(items.get(position));
		return v;
	}
}
