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
 * MainFragment��ҵ����
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
	 * ����Http�����������������Ϣ
	 * @return 
	 */
	public void loadFirst() {
		//TODO ���ع���MyApplication��������Ƿ�����
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.LATESTNEWS;
			try {
				((MainActivity)context).sr.setRefreshing(true);
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//�����ݴ��뱾��cache���ݿ⣬��������
							DBUtil.replaceFirstData(response);
							//����JSON����
							latest = parse.parseFirstJson(response);
							//���͹㲥֪ͨ����UI
							Intent intent = new Intent(Constant.ACTION_LOAD_FIRST_SUCCESS);
							intent.putExtra("latest", latest);
							LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//��ȡ���ݿ�������������Ϣ��������
						getFirstOfflineData();
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ���ݿ�������������Ϣ��������
			getFirstOfflineData();
		}
	}

	/**
	 * ��ȡ���ݿ�������������Ϣ��������
	 */
	private void getFirstOfflineData() {
		try {
			//��ȡ���ݿ��д洢��JSON����
			String json = DBUtil.getFirstData();
			Intent intent = null;
			if (json != null) {
				//����JSON���ݲ����أ�
				latest = parse.parseFirstJson(json);
				//���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS);
				intent.putExtra("latest", latest);
			}else {
				//û�и�������,���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * ����Http������ع�����Ϣ
	 * @return 
	 */
	public void loadMore(final String date){
		String url = Constant.BASEURL+Constant.BEFORE+date;
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {

					@Override
					public void onResponse(String response) {
						//�����ݴ��뱾��cache���ݿ⣬��������
						DBUtil.replaceBeforeData(date, response);
						//����JSON����
						before = parse.parseBeforeJson(response);
						//���͹㲥֪ͨ����UI
						Intent intent = new Intent(Constant.ACTION_LOAD_BEFORE_SUCCESS);
						intent.putExtra("before", before);
						LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						//��ȡ���ݿ�������������Ϣ��������
						getBeforeOfflineData(date);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			//��ȡ���ݿ�������������Ϣ��������
			getBeforeOfflineData(date);
		}
	}

	/**
	 * ��ȡ���ݿ��й�����������Ϣ��������
	 */
	private void getBeforeOfflineData(String date) {
		try {
			//��ȡ���ݿ��д洢��JSON����
			String json = DBUtil.getBeforeData(date);
			Intent intent = null;
			if (json != null) {
				//����JSON���ݲ����أ�
				before = parse.parseBeforeJson(json);
				//���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS);
				intent.putExtra("before", before);
			}else {
				//û�и�������,���͹㲥֪ͨ����UI
				intent = new Intent(Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE);
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
