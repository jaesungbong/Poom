package com.project.poom.recharge;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fpang.lib.FpangSession;
import com.project.poom.R;
import com.project.poom.manager.GetEnergy;
import com.project.poom.manager.NetworkManager;
import com.project.poom.manager.NetworkManager.OnResultListener;
import com.project.poom.manager.UserManager;

public class FreeRechargeFragment extends PagerFragment {
	
	ListView listView;
	FreeRechargeItemAdapter mAdapter;
	Button btnPoint, btnGetPointSync;
	ProgressDialog progress;
	int point;
	
	private static int status = 0;
	Handler m_hd = new Handler(){
		public void handleMessage(Message msg){
			Bundle bd = msg.getData();
			status = bd.getInt("status");
		}
		
		private void ToastValue(int status){
			if (status>0) {
				Toast.makeText(getActivity(), "실행형 앱 실행 중", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getActivity(), "게시 상태 조회오류", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.free_recharge_fragment_layout, container, false);
		
		FpangSession.setDebug(true);
		FpangSession.setMarket("tstore");
		FpangSession.setUserId(UserManager.getInstance().getUserId()+"");
		FpangSession.readyAdsyncPopup(getActivity());
		btnPoint = (Button)v.findViewById(R.id.btnPoint);
		btnPoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FpangSession.showAdsyncList(getActivity(), "무료 충전");
			}
		});
				
		
		progress = ProgressDialog.show(getActivity(), "게시 상태 조회 중", "잠시만 기다려주세요.");
		
		new Thread(){
			
			public void run() {
				int status = FpangSession.appStarted(getActivity());
				Bundle bd = new Bundle();
				bd.putInt("status", status);
				Message msg = m_hd.obtainMessage();
				msg.setData(bd);
				m_hd.sendMessage(msg);
				progress.dismiss();
			}
		}.start();
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		new MyPointTask().execute();
	}
	
	
	class MyPointTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			String my_user_id = UserManager.getInstance().getUserId() + "";
			FpangSession.setUserId(my_user_id);
			int point = FpangSession.getUserPoint(getActivity());

			if (point >= 0) {
				Message msg = m_hd.obtainMessage();
				msg.arg1 = point;
				m_hd.sendMessage(msg);
				Log.e("Fpang", "point : " + point);
				
			} else {
				Log.e("AdSync", "조회 오류");
			}
			return point;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			point = result;
			NetworkManager.getInstnace().getEnergy(getActivity(), UserManager.getInstance().getUserId(), 
					point, new OnResultListener<GetEnergy>() {
						
						@Override
						public void onSuccess(GetEnergy result) {
							UserManager.getInstance().setUserEnergy(point);
						}
						
						@Override
						public void onFail(int code) {}
					});
		}
	}
	
	@Override
	public void onDestroy() {
		if (progress != null) {
			progress.dismiss();
			progress = null;
		}
		super.onDestroy();
	}
}
