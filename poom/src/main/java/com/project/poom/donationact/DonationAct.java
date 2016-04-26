package com.project.poom.donationact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.detailsign.DetailSignActivity;
import com.project.poom.detailstory.DetailStoryActivity;
import com.project.poom.manager.CallDonActList;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.written.WrittenActivity;

public class DonationAct extends Activity {

	ExpandableListView listView;
	MyAdapter mAdapter;
	DonationActMonthlyData mData;
	DonationActMonthlyItemData mChildData;
	public static final String DONATE_ID = "donate_id";
	public int donate_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.donation_act_activity);
	    
		Intent intent = getIntent();
		donate_id=intent.getIntExtra(DONATE_ID, 1);
		
	    listView= (ExpandableListView)findViewById(R.id.donation_act_activity_monthlylist);
	    mAdapter = new MyAdapter(this);
	    listView.setAdapter(mAdapter);

	    for(int i = 0; i <mAdapter.getGroupCount(); i++){
	    	listView.expandGroup(i);
	    }
//	    listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//			
//			@Override
//			public void onGroupCollapse(int groupPosition) {
//				listView.expandGroup(groupPosition);
//			}
//		});
	    
	    listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				int type = mAdapter.getChild(groupPosition, childPosition).type;
				if(type==1){
					Intent i=new Intent(DonationAct.this,DetailStoryActivity.class );
					int story_id=mAdapter.getChild(groupPosition, childPosition).story_id;
					i.putExtra(DetailStoryActivity.DTSA, story_id);
					startActivity(i);
				}else{
					Intent i=new Intent(DonationAct.this,DetailSignActivity.class);
					int story_id=mAdapter.getChild(groupPosition, childPosition).story_id;
					i.putExtra(DetailSignActivity.DETAILSIGNTAG, story_id);
					startActivity(i);
				}
				return true;
			}
		});
	    NetworkManager.getInstnace().callDonActList(DonationAct.this, donate_id,new OnResultListener<CallDonActList>(	) {
			
			@Override
			public void onSuccess(CallDonActList result) {
				for(int i=0;i<result.result.size(); i++){
					mData = new DonationActMonthlyData();
					mChildData = new DonationActMonthlyItemData();
					mData.month = result.result.get(i).date.substring(5,7);
					mData.strMonth = initStrMonth(Integer.parseInt(result.result.get(i).date.substring(5,7)));
					mData.year = Integer.parseInt(result.result.get(i).date.substring(0,4));
					mChildData = (DonationActMonthlyItemData)result.result.get(i);
					mAdapter.put(mData, mChildData);
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(DonationAct.this, "fail", Toast.LENGTH_SHORT).show();
			}
		});
	    
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setCustomView(R.layout.actionbar_donation_act);
	    
	}

	private String initStrMonth(int i) {
		switch (i) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return null;
	}
	
	@Override
	protected void onResume() {
	    for(int i = 0; i <mAdapter.getGroupCount(); i++){
	    	listView.expandGroup(i);
	    }
		super.onResume();
	}
}
