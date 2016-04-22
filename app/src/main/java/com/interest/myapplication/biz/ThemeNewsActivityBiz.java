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
 * ThemeNewsActivity业务类
 * @author Android
 */
public class ThemeNewsActivityBiz {
	private Context context;
	private Content content;
	private ParseJson parse = new ParseJson();

	public ThemeNewsActivityBiz(Context context) {
		this.context = context;
	}

	public void loadThemeNews(final int themeNewsId){
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.CONTENT+themeNewsId;
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//将数据存入本地webCache数据库，用作缓存
						DBUtil.replaceNewsContent(""+themeNewsId, response);
						//解析JSON数据
						content = parse.parseContentJson(response);
						//发送广播通知更新UI
						Intent intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_SUCCESS);
						intent.putExtra("content", content);
						context.sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中的缓存数据
						getOfflineData(themeNewsId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中的缓存数据
			getOfflineData(themeNewsId);
		}
	}

	/**
	 * 获取数据库中的缓存数据
	 * @param themeNewsId
	 */
	private void getOfflineData(int themeNewsId) {
		try {
			//获取数据库中存储的JSON数据
			String json = DBUtil.getNewsContent(""+themeNewsId);
			Intent intent = null;
			if (json != null) {
				//解析JSON数据
				content = parse.parseContentJson(json);
				//发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_SUCCESS);
				intent.putExtra("content", content);
			}else {
				//没有更多数据,发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_FAILURE);
			}
			context.sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
