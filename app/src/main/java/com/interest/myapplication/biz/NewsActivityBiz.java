package com.interest.myapplication.biz;

import okhttp3.Call;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.interest.myapplication.entity.Content;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.DBUtil;
import com.interest.myapplication.util.HttpUtils;
import com.interest.myapplication.util.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * NewsActivity业务类
 * @author Android
 */
public class NewsActivityBiz {
	private Context context;
	private Content content;
	private ParseJson parse = new ParseJson();

	public NewsActivityBiz(Context context) {
		this.context = context;
	}

	public void loadNews(final int newsId){
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.CONTENT+newsId;
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//将数据存入本地webCache数据库，用作缓存
						DBUtil.replaceNewsContent(""+newsId, response);
						//解析JSON数据
						content = parse.parseContentJson(response);
						//发送广播通知更新UI
						Intent intent = new Intent(Constant.ACTION_LOAD_NEWS_CONTENT_SUCCESS);
						intent.putExtra("content", content);
						context.sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中的缓存数据
						loadOfflineData(newsId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中的缓存数据
			loadOfflineData(newsId);
		}
	}

	/**
	 * 获取数据库中的缓存数据
	 */
	private void loadOfflineData(int newsId) {
		try {
			//获取数据库中存储的JSON数据
			String json = DBUtil.getNewsContent(""+newsId);
			Intent intent = null;
			if (json != null) {
				//解析JSON数据
				content = parse.parseContentJson(json);
				//发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_SUCCESS);
				intent.putExtra("content", content);
			}else {
				//没有更多数据,发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_NEWS_CONTENT_OFFLINE_FAILURE);
			}
			context.sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
