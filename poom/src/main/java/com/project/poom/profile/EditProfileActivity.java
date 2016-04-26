package com.project.poom.profile;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.poom.R;
import com.project.poom.manager.CallUserInfo;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.StatusResult;
import com.project.poom.manager.UserManager;

public class EditProfileActivity extends ActionBarActivity {

	private static final String TEMP_PHOTO_FILE = "temp.jpg";
	private static final int REQ_CODE_PICK_IMAGE = 0;

	EditText name, message;
	Button btn;
	ImageView img;
	String filePath;
	Bitmap selectedImage;
	BitmapDrawable postimage;
	Bundle extra;
	Intent intent;
	DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		init();
		options = new DisplayImageOptions.Builder()
	      .showImageOnLoading(R.drawable.login_graphic)
	      .showImageForEmptyUri(R.drawable.ic_empty)
	      .showImageOnFail(R.drawable.profile_sample_image)
	      .cacheInMemory(true)
	      .cacheOnDisc(true)
	      .considerExifParams(true)
	      /*.displayer(new RoundedBitmapDisplayer(50))*/
	      .build();
		NetworkManager.getInstnace().callUserInfo(EditProfileActivity.this, UserManager.getInstance().getUserId(), 
				new OnResultListener<CallUserInfo>() {

					@Override
					public void onSuccess(CallUserInfo result) {
						name.setText(result.result.get(0).nick);
						message.setText(result.result.get(0).status);
						ImageLoader.getInstance().displayImage("http://54.92.116.151/view/user/image/"+
					result.result.get(0).image, img, options);
					}

					@Override
					public void onFail(int code) {
						
					}
				});
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_GET_CONTENT,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				intent.putExtra("crop", "true");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
				intent.putExtra("outputFormat",
						Bitmap.CompressFormat.JPEG.toString());

				startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
			}
		});
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setCustomView(R.layout.actionbar_profile_change);
		getSupportActionBar().setIcon(R.drawable.null_image);
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {
		if (isSDCARDMOUNTED()) {
			File f = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return f;
		} else {
			return null;
		}
	}

	private boolean isSDCARDMOUNTED() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED))
			return true;

		return false;
	}

	private void init() {
		name = (EditText) findViewById(R.id.activity_editprofile_name);
		message = (EditText) findViewById(R.id.activity_editprofile_introduce);
		btn = (Button) findViewById(R.id.activity_editprofile_btn);
		img = (ImageView) findViewById(R.id.activity_editprofile_img);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.editprofile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.editpage_complete:
			String strName = name.getText().toString();
			String strMessage = message.getText().toString();
			String strfilePath = filePath;
			if (strfilePath == null) {
				strfilePath="null";
			}
			NetworkManager.getInstnace().modifyUser(EditProfileActivity.this, UserManager.getInstance().getUserId(), 
					 strName, strMessage, strfilePath, new OnResultListener<StatusResult>(){

						@Override
						public void onSuccess(StatusResult result) {
							Toast.makeText(EditProfileActivity.this, "회원정보가 성공적으로 변경되었어요!", Toast.LENGTH_SHORT).show();
							finish();
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(EditProfileActivity.this, "회원정보 수정에 실패했어요ㅠㅠ", Toast.LENGTH_SHORT).show();
						}
			});
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageData) {
		super.onActivityResult(requestCode, resultCode, imageData);

		switch (requestCode) {
		case REQ_CODE_PICK_IMAGE:
			if (resultCode == RESULT_OK) {
				if (imageData != null) {
					filePath = Environment.getExternalStorageDirectory()
							+ "/temp.jpg";

					Log.i("path", filePath); // logCat으로 경로확인.
					selectedImage = BitmapFactory.decodeFile(filePath);
					// temp.jpg파일을 Bitmap으로 디코딩한다.

					img.setImageBitmap(selectedImage);
					// temp.jpg파일을 이미지뷰에 씌운다.
				}
			}
			break;
		}
	}
}
