package com.project.poom.detailstory.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.project.poom.PagerFragment;
import com.project.poom.R;
import com.project.poom.detailstory.tab2.DtailStoryTab2Adapter.OnAdapterItemClickListener;
import com.project.poom.energyshare.EnergyShare;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.CallStory;
import com.project.poom.manager.EnrollAttention;
import com.project.poom.manager.UserManager;
import com.project.poom.manager.ViewNewsList;
import com.project.poom.share.ShareDialogFragment;

public class DtailStoryTab2Fragment extends PagerFragment{

	ListView listView;
	DtailStoryTab2Adapter mAdapter;
	Button btn, enroll_btn;
	ImageView footmark,footmarking;
	int story_id, writtenuser_id, news_id, user_id;
	
	public static final String DAT2F = "story_id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Bundle b=getArguments();
		story_id=b.getInt(DAT2F);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.dtail_tab2_layout, container, false);
		
		initData();
		listView = (ListView)v.findViewById(R.id.newslist);
		footmark = (ImageView)v.findViewById(R.id.dtail_tab2_layout_footmark);
		footmark.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().enrollAttention(getActivity(), story_id, UserManager.getInstance().getUserId(), new OnResultListener<EnrollAttention>(){

					@Override
					public void onSuccess(EnrollAttention result) {
						footmark.setVisibility(View.GONE);
						footmarking.setVisibility(View.VISIBLE);
						Toast.makeText(getActivity(), "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
					}
						
				});
			}
		});
		footmarking = (ImageView)v.findViewById(R.id.dtail_tab2_layout_footmarking);
		footmarking.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().enrollAttention(getActivity(), story_id, UserManager.getInstance().getUserId(), new OnResultListener<EnrollAttention>(){

					@Override
					public void onSuccess(EnrollAttention result) {
						footmark.setVisibility(View.VISIBLE);
						footmarking.setVisibility(View.GONE);
						Toast.makeText(getActivity(), "즐겨찾기에 해제 되었습니다.", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
					}
						
				});				
			}
		});
		mAdapter = new DtailStoryTab2Adapter(getActivity());
		enroll_btn = (Button)v.findViewById(R.id.enroll_news);
		enroll_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(), NewsAlertActivity.class);
				i.putExtra(NewsAlertActivity.NEWS, story_id);
				startActivity(i);				
			}
		});
		
		listView.setAdapter(mAdapter);
		
		
		btn = (Button)v.findViewById(R.id.share2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				ShareDialogFragment f = new ShareDialogFragment(image_url,subject, contents);
//				f.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "dialog");
				
			}
		});
		
		btn=(Button)v.findViewById(R.id.share_energy2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EnergyShare f = new EnergyShare();
				f.show(getFragmentManager(), "dialog");
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				news_id = mAdapter.items.get(position).getNews_id();
				
				Bundle b2 = new Bundle();
				b2.putInt(NewsPopUpDialog.USER_ID,user_id);
				NewsPopUpDialog f = new NewsPopUpDialog(news_id);
				f.setArguments(b2);
				f.show(getFragmentManager(), "dialog");				
			}			
		});
		
		NetworkManager.getInstnace().callStory(getActivity(), story_id, UserManager.getInstance().getUserId(), new OnResultListener<CallStory>() {
			
			@Override
			public void onSuccess(CallStory result) {
				
				if(result.result.story.get(0).attend==0){
					footmark.setVisibility(View.VISIBLE);
				}else{
					footmarking.setVisibility(View.VISIBLE);
				}
			}
			
			@Override	
			public void onFail(int code) {
			}
		});
		return v;
	}
	
	private void initData(){
		NetworkManager.getInstnace().viewNewsList(getActivity(), story_id, new OnResultListener<ViewNewsList>() {
			
			@Override
			public void onSuccess(ViewNewsList result) {
				mAdapter.clear();
				if (result.result.size()>0) {
		               mAdapter.addAll(result.result);
		               if (UserManager.getInstance().getUserId() != result.result.get(0).user_id) {
		            	   enroll_btn.setVisibility(View.INVISIBLE);
		               }else {
		            	   enroll_btn.setVisibility(View.VISIBLE);
		               }
		               user_id = result.result.get(0).user_id;
		        }
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "뉴스불러오기 오류", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		initData();
	}
	
	@Override
	public void onPageCurrent() {
		NetworkManager.getInstnace().callStory(getActivity(), story_id, UserManager.getInstance().getUserId(), new OnResultListener<CallStory>() {

			@Override
			public void onSuccess(CallStory result) {
				if(result.result.story.get(0).attend==0){
					footmark.setVisibility(View.VISIBLE);
				}else{
					footmarking.setVisibility(View.VISIBLE);
				}
			}
			
			@Override	
			public void onFail(int code) {
			}
		});
		initData();
		super.onPageCurrent();
	}
}
