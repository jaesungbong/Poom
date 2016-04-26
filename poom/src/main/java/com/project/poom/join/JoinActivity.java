package com.project.poom.join;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.poom.MainActivity;
import com.project.poom.R;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserInfoResult;

public class JoinActivity extends FragmentActivity {

	Fragment Base =  new AgreementFragment();
//			new JoinFragmentOne();
	Fragment[] fragmentList = new Fragment[] { new JoinFragmentOne(), new JoinFragmentTwo(), new JoinFragmentThree() };
	
	ImageView join_one_img, join_two_img, join_three_img;
	Button btnprev, btnnext, x;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_activity);
		join_one_img = (ImageView)findViewById(R.id.join_one_img);
		join_two_img = (ImageView)findViewById(R.id.join_two_img);
		join_three_img = (ImageView)findViewById(R.id.join_three_img);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.join_container, Base).commit();
		btnnext = (Button) findViewById(R.id.next);
		btnnext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = getSupportFragmentManager()
						.getBackStackEntryCount();

				if (count < fragmentList.length) {

					boolean check = false;

					switch (count) {
					case 0:
						check = (((AgreementFragment) Base).getCheck1());
						break;

					case 1:
						check = (((JoinFragmentOne) fragmentList[0]).getCheck1())&&(((JoinFragmentOne) fragmentList[0]).getCheck2());
						break;
					
					case 2:
						check = (((JoinFragmentTwo) fragmentList[1]).getCheck1())&&(((JoinFragmentTwo) fragmentList[1]).getCheck2());
					}

					if (check) {
						FragmentTransaction ft = getSupportFragmentManager()
								.beginTransaction();
						ft.replace(R.id.join_container, fragmentList[count]);
						if (count == 0) {
							join_one_img.setVisibility(View.VISIBLE);
							join_two_img.setVisibility(View.INVISIBLE);
							join_three_img.setVisibility(View.INVISIBLE);
						}else if (count == 1) {
							join_one_img.setVisibility(View.INVISIBLE);
							join_two_img.setVisibility(View.VISIBLE);
							join_three_img.setVisibility(View.INVISIBLE);
							
						}else if (count == 2) {
							join_one_img.setVisibility(View.INVISIBLE);
							join_two_img.setVisibility(View.INVISIBLE);
							join_three_img.setVisibility(View.VISIBLE);
							btnnext.setBackgroundResource(R.drawable.btn_end);
						}
						ft.addToBackStack("backstack" + count);
						ft.commit();
					} else {

						switch (count) {
						case 0:
							if(!((AgreementFragment)Base).getCheck1()){
								((AgreementFragment)Base).resultCheck1();
							}
							break;
							
						case 1:
							if(!((JoinFragmentOne)fragmentList[0]).getCheck1()){
								((JoinFragmentOne)fragmentList[0]).resultCheck1();
							}else if(!((JoinFragmentOne)fragmentList[0]).getCheck2()){
								((JoinFragmentOne)fragmentList[0]).resultCheck2();
							}
							break;
							
						case 2:
							if(!((JoinFragmentTwo)fragmentList[1]).getCheck1()){
								((JoinFragmentTwo)fragmentList[1]).resultCheck1();
							} else if(!((JoinFragmentTwo)fragmentList[1]).getCheck2()){
								((JoinFragmentTwo)fragmentList[1]).resultCheck1();
							}
							break;
						}

					}
				} else {
					boolean check = false;
					check = ((JoinFragmentThree) fragmentList[2]).getCheck1()
							&& ((JoinFragmentThree) fragmentList[2])
									.getCheck2();
					if (check) {
						String mail = ((JoinFragmentTwo) fragmentList[1])
								.getEmail();
						String password = ((JoinFragmentThree) fragmentList[2])
								.getPassword2();
						String nick = ((JoinFragmentOne)fragmentList[0]).getNickName();

						NetworkManager.getInstnace().join(JoinActivity.this,
								mail, password, nick,
								new OnResultListener<UserInfoResult>() {

									@Override
									public void onSuccess(UserInfoResult result) {
										Toast.makeText(
												JoinActivity.this,
												"회원 가입이 완료 되었어요!!",
												Toast.LENGTH_SHORT).show();
										finish();
									}

									@Override
									public void onFail(int code) {
										Toast.makeText(JoinActivity.this,
												"fail", Toast.LENGTH_SHORT)
												.show();
										finish();
									}
								});
					} else {
						if (!((JoinFragmentThree) fragmentList[2]).getCheck1()) {
							((JoinFragmentThree) fragmentList[2]).resultCheck1();
						} else if (!((JoinFragmentThree) fragmentList[2])
								.getCheck2()) {
							((JoinFragmentThree) fragmentList[2]).resultCheck2();
						}
					}
				}
			}
		});

		btnprev = (Button) findViewById(R.id.prev);
		btnprev.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = getSupportFragmentManager()
						.getBackStackEntryCount();
				if (count > 0) {
					getSupportFragmentManager().popBackStack();
				}
			}
		});
		
		x = (Button)findViewById(R.id.ja_xbox);
		x.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
