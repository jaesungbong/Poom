package com.project.poom.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import org.apache.http.Header;

import android.content.Context;
import android.renderscript.Element;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.project.poom.MyApplication;

public class NetworkManager {
	private static NetworkManager instance;

	public static NetworkManager getInstnace() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}

	AsyncHttpClient client;

	private NetworkManager() { // 쿠키 저장소: 로그인 상태 관리 유지하는데 사용함.
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory socketFactory = new MySSLSocketFactory(
					trustStore);
			socketFactory
					.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client = new AsyncHttpClient();
			client.setSSLSocketFactory(socketFactory);
			client.setCookieStore(new PersistentCookieStore(MyApplication
					.getContext()));
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block/
			e.printStackTrace();
		}
		// client.setTimeout(30000);
	}

	public interface OnResultListener<T> { // 요청시 응답하는 리스너
		public void onSuccess(T result);

		public void onFail(int code);
	}

	public static final String SERVER = "http://54.92.116.151";

	public static final String URL_JOIN = SERVER + "/join";

	public void join(Context context, String email, String password,
			String nickname, final OnResultListener<UserInfoResult> listener) {
		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("password", password);
		params.put("nick", nickname);
		client.post(context, URL_JOIN, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				UserInfoResult result = gson.fromJson(responseString,
						UserInfoResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_LOGIN = SERVER + "/login";

	public void login(Context context, String email, String password,
			String registrationId,
			final OnResultListener<UserInfoResult> listener) {
		RequestParams params = new RequestParams();
		params.put("email", email + "");
		params.put("password", password + "");
		params.put("registrationId", registrationId);
		client.post(context, URL_LOGIN, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				UserInfoResult result = gson.fromJson(responseString,
						UserInfoResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_LOGOUT = SERVER + "/logout";

	public void logout(Context context,
			final OnResultListener<StatusResult> listener) {
		RequestParams params = new RequestParams();
		client.post(context, URL_LOGOUT, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				StatusResult result = gson.fromJson(responseString,
						StatusResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_MODIFY_USER = SERVER + "/user/%s/alter";

	public void modifyUser(Context context, int userId, String nick,
			String status, String imagepath,
			final OnResultListener<StatusResult> listener) {
		String url = String.format(URL_MODIFY_USER, userId);
		RequestParams params = new RequestParams();
		params.put("nick", nick);
		params.put("status", status);
		try {
			if (imagepath.equals("null")) {
				params.put("images", "null");
			} else {
				params.put("images", new File(imagepath));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				StatusResult result = gson.fromJson(responseString,
						StatusResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_CALL_USER_INFO = SERVER + "/user/%s/view";

	public void callUserInfo(Context context, int userId,
			final OnResultListener<CallUserInfo> listener) {
		String url = String.format(URL_CALL_USER_INFO, userId);
		RequestParams params = new RequestParams();
		params.put("user_id", userId);
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				CallUserInfo result = gson.fromJson(responseString,
						CallUserInfo.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_NEWSPEED_LOG = SERVER
			+ "/user/%s/newsfeed/viewlist";

	public void newSpeedLog(Context context, int userId,
			final OnResultListener<NewSpeedLog> listener) {
		String url = String.format(URL_NEWSPEED_LOG, userId);
		RequestParams params = new RequestParams();
		params.put("user_id", userId);
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				NewSpeedLog result = gson.fromJson(responseString,
						NewSpeedLog.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_AWARD_BADGE = SERVER
			+ "/user/%s/badge/%s/collect";

	public void awardBadge(Context context, int userId, int badgeId,
			final OnResultListener<AwardBadge> listener) {
		String url = String.format(URL_AWARD_BADGE, userId, badgeId);
		RequestParams params = new RequestParams();
		params.put("user_id", userId);
		params.put("badge_id", badgeId);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				AwardBadge result = gson.fromJson(responseString,
						AwardBadge.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_BADGE_LIST = SERVER
			+ "/user/%s/badge/viewlist";

	public void badgeList(Context context, int userId,
			final OnResultListener<BadgeList> listener) {
		String url = String.format(URL_BADGE_LIST, userId);
		RequestParams params = new RequestParams();
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				BadgeList result = gson.fromJson(responseString,
						BadgeList.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_DONATE_STORY = SERVER
			+ "/story/%s/user/%s/donate";

	public void donatestory(Context context, int storyId, int userId,
			int donateEnergy, final OnResultListener<DonateStory> listener) {
		String url = String.format(URL_DONATE_STORY, storyId, userId);
		RequestParams params = new RequestParams();
		params.put("donate_energy", "" + donateEnergy);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				DonateStory result = gson.fromJson(responseString,
						DonateStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_ENROLL_INTERESTSTY = SERVER
			+ "/story/%s/user/%s/attention";

	public void enrollIntereststy(Context context, int storyId, int userId,
			final OnResultListener<EnrollIntereststy> listener) {
		String url = String.format(URL_ENROLL_INTERESTSTY, storyId, userId);
		RequestParams params = new RequestParams();
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				EnrollIntereststy result = gson.fromJson(responseString,
						EnrollIntereststy.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_SIGN_STORY = SERVER
			+ "/story/%s/user/%s/sign";

	public void signStory(Context context, int storyId, int userId,
			final OnResultListener<SignStory> listener) {
		String url = String.format(URL_SIGN_STORY, storyId, userId);
		RequestParams params = new RequestParams();
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				SignStory result = gson.fromJson(responseString,
						SignStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_ENROLL_STORY = SERVER
			+ "/story/user/%s/enroll";

	public void enrollStory(Context context, int userId, String title,
			String content, String plan, int goalFund, String endFundDate,
			ArrayList<String> imageList,
			final OnResultListener<EnrollStory> listener)
			throws FileNotFoundException {
		String url = String.format(URL_ENROLL_STORY, userId);
		RequestParams params = new RequestParams();
		params.put("title", title);
		params.put("content", content);
		params.put("plan", plan);
		params.put("goal_fund", "" + goalFund);
		params.put("end_fund_date", endFundDate);
		for (String path : imageList) {
			params.put("images", new File(path));
		}
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				EnrollStory result = gson.fromJson(responseString,
						EnrollStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_DELETE_STORY = SERVER + "/story/%s/delete";

	public void deleteStory(Context context, int storyId,
			final OnResultListener<DeleteStory> listener) {
		String url = String.format(URL_DELETE_STORY, storyId);
		RequestParams params = new RequestParams();
		params.put("story_id", storyId);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				DeleteStory result = gson.fromJson(responseString,
						DeleteStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_CALL_SIGNLIST = SERVER + "/sign/viewlist/order/%s";

	public void callSignList(Context context, int order, 
			final OnResultListener<CallSignList> listener) {
		String url = String.format(URL_CALL_SIGNLIST, order);
		RequestParams params = new RequestParams();
		client.get(context, url, params,	new TextHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						Gson gson = new Gson();
						CallSignList result = gson.fromJson(responseString,
								CallSignList.class);
						listener.onSuccess(result);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						listener.onFail(statusCode);
					}
				});
	}

	public static final String URL_CALL_STORYLIST = SERVER + "/story/viewlist/order/%s";

	public void callStoryList(Context context, int order,
			final OnResultListener<CallStoryList> listener) {
		String url = String.format(URL_CALL_STORYLIST, order);
		RequestParams params = new RequestParams();
		client.get(context, url, params,	new TextHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						Gson gson = new Gson();
						CallStoryList result = gson.fromJson(responseString,
								CallStoryList.class);
						listener.onSuccess(result);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						listener.onFail(statusCode);
					}
				});
	}

	public static final String URL_CALL_STORY = SERVER + "/story/%s/view/%s";

	public void callStory(Context context, int storyId, int userId,
			final OnResultListener<CallStory> listener) {
		String url = String.format(URL_CALL_STORY, storyId, userId);
		RequestParams params = new RequestParams();
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				CallStory result = gson.fromJson(responseString,
						CallStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_ENROLL_REP_AT_STORY = SERVER
			+ "/story/%s/comment/user/%s/enroll";

	public void enrollRepAtStory(Context context, int storyId, int userId,
			String content, final OnResultListener<EnrollRepAtStory> listener) {
		String url = String.format(URL_ENROLL_REP_AT_STORY, storyId, userId);
		RequestParams params = new RequestParams();
		params.put("story_id", storyId);
		params.put("user_id", userId);
		params.put("content", content);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				EnrollRepAtStory result = gson.fromJson(responseString,
						EnrollRepAtStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_DELETE_REP_AT_STORY = SERVER
			+ "/story/comment/%s/delete";

	public void deleteRepAtStory(Context context, int commentId,
			final OnResultListener<DeleteRepAtStory> lisetener) {
		String url = String.format(URL_DELETE_REP_AT_STORY, commentId);
		RequestParams params = new RequestParams();
		params.put("comment_id", commentId);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				DeleteRepAtStory result = gson.fromJson(responseString,
						DeleteRepAtStory.class);
				lisetener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				lisetener.onFail(statusCode);
			}
		});
	}

	public static final String URL_ENROLL_NEWS_AT_STORY = SERVER
			+ "/story/%s/news/enroll";

	public void enrollNewsAtStory(Context context, int storyId, int userId, String content,
			ArrayList<String> imageList,
			final OnResultListener<EnrollNewsAtStory> listener)
			throws FileNotFoundException {
		String url = String.format(URL_ENROLL_NEWS_AT_STORY, storyId);
		RequestParams params = new RequestParams();
		params.put("user_id", ""+userId);
		if (imageList.size() == 0) {
			params.put("content", content);
		} else {
			params.put("content", content);
			for (String path : imageList) {
				params.put("images", new File(path));
			}
		}
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				EnrollNewsAtStory result = gson.fromJson(responseString,
						EnrollNewsAtStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_VIEW_NEWSLIST = SERVER
			+ "/story/%s/news/viewlist";

	public void viewNewsList(Context context, int storyId,
			final OnResultListener<ViewNewsList> listener) {
		String url = String.format(URL_VIEW_NEWSLIST, storyId);
		RequestParams params = new RequestParams();
		params.put("story_id", storyId);
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				ViewNewsList result = gson.fromJson(responseString,
						ViewNewsList.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_VIEW_NEWS = SERVER + "/story/news/%s/view";

	public void viewNews(Context context, int newsId,
			final OnResultListener<ViewNews> listener) {
		String url = String.format(URL_VIEW_NEWS, newsId);
		RequestParams params = new RequestParams();
		params.put("news_id", newsId);
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				ViewNews result = gson.fromJson(responseString, ViewNews.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_DELETE_NEWS = SERVER
			+ "/story/news/%s/delete";

	public void deleteNews(Context context, int newsId,
			final OnResultListener<DeleteNews> listener) {
		String url = String.format(URL_DELETE_NEWS, newsId);
		RequestParams params = new RequestParams();
		params.put("news_id", newsId);
		client.delete(context, url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				DeleteNews result = gson.fromJson(responseString,
						DeleteNews.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_ENROLL_REP_AT_NEWS = SERVER
			+ "/story/news/%s/comment/user/%s/enroll";

	public void enrollRepAtNews(Context context, int newsId, int userId,
			String content, final OnResultListener<EnrollRepAtNews> listener) {
		String url = String.format(URL_ENROLL_REP_AT_NEWS, newsId, userId,
				content);
		RequestParams params = new RequestParams();
		params.put("news_id", newsId);
		params.put("user_id", userId);
		params.put("content", content);
		client.put(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				EnrollRepAtNews result = gson.fromJson(responseString,
						EnrollRepAtNews.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_DELETE_REP_AT_NEWS = SERVER
			+ "/story/news/%s/comment/%s/delete";

	public void deleteRepAtNews(Context context, int newsId, int commentId,
			final OnResultListener<DeleteRepAtNews> listener) {
		String url = String.format(URL_DELETE_REP_AT_NEWS, newsId, commentId);
		RequestParams params = new RequestParams();
		params.put("news_id", newsId);
		params.put("comment_id", commentId);
		client.delete(context, url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				DeleteRepAtNews result = gson.fromJson(responseString,
						DeleteRepAtNews.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_CHECK_EMAIL = SERVER + "/user/checkemail";

	public void checkEmailDuplication(Context context, String email,
			final OnResultListener<CheckEmailDuplication> listener) {
		String url = String.format(URL_CHECK_EMAIL, email);
		RequestParams params = new RequestParams();
		params.put("email", email);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				CheckEmailDuplication result = gson.fromJson(responseString,
						CheckEmailDuplication.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});

	}

	public static final String URL_CHECK_NICK = SERVER + "/user/checknick";

	public void checkNickDuplication(Context context, String nick,
			final OnResultListener<CheckNickDuplication> listener) {
		String url = String.format(URL_CHECK_NICK, nick);
		RequestParams params = new RequestParams();
		params.put("nick", nick);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				CheckNickDuplication result = gson.fromJson(responseString,
						CheckNickDuplication.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_DONATE_VIEWLIST = SERVER+ "/story/%s/donate/viewlist";

	public void callDonViewList(Context context, int storyId,final OnResultListener<CallDonViewList> listener) {
		String url = String.format(URL_DONATE_VIEWLIST, storyId);
		RequestParams params = new RequestParams();
		params.put("story_id", storyId);
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				CallDonViewList result = gson.fromJson(responseString,
						CallDonViewList.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_CALL_DONATION_ACTIVITY = SERVER
			+ "/user/%s/donate/viewlist";

	public void callDonActList(Context context, int userId,
			final OnResultListener<CallDonActList> listener) {
		String url = String.format(URL_CALL_DONATION_ACTIVITY, userId);
		RequestParams params = new RequestParams();
		params.put("user_id", userId);
		client.get(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				CallDonActList result = gson.fromJson(responseString,
						CallDonActList.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_ENROLL_ATTENTION = SERVER
			+ "/story/%s/user/%s/attention";

	public void enrollAttention(Context context, int storyId, int userId,
			final OnResultListener<EnrollAttention> listener) {
		String url = String.format(URL_ENROLL_ATTENTION, storyId, userId);
		RequestParams params = new RequestParams();
		// params.put("story_id",storyId);
		// params.put("user_id", userId);
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				EnrollAttention result = gson.fromJson(responseString,
						EnrollAttention.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_MY_STORY = SERVER
			+ "/user/%s/mystory/viewlist";

	public void myStory(Context context, int userId,
			final OnResultListener<MyStory> listener) {
		String url = String.format(URL_MY_STORY, userId);
		client.get(context, url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				MyStory result = gson.fromJson(responseString, MyStory.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_MY_ATTENTION = SERVER
			+ "/user/%s/myattention/viewlist";

	public void myAttention(Context context, int userId,
			final OnResultListener<MyAttention> listener) {
		String url = String.format(URL_MY_ATTENTION, userId);
		client.get(context, url, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				MyAttention result = gson.fromJson(responseString,
						MyAttention.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	public static final String URL_GET_ENERGY = SERVER
			+ "/user/%s/energy/%s/get";

	public void getEnergy(Context context, int userId, int point,
			final OnResultListener<GetEnergy> listener) {
		String url = String.format(URL_GET_ENERGY, userId, point);
		RequestParams params = new RequestParams();
		client.post(context, url, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				GetEnergy result = gson.fromJson(responseString,
						GetEnergy.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
}