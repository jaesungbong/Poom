package com.project.poom.needhelp;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NeedHelpAdapter extends BaseAdapter {

	ArrayList<NeedHelpData> mItems=new ArrayList<NeedHelpData>();
	Context mContext;
	
	public NeedHelpAdapter(Context context){
		mContext=context;
	}
	
	public void add(NeedHelpData data){
		mItems.add(data);
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
		NeedHelpView v;
		if (convertView == null) {
			v=new NeedHelpView(mContext);
		}else {
			v=(NeedHelpView)convertView;
		}
		v.setNeedHelpData(mItems.get(position));
		return v;
	}

}
