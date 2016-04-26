package com.project.poom.detailstory.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.detailstory.tab2.comment.NewsPopUpAdapter;
import com.project.poom.manager.CallUserInfo;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.ViewNews;
import com.project.poom.profile.ProfileActivity;

public class NewsPopUpDialog extends DialogFragment {
	
	ListView listView;
	Button btn;
	ImageView close, user_img;
	TextView nick, date, content;
	NewsPopUpAdapter mAdapter;
	DisplayImageOptions options;
	int news_id, user_id;
	
	public static final String NEWS_ID = "news_id";
	public static final String USER_ID = "user_ud";
	
	public NewsPopUpDialog(int news_id){
		this.news_id = news_id;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Bundle b2 = getArguments();
		user_id = b2.getInt(USER_ID);
		
		View v = inflater.inflate(R.layout.news_pop_up_fragment_header, container, false);
		close = (ImageView)v.findViewById(R.id.newspopup_close);
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		listView = (ListView)v.findViewById(R.id.newspopup_list);
		mAdapter = new NewsPopUpAdapter(getActivity());
		listView.setAdapter(mAdapter);
		user_img = (ImageView)v.findViewById(R.id.newspopup_user_img);
		user_img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),ProfileActivity.class);
				int profile_id = user_id;
				i.putExtra(ProfileActivity.PROFILE_ID, profile_id);
				startActivity(i);
			}
		});
		nick = (TextView)v.findViewById(R.id.newspopup_name);
		date = (TextView)v.findViewById(R.id.newspopup_enroll_date);
		content = (TextView)v.findViewById(R.id.newspopup_con);
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.login_graphic)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		NetworkManager.getInstnace().callUserInfo(getActivity(), user_id, new OnResultListener<CallUserInfo>() {

			@Override
			public void onSuccess(CallUserInfo result) {
				ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"
						+result.result.get(0).image, user_img, options);
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "회원사진 가져오기 오류", Toast.LENGTH_SHORT).show();
			}
		});
		
		NetworkManager.getInstnace().viewNews(getActivity(), news_id, new OnResultListener<ViewNews>() {
			
			@Override
			public void onSuccess(ViewNews result) {
				Log.i("news_id", news_id+"");
				nick.setText(result.result.news.get(0).nick);
				
				String temp = result.result.news.get(0).enroll_date;
				String year, month, day, time, ampm;
				year = temp.substring(0, 4);
				month = temp.substring(5, 7);
				day = temp.substring(8, 10);
				time = temp.substring(11, 16);
				if (Integer.parseInt(time.substring(0, 2)) > 12 && Integer.parseInt(time.substring(3))>1) {
					ampm = "오후";
				}else {
					ampm = "오전";
				}
				StringBuilder sb = new StringBuilder();
				sb.append(year).append(".").append(month).append(".").append(day).append(" "+ampm+" "+time);
				date.setText(sb.toString());
				
				content.setText(result.result.news.get(0).content);
				mAdapter.clear();
				mAdapter.addAll(result.result.images);
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(getActivity(), "소식가져오기 오류", Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}

}
