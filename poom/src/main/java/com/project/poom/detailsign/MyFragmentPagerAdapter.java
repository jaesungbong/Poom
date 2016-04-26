package com.project.poom.detailsign;

import java.util.ArrayList;

import com.project.poom.manager.ImagePath_Bundle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	
	int imageCount, story_id;
	ArrayList<ImagePath_Bundle> fragmentBundle;
	NumberFragment f, f2;
	
	public static final String MFFA = "story_id";
	
	public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<ImagePath_Bundle> data, int count) {
		super(fm);
		fragmentBundle = data;
		imageCount = count;
	}

	@Override
	public Fragment getItem(int position) {
		f = new NumberFragment();
		Bundle b = new Bundle();
		b.putString(NumberFragment.NBF2, fragmentBundle.get(position).image);
		f.setArguments(b);
		return f;
	}

	@Override
	public int getCount() {
		return imageCount;
	}
}
