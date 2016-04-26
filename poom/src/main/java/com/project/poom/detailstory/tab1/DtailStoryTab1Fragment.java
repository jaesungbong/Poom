package com.project.poom.detailstory.tab1;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.PagerFragment;
import com.project.poom.R;
import com.project.poom.detailstory.tab1.comment.CommentAdapter;
import com.project.poom.detailstory.tab1.comment.CommentData;
import com.project.poom.energyshare.EnergyShare;
import com.project.poom.login.LoginActivity;
import com.project.poom.maintab1.DDay;
import com.project.poom.manager.CallStory;
import com.project.poom.manager.CallUserInfo;
import com.project.poom.manager.DeleteRepAtStory;
import com.project.poom.manager.EnrollAttention;
import com.project.poom.manager.EnrollRepAtStory;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.profile.ProfileActivity;
import com.project.poom.share.ShareDialogFragment;

public class DtailStoryTab1Fragment extends PagerFragment {

	ListView listView;
	CommentAdapter mAdapter;
	EditText keywordView;
	Button btn;
	TextView title, goal_fund, content, d_day, cheerup, enroll_date, writerName, current_percent;
	SeekBar mBar;
	ImageView mainimg, profpic, writerPhoto;
	int story_id, id;
	String image_url, plan, subject, contents;
	CommentData d;
	LinearLayout textpercentage;
	int bookmark, imgindex, max, current, percentage;
	DisplayImageOptions options =new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.login_graphic)
	.showImageForEmptyUri(R.drawable.ic_empty)
	.showImageOnFail(R.drawable.login_graphic)
	.cacheInMemory(true).cacheOnDisc(true)
	.considerExifParams(true)
	/* .displayer(new RoundedBitmapDisplayer(50)) */
	.build();
	ImageView footmark, footmarking;
	ArrayList<String> images = new ArrayList<String>();

	public static final String DAT1F = "story_id";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		Bundle b = getArguments();
		story_id = b.getInt(DAT1F);
		
		View v = inflater.inflate(R.layout.dtail_tab1_layout, container, false);
		View header = inflater.inflate(R.layout.dtail_tab1_layout_header, null,false);
		current_percent = (TextView)header.findViewById(R.id.dt1_current_percent);
		textpercentage = (LinearLayout)header.findViewById(R.id.dt1_textpercentage);
		mainimg = (ImageView) header.findViewById(R.id.ds_mainimg);
		images.clear();
		imgindex = 0;
		mainimg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgindex++;
				Log.i("test", ""+images.size()+"/"+imgindex);
				if (imgindex == images.size()) {
					imgindex = 0;
				}
				ImageLoader.getInstance().displayImage("http://54.92.116.151/view/story/image/"+images.get(imgindex), mainimg, options);
			}
		});
		title = (TextView) header.findViewById(R.id.ds_title);
		goal_fund = (TextView) header.findViewById(R.id.ds_goal_fund);
		content = (TextView) header.findViewById(R.id.ds_content);
		mBar = (SeekBar) header.findViewById(R.id.ds_progressBar);
		mBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int origin;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				origin = current;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				seekBar.setProgress(current);
			}
		});
		d_day = (TextView) header.findViewById(R.id.dtail_tab1_layout_header_d_day);
		cheerup = (TextView) header.findViewById(R.id.dtail_tab1_layout_header_cheerup_number);
		enroll_date = (TextView) header.findViewById(R.id.dtail_tab1_layout_header_date);
		footmark = (ImageView) v.findViewById(R.id.dtail_tab1_layout_footmark);
		writerPhoto = (ImageView)header.findViewById(R.id.dtail_tab1_layout_header_photo);
		writerPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),ProfileActivity.class);
				int profile_id = id;
				i.putExtra(ProfileActivity.PROFILE_ID, profile_id);
				startActivity(i);
			}
		});	
		writerName = (TextView)header.findViewById(R.id.dtail_tab1_layout_header_nickname);	
		footmarking = (ImageView) v.findViewById(R.id.dtail_tab1_layout_footmarking);
		footmark.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().enrollAttention(getActivity(),
						story_id, UserManager.getInstance().getUserId(),
						new OnResultListener<EnrollAttention>() {

							@Override
							public void onSuccess(EnrollAttention result) {
								footmark.setVisibility(View.GONE);
								footmarking.setVisibility(View.VISIBLE);
								Toast.makeText(getActivity(),
										"즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT)
										.show();
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(getActivity(), "fail",
										Toast.LENGTH_SHORT).show();
							}

						});
			}
		});
		footmarking.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NetworkManager.getInstnace().enrollAttention(getActivity(),
						story_id, UserManager.getInstance().getUserId(),
						new OnResultListener<EnrollAttention>() {

							@Override
							public void onSuccess(EnrollAttention result) {
								footmark.setVisibility(View.VISIBLE);
								footmarking.setVisibility(View.GONE);
								Toast.makeText(getActivity(),
										"즐겨찾기에 해제 되었습니다.", Toast.LENGTH_SHORT)
										.show();
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(getActivity(), "fail",
										Toast.LENGTH_SHORT).show();
							}

						});
			}
		});

		btn = (Button) header.findViewById(R.id.ds_breakdown);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				DtailStoryTab1BreakdownDialog db = new DtailStoryTab1BreakdownDialog(
						story_id);
				db.show(getChildFragmentManager(), "dialog");
			}
		});

		NetworkManager.getInstnace().callStory(getActivity(), story_id,
				UserManager.getInstance().getUserId(),
				new OnResultListener<CallStory>() {

					@Override
					public void onSuccess(CallStory result) {
						id = result.result.story.get(0).id;
						writerName.setText(result.result.story.get(0).nick);
						plan = result.result.story.get(0).plan;
						title.setText(result.result.story.get(0).title + "");
						subject = result.result.story.get(0).title;
						goal_fund.setText(result.result.story.get(0).goal_fund
								+ "");
						content.setText(result.result.story.get(0).content + "");
						contents = result.result.story.get(0).content;
						if (result.result.story.get(0).attend == 0) {
							footmark.setVisibility(View.VISIBLE);
						} else {
							footmarking.setVisibility(View.VISIBLE);
						}
						String dday = result.result.story.get(0).end_fund_date;
						int year, month, day;
						year = Integer.parseInt(dday.substring(0, 4));
						month = Integer.parseInt(dday.substring(5, 7));
						day = Integer.parseInt(dday.substring(8, 10));
						DDay d = new DDay();
						d_day.setText((d.caldate(year, month, day) * -1) + "");
						cheerup.setText(result.result.story.get(0).donator+"");
						String ed = result.result.story.get(0).enroll_date;
						String eyear, emonth, eday, etime;
						eyear = ed.substring(0, 4);
						emonth = ed.substring(5, 7);
						eday = ed.substring(8, 10);
						etime = ed.substring(11, 16);
						StringBuilder sb = new StringBuilder();
						sb.append(eyear).append(".").append(emonth).append(".")
								.append(eday);
						enroll_date.setText(sb.toString());
						mAdapter.addAll(result.result.comments);
						image_url = result.result.images.get(0).image;
						ImageLoader.getInstance().displayImage("http://54.92.116.151/view/story/image/"+result.result.images.get(0).image, mainimg, options);
						NetworkManager.getInstnace().callUserInfo(getActivity(), id, new OnResultListener<CallUserInfo>() {

							@Override
							public void onSuccess(CallUserInfo result) {
								ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"
										+result.result.get(0).image, writerPhoto, options);
							}

							@Override
							public void onFail(int code) {
								Toast.makeText(getActivity(), "회원사진 가져오기 오류", Toast.LENGTH_SHORT).show();
							}
						});
						
						for (int i = 0; i < result.result.images.size(); i++) {
							images.add(result.result.images.get(i).image);
						}
						
						max = result.result.story.get(0).goal_fund;
						current = result.result.story.get(0).current_fund;
						percentage = (int)((double)current / (double)max * 100.0);
						mBar.setMax(max);
						mBar.setProgress(current);
						current_percent.setText(percentage+"");
						
						if (percentage<25) {
				        	textpercentage.setVisibility(View.GONE);
							Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to25);
							mBar.setThumb(dd);
						}else if (percentage < 50) {
							LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						    llp.setMargins(50+percentage, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
						    Log.i("udy", percentage+"");
						    current_percent.setLayoutParams(llp);
							textpercentage.setVisibility(View.VISIBLE);
							Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to25);
							mBar.setThumb(dd);
						}else if (percentage < 75) {
							LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						    llp.setMargins(percentage, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
						    Log.i("udy", percentage+"");
						    current_percent.setLayoutParams(llp);
							textpercentage.setVisibility(View.VISIBLE);
							Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to50);
							mBar.setThumb(dd);
						}else if (percentage < 100) {
							LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						    llp.setMargins(218+percentage, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
						    Log.i("udy", percentage+"");
						    current_percent.setLayoutParams(llp);
							textpercentage.setVisibility(View.VISIBLE);
							Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to75);
							mBar.setThumb(dd);
						}else{
							LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						    llp.setMargins(295, 0, 0, 0); // llp.setMargins(left, top, right, bottom);
						    current_percent.setLayoutParams(llp);
							textpercentage.setVisibility(View.VISIBLE);
							Drawable dd= getResources().getDrawable(R.drawable.progress_icon_dog_to100);
							mBar.setThumb(dd);
						}
					}
					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "해당 사연을 불러올수 없습니다",
								Toast.LENGTH_SHORT).show();
					}
				});
		View footer = inflater.inflate(R.layout.dtail_tab1_layout_footer, null,
				false);
		listView = (ListView) v.findViewById(R.id.storylist);
		listView.addHeaderView(header);
		listView.addFooterView(footer);
		mAdapter = new CommentAdapter(getActivity());
		listView.setAdapter(mAdapter);
		mAdapter.setOnAdapterItemClickListener(new CommentAdapter.OnAdapterItemClickListener() {

			@Override
			public void onItemDelClick(View v, final CommentData data) {
				NetworkManager.getInstnace().deleteRepAtStory(getActivity(), data.id, new OnResultListener<DeleteRepAtStory>() {

							@Override
							public void onSuccess(DeleteRepAtStory result) {
								Toast.makeText(getActivity(),"댓글이 삭제되었습니다",Toast.LENGTH_SHORT).show();
								
								NetworkManager.getInstnace().callStory(getActivity(),story_id,UserManager.getInstance().getUserId(),new OnResultListener<CallStory>() {

													@Override
													public void onSuccess(CallStory result) {
														mAdapter.clear();
														mAdapter.addAll(result.result.comments);
													}

													@Override
													public void onFail(int code) {
														Toast.makeText(getActivity(),"댓글을 갱신하는데 실패하였습니다",Toast.LENGTH_SHORT).show();
													}
												});

							}

							@Override
							public void onFail(int code) {
								Toast.makeText(getActivity(),
										"댓글이 삭제에 실패했습니다",Toast.LENGTH_SHORT).show();
							}
						});
			}

			@Override
			public void onProfileClick(View v, CommentData data) {
				Intent i = new Intent(getActivity(),ProfileActivity.class);
				int profile_id = data.user_id;
				i.putExtra(ProfileActivity.PROFILE_ID, profile_id);
				startActivity(i);
			}
		});

		keywordView = (EditText) v.findViewById(R.id.keyword);
		keywordView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!UserManager.getInstance().isLogin()) {
					Intent i = new Intent(getActivity(), LoginActivity.class);
					startActivity(i);
				}
			}
		});
		btn = (Button) v.findViewById(R.id.keywordbtn);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().enrollRepAtStory(getActivity(), story_id,UserManager.getInstance().getUserId(), keyword,new OnResultListener<EnrollRepAtStory>() {
								@Override
								public void onSuccess(EnrollRepAtStory result) {
									Toast.makeText(getActivity(),"댓글이 등록되었습니다", Toast.LENGTH_SHORT).show();
									NetworkManager.getInstnace().callStory(getActivity(),story_id,UserManager.getInstance().getUserId(),new OnResultListener<CallStory>() {

												@Override
												public void onSuccess(CallStory result) {
													mAdapter.clear();
													mAdapter.addAll(result.result.comments);
												}

												@Override
												public void onFail(int code) {
													Toast.makeText(getActivity(),"댓글을 갱신하는데 실패하였습니다",Toast.LENGTH_SHORT).show();
												}
											});
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(getActivity(),
											"댓글 등록 과정에서 문제가 일어났습니다",
											Toast.LENGTH_SHORT).show();
								}
							});
				}
				keywordView.setText("");
			}
		});

		btn = (Button) v.findViewById(R.id.share);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ShareDialogFragment f = new ShareDialogFragment(image_url,
						subject, contents);
				f.show(((FragmentActivity) getActivity())
						.getSupportFragmentManager(), "dialog");
			}
		});

		btn = (Button) v.findViewById(R.id.share_energy);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (UserManager.getInstance().isLogin()) {
					EnergyShare f = new EnergyShare();
					Bundle b2 = new Bundle();
					b2.putInt(EnergyShare.ENERGYSHARE, story_id);
					f.setArguments(b2);
					f.show(getFragmentManager(), "dialog");
				} else {
					Intent i = new Intent(getActivity(), LoginActivity.class);
					startActivity(i);
				}
			}
		});
		return v;
	}

	@Override
	public void onPageCurrent() {
		images.clear();
		NetworkManager.getInstnace().callStory(getActivity(), story_id,UserManager.getInstance().getUserId(),new OnResultListener<CallStory>() {

					@Override
					public void onSuccess(CallStory result) {
						if (result.result.story.get(0).attend == 0) {
							footmark.setVisibility(View.VISIBLE);
						} else {
							footmarking.setVisibility(View.VISIBLE);
						}
						for (int i = 0; i < result.result.images.size(); i++) {
							images.add(result.result.images.get(i).image);
						}
					}

					@Override
					public void onFail(int code) {
						Toast.makeText(getActivity(), "해당 사연을 불러올수 없습니다",Toast.LENGTH_SHORT).show();
					}
				});
		imgindex = 0;
		super.onPageCurrent();
	}
}
