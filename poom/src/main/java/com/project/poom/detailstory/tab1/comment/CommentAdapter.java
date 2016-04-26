package com.project.poom.detailstory.tab1.comment;

import java.util.ArrayList;

import com.project.poom.detailstory.tab1.comment.CommentView.OnItemClickListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;

public class CommentAdapter extends BaseAdapter implements OnItemClickListener{
	
	public ArrayList<CommentData> items = new ArrayList<CommentData>();
	Context mContext;
	
	public CommentAdapter(Context context)
	{
		mContext = context;
	}
	
	public void add(CommentData data)
	{
		items.add(data);
		notifyDataSetChanged();
	}
	
	public void addAll(ArrayList<CommentData> items)
	{
		this.items.addAll(items);
		notifyDataSetChanged();
	}
	
	public void remove(CommentData data) {
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
		CommentView  v;
		
		if(convertView == null){
			v = new CommentView(mContext, items.get(position).getName());
			v.setOnItemClickListener(this);
		} else {
			v = (CommentView)convertView;
		}
		v.setMyData(items.get(position));
		return v;
	}
	
	public interface OnAdapterItemClickListener{
		public void onItemDelClick(View v, CommentData data);
		public void onProfileClick(View v, CommentData data);
	}
	
	OnAdapterItemClickListener mListener;
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener){
		mListener = listener;
	}

	@Override
	public void onDelClick(View v, CommentData data) {
		if (mListener != null) {
			mListener.onItemDelClick(v, data);
		}
	}
	
	@Override
	public void onProfileClick(View v, CommentData data) {
		if(mListener !=null){
			mListener.onProfileClick(v, data);
		}
	}
	
}
