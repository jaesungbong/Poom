package com.project.poom.donationact;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.poom.R;

public class DonationActMonthly extends FrameLayout {
	
	TextView month;
	TextView strMonth;
	TextView year;
	DonationActMonthlyData mData;
	
	public DonationActMonthly(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.donation_act_monthly, this);
		month = (TextView)findViewById(R.id.donation_act_monthly_month);
		strMonth = (TextView)findViewById(R.id.donation_act_monthly_strmonth);
		year = (TextView)findViewById(R.id.donation_act_monthly_year);
	}
	
	public void setDonationActMonthlyData(DonationActMonthlyData data){
		mData = data;
		month.setText(data.month);
		strMonth.setText(data.strMonth);
		year.setText(Integer.toString(data.year));
	}

}
