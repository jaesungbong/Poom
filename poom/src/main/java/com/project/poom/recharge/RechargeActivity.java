package com.project.poom.recharge;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.project.poom.R;

public class RechargeActivity extends ActionBarActivity {
	
	ViewPager pager;
	TabHost tabHost;
	TabsAdapter mAdapter;
	public static final String PARAM_CURRENT_TAB = "currentTab";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_recharge);
	    tabHost = (TabHost)findViewById(R.id.tabhost);
	    pager = (ViewPager)findViewById(R.id.pager);
	    tabHost.setup();
		mAdapter = new TabsAdapter(this, getSupportFragmentManager(), tabHost, pager);
		
		mAdapter.addTab(tabHost.newTabSpec("free").setIndicator(""), FreeRechargeFragment.class, null);
		mAdapter.addTab(tabHost.newTabSpec("subscribe").setIndicator(""), SubscribeRecharge.class, null);

		int width = getResources().getDimensionPixelSize(R.dimen.recharge_width);
		int height = getResources().getDimensionPixelSize(R.dimen.recharge_height);
		
		tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.recharge_tab1);
		tabHost.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(width,height));
		tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.recharge_tab2);
		tabHost.getTabWidget().getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(width,height));
		
		if (savedInstanceState != null) {
			mAdapter.onRestoreInstanceState(savedInstanceState);
			tabHost.setCurrentTabByTag(savedInstanceState.getString(PARAM_CURRENT_TAB));
		}
		
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSupportActionBar().setCustomView(R.layout.actionbar_point);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {	
		super.onSaveInstanceState(outState);
		mAdapter.onSaveInstanceState(outState);
		outState.putString(PARAM_CURRENT_TAB, tabHost.getCurrentTabTag());
	}

}
