package com.project.poom.detailstory.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.manager.CallStory;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;

public class DtailStoryTab1BreakdownDialog extends DialogFragment {

	TextView goal_fund;
	ListView bd_list;
	Button bd_dismiss;
	int width, height, story_id;
	BDAdapter mAdapter;

	
	public DtailStoryTab1BreakdownDialog(int story_id) {
		this.story_id = story_id;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.breakdown_dialog_fragment, container, false);
		bd_list = (ListView)v.findViewById(R.id.bd_list);
		mAdapter = new BDAdapter(getActivity());
		bd_list.setAdapter(mAdapter);
		bd_list.setDivider(null);
		goal_fund = (TextView)v.findViewById(R.id.myinterest_item_view_content);
		NetworkManager.getInstnace().callStory(getActivity(), story_id, 
				UserManager.getInstance().getUserId(), new OnResultListener<CallStory>() {
					
					@Override
					public void onSuccess(CallStory result) {
						String temp = result.result.story.get(0).goal_fund+"";
						if (temp.length() > 6) {
							int index = temp.length()%3;
							StringBuffer sb = new StringBuffer(temp);
							sb.insert(index, ",");
							index+=4;
							sb.insert(index, ",");
							goal_fund.setText(sb.toString());
						}else if (temp.length() > 3) {
							int index = temp.length()%3+3;
							StringBuffer sb = new StringBuffer(temp);
							sb.insert(index, ",");
							goal_fund.setText(sb.toString());
						}else{
							goal_fund.setText(result.result.story.get(0).goal_fund+"");
						}
						
						String[] plan = (result.result.story.get(0).plan+"").split("/");
						for (int i = 0; i < plan.length; i++) {
							String[] sep = plan[i].split("-");
							BDData data = new BDData();
							data.category = sep[0];
							data.cost = sep[1];
							mAdapter.add(data);
						}
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "fail : "+getClass().toString(), Toast.LENGTH_SHORT).show();
					}
				});
		
		bd_dismiss = (Button)v.findViewById(R.id.bd_dismiss);
		bd_dismiss.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		return v;
	}

}
