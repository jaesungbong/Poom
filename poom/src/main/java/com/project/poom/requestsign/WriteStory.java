package com.project.poom.requestsign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.project.poom.R;

public class WriteStory extends ActionBarActivity {

	public static final String WS_TAG = "story";
	Bundle extra;
	Intent intent, intent2;
	EditText story;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.writestory);
	    story = (EditText)findViewById(R.id.writestory);
	    extra = new Bundle();
	    intent = new Intent();
	    intent2 = getIntent();
	    story.setText(intent2.getExtras().getString("edit"));
	    getActionBar().setTitle("글쓰기");
	    getSupportActionBar().setDisplayShowHomeEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.writestory, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ws_complete:
			extra.putString(WS_TAG, story.getText().toString());
		    intent.putExtras(extra);
		    setResult(RESULT_OK, intent);
		    finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
