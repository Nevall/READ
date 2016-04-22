package com.interest.myapplication.util;

import com.interest.myapplication.MyApplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ���ݿ⹤����
 * @author H
 *
 */
public class DBUtil {

	/**
	 * ������������Ϣ��JSON���ݴ洢�����ݿ���
	 * @param json
	 */
	public static void replaceFirstData(String json){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.CACHE_COLUMN_DATE, Integer.MAX_VALUE);
		contentValues.put(Constant.CACHE_COLUMN_JSON, json);
		db.replace(Constant.CACHE_TABLE_NAME, Constant.CACHE_COLUMN_DATE, contentValues );
		db.close();
	}

	/**
	 * ��ȡ���ݿ��д洢������������ϢJSON����
	 * @return
	 */
	public static String getFirstData(){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		String selection = Constant.CACHE_COLUMN_DATE+" = ?";
		String[] selectionArgs = {""+Integer.MAX_VALUE};
		Cursor cursor = db.query(Constant.CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//�����α�
		if (cursor.moveToFirst()) {
			//��ȡ���ݿ��д洢��JSON����,������
			String json = cursor.getString(cursor.getColumnIndex(Constant.CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else {
			cursor.close();
			db.close();
			return null;
		}
	}

	/**
	 * ������������Ϣ��JSON���ݴ洢�����ݿ���
	 * @param json
	 */
	public static void replaceBeforeData(String date,String json){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.CACHE_COLUMN_DATE,date);
		contentValues.put(Constant.CACHE_COLUMN_JSON, json);
		db.replace(Constant.CACHE_TABLE_NAME, Constant.CACHE_COLUMN_DATE, contentValues );
		db.close();
	}

	/**
	 * ��ȡ���ݿ��д洢������������ϢJSON����
	 * @return
	 */
	public static String getBeforeData(String date){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		String selection = Constant.CACHE_COLUMN_DATE+" = ?";
		String[] selectionArgs = { date };
		Cursor cursor = db.query(Constant.CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//�����α�
		if (cursor.moveToFirst()) {
			//��ȡ���ݿ��д洢��JSON����
			String json = cursor.getString(cursor.getColumnIndex(Constant.CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else{
			//û�и�������
			String whereClause = Constant.CACHE_COLUMN_DATE + " < ?";
			String[] whereArgs = { date };
			db.delete(Constant.CACHE_TABLE_NAME, whereClause, whereArgs);
			cursor.close();
			db.close();
			return null;
		}
	}

	/**
	 * �������ձ�������Ϣ��JSON���ݴ洢�����ݿ���
	 * @param json
	 */
	public static void replaceThemeNewsData(String urlId,String json){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.CACHE_COLUMN_DATE,Constant.BASE_COLUMN+urlId);
		contentValues.put(Constant.CACHE_COLUMN_JSON, json);
		db.replace(Constant.CACHE_TABLE_NAME, Constant.CACHE_COLUMN_DATE, contentValues );
		db.close();
	}

	/**
	 * ��ȡ���ݿ��д洢�������ձ�������ϢJSON����
	 * @return
	 */
	public static String getThemeNewsData(String urlId){
		SQLiteDatabase db = MyApplication.getCacheDBHelper().getWritableDatabase();
		String selection = Constant.CACHE_COLUMN_DATE+" = ?";
		String[] selectionArgs = { Constant.BASE_COLUMN + urlId};
		Cursor cursor = db.query(Constant.CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//�����α�
		if (cursor.moveToFirst()) {
			//��ȡ���ݿ��д洢��JSON����
			String json = cursor.getString(cursor.getColumnIndex(Constant.CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else{
			//û�и�������
			cursor.close();
			db.close();
			return null;
		}
	}

	/**
	 * ��������Ϣ�ľ�������JSON���ݴ洢�����ݿ���
	 * @param newsId
	 * @param json
	 */
	public static void replaceNewsContent(String newsId,String json){
		SQLiteDatabase db = MyApplication.getWebCacheDBHelper().getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constant.WEB_CACHE_COLUMN_NEWSID,newsId);
		contentValues.put(Constant.WEB_CACHE_COLUMN_JSON, json);
		db.replace(Constant.WEB_CACHE_TABLE_NAME, Constant.WEB_CACHE_COLUMN_NEWSID, contentValues);
		db.close();
	}

	/**
	 * ��ȡ���ݿ��д洢�������ձ�������ϢJSON����
	 * @return
	 */
	public static String getNewsContent(String newsId){
		SQLiteDatabase db = MyApplication.getWebCacheDBHelper().getWritableDatabase();
		String selection = Constant.WEB_CACHE_COLUMN_NEWSID+" = ?";
		String[] selectionArgs = { newsId};
		Cursor cursor = db.query(Constant.WEB_CACHE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
		//�����α�
		if (cursor.moveToFirst()) {
			//��ȡ���ݿ��д洢��JSON����
			String json = cursor.getString(cursor.getColumnIndex(Constant.WEB_CACHE_COLUMN_JSON));
			cursor.close();
			db.close();
			return json;
		}else{
			//û�и�������
			cursor.close();
			db.close();
			return null;
		}
	}
}
