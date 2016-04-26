package com.project.poom.badge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.project.poom.R;
import com.project.poom.manager.BadgeList;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;

public class BadgeActivity extends ActionBarActivity {

	GridView gridView;
	BadgeAdapter mAdapter;
	int user_id;
	
	public final static String PARAM_RESULT = "result";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_badge);
	    
	    Intent intent = getIntent();
		user_id=intent.getIntExtra(PARAM_RESULT, 1);
		
	    gridView=(GridView)findViewById(R.id.activity_badge_gridview);
	    mAdapter = new BadgeAdapter(BadgeActivity.this);
	    gridView.setAdapter(mAdapter);
	    gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(BadgeActivity.this, Medalpopup.class);
				i.putExtra(Medalpopup.MEDALPOPUP, user_id);
				i.putExtra(Medalpopup.POSITION, position);
				startActivity(i);
			}
		});
	    initData();
	}
	
	private void initData(){
		NetworkManager.getInstnace().badgeList(BadgeActivity.this, user_id, new OnResultListener<BadgeList>() {
			
			@Override
			public void onSuccess(BadgeList result) {
				mAdapter.clear();
				mAdapter.addAll(result.result);
			}
			
			@Override
			public void onFail(int code) {
				Toast.makeText(BadgeActivity.this, "훈장 불러오기 오류", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
