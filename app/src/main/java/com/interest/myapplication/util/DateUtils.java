package com.interest.myapplication.util;
/**
 * ���ڸ�ʽ������
 */
public class DateUtils {
	/**
	 * �������ڸ�ʽ
	 */
	public static String convertDate(String date) {
		String result = date.substring(0,4);
		result += "��";
        result += date.substring(4, 6);
        result += "��";
        result += date.substring(6, 8);
        result += "��";
        return result;
	}

}
