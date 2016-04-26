package com.project.poom;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.project.poom.badge.BadgeActivity;
import com.project.poom.detailsign.DetailSignActivity;
import com.project.poom.detailstory.DetailStoryActivity;
import com.project.poom.profile.ProfileActivity;

public class GcmIntentService extends IntentService {
	private static final String TAG = "GcmIntengService";
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				// sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				// sendNotification("Deleted messages on server: " +
				// extras.toString());
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {
				// This loop represents the service doing some work.
				String title = intent.getStringExtra("title");
				String type = intent.getStringExtra("type");
				String story_id = intent.getStringExtra("story_id");

				// sendNotification("Received: " + extras.toString());
				Toast.makeText(MyApplication.getContext(), title,Toast.LENGTH_SHORT);
				sendNotification(title, type, story_id);
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String title, String type, String story_id) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.poom_logo);
		int integerType = Integer.parseInt(type);
		int integerStoryId = Integer.parseInt(story_id);

		String ticker = null;
		String contentTitle = title;
		String contentText = null;
		Intent i = null;

		switch (integerType) {
		case 1:
			ticker = title + "에 새로운 글이 등록되었어요";
			contentText = "새로운 글이 등록되었어요";
			i = new Intent(this, DetailStoryActivity.class);
			i.putExtra(DetailStoryActivity.DTSA, integerStoryId);
			break;

		case 2:
			ticker = title + "에 펀딩이 완료되었습니다!";
			contentText = "펀딩이 완료되었습니다!";
			i = new Intent(this, DetailStoryActivity.class);
			i.putExtra(DetailStoryActivity.DTSA, integerStoryId);
			break;

		case 3:
			ticker = title + "의 서명이 완료되었습니다!";
			contentText = "서명이 완료되었습니다!";
			i = new Intent(this, DetailSignActivity.class);
			i.putExtra(DetailSignActivity.DETAILSIGNTAG, integerStoryId);
			break;

		case 4:
			ticker = "작성하신" + title + "의 서명이 완료 되었습니다.";
			contentText = "작성하신 서명이 완료되었습니다.";
			i = new Intent(this, DetailSignActivity.class);
			i.putExtra(DetailSignActivity.DETAILSIGNTAG, integerStoryId);
			break;

		case 5:
			ticker = "작성하신" + title + "의 기부가 시작했습니다.";
			contentText = "작성하신 사연의 기부가 시작했습니다.";
			i = new Intent(this, DetailStoryActivity.class);
			i.putExtra(DetailStoryActivity.DTSA, integerStoryId);
			break;

		case 6:
			ticker = "작성하신" + title + "의 기부가 완료 되었습니다.";
			contentText = "작성하신 사연의 기부가 완료 되었습니다.";
			i = new Intent(this, DetailStoryActivity.class);
			i.putExtra(DetailStoryActivity.DTSA, integerStoryId);
			break;

		case 7:
			ticker = title + "훈장이 발급되었어요. 직접 확인해보세요!";
			contentText = "훈장이 발급 되었어요. 직접 확인해 보세요!";
			i = new Intent(this, BadgeActivity.class);
			break;
		}
		builder.setTicker(ticker);
		builder.setContentTitle(contentTitle);
		builder.setContentText(contentText);
		builder.setWhen(System.currentTimeMillis());

		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		builder.setDefaults(Notification.DEFAULT_ALL);
		builder.setAutoCancel(true);

		mNotificationManager.notify(0, builder.build());
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
}
