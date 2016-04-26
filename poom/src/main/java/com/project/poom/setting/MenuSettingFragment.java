package com.project.poom.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.poom.R;
import com.project.poom.manager.UserManager;
import com.project.poom.setting.LogOutDialog.onLogOutClickListener;

public class MenuSettingFragment extends Fragment {

	ExpandableListView listView;
	MyAdapter mAdapter;
	Button btn;
	LinearLayout logout;
	
	SettingLowItemData alarmList1,alarmList2,alarmList3,alarmList4;
	SettingLowItemData soundList;
	
	SettingHighItemData alarm;
	SettingHighItemData sound;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menu_setting2_fragment, container,
				false);
		listView = (ExpandableListView) v
				.findViewById(R.id.menu_setting2_fragment_expandablelist);
		mAdapter = new MyAdapter(getActivity());
		mAdapter.setOnAdapterItemSwitcherListener(new MyAdapter.OnAdapterItemSwitcherListener() {

			@Override
			public void onItemSwitcher(View v, SettingHighItemData data) {
				if(data.name == "알람 설정"){
					alarm.color = Color.BLACK;
					alarmList1.color = Color.BLACK;
					alarmList2.color = Color.BLACK;
					alarmList3.color = Color.BLACK;
					alarmList4.color = Color.BLACK;
					alarmList1.check = true;
					alarmList2.check = true;
					alarmList3.check = true;
					alarmList4.check = true;
				} else {
					sound.color = Color.BLACK;
					soundList.color = Color.BLACK;
					soundList.check = true;
				}
			}

			@Override
			public void onItemOffSwitcher(View v, SettingHighItemData data) {
				if(data.name == "알람 설정"){
					alarm.color = Color.GRAY;
					alarmList1.color = Color.GRAY;
					alarmList2.color = Color.GRAY;
					alarmList3.color = Color.GRAY;
					alarmList4.color = Color.GRAY;
					alarmList1.check = false;
					alarmList2.check = false;
					alarmList3.check = false;
					alarmList4.check = false;
				} else {
					sound.color=Color.GRAY;
					soundList.color = Color.GRAY;
					soundList.check = false;
				}
			}
		});

		listView.setAdapter(mAdapter);
		initData();
		if (UserManager.getInstance().isLogin()) {
			logout = (LinearLayout) v.findViewById(R.id.menu_setting_fragment_logout);
			logout.setVisibility(View.VISIBLE);
			logout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					LogOutDialog logout = new LogOutDialog();
					logout.setonLogOutClickListener(new onLogOutClickListener() {
						@Override
						public void onLogout() {
							getFragmentManager().popBackStackImmediate();
						}
					});
					logout.show(getFragmentManager(), "dialog");
				}
			});
		}
		btn =(Button)v.findViewById(R.id.menu_setting2_fragment_faq);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), FaqActivity.class);
				startActivity(i);
			}
		});
		return v;
	}

	private void initData() {
		alarm = new SettingHighItemData();
		alarm.name = "알람 설정";
		alarmList1 = new SettingLowItemData();
		alarmList2 = new SettingLowItemData();
		alarmList3 = new SettingLowItemData();
		alarmList4 = new SettingLowItemData();
		alarmList1.name = "내가 한 기부 새 소식";
		alarmList2.name = "관심있는 게시물 새 소식";
		alarmList3.name = "내가 한 기부 목록 완료";
		alarmList4.name = "내가 한 서명 서명 완료";

		sound = new SettingHighItemData();
		sound.name = "소리 설정";
		soundList = new SettingLowItemData();
		soundList.name = "진동";
		mAdapter.put(alarm, alarmList1);
		mAdapter.put(alarm, alarmList2);
		mAdapter.put(alarm, alarmList3);
		mAdapter.put(alarm, alarmList4);
		mAdapter.put(sound, soundList);
	}

}
