package com.project.poom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.project.poom.maintab1.MainTab1Fragment;
import com.project.poom.maintab2.MainTab2Fragment;

public class MainFragment extends Fragment {	

	ViewPager pager;
	TabHost mTabHost;
	TabsAdapter mAdapter;
	
	public static final String PARAM_CURRENT_TAB="currentTab";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_fragment, container, false);

		mTabHost=(TabHost)v.findViewById(R.id.tabhost);
		pager=(ViewPager)v.findViewById(R.id.pager);
		mTabHost.setup();
		
		final ViewPager pager=(ViewPager)v.findViewById(R.id.pager);
		
		mAdapter=new TabsAdapter(getActivity(), getChildFragmentManager(), mTabHost, pager);
		mAdapter.addTab(mTabHost.newTabSpec("tab1").setIndicator(""), MainTab1Fragment.class, null);
		mAdapter.addTab(mTabHost.newTabSpec("tab2").setIndicator(""), MainTab2Fragment.class, null);
		
		int width = getActivity().getResources().getDimensionPixelSize(R.dimen.tab_width);
		int height = getActivity().getResources().getDimensionPixelSize(R.dimen.tab_height);
		
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.main_tab1);
		mTabHost.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(width,height));
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.main_tab2);
		mTabHost.getTabWidget().getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(width,height));
		
		if (savedInstanceState != null) {
			mAdapter.onRestoreInstanceState(savedInstanceState);
			mTabHost.setCurrentTabByTag(savedInstanceState.getString(PARAM_CURRENT_TAB));
		}
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAdapter.onSavedInstanceState(outState);
		outState.putString(PARAM_CURRENT_TAB, mTabHost.getCurrentTabTag());
	}

}
