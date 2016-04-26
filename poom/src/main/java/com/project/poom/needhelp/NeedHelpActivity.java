package com.project.poom.needhelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.poom.R;

public class NeedHelpActivity extends Activity {

	ListView listView;
	NeedHelpAdapter mAdapter;
	NeedHelpData mData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.needhelp_layout);
	    mAdapter=new NeedHelpAdapter(this);
	    listView=(ListView)findViewById(R.id.nhlist);
	    listView.setAdapter(mAdapter);
	    initData();
	}

	private void initData(){
		for (int i = 0; i < 10; i++) {
			mData=new NeedHelpData();
			//mData.image=R.drawable.default_img;
			mData.address="수원시  팔달구 우만1동";
			mData.ing=""+i;
			mData.max=""+1000;
			mData.ratio=""+i;
			mData.subject="Subject "+i;
			mAdapter.add(mData);
		}
	}
}
