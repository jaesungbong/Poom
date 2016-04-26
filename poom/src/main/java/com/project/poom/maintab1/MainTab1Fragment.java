package com.project.poom.maintab1;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.project.poom.detailstory.DetailStoryActivity;
import com.project.poom.manager.CallStoryList;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;

public class MainTab1Fragment extends PagerFragment {
	
	ListView listView;
	MainTab1ViewAdapter mAdapter;
	MainTab1Data mData;
	ImageButton sortbtn;
	boolean btncheck = false;
	int ordertype = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter=new MainTab1ViewAdapter(getActivity());
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume() {
		Log.i("ordertype", "resume: "+ordertype);
		if (ordertype == 1) {
			sortbtn.setBackgroundResource(R.drawable.main_btn_deadline);
		}else {
			sortbtn.setBackgroundResource(R.drawable.main_btn_timeline);
		}
		NetworkManager.getInstnace().callStoryList(getActivity(), ordertype,new OnResultListener<CallStoryList>() {
			@Override
			public void onSuccess(CallStoryList result) {
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

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.i("ordertype", "createview: "+ordertype);
		View v=inflater.inflate(R.layout.main_tab1_layout,container, false);
		listView=(ListView)v.findViewById(R.id.maintab1list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i=new Intent(getActivity(),DetailStoryActivity.class );
				int story_id=mAdapter.mItems.get(position).id;
				i.putExtra(DetailStoryActivity.DTSA, story_id);
				startActivity(i);
			}
		});	
		
		NetworkManager.getInstnace().callStoryList(getActivity(), ordertype,new OnResultListener<CallStoryList>() {
			
			@Override
			public void onSuccess(CallStoryList result) {
				mAdapter.clear();
				mAdapter.addAll(result.result);
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
		
		sortbtn = (ImageButton)v.findViewById(R.id.main_tab1_sort_btn);
		sortbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { // dday 적게 남은 순으로 정렬
				if (ordertype == 1) {
					NetworkManager.getInstnace().callStoryList(getActivity(), 2,new OnResultListener<CallStoryList>() {
						
						@Override
						public void onSuccess(CallStoryList result) {
							Log.i("ordertype", "click1: "+ordertype);
							ordertype =2;
							mAdapter.clear();
							mAdapter.addAll(result.result);
							Log.i("ordertype", "click1-1: "+ordertype);
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
						}
					});
					sortbtn.setBackgroundResource(R.drawable.main_btn_timeline);
					btncheck = true;
				}else if(ordertype ==2){
					NetworkManager.getInstnace().callStoryList(getActivity(), 1,new OnResultListener<CallStoryList>() {
						
						@Override
						public void onSuccess(CallStoryList result) {
							Log.i("ordertype", "click2: "+ordertype);
							ordertype =1;
							mAdapter.clear();
							mAdapter.addAll(result.result);
							Log.i("ordertype", "click2-1: "+ordertype);
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
}
