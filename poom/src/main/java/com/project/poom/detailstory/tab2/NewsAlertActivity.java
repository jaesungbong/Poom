package com.project.poom.detailstory.tab2;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.poom.R;
import com.project.poom.detailstory.tab2.UploadNewsDialog.OnUploadNewsDialogClickListener;
import com.project.poom.manager.EnrollNewsAtStory;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;

public class NewsAlertActivity extends ActionBarActivity 
implements OnClickListener, OnItemClickListener{

	public static final String NEWS="story_id";
	HListView listView;
	ImageButton ib;
	NewsAlertAdapter mAdapter;
	ArrayList<String> imagePathList = new ArrayList<String>();
	Bitmap bitmap;
	EditText w_content;
	int imagesize;
	String content;
	UploadNewsDialog mDialog;
	int story_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.newsalert_main);
	    w_content = (EditText)findViewById(R.id.newsalert_write);
	    ib=(ImageButton)findViewById(R.id.newsalert_callgal);
	    ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mAdapter.mItems.size() >= 4) {
					Toast.makeText(NewsAlertActivity.this, "사진은 최대 4장까지 업로드 가능합니다", Toast.LENGTH_SHORT).show();
				}else {
					Intent i=new Intent(NewsAlertActivity.this, CallGallertyActivity.class);
					i.putExtra("imagenum", mAdapter.mItems.size());
					startActivityForResult(i, 1);
				}				
			}
		});
	    
	    listView=(HListView)findViewById(R.id.naimglist);
	    
	    List<String> items=new ArrayList<String>();
	    for (int i = 0; i < 5; i++) {
			items.add(String.valueOf(i));
		}
	    mAdapter=new NewsAlertAdapter(this);
		listView.setAdapter( mAdapter );
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAdapter.remove(position);
				return true;
			}
		});
		
		getActionBar().setTitle("새소식 알리기");
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		
		Intent i = getIntent();
		story_id = i.getIntExtra(NEWS, 1);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				imagePathList = intent.getExtras().getStringArrayList("multipic");
				if (imagePathList != null) {
					for (int i = 0; i < imagePathList.size(); i++) {
						CallGalleryData data = new CallGalleryData();
						try {
							FileInputStream fis = new FileInputStream(new File(imagePathList.get(i).toString()));
							BitmapFactory.Options opts = new BitmapFactory.Options();
							opts.inSampleSize = 1;
							bitmap = BitmapFactory.decodeStream(fis, null, opts);
							data.resId = bitmap;
							mAdapter.add(data);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	class NewsAlertAdapter extends BaseAdapter{

		ArrayList<CallGalleryData> mItems = new ArrayList<CallGalleryData>();
		Context mContext;
		
		public NewsAlertAdapter(Context context) {
			mContext = context;
		}
		
		public void add(CallGalleryData data){
			mItems.add(data);
			notifyDataSetChanged();
		}
		
		public void remove(int postion){
			mItems.remove(postion);
			notifyDataSetChanged();
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CallGalleryView v;
			if (null == convertView) {
				v = new CallGalleryView(mContext);
			}else {
				v=(CallGalleryView)convertView;
			}
			v.setCallGalleryData(mItems.get(position));
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

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {}

	@Override
	public void onClick(View v) {}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.newsalert, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.na_complete:
			content = w_content.getText().toString();
			Log.i("content", content);
			if (content.equals("")) {
				mDialog = new UploadNewsDialog(R.drawable.popup_text_1);
				mDialog.setOnUploadNewsDialogClickListener(new OnUploadNewsDialogClickListener() {
					
					@Override
					public void onConfirmClicked() {
						mDialog.dismiss();
						finish();
					}
					
					@Override
					public void onCancleClicked() {
						mDialog.dismiss();
					}
				});
				mDialog.show(getSupportFragmentManager(), "dialog");
			}else {
				try {
					NetworkManager.getInstnace().enrollNewsAtStory(NewsAlertActivity.this, 
							story_id, UserManager.getInstance().getUserId(),content, 
							imagePathList , new OnResultListener<EnrollNewsAtStory>() {
								
								@Override
								public void onSuccess(EnrollNewsAtStory result) {
									Toast.makeText(NewsAlertActivity.this, "소식이 등록되었어요", Toast.LENGTH_SHORT).show();
									finish();
								}
								
								@Override
								public void onFail(int code) {
									Toast.makeText(NewsAlertActivity.this, "소식이 등록오류", Toast.LENGTH_SHORT).show();
								}
							});
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
