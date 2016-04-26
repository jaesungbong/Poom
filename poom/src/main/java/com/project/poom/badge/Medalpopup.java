package com.project.poom.badge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.R.id;
import com.project.poom.manager.BadgeList;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;

public class Medalpopup extends Activity {

	public static final String MEDALPOPUP = "user_id";
	public static final String POSITION = "position";
	ImageView medalimg;
	Button btn;
	TextView name, content;
	ArrayList medal_id = new ArrayList();
	int user_id, position, temp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.badge_dialog_layout);
		medalimg = (ImageView) findViewById(R.id.medal_popup_img);
		name = (TextView) findViewById(R.id.medal_popup_name);
		content = (TextView) findViewById(R.id.medal_popup_content);
		btn = (Button) findViewById(R.id.medal_popup_x);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btn = (Button) findViewById(R.id.medal_popup_pre);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().badgeList(Medalpopup.this,	user_id, new OnResultListener<BadgeList>() {

							@Override
							public void onSuccess(BadgeList result) {
								if (position == 0) {
									position = result.result.size()-1;
									viewimg(result.result.get(position).badge_id);
								}else {
									position--;
									viewimg(result.result.get(position).badge_id);
								}								
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(Medalpopup.this, "훈장 불러오기 오류",	Toast.LENGTH_SHORT).show();
							}
				});
			}
		});

		btn = (Button) findViewById(R.id.medal_popup_next);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().badgeList(Medalpopup.this,	user_id, new OnResultListener<BadgeList>() {

					@Override
					public void onSuccess(BadgeList result) {
						position++;
						if (position > result.result.size()-1) {
							position = 0;
							viewimg(result.result.get(position).badge_id);
						}else {
							viewimg(result.result.get(position).badge_id);
						} 
							
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(Medalpopup.this, "훈장 불러오기 오류",	Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

		btn = (Button) findViewById(R.id.medal_popup_share);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		Intent i = getIntent();
		user_id = i.getIntExtra(MEDALPOPUP, 1);
		position = i.getIntExtra(POSITION, 21);

		NetworkManager.getInstnace().badgeList(Medalpopup.this, user_id,
				new OnResultListener<BadgeList>() {

					@Override
					public void onSuccess(BadgeList result) {
						medal_id.clear();
						for (int j = 0; j < result.result.size(); j++) {
							medal_id.add(result.result.get(j).badge_id);
						}
						viewimg(result.result.get(position).badge_id);
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(Medalpopup.this, "훈장 불러오기 오류",
								Toast.LENGTH_SHORT).show();
					}
				});

	}

	private void viewimg(int selectposition) {
		switch (selectposition) {
		case 1:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 2:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 3:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 4:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 5:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 6:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 7:
			medalimg.setImageResource(R.drawable.medal_7);
			name.setText("내품에강아지");
			content.setText("첫 POOM point 충전시 획득");
			break;
		case 8:
			medalimg.setImageResource(R.drawable.medal_8);
			name.setText("대담한 기부용사");
			content.setText("한번에 10point 이상 기부했을 때 획득");
			break;
		case 9:
			medalimg.setImageResource(R.drawable.medal_9);
			name.setText("통큰 기부천사");
			content.setText("한번에 50point 이상 기부했을 때 획득");
			break;
		case 10:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 11:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 12:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 13:
			medalimg.setImageResource(R.drawable.medal_10);
			name.setText("기부했닥스훈트");
			content.setText("누적 기부 10point 기록시");
			break;
		case 14:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 15:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 16:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 17:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 18:
			medalimg.setImageResource(R.drawable.medal_sample2);
			name.setText("멍멍이발자국");
			content.setText("사연에 다섯번째 서명시");
			break;
		case 19:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		case 20:
			// img.setImageResource(R.drawable.);
			// number.setText("");
			break;
		}
	}
}
