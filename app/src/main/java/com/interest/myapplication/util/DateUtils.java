package com.interest.myapplication.util;

import android.content.Context;

import com.interest.myapplication.R;

/**
 * ���ڸ�ʽ������
 */
public class DateUtils {
	/**
	 * �������ڸ�ʽ
	 */
	public static String convertDate(Context context, String date) {
		String result = date.substring(0,4);
		result += context.getString(R.string.year);
        result += date.substring(4, 6);
        result += context.getString(R.string.month);
        result += date.substring(6, 8);
        result += context.getString(R.string.day);
        return result;
	}
}
