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
 * ThemeNewsActivityҵ����
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
						//�����ݴ��뱾��webCache���ݿ⣬��������
						DBUtil.replaceNewsContent(""+themeNewsId, response);
						//����JSON����
						content = parse.parseContentJson(response);
						//���͹㲥֪ͨ����UI
						Intent intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_SUCCESS);
						intent.putExtra("content", content);
						context.sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//��ȡ���ݿ��еĻ�������
						getOfflineData(themeNewsId);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ���ݿ��еĻ�������
			getOfflineData(themeNewsId);
		}
	}

	/**
	 * ��ȡ���ݿ��еĻ�������
	 * @param themeNewsId
	 */
	private void getOfflineData(int themeNewsId) {
		try {
			//��ȡ���ݿ��д洢��JSON����
			String json = DBUtil.getNewsContent(""+themeNewsId);
			Intent intent = null;
			if (json != null) {
				//����JSON����
				content = parse.parseContentJson(json);
				//���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_SUCCESS);
				intent.putExtra("content", content);
			}else {
				//û�и�������,���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_FAILURE);
			}
			context.sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
