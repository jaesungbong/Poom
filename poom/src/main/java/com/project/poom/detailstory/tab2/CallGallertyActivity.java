package com.project.poom.detailstory.tab2;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.poom.R;

public class CallGallertyActivity extends ActionBarActivity implements LoaderCallbacks<Cursor>{
	
	GridView gridView;
	SimpleCursorAdapter mAdapter;
	public static final int NOT_INITIAL = -1;
	private int idColumnIndex = NOT_INITIAL;
	Bundle extra;
	Intent intent;
	ArrayList<String> imagePathList;
	int imagesize_news, imagesize_request;
	public static final String FROMREQUESTSIGN = "imagelist";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.newsalert_layout);
	    Intent ii = getIntent();
	    Bundle b = ii.getExtras();
	    imagesize_news = b.getInt("imagenum");
	    Intent iii = getIntent();
	    Bundle bb = iii.getExtras();
	    imagesize_request = bb.getInt(FROMREQUESTSIGN);
	    
	    gridView=(GridView)findViewById(R.id.newsalertgrid);
	    String[] from={MediaStore.Images.Media._ID};
	    int[] to={R.id.newsalert_pic};
	    mAdapter=new SimpleCursorAdapter(this, R.layout.newsalert_check_item, null, from, to, 0);
	    mAdapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Cursor c, int columnIndex) {
				if (columnIndex == idColumnIndex) {
					ImageView imageView=(ImageView)view;
					long id=c.getLong(columnIndex);
					Bitmap bm=MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
					imageView.setImageBitmap(bm);
					return true;
				}
				return false;
			}
		});
	    
	    gridView.setAdapter(mAdapter);
	    gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
	    getSupportLoaderManager().initLoader(0, null, this);
	    
	    Button btn=(Button)findViewById(R.id.complete);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (gridView.getChoiceMode() == GridView.CHOICE_MODE_MULTIPLE) {
					SparseBooleanArray array=gridView.getCheckedItemPositions();
					imagePathList = new ArrayList<String>();
					
					if (array.size()+imagesize_news>4 || array.size()+imagesize_request>4) {
						Toast.makeText(CallGallertyActivity.this, "사진은 최대 4장까지 업로드할수 있습니다.", Toast.LENGTH_LONG).show();
						gridView.clearChoices();
						mAdapter.notifyDataSetChanged();
					}else {
						for (int i = 0; i < array.size(); i++) {
							int position = array.keyAt(i);
							if (array.get(position)) {
								Cursor c = (Cursor)gridView.getItemAtPosition(position);
								String path=c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
								imagePathList.add(path);
							}
						}
						extra=new Bundle();
					    intent=new Intent();
					    extra.putStringArrayList("multipic", imagePathList);
					    intent.putExtras(extra);
					    setResult(RESULT_OK, intent);
					    finish();
					}
				}
			}
		});
	    
	}
	
	String[] projection={MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		idColumnIndex = data.getColumnIndex(MediaStore.Images.Media._ID);
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}
}
