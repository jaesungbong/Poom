package com.project.poom.donationact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.manager.UserManager;

public class DonationActMonthlyItem extends FrameLayout {

	ImageView image;
	TextView title;
	TextView day;
	TextView time;
	DonationActMonthlyItemData mData;
	DisplayImageOptions options;
	int type;

	public DonationActMonthlyItem(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.donation_act_monthly_item, this);
		image = (ImageView) findViewById(R.id.donation_act_monthly_item_img);
		title = (TextView) findViewById(R.id.donation_act_monthly_item_message);
		day = (TextView) findViewById(R.id.donation_act_monthly_item_day);
		time = (TextView) findViewById(R.id.donation_act_monthly_item_time);
	}

	public void setDonationActMonthlyItemData(DonationActMonthlyItemData data) {
		mData = data;
		type = data.type;
		if(type==1){
			title.setText(data.title+" 에 포인트를 전달 하셨습니다.");
		} else {
			title.setText(data.title+" 에 서명하셨습니다.");
		}
		day.setText(data.date.substring(8, 10));
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.login_graphic)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				/* .displayer(new RoundedBitmapDisplayer(50)) */
				.build();
		ImageLoader.getInstance().displayImage(
				"http://54.92.116.151/view/story/image/" + data.image, image, options);
		time.setText(data.date.substring(11, 16));
	}
}
