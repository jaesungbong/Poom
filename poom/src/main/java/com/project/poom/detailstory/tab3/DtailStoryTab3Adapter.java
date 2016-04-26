package com.project.poom.detailstory.tab3;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DtailStoryTab3Adapter extends BaseAdapter implements DtailStoryTab3ItemView.OnProfileClickListener {

	ArrayList<DtailStoryTab3Data> items  = new ArrayList<DtailStoryTab3Data>();
	Context mContext;
	
	public DtailStoryTab3Adapter(Context context)
	{
		mContext = context;
	}
	
	public void add(DtailStoryTab3Data data){
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<DtailStoryTab3Data> items) {
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
	public DtailStoryTab3Data getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DtailStoryTab3ItemView v;
		
		if(convertView == null)
		{
			v = new DtailStoryTab3ItemView(mContext);
			v.setOnprofileClickListener(this);
		} else {
			v = (DtailStoryTab3ItemView)convertView;
		}
		
		v.setDtailStoryTab3Data(items.get(position));
		return v;
	}
	
	public interface OnAdapterProfileClickListener{
		public void onItemProfileClick(View v,DtailStoryTab3Data data);
	}
	
	OnAdapterProfileClickListener mListener;
	
	public void setOnAdapterProfileClickListener(OnAdapterProfileClickListener listener){
		mListener = listener;
	}

	@Override
	public void onProfileClick(View v, DtailStoryTab3Data data) {
		if(mListener !=null){
			mListener.onItemProfileClick(v, data);
		}
	}

}
