package com.interest.myapplication.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.interest.myapplication.adapter.NavigationFragmentAdapter;
import com.interest.myapplication.entity.Before;
import com.interest.myapplication.entity.Content;
import com.interest.myapplication.entity.Latest;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.entity.Theme;
import com.interest.myapplication.entity.ThemesListItem;

/**
 * ����JSON�ַ���������
 */
public class ParseJson {
	private Gson gson = new Gson();

	/**
	 * ��������������Ϣ��JSON����
	 * @param json
	 * @return 
	 */
	public Latest parseFirstJson(String json) {
		return gson.fromJson(json, Latest.class);
	}

	/**
	 * ����������Ϣ��JSON����
	 * @param json
	 * @return 
	 */
	public Before parseBeforeJson(String json) {
		return gson.fromJson(json, Before.class);
	}


	/**
	 * ����NavigationFragment��ߵ�������JSON����
	 */
	public List<ThemesListItem> parseThemesJson(String json){
		List<ThemesListItem> themes = new ArrayList<ThemesListItem>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray itemsArray = obj.getJSONArray("others");
			for (int i = 0; i < itemsArray.length(); i++) {
				ThemesListItem newsListItem = new ThemesListItem();
				JSONObject itemObject = itemsArray.getJSONObject(i);
				newsListItem.setTitle(itemObject.getString("name"));
				newsListItem.setId(itemObject.getString("id"));
				themes.add(newsListItem);
			}
			return themes;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����ThemeFragment�����ձ���JSON����
	 */
	public Theme parseThemeJson(String json) {
		return gson.fromJson(json, Theme.class);
	}


	/**
	 * �������ž������ݵ�JSON����
	 */
	public Content parseContentJson(String json){
		return gson.fromJson(json, Content.class);
	}


	public String parseSplashJson(String json){
		String imageUrl = null;
		try {
			JSONObject obj = new JSONObject(json);
			imageUrl = obj.getString("img");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return imageUrl;
	}
}
