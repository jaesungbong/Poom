package com.project.poom.detailstory.tab1;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BDAdapter extends BaseAdapter {

	ArrayList<BDData> mItems = new ArrayList<BDData>();
	Context mContext;
	
	public BDAdapter(Context context){
		mContext = context;
	}
	
	public void add(BDData data){
		mItems.add(data);
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
		BDView v;
		if (convertView == null) {
			v = new BDView(mContext);
		}else {
			v = (BDView)convertView;
		}
		v.setBDData(mItems.get(position));
		return v;
	}

}
