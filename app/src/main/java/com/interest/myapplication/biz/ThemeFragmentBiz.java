package com.interest.myapplication.biz;

import okhttp3.Call;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.interest.myapplication.activity.MainActivity;
import com.interest.myapplication.entity.Theme;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.DBUtil;
import com.interest.myapplication.util.HttpUtils;
import com.interest.myapplication.util.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * ThemesFragment的业务类
 */
public class ThemeFragmentBiz {
	private Context context;
	private Theme themeNews;
	private ParseJson parse = new ParseJson();

	public ThemeFragmentBiz(Context context) {
		this.context = context;
	}

	/**
	 * 发送Http请求加载最新主题日报新闻消息
	 * @return 
	 */
	public void loadThemeNews(final String urlId) {
		//TODO 可重构在MyApplication检查网络是否连接
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.THEMENEWS+urlId;
			try {
				((MainActivity)context).sr.setRefreshing(true);
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//将数据存入本地cache数据库，用作缓存
							DBUtil.replaceThemeNewsData(urlId, response);
							themeNews = parse.parseThemeJson(response);
							Intent intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_SUCCESS);
							intent.putExtra("themeNews", themeNews);
							LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中的缓存数据
						getThemeNewsOfflineData(urlId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中的缓存数据
			getThemeNewsOfflineData(urlId);
		}
	}

	/**
	 * 获取数据库中的缓存数据
	 * @param urlId
	 */
	private void getThemeNewsOfflineData(String urlId) {
		try {
			//获取数据库中存储的JSON数据
			String json = DBUtil.getThemeNewsData(urlId);
			Intent intent = null;
			if (json != null) {
				//解析JSON数据并返回；
				themeNews = parse.parseThemeJson(json);
				//发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_SUCCESS);
				intent.putExtra("themeNews", themeNews);
			}else {
				//没有更多数据,发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送Http请求加载过往主题日报新闻消息
	 * @return 
	 */
	public void loadMore(final String urlId,final String lastNewsId) {
		//TODO 可重构在MyApplication检查网络是否连接
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.THEMENEWS+urlId+Constant.THEME_BEFORE+lastNewsId;
			try {
				((MainActivity)context).sr.setRefreshing(true);
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//将数据存入本地cache数据库，用作缓存
							DBUtil.replaceThemeNewsData(lastNewsId, response);
							themeNews = parse.parseThemeJson(response);
							Intent intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_SUCCESS);
							intent.putExtra("themeNews", themeNews);
							LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中的缓存数据
						getMoreOfflineData(lastNewsId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中的缓存数据
			getMoreOfflineData(lastNewsId);
		}
	}

	/**
	 * 获取数据库中的缓存数据
	 * @param lastNewsId
	 */
	private void getMoreOfflineData(String lastNewsId) {
		try {
			//获取数据库中存储的JSON数据
			String json = DBUtil.getThemeNewsData(lastNewsId);
			Intent intent = null;
			if (json != null) {
				//解析JSON数据并返回；
				themeNews = parse.parseThemeJson(json);
				//发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_SUCCESS);
				intent.putExtra("themeNews", themeNews);
			}else {
				//没有更多数据,发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
