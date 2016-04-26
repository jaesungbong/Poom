package com.project.poom.recharge;

import java.util.zip.Inflater;

import org.w3c.dom.Text;

import com.project.poom.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class FreeRechargeItemView extends FrameLayout {
	
	ImageView img;
	TextView title, content, literOfRecharge;
	FreeRechargeItemData mData;

	public FreeRechargeItemView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.free_recharge_item_view_layout, this);
		img = (ImageView)findViewById(R.id.free_recharge_img);
		title = (TextView)findViewById(R.id.free_recharge_title);
		content = (TextView)findViewById(R.id.free_recharge_content);
		literOfRecharge = (TextView)findViewById(R.id.free_recharge_liter);		
	}
	
	public void setFreeRechargeItemData(FreeRechargeItemData data){
		mData = data;
		img.setImageResource(data.resId);
		title.setText(data.title);
		content.setText(data.content);
		literOfRecharge.setText(data.literOfRecharge);
	}

}
