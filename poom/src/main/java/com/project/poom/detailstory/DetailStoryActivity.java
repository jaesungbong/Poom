package com.project.poom.detailstory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.detailstory.tab1.DtailStoryTab1Fragment;
import com.project.poom.detailstory.tab2.DtailStoryTab2Fragment;
import com.project.poom.detailstory.tab3.DtailStoryTab3Fragment;
import com.project.poom.menufragments.MenuMyrechargeFragment;


public class DetailStoryActivity extends ActionBarActivity {
	
	private Fragment mContent;
	ViewPager pager;
	TabHost mTabHost;
	DetailStoryTabsAdapter mAdapter;
	public static final String PARAM_CURRENT_TAB="currentTab";
	public static final String DTSA = "story_id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.dtail_content_frame);
	    mTabHost=(TabHost)findViewById(R.id.subtabhost);
	    pager=(ViewPager)findViewById(R.id.subpager);
	    mTabHost.setup();
	    
	    final ViewPager pager=(ViewPager)findViewById(R.id.subpager);

	    Intent intent=getIntent();
	    int story_id = intent.getIntExtra(DTSA, 1);
	    
	    Bundle b= new Bundle();
	    b.putInt(DtailStoryTab1Fragment.DAT1F, story_id);
	    
	    Bundle b2= new Bundle();
	    b2.putInt(DtailStoryTab2Fragment.DAT2F, story_id);
	    
	    Bundle b3= new Bundle();
	    b3.putInt(DtailStoryTab3Fragment.DST3F, story_id);
	    
	    mAdapter=new DetailStoryTabsAdapter(this,getSupportFragmentManager() , mTabHost, pager);
	    mAdapter.addTab(mTabHost.newTabSpec("subtab1").setIndicator(""), DtailStoryTab1Fragment.class, b);
	    mAdapter.addTab(mTabHost.newTabSpec("subtab2").setIndicator(""), DtailStoryTab2Fragment.class, b2);
	    mAdapter.addTab(mTabHost.newTabSpec("subtab3").setIndicator(""), DtailStoryTab3Fragment.class, b3);
	    
	    int width = getResources().getDimensionPixelSize(R.dimen.ds_tab_width);
	    int height = getResources().getDimensionPixelSize(R.dimen.ds_tab_height);
	    
	    mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.story_tab1);
	    mTabHost.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(width, height));
	    mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.story_tab2);
	    mTabHost.getTabWidget().getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(width, height));
	    mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.story_tab3);
	    mTabHost.getTabWidget().getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(width, height));
	    
	    if (savedInstanceState != null) {
			mAdapter.onRestoreInstanceState(savedInstanceState);
			mTabHost.setCurrentTabByTag(savedInstanceState.getString(PARAM_CURRENT_TAB));
		}	
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	    getSupportActionBar().setCustomView(R.layout.actionbar_donate);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}
}
