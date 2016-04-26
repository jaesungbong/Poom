package com.project.poom.share;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//import com.facebook.FacebookRequestError;
//import com.facebook.HttpMethod;
//import com.facebook.Request;
//import com.facebook.Response;
//import com.facebook.Session;
//import com.facebook.Session.StatusCallback;
//import com.facebook.SessionState;
import com.project.poom.MainActivity;
import com.project.poom.R;

public class ShareDialogFragment extends DialogFragment {
	
	public static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	String imagepath, title, content;
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset){ // 단순한 matching 함수
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}
	
	public ShareDialogFragment(String imagepath, String title, String content){
		this.imagepath = imagepath;
		this.title = title;
		this.content = content;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0); 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.share_dialog_layout, container, false);
		Button btn = (Button)v.findViewById(R.id.face);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Session.openActiveSession(getActivity(), ShareDialogFragment.this, true, new StatusCallback() {
//					
//					@Override
//					public void call(Session session, SessionState state, Exception exception) {
//						if (session.isOpened()) {
//							List<String> permission = session.getPermissions();
//							if (!isSubsetOf(PERMISSIONS, permission)) {
//								session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
//										ShareDialogFragment.this, PERMISSIONS));
//								return;
//							}
//							
//							Bundle postParams = new Bundle();
//							postParams.putString("name", title);
//							postParams.putString("caption", "Cheer Up Animals POOM!");
//							postParams.putString("description", content);
//							postParams.putString("link", "https://developers.facebook.com/android");
//							postParams.putString("picture", "http://54.92.116.151/view/story/image/"+imagepath);
//							Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, new Request.Callback() {
//								
//								@Override
//								public void onCompleted(Response response) {
//									if (response.getGraphObject() != null) {
//										Toast.makeText(getActivity(), "Facebook 공유가 완료되었어요", Toast.LENGTH_SHORT).show();
//										dismiss();
//									}else {
//										FacebookRequestError error = response.getError();
//										Toast.makeText(getActivity(), "error : "+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
//									}
//								}
//							});
//							request.executeAsync();
//						}
//					}
//				});
//			}
//		});
		
		btn = (Button)v.findViewById(R.id.kas);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "카스 공유", Toast.LENGTH_SHORT).show();
				dismiss();
			}
		});
		
		btn = (Button)v.findViewById(R.id.katalk);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "카톡 공유", Toast.LENGTH_SHORT).show();
				dismiss();
			}
		});
		return v;
	}
	
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (Session.getActiveSession() != null) {
//			Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
//		}
//	}
}
