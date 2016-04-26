package com.project.poom.detailstory.tab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.dp;
import com.project.poom.PagerFragment;
import com.project.poom.R;
import com.project.poom.detailstory.tab3.DtailStoryTab3Adapter.OnAdapterProfileClickListener;
import com.project.poom.manager.CallDonViewList;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.profile.ProfileActivity;

public class DtailStoryTab3Fragment extends PagerFragment {
	
	ListView listView;
	DtailStoryTab3Adapter mAdapter;
	TextView donator;
	int story_id;
	public static final String DST3F = "story_id";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Bundle b=getArguments();
		story_id=b.getInt(DST3F);
		View v= inflater.inflate(R.layout.dtail_tab3_layout, container, false);
		donator = (TextView)v.findViewById(R.id.dt3_donator);
		listView = (ListView)v.findViewById(R.id.donationbreakdownlist);
		mAdapter = new DtailStoryTab3Adapter(getActivity());
		mAdapter.setOnAdapterProfileClickListener(new OnAdapterProfileClickListener() {
			
			@Override
			public void onItemProfileClick(View v, DtailStoryTab3Data data) {
				Intent i = new Intent(getActivity(),ProfileActivity.class);
				int profile_id = data.id;
				i.putExtra(ProfileActivity.PROFILE_ID, profile_id);
				startActivity(i);
			}
		});
		listView.setAdapter(mAdapter);
		
		NetworkManager.getInstnace().callDonViewList(getActivity(), story_id, new OnResultListener<CallDonViewList>() {
			
			@Override
			public void onSuccess(CallDonViewList result) {
				if (result.result.size() > 0) {
					mAdapter.clear();
					mAdapter.addAll(result.result);
					donator.setText(result.result.get(0).getDonate_users()+"");
				}else {
					donator.setText("0");
				}				
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
		
		return v;
	}
}
