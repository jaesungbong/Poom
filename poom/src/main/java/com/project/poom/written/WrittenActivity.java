package com.project.poom.written;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.detailsign.DetailSignActivity;
import com.project.poom.detailstory.DetailStoryActivity;
import com.project.poom.donationact.DonationActMonthlyData;
import com.project.poom.manager.MyStory;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.myinterest.MyInterestItemData;

public class WrittenActivity extends Activity {
	
	ExpandableListView listView;
	MyAdapter mAdapter;
	DonationActMonthlyData mData;
	MyInterestItemData mChildData;
	
	public static final String WRITTEN_ID = "written_id";
	public int written_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_written);
	    
		Intent intent = getIntent();
		written_id=intent.getIntExtra(WRITTEN_ID, 1);
		
	    listView = (ExpandableListView)findViewById(R.id.activity_written_list);
	    mAdapter = new MyAdapter(this);
	    
	    listView.setAdapter(mAdapter);


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
					int approve = mAdapter.getChild(groupPosition, childPosition).approve;
					if(approve==0){
						Intent i=new Intent(WrittenActivity.this,DetailSignActivity.class);
						int story_id=mAdapter.getChild(groupPosition, childPosition).id;
						i.putExtra(DetailSignActivity.DETAILSIGNTAG, story_id);
						startActivity(i);
					} else{
						Intent i=new Intent(WrittenActivity.this,DetailStoryActivity.class );
						int story_id=mAdapter.getChild(groupPosition, childPosition).id;
						i.putExtra(DetailStoryActivity.DTSA, story_id);
						startActivity(i);
					}

						
				return true;
			}
		});
	    
	    NetworkManager.getInstnace().myStory(WrittenActivity.this, written_id, new OnResultListener<MyStory>() {
			
			@Override
			public void onSuccess(MyStory result) {
				for(int i=0;i<result.result.size();i++){
					mData = new DonationActMonthlyData();
					mData.year=Integer.parseInt((result.result.get(i).enroll_date).substring(0, 4));
					mData.month=(result.result.get(i).enroll_date).substring(5, 7);
					mData.strMonth = initStrMonth(Integer.parseInt((result.result.get(i).enroll_date).substring(5, 7)));
					mChildData = (MyInterestItemData)result.result.get(i);
					mAdapter.put(mData, mChildData);
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(WrittenActivity.this, "fail", Toast.LENGTH_SHORT).show();
			}
		});
	    
	    for(int i = 0; i <mAdapter.getGroupCount(); i++){
	    	listView.expandGroup(i);
	    }
	    
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setCustomView(R.layout.actionbar_myedit);
	}
	
	private String initStrMonth(int i){
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
}
