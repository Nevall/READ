package com.interest.myapplication.biz;

import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.interest.myapplication.entity.ThemesListItem;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.HttpUtils;
import com.interest.myapplication.util.ParseJson;
import com.interest.myapplication.util.PreUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * NavigationFragment��ҵ����
 * @author Android
 *
 */
public class NavigationFragmentBiz {
	private Context context;
	private List<ThemesListItem> themes;
	private ParseJson parse = new ParseJson();

	public NavigationFragmentBiz(Context context) {
		this.context = context;
	}

	/**
	 * ����Http�����������������Ϣ
	 * @return 
	 */
	public void loadThemes() {
		//TODO ���ع���MyApplication��������Ƿ�����
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				String url = Constant.BASEURL+Constant.THEMES;
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//�����ݴ���SharedPreferences���ݿ⣬��������
						PreUtil.putStringToDefault(context, Constant.THEMES, response);
						themes = parse.parseThemesJson(response);
						Intent intent = new Intent(Constant.ACTION_LOAD_NAVIGATION_THEMES_SUCCESS);
						intent.putExtra("themes", (Serializable)themes);
						LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
					}
					@Override
					public void onError(Call arg0, Exception arg1) {
						//��ȡ���ݿ��������ձ��б�Ļ�������
						getOfflineData();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ���ݿ��������ձ��б�Ļ�������
			getOfflineData();
		}
	}

	/**
	 * ��ȡ���ݿ��������ձ��б�Ļ�������
	 */
	private void getOfflineData() {
		try {
			//��SharedPreferences���ݿ��л�ȡ����
			String json = PreUtil.getStringFromDefault(context, Constant.THEMES, "");
			if (!"".equals(json)) {
				themes = parse.parseThemesJson(json);
				Intent intent = new Intent(Constant.ACTION_LOAD_NAVIGATION_THEMES_SUCCESS);
				intent.putExtra("themes", (Serializable)themes);
				LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
			}else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
