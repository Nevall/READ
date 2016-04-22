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
 * NavigationFragment的业务类
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
	 * 发送Http请求加载最新新闻消息
	 * @return 
	 */
	public void loadThemes() {
		//TODO 可重构在MyApplication检查网络是否连接
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				String url = Constant.BASEURL+Constant.THEMES;
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//将数据存入SharedPreferences数据库，用作缓存
						PreUtil.putStringToDefault(context, Constant.THEMES, response);
						themes = parse.parseThemesJson(response);
						Intent intent = new Intent(Constant.ACTION_LOAD_NAVIGATION_THEMES_SUCCESS);
						intent.putExtra("themes", (Serializable)themes);
						LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
					}
					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中主题日报列表的缓存数据
						getOfflineData();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中主题日报列表的缓存数据
			getOfflineData();
		}
	}

	/**
	 * 获取数据库中主题日报列表的缓存数据
	 */
	private void getOfflineData() {
		try {
			//从SharedPreferences数据库中获取数据
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
