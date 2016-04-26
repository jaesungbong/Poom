package com.project.poom.requestsign;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.detailstory.tab2.CallGallertyActivity;
import com.project.poom.maintab1.DDay;
import com.project.poom.manager.EnrollStory;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;
import com.project.poom.requestsign.ReceiptDialog.OnDialogClickListener;

public class RequestSignActivity extends ActionBarActivity {
	
	public static final int WRITESTORY = 0;
	ArrayList<String> imagePathList;
 	Bitmap bitmap;
	HListView listView;
	Button addlist, rs_confirm;
	RequestSignAdapter mAdapter;
	ReceiptAdapter mRcAdapter;
	ListView rclistView;
	ReceiptDialog mDialog;
	EditText rs_edit, rs_story, rsdurationend;
	String title, content, end_fund_date, plan, story;
	ImageButton add_pic;
	TextView total_amount, rs_cop_date;
	int goalFund, tot;
	boolean datecheck = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.requestsign_layout);
	    rsdurationend = (EditText)findViewById(R.id.rsdurationend);
	    rs_cop_date = (TextView)findViewById(R.id.rs_cop_date);
	    rs_confirm = (Button)findViewById(R.id.rs_confirm);
	    rs_confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String endday = rsdurationend.getText().toString();
				if (endday.equals("")) {
					Toast.makeText(RequestSignActivity.this, "날짜를 기입해주세요", Toast.LENGTH_SHORT).show();
				}else {
					int year, month, day;
					year = Integer.parseInt(endday.substring(0,4));
					month = Integer.parseInt(endday.substring(5,7));
					day = Integer.parseInt(endday.substring(8,10));
					DDay d = new DDay();
					if (d.caldate(year, month, day)*-1 <30) {
						Toast.makeText(RequestSignActivity.this, "마감일은 최소 30일 이상이어야해요", Toast.LENGTH_SHORT).show();
					}else if (d.caldate(year, month, day)*-1>90) {
						Toast.makeText(RequestSignActivity.this, "마감일은 최대 90일 까지에요", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(RequestSignActivity.this, "마감일이 설정되었어요", Toast.LENGTH_SHORT).show();
						rs_cop_date.setVisibility(View.VISIBLE);
						rsdurationend.setVisibility(View.INVISIBLE);
						rs_confirm.setVisibility(View.INVISIBLE);
						rs_cop_date.setText(rsdurationend.getText().toString());
						datecheck = true;
					}
				}
			}
		});
	    
	    rs_edit = (EditText)findViewById(R.id.rsedit);
	    rs_story = (EditText)findViewById(R.id.rsstory);
	    rs_story.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RequestSignActivity.this, WriteStory.class);
				i.putExtra("edit", rs_story.getText().toString());
				startActivityForResult(i, WRITESTORY);
			}
		});
	    rclistView = (ListView)findViewById(R.id.rsreceipt);
	    mRcAdapter = new ReceiptAdapter(this);
	    mRcAdapter.setOnAdapterItemClickListener(new ReceiptAdapter.OnAdapterItemClickListener() {
			
			@Override
			public void onItemDelClick(View v, ReceiptData data) {
				mRcAdapter.remove(data);
				total_amount.setText(getGoalFund(mRcAdapter)+""); 
			}
		});
	    rclistView.setAdapter(mRcAdapter);
	    rclistView.setStackFromBottom(false);
	    rclistView.setDivider(null);
	    addlist=(Button)findViewById(R.id.rsadd);
	    mDialog = new ReceiptDialog();
	    getActionBar().setTitle("서명 요청하기");
	    
	    listView=(HListView)findViewById(R.id.rsimglist);
	    List<String> items=new ArrayList<String>();
	    for (int i = 0; i < 5; i++) {
			items.add(String.valueOf(i));
		}
	    mAdapter=new RequestSignAdapter(this);
	    listView.setAdapter(mAdapter);
	    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.remove(position);
				return true;
			}
		});
	    
	    addlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.show(getSupportFragmentManager(),"dialog");
			}
		});
	    
	    mDialog.setOnDialogClickListener(new OnDialogClickListener() {
			
			@Override
			public void OnConfirmClicked(String category, String goalamount) {
				Toast.makeText(RequestSignActivity.this, "등록되었습니다", Toast.LENGTH_SHORT).show();
				insertInfo(category, goalamount);
				total_amount.setText(getGoalFund(mRcAdapter)+"");
				mDialog.dismiss();
			}
		});
	    
	    add_pic=(ImageButton)findViewById(R.id.add_pic);
	    add_pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mAdapter.mItems.size() >= 4) {
					Toast.makeText(RequestSignActivity.this, "사진은 최대 4장까지 업로드 가능합니다", Toast.LENGTH_SHORT).show();
				}else {
					Intent i=new Intent(RequestSignActivity.this, CallGallertyActivity.class);
					i.putExtra(CallGallertyActivity.FROMREQUESTSIGN, mAdapter.mItems.size());
					startActivityForResult(i, 1);
				}
			}
		});
	    
	    total_amount = (TextView)findViewById(R.id.total_amount);
	    total_amount.setText(tot+"");
	    
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setIcon(R.drawable.null_image);
		getSupportActionBar().setCustomView(R.layout.actionbar_request); //액션바 제목 설정
		getSupportActionBar().setDisplayUseLogoEnabled(false);
	}
	
	private int getGoalFund(ReceiptAdapter mfundAdapter) {
		tot=0;
		for (int i = 0; i < mfundAdapter.mItems.size(); i++) {
			tot+=Integer.parseInt(mfundAdapter.mItems.get(i).goalamount.toString());
		}
		return tot;
	}

	private String getPlan(ReceiptAdapter mtempAdapter) {
		StringBuilder planlistncost= new StringBuilder();
		for (int i = 0; i < mtempAdapter.mItems.size(); i++) {
			planlistncost.append(mtempAdapter.mItems.get(i).category);
			planlistncost.append("-");
			planlistncost.append(mtempAdapter.mItems.get(i).goalamount);
			planlistncost.append("/");
		}
		return planlistncost.toString();
	}

	private String getNowDate() {
		Date temp = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		return date.format(temp);
	}

	class RequestSignAdapter extends BaseAdapter{

		ArrayList<ViewGalleryData> mItems = new ArrayList<ViewGalleryData>();;
		Context mContext;
		
		public RequestSignAdapter(Context context) {
			mContext = context;
		}
		
		public void add(ViewGalleryData data){
			mItems.add(data);
			notifyDataSetChanged();
		}
		
		public void remove(int position){
			mItems.remove(position);
			notifyDataSetChanged();
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewGalleryView v;
			if (null == convertView) {
				v = new ViewGalleryView(mContext);
			}else {
				v = (ViewGalleryView)convertView;
			}
			v.setViewGalleryData(mItems.get(position));
			return v;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}
	}
	
	public void insertInfo(String category, String goalamount){
		ReceiptData d = new ReceiptData();
		d.category = category;
		d.goalamount = goalamount;
		mRcAdapter.add(d);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.requestsign, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.rs_complete:
			title = rs_edit.getText().toString();
		    content = rs_story.getText().toString();
		    end_fund_date = rsdurationend.getText().toString();
		    plan = getPlan(mRcAdapter);
		    goalFund = getGoalFund(mRcAdapter);
		    if ((title.equals("")) || (content.equals("")) || !datecheck || (plan.equals("")) || ((goalFund+"").equals(""))) {
				Toast.makeText(RequestSignActivity.this, "아직 작성하지 않은 목록이 있어요", Toast.LENGTH_SHORT).show();
			}else if (imagePathList == null) {
				Toast.makeText(RequestSignActivity.this, "사진은 최소 1장이상 등록되어야 해요", Toast.LENGTH_SHORT).show();
			}else {
				try {
					NetworkManager.getInstnace().enrollStory(RequestSignActivity.this, UserManager.getInstance().getUserId(),
							title, content, plan, goalFund, end_fund_date, imagePathList , new OnResultListener<EnrollStory>() {
						
								@Override
								public void onSuccess(EnrollStory result) {
									Toast.makeText(RequestSignActivity.this, "서명이 등록되었어요!!", Toast.LENGTH_SHORT).show();
									finish();
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(RequestSignActivity.this, "fail!!"+code, Toast.LENGTH_LONG).show();
								}
							});
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}			
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
		case WRITESTORY:
			if (resultCode == RESULT_OK) {
				story = intent.getExtras().getString(WriteStory.WS_TAG);
				rs_story.setText(story);
			}
			break;
		case 1:
			if (resultCode == RESULT_OK) {
				imagePathList = intent.getExtras().getStringArrayList("multipic");
				if (imagePathList != null) {
					for (int i = 0; i < imagePathList.size(); i++) {
						ViewGalleryData data = new ViewGalleryData();
						try {
							FileInputStream fis = new FileInputStream(new File(imagePathList.get(i).toString()));
							BitmapFactory.Options opts = new BitmapFactory.Options();
							opts.inSampleSize = 1;
							bitmap = BitmapFactory.decodeStream(fis, null, opts);
							data.image = bitmap;
							mAdapter.add(data);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}