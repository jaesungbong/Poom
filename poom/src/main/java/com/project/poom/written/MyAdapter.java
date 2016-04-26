package com.project.poom.written;

import java.util.ArrayList;

import com.project.poom.donationact.DonationActMonthly;
import com.project.poom.donationact.DonationActMonthlyData;
import com.project.poom.myinterest.MyInterestItemData;
import com.project.poom.myinterest.MyInterestItemView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter {

	Context mContext;
	ArrayList<DonationActMonthlyData> mItems = new ArrayList<DonationActMonthlyData>();
	public MyAdapter(Context context) {
		mContext = context;
	}
	
	public void put(DonationActMonthlyData data, MyInterestItemData childdata) {
		DonationActMonthlyData item = null;
		for (DonationActMonthlyData id : mItems) {
			if (id.month.equals(data.month)) {
				item = id;
				break;
			}
		}
		if (item == null) {
			item = new DonationActMonthlyData();
			item.month = data.month;
			item.strMonth = data.strMonth;
			item.year = data.year;
			mItems.add(item);
		}
		item.iitems.add(childdata);
		notifyDataSetChanged();
	}
	
	public void putAll(ArrayList<DonationActMonthlyData> mItems){
		this.mItems.addAll(mItems);
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		return mItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		DonationActMonthlyData item = mItems.get(groupPosition);
		return item.iitems.size();
	}

	@Override
	public String getGroup(int groupPosition) {
		DonationActMonthlyData item = mItems.get(groupPosition);
		return item.month;
	}

	@Override
	public MyInterestItemData getChild(int groupPosition, int childPosition) {
		DonationActMonthlyData item = mItems.get(groupPosition);
		return item.iitems.get(childPosition);
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
		DonationActMonthly v;
		if (convertView == null) {
			v = new DonationActMonthly(mContext);	
		} else {
			v = (DonationActMonthly)convertView;
		}
		v.setDonationActMonthlyData(mItems.get(groupPosition));
		
		return v;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		MyInterestItemView v;
		if (convertView == null) {
			v = new MyInterestItemView(mContext);
		} else {
			v = (MyInterestItemView)convertView;
		}
		v.setMyInterestItemData(mItems.get(groupPosition).iitems.get(childPosition));

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
