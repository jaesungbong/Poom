package com.project.poom.maintab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.project.poom.PagerFragment;
import com.project.poom.R;
import com.project.poom.detailsign.DetailSignActivity;
import com.project.poom.manager.CallSignList;
import com.project.poom.manager.CallStoryList;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;

public class MainTab2Fragment extends PagerFragment {
	
	ListView listView;
	MainTab2ViewAdapter mAdapter;
	MainTab2Data mData;
	ImageButton sortbtn;
	boolean btncheck;
	int ordertype = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.main_tab2_layout, container, false);
		listView = (ListView)v.findViewById(R.id.signlist);
		mAdapter = new MainTab2ViewAdapter(getActivity());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i=new Intent(getActivity(),DetailSignActivity.class );
				int story_id=mAdapter.items.get(position).id;
				i.putExtra(DetailSignActivity.DETAILSIGNTAG, story_id);
				startActivity(i);
			}
		});
		
		NetworkManager.getInstnace().callSignList(getActivity(), ordertype,new OnResultListener<CallSignList>() {
			
			@Override
			public void onSuccess(CallSignList result) {
				mAdapter.clear();
				mAdapter.addAll(result.result);
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
		
		sortbtn = (ImageButton)v.findViewById(R.id.main_tab2_sort_btn);
		sortbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ordertype == 1) {
					NetworkManager.getInstnace().callSignList(getActivity(), 2, new OnResultListener<CallSignList>() {
						
						@Override
						public void onSuccess(CallSignList result) {
							ordertype =2;
							mAdapter.clear();
							mAdapter.addAll(result.result);
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
						}
					});
					sortbtn.setBackgroundResource(R.drawable.main_btn_timeline);
					btncheck = true;
				}else if(ordertype == 2){
					NetworkManager.getInstnace().callSignList(getActivity(), 1,new OnResultListener<CallSignList>() {
						
						@Override
						public void onSuccess(CallSignList result) {
							ordertype =1;
							mAdapter.clear();
							mAdapter.addAll(result.result);
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
						}
					});
					sortbtn.setBackgroundResource(R.drawable.main_btn_deadline);
					btncheck = false;
				}
			}
		});
		
		return v;
	}
	
	@Override
	public void onResume() {
		if (ordertype == 1) {
			sortbtn.setBackgroundResource(R.drawable.main_btn_deadline);
		}else {
			sortbtn.setBackgroundResource(R.drawable.main_btn_timeline);
		}
		NetworkManager.getInstnace().callSignList(getActivity(), ordertype,new OnResultListener<CallSignList>() {
			
			@Override
			public void onSuccess(CallSignList result) {
				mAdapter.clear();
				mAdapter.addAll(result.result);
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
		super.onResume();
	}
}
