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
 * 解析JSON字符串工具类
 */
public class ParseJson {
	private Gson gson = new Gson();

	/**
	 * 解析最新新闻消息的JSON数据
	 * @param json
	 * @return 
	 */
	public Latest parseFirstJson(String json) {
		return gson.fromJson(json, Latest.class);
	}

	/**
	 * 解析过往消息的JSON数据
	 * @param json
	 * @return 
	 */
	public Before parseBeforeJson(String json) {
		return gson.fromJson(json, Before.class);
	}


	/**
	 * 解析NavigationFragment侧边导航栏的JSON数据
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
	 * 解析ThemeFragment主题日报的JSON数据
	 */
	public Theme parseThemeJson(String json) {
		return gson.fromJson(json, Theme.class);
	}


	/**
	 * 解析新闻具体内容的JSON数据
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
