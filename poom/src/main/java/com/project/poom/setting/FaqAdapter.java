package com.project.poom.setting;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class FaqAdapter extends BaseExpandableListAdapter {

	Context mContext;
	ArrayList<FaqHighItemData> mItems = new ArrayList<FaqHighItemData>();
	public FaqAdapter(Context context) {
		mContext = context;
	}
	
	public void put(FaqHighItemData data, FaqLowItemData childdata) {
		FaqHighItemData item = null;
		for (FaqHighItemData id : mItems) {
			if (id.title.equals(data.title)) {
				item = id;
				break;
			}
		}
		if (item == null) {
			item = new FaqHighItemData();
			item.title = data.title;
			mItems.add(item);
		}
		item.items.add(childdata);
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		return mItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		FaqHighItemData item = mItems.get(groupPosition);
		return item.items.size();
	}

	@Override
	public String getGroup(int groupPosition) {
		FaqHighItemData item = mItems.get(groupPosition);
		return item.title;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		FaqHighItemData item = mItems.get(groupPosition);
		return item.items.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return (((long)groupPosition) << 32 | ((long)childPosition));
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		FaqHighItem v;
		if (convertView == null) {
			v = new FaqHighItem(mContext);
		} else {
			v = (FaqHighItem)convertView;
		}
		v.setFaqHighItemData(mItems.get(groupPosition));
		
		return v;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		FaqLowItem v;
		if (convertView == null) {
			v = new FaqLowItem(mContext);
		} else {
			v = (FaqLowItem)convertView;
		}
		v.setFaqLowItemData(mItems.get(groupPosition).items.get(childPosition));
		return v;
	}

	
	@Override
	public int getGroupTypeCount() {
		return super.getGroupTypeCount();
	}
	
	@Override
	public int getGroupType(int groupPosition) {
		// TODO Auto-generated method stub
		return super.getGroupType(groupPosition);
	}
	
	@Override
	public int getChildTypeCount() {
		// TODO Auto-generated method stub
		return super.getChildTypeCount();
	}
	
	@Override
	public int getChildType(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return super.getChildType(groupPosition, childPosition);
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
