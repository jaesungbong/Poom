package com.project.poom.setting;

import com.project.poom.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class FaqActivity extends Activity {
	
	ExpandableListView listView;
	FaqAdapter mAdapter;
	
	FaqHighItemData one,two,three,four,five,six,seven,eight,nine,ten;
	FaqLowItemData lone,ltwo,lthree,lfour,lfive,lsix,lseven,leight,lnine,lten;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_faq);
	    listView = (ExpandableListView)findViewById(R.id.activity_faq_expandableListView);
	    mAdapter = new FaqAdapter(FaqActivity.this);
	    listView.setAdapter(mAdapter);
	    initData();
	}
	
	private void initData(){
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);

		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?1";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?2";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?3";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?4";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?5";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?6";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?7";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
		
		one = new FaqHighItemData();
		one.title = "POOM은 어떤 서비스 인가요?8";
		lone = new FaqLowItemData();
		lone.content = "Poom은 유기동물이 새로운\n 주인의 품을 찾아 떠나는 \n긴 여정에 함께 응원하고 모금하는\n ‘유기동물 전문 소액 크라우드 펀딩 서비스’ 입니다.";
		mAdapter.put(one, lone);
	}

}
