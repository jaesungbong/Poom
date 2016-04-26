package com.project.poom.setting;

import java.util.ArrayList;

import android.content.Context;
import android.provider.Contacts.SettingsColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter implements SettingHighItem.OnItemDataSwitcherListener {

	Context mContext;
	ArrayList<SettingHighItemData> mItems = new ArrayList<SettingHighItemData>();
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void put(SettingHighItemData data, SettingLowItemData childdata) {
		SettingHighItemData item = null;
		for (SettingHighItemData id : mItems) {
			if (id.name.equals(data.name)) {
				item = id;
				break;
			}
		}
		if (item == null) {
			item = new SettingHighItemData();
			item.name = data.name;
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
		SettingHighItemData item = mItems.get(groupPosition);
		return item.items.size();
	}

	@Override
	public String getGroup(int groupPosition) {
		SettingHighItemData item = mItems.get(groupPosition);
		return item.name;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		SettingHighItemData item = mItems.get(groupPosition);
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
		SettingHighItem v;
		if (convertView == null) {
			v = new SettingHighItem(mContext);
			v.setOnItemDataSwitcherLIstener(this);
		} else {
			v = (SettingHighItem)convertView;
		}
		v.setSettingHighItemData(mItems.get(groupPosition));
		
		return v;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		SettingLowItem v;
		if (convertView == null) {
			v = new SettingLowItem(mContext);
		} else {
			v = (SettingLowItem)convertView;
		}
		v.setSettingLowItemData(mItems.get(groupPosition).items.get(childPosition));
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
	
	public interface OnAdapterItemSwitcherListener{
		public void onItemSwitcher(View v, SettingHighItemData data);
		public void onItemOffSwitcher(View v, SettingHighItemData data);
	}
	
	OnAdapterItemSwitcherListener mAdapterListener;
	
	public void setOnAdapterItemSwitcherListener(OnAdapterItemSwitcherListener listener){
		mAdapterListener = listener;
	}
	
	@Override
	public void onSwitcher(View v, SettingHighItemData data) {
		if(mAdapterListener !=null){
			mAdapterListener.onItemSwitcher(v, data);
		}
	}

	@Override
	public void onOffSwitcher(View v, SettingHighItemData data) {
		if(mAdapterListener !=null){
			mAdapterListener.onItemOffSwitcher(v, data);
		}
		
	}

}
