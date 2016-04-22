package com.interest.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类,用于存储用户设置数据
 * @author H
 *
 */
public class PreUtil {

	/**
	 * 将数据以SharedPreferences的形式存储
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putStringToDefault(Context context, String key, String value){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 读取SharedPreferences中存储的数据
	 * @param context
	 * @param key
	 * @param defValue
	 */
	public static String getStringFromDefault(Context context ,String key,String defValue){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getString(key, defValue);
	}
}
