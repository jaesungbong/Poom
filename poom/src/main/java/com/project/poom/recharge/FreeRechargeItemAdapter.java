package com.project.poom.recharge;

import java.util.ArrayList;

import com.project.poom.maintab2.MainTab2Data;
import com.project.poom.maintab2.MainTab2View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FreeRechargeItemAdapter extends BaseAdapter {
	
	ArrayList<FreeRechargeItemData> items = new ArrayList<FreeRechargeItemData>();
	Context mContext;

	public FreeRechargeItemAdapter(Context context){
		mContext = context;
	}
	
	public void add(FreeRechargeItemData data) {
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<FreeRechargeItemData> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void remove(FreeRechargeItemData data) {
		items.remove(data);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public FreeRechargeItemData getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FreeRechargeItemView v;
		
		if (convertView == null) {
			v = new FreeRechargeItemView(mContext);
		} else {
			v = (FreeRechargeItemView)convertView;
		}
		
		v.setFreeRechargeItemData(items.get(position));
		return v;
	}

}
