package com.project.poom;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

public class TabsAdapter extends FragmentPagerAdapter implements
		OnTabChangeListener, OnPageChangeListener {
	
	private final Context mContext;
	private final TabHost mTabHost;
	private final ViewPager mViewPager;
	private final FragmentManager mFragmentManager;
	private final ArrayList<TabInfo> mTabs=new ArrayList<TabInfo>();
	private final static String FIELD_KEY_PREFIX="tabinfo";
	private final static int MESSAGE_PAGE_CURRENT=1;
	OnTabChangeListener mTabChangeListener;
	OnPageChangeListener mPageChangeListener;
	boolean isFirst=true;
	private final static int FIRST_DELAY_INTERVAL = 20;
	
	Handler mHandler=new Handler(){
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_PAGE_CURRENT:
				int position=msg.arg1;
				Fragment f=getTabFragment(position);
				if (f != null && f instanceof PagerFragment) {
					((PagerFragment)f).onPageCurrent();
				}
			}
		}
	};
	
	ArrayList<Message> delayedMessage=new ArrayList<Message>();
	
	static final class TabInfo{
		private final String tag;
		private final Class<?> clss;
		private final Bundle args;
		private Fragment mFragment;
		
		TabInfo(String _tag, Class<?> _class, Bundle _args){
			tag=_tag;
			clss=_class;
			args=_args;
		}
	}
	
	static class DummyTabFactory implements TabHost.TabContentFactory{
		private final Context mContext;
		
		public DummyTabFactory(Context context){
			mContext=context;
		}

		@Override
		public View createTabContent(String tag) {
			View v=new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}
	
	public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager){
		this(activity, activity.getSupportFragmentManager(), tabHost, pager);
	}
	
	public TabsAdapter(Context context, FragmentManager fragmentManager, TabHost tabHost, ViewPager pager){
		super(fragmentManager);
		mContext=context;
		mFragmentManager=fragmentManager;
		mTabHost=tabHost;
		mViewPager=pager;
		mTabHost.setOnTabChangedListener(this);
		mViewPager.setAdapter(this);
		mViewPager.setOnPageChangeListener(this);
	}
	
	public void onRestoreInstanceState(Bundle savedInstanceState){
		for (TabInfo info : mTabs) {
			String keyfield=FIELD_KEY_PREFIX+info.tag;
			info.mFragment=mFragmentManager.getFragment(savedInstanceState, keyfield);
		}
	}
	
	public void onSavedInstanceState (Bundle outState){
		for (TabInfo info : mTabs) {
			String keyfield=FIELD_KEY_PREFIX+info.tag;
			if (info.mFragment != null) {
				mFragmentManager.putFragment(outState, keyfield, info.mFragment);
			}
		}
	}
	
	public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args){
		tabSpec.setContent(new DummyTabFactory(mContext));
		String tag=tabSpec.getTag();
		
		TabInfo info=new TabInfo(tag, clss, args);
		mTabs.add(info);
		notifyDataSetChanged();
		mTabHost.addTab(tabSpec);
	}	

	@Override
	public void onPageScrollStateChanged(int state) {
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageScrollStateChanged(state);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position) {
		TabWidget widget=mTabHost.getTabWidget();
		int oldFocusability=widget.getDescendantFocusability();
		widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		mTabHost.setCurrentTab(position);
		widget.setDescendantFocusability(oldFocusability);
		if (mPageChangeListener != null) {
			mPageChangeListener.onPageSelected(position);
		}
	}

	@Override
	public void onTabChanged(String tabId) {
		int position=mTabHost.getCurrentTab();
		mViewPager.setCurrentItem(position);
		if (mTabChangeListener != null) {
			mTabChangeListener.onTabChanged(tabId);
		}
		mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_PAGE_CURRENT,position,0), FIRST_DELAY_INTERVAL);
	}

	@Override
	public Fragment getItem(int position) {
		TabInfo info=mTabs.get(position);
		info.mFragment=Fragment.instantiate(mContext, info.clss.getName(),info.args);
		return info.mFragment;
	}
	
	public void setOnTabChangedListener(OnTabChangeListener listener){
		mTabChangeListener=listener;
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}
	
	public Fragment getTabFragment(int position){
		TabInfo info=mTabs.get(position);
		if (info!=null) {
			return info.mFragment;
		}
		return null;
	}
	
	public Fragment getCurrentTabFragment(){
		return getTabFragment(mTabHost.getCurrentTab());
	}
	
	public void setOnPageChangeListener(OnPageChangeListener listener){
		mPageChangeListener=listener;
	}

}
