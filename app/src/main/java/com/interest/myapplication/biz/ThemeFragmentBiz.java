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
 * ThemesFragment��ҵ����
 */
public class ThemeFragmentBiz {
	private Context context;
	private Theme themeNews;
	private ParseJson parse = new ParseJson();

	public ThemeFragmentBiz(Context context) {
		this.context = context;
	}

	/**
	 * ����Http����������������ձ�������Ϣ
	 * @return 
	 */
	public void loadThemeNews(final String urlId) {
		//TODO ���ع���MyApplication��������Ƿ�����
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.THEMENEWS+urlId;
			try {
				((MainActivity)context).sr.setRefreshing(true);
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//�����ݴ��뱾��cache���ݿ⣬��������
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
						//��ȡ���ݿ��еĻ�������
						getThemeNewsOfflineData(urlId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ���ݿ��еĻ�������
			getThemeNewsOfflineData(urlId);
		}
	}

	/**
	 * ��ȡ���ݿ��еĻ�������
	 * @param urlId
	 */
	private void getThemeNewsOfflineData(String urlId) {
		try {
			//��ȡ���ݿ��д洢��JSON����
			String json = DBUtil.getThemeNewsData(urlId);
			Intent intent = null;
			if (json != null) {
				//����JSON���ݲ����أ�
				themeNews = parse.parseThemeJson(json);
				//���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_SUCCESS);
				intent.putExtra("themeNews", themeNews);
			}else {
				//û�и�������,���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ����Http������ع��������ձ�������Ϣ
	 * @return 
	 */
	public void loadMore(final String urlId,final String lastNewsId) {
		//TODO ���ع���MyApplication��������Ƿ�����
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.THEMENEWS+urlId+Constant.THEME_BEFORE+lastNewsId;
			try {
				((MainActivity)context).sr.setRefreshing(true);
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//�����ݴ��뱾��cache���ݿ⣬��������
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
						//��ȡ���ݿ��еĻ�������
						getMoreOfflineData(lastNewsId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ���ݿ��еĻ�������
			getMoreOfflineData(lastNewsId);
		}
	}

	/**
	 * ��ȡ���ݿ��еĻ�������
	 * @param lastNewsId
	 */
	private void getMoreOfflineData(String lastNewsId) {
		try {
			//��ȡ���ݿ��д洢��JSON����
			String json = DBUtil.getThemeNewsData(lastNewsId);
			Intent intent = null;
			if (json != null) {
				//����JSON���ݲ����أ�
				themeNews = parse.parseThemeJson(json);
				//���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_SUCCESS);
				intent.putExtra("themeNews", themeNews);
			}else {
				//û�и�������,���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
