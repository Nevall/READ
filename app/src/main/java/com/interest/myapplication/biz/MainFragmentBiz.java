package com.interest.myapplication.biz;


import okhttp3.Call;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.interest.myapplication.MyApplication;
import com.interest.myapplication.activity.MainActivity;
import com.interest.myapplication.entity.Before;
import com.interest.myapplication.entity.Latest;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.DBUtil;
import com.interest.myapplication.util.HttpUtils;
import com.interest.myapplication.util.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * MainFragment的业务类
 */
public class MainFragmentBiz {
	private Context context;
	private Latest latest;
	private Before before;
	private ParseJson parse = new ParseJson();

	public MainFragmentBiz(Context context) {
		this.context = context;
	}

	/**
	 * 发送Http请求加载最新新闻消息
	 * @return 
	 */
	public void loadFirst() {
		//TODO 可重构在MyApplication检查网络是否连接
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.LATESTNEWS;
			try {
				((MainActivity)context).sr.setRefreshing(true);
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//将数据存入本地cache数据库，用作缓存
							DBUtil.replaceFirstData(response);
							//解析JSON数据
							latest = parse.parseFirstJson(response);
							//发送广播通知更新UI
							Intent intent = new Intent(Constant.ACTION_LOAD_FIRST_SUCCESS);
							intent.putExtra("latest", latest);
							LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中最新新闻消息缓存数据
						getFirstOfflineData();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中最新新闻消息缓存数据
			getFirstOfflineData();
		}
	}

	/**
	 * 获取数据库中最新新闻消息缓存数据
	 */
	private void getFirstOfflineData() {
		try {
			//获取数据库中存储的JSON数据
			String json = DBUtil.getFirstData();
			Intent intent = null;
			if (json != null) {
				//解析JSON数据并返回；
				latest = parse.parseFirstJson(json);
				//发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS);
				intent.putExtra("latest", latest);
			}else {
				//没有更多数据,发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 发送Http请求加载过往消息
	 * @return 
	 */
	public void loadMore(final String date){
		String url = Constant.BASEURL+Constant.BEFORE+date;
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//将数据存入本地cache数据库，用作缓存
						DBUtil.replaceBeforeData(date, response);
						//解析JSON数据
						before = parse.parseBeforeJson(response);
						//发送广播通知更新UI
						Intent intent = new Intent(Constant.ACTION_LOAD_BEFORE_SUCCESS);
						intent.putExtra("before", before);
						LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//获取数据库中最新新闻消息缓存数据
						getBeforeOfflineData(date);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//获取数据库中最新新闻消息缓存数据
			getBeforeOfflineData(date);
		}
	}

	/**
	 * 获取数据库中过往的新闻消息缓存数据
	 */
	private void getBeforeOfflineData(String date) {
		try {
			//获取数据库中存储的JSON数据
			String json = DBUtil.getBeforeData(date);
			Intent intent = null;
			if (json != null) {
				//解析JSON数据并返回；
				before = parse.parseBeforeJson(json);
				//发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS);
				intent.putExtra("before", before);
			}else {
				//没有更多数据,发送广播通知更新UI
				intent = new Intent(Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
