package com.project.poom.requestsign;

import java.util.ArrayList;

import com.project.poom.requestsign.ReceiptItemView.OnItemClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ReceiptAdapter extends BaseAdapter 
	implements OnItemClickListener{

	ArrayList<ReceiptData> mItems = new ArrayList<ReceiptData>();
	Context mContext;
	
	public ReceiptAdapter(Context context){
		mContext=context;
	}
	
	public void add(ReceiptData data){
		mItems.add(data);
		notifyDataSetChanged();
	}
	
	public void remove(ReceiptData data){
		mItems.remove(data);
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
		ReceiptItemView v;
		if (convertView == null) {
			v = new ReceiptItemView(mContext);
			v.setOnItemClickListener(this);
		}else {
			v= (ReceiptItemView)convertView;
		}
		v.setReceiptItemData(mItems.get(position));
		return v;
	}

	public interface OnAdapterItemClickListener{
		public void onItemDelClick(View v, ReceiptData data);
	}
	
	OnAdapterItemClickListener mAdapterListener;
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener){
		mAdapterListener = listener;
	}

	@Override
	public void onDelBtnClick(View v, ReceiptData data) {
		if (mAdapterListener != null) {
			mAdapterListener.onItemDelClick(v, data);
		}
	}
}
