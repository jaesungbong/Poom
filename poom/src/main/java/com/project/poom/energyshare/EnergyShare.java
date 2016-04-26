package com.project.poom.energyshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.manager.DonateStory;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.recharge.RechargeActivity;

public class EnergyShare extends DialogFragment {
	
	EditText donate_energy;
	TextView have_energy, mycache, donate_cache;
	int story_id;
	
	public static final String ENERGYSHARE = "story_id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.energy_share_dialog_layout, container, false);
		donate_cache = (TextView)v.findViewById(R.id.textView4);
		donate_energy=(EditText)v.findViewById(R.id.energy_share_dialog_text);
		donate_energy.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)) {
					donate_cache.setText(Integer.parseInt(donate_energy.getText()+"")*100+"");
				}
				return false;
			}
		});
		mycache = (TextView)v.findViewById(R.id.textView3);
		mycache.setText(UserManager.getInstance().getUserEnergy()*100+"");
		have_energy=(TextView)v.findViewById(R.id.myenergy);
		have_energy.setText(UserManager.getInstance().getUserEnergy()+"");

		Bundle b=getArguments();
		story_id=b.getInt(ENERGYSHARE, 1);
		
		Button btn=(Button)v.findViewById(R.id.recharge);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), RechargeActivity.class);
				startActivity(i);
			}
		});
		
		btn = (Button)v.findViewById(R.id.delivery);
		btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String strDonate_energy = donate_energy.getText().toString();
					int myPoint= Integer.parseInt(have_energy.getText().toString());
					int donatePoint = Integer.parseInt(strDonate_energy);
					if(donatePoint<=myPoint){
						NetworkManager.getInstnace().donatestory(getActivity(), story_id, UserManager.getInstance().getUserId(),
								Integer.parseInt(donate_energy.getText().toString()),new OnResultListener<DonateStory> () {
		
									@Override
									public void onSuccess(DonateStory result) {
										Toast.makeText(getActivity(), "기부되었어요!", Toast.LENGTH_SHORT).show();
										dismiss();
									}
		
									@Override
									public void onFail(int code) {
										Toast.makeText(getActivity(), "fail!", Toast.LENGTH_SHORT).show();
									}
							
								});
					} else {
						Toast.makeText(getActivity(), "기부할 에너지가 보유 에너지보다 많아요", Toast.LENGTH_SHORT).show();
					}
				}
		});
		
		btn = (Button)v.findViewById(R.id.cancel);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		return v;
	}
}