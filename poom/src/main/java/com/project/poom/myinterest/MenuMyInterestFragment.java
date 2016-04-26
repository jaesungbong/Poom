package com.project.poom.myinterest;

import android.content.Intent;
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
import com.project.poom.detailstory.DetailStoryActivity;
import com.project.poom.manager.MyAttention;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;

public class MenuMyInterestFragment extends Fragment {
	
	ListView listView;
	MyInterestAdapter mAdapter;
	MyInterestItemData mData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menu_myinterest_fragment, container,false);
		listView = (ListView)v.findViewById(R.id.menu_myinterest_fragment_list);
		mAdapter = new MyInterestAdapter(getActivity());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i=new Intent(getActivity(),DetailStoryActivity.class );
					int story_id=mAdapter.items.get(position).id;
					i.putExtra(DetailStoryActivity.DTSA, story_id);
					startActivity(i);
				}
		});
		
		NetworkManager.getInstnace().myAttention(getActivity(), UserManager.getInstance().getUserId(), new OnResultListener<MyAttention>() {
			
			@Override
			public void onSuccess(MyAttention result) {
				mData = new MyInterestItemData();
				for(int i=0; i<result.result.size();i++){
					mData = (MyInterestItemData)result.result.get(i);
					mAdapter.add(mData);
				}
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "즐겨찾기 불러오기 실패", Toast.LENGTH_SHORT).show();
			}
		});
		
		return v;
	}
}

