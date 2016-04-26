package com.project.poom.detailstory.tab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;

public class DtailStoryTab3ItemView extends FrameLayout {
	
	DtailStoryTab3Data mData;
	ImageView img;
	TextView nick, enroll_date, donate_energy, enroll_time;
	DisplayImageOptions options;
	
	public interface OnProfileClickListener{
		public void onProfileClick(View v, DtailStoryTab3Data data);
	}
	
	OnProfileClickListener mListener;
	
	public void setOnprofileClickListener(OnProfileClickListener listener){
		mListener = listener;
	}
	

	public DtailStoryTab3ItemView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.dtail_tab3_item_layout, this);
		img = (ImageView)findViewById(R.id.tab3_item_img);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mListener!=null){
					mListener.onProfileClick(DtailStoryTab3ItemView.this, mData);
				}
			}
		});
		nick = (TextView)findViewById(R.id.tab3_item_name);
		enroll_date = (TextView)findViewById(R.id.tab3_item_date);
		enroll_time = (TextView)findViewById(R.id.tab3_item_time);
		donate_energy = (TextView)findViewById(R.id.tab3_item_donationamount);
	}
	
	public void setDtailStoryTab3Data(DtailStoryTab3Data data){
		mData = data;
		nick.setText(data.getNick()+"");
		String date = data.getDonate_date();
		String eyear, emonth, eday, etime;
		eyear = date.substring(0, 4);
		emonth = date.substring(5, 7);
		eday = date.substring(8, 10);
		etime = date.substring(11, 16);
		StringBuilder sb = new StringBuilder();
		sb.append(eyear).append("-").append(emonth).append("-")
				.append(eday);
		enroll_date.setText(sb.toString());
		enroll_time.setText(etime);
		donate_energy.setText(data.getDonate_energy()+"");
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.login_graphic)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"+data.image, img, options);
	}
}
