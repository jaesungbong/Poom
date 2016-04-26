package com.project.poom.detailstory.tab2;

import java.util.ArrayList;

import com.project.poom.detailstory.tab2.DtailStoryTab2ViewTop.OnItemDataClickListener;
import com.project.poom.maintab2.MainTab2Data;
import com.project.poom.maintab2.MainTab2View;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DtailStoryTab2Adapter extends BaseAdapter implements
		OnItemDataClickListener {

	public static final int ITEM_TYPE_COUNT = 2;
	public static final int VIEW_TYPE_TOP = 0;
	public static final int VIEW_TYPE_NORMAL = 1;
	ArrayList<DtailStoryTab2Data> items = new ArrayList<DtailStoryTab2Data>();
	Context mContext;
	int writtenuser_id;

	public DtailStoryTab2Adapter(Context context) {
		mContext = context;
	}

	public void add(DtailStoryTab2Data data) {
		items.add(data);
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<DtailStoryTab2Data> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void remove(DtailStoryTab2Data data) {
		items.remove(data);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public DtailStoryTab2Data getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return ITEM_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		int type = items.get(position).getType();
		if (type == DtailStoryTab2Data.DATA_TYPE_TOP) {
			return VIEW_TYPE_TOP;
		} else if (type == DtailStoryTab2Data.DATA_TYPE_NORMAL) {
			return VIEW_TYPE_NORMAL;
		}
		return VIEW_TYPE_NORMAL;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DtailStoryTab2View v;
		if (convertView == null) {
			v = new DtailStoryTab2View(mContext);
		} else {
			v = (DtailStoryTab2View) convertView;
		}

		v.setDtailStoryTab2ViewData(items.get(position));
		return v;
	}

	public interface OnAdapterItemClickListener {
		public void onItembtnClick(View v, DtailStoryTab2Data data);
	}

	OnAdapterItemClickListener mAdapterListener;

	public void setOnAdapterItemClickListener(
			OnAdapterItemClickListener listener) {
		mAdapterListener = listener;
	}

	@Override
	public void onBtnClick(View v, DtailStoryTab2Data data) {
		if (mAdapterListener != null) {
			mAdapterListener.onItembtnClick(v, data);
		}
	}

}
