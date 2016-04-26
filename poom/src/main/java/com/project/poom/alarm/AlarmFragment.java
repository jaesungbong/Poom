package com.project.poom.alarm;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.NewSpeedLog;
import com.project.poom.manager.UserManager;

public class AlarmFragment extends Fragment {
	
	AlarmAdapter mAdapter;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter=new AlarmAdapter(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.alarm_fragment, container, false);
		listView=(ListView)v.findViewById(R.id.alarmlist);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlarmData d=(AlarmData) mAdapter.getItem(position);
				if(d.TYPE_STORY_NEWS==0){
					Toast.makeText(getActivity(), "사연 페이지 이동", Toast.LENGTH_SHORT).show();
				} else{
					Toast.makeText(getActivity(), "새소식 페이지 이동", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		NetworkManager.getInstnace().newSpeedLog(getActivity(), UserManager.getInstance().getUserId(), 
				new OnResultListener<NewSpeedLog>() {
					
					@Override
					public void onSuccess(NewSpeedLog result) {
						if (result.result.size()>0) {
							mAdapter.clear();
							mAdapter.addAll(result.result);
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "뉴스피드 가져오기 오류", Toast.LENGTH_SHORT).show();
					}
				});
		return v;
	}
}
