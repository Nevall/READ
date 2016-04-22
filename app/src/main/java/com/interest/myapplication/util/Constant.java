package com.interest.myapplication.util;

import android.content.ContentValues;

/**
 * Created by Android on 2016/3/5.
 * 全局常量
 */
public class Constant {
	//url路径
    public static final String BASEURL = "http://news-at.zhihu.com/api/4/";
    public static final String START = "start-image/1080*1776";//�?始欢迎界面地�?
    public static final String THEMES = "themes";//主题日报列表地址
    public static final String LATESTNEWS = "news/latest";//�?新新闻消息地�?
    public static final String BEFORE = "news/before/";//离线新闻消息地址
    public static final String THEMENEWS = "theme/";//主题日报内容地址
    public static final String THEME_BEFORE = "/before/";//过往主题日报内容地址
    public static final String CONTENT = "news/";//具体新闻内容地址
    public static final int TOPIC = 131;//具体新闻内容地址
    
    //MainFragment发�?�广播的ACTION
    public static final String ACTION_LOAD_FIRST_SUCCESS = "action_load_first_success";
    public static final String ACTION_LOAD_FIRST_FAILURE = "action_load_first_failure";
    public static final String ACTION_LOAD_FIRST_OFFLINE_SUCCESS = "action_load_first_offline_success";
    public static final String ACTION_LOAD_FIRST_OFFLINE_FAILURE = "action_load_first_offline_failure";
	public static final String ACTION_LOAD_BEFORE_SUCCESS = "action_load_before_success";
	public static final String ACTION_LOAD_BEFORE_FAILURE = "action_load_before_failure";
	public static final String ACTION_LOAD_BEFORE_OFFLINE_SUCCESS = "action_load_before_offline_success";
    public static final String ACTION_LOAD_BEFORE_OFFLINE_FAILURE = "action_load_before_offline_failure";
    
	//NavigationFragment发�?�广播的ACTION
	public static final String ACTION_LOAD_NAVIGATION_THEMES_SUCCESS = "action_load_navigation_themes_success";
	public static final String ACTION_LOAD_NAVIGATION_THEMES_OFFLINE_SUCCESS = "action_load_navigation_themes_offline_success";
	
	//ThemeFragment发�?�广播的ACTION
	public static final String ACTION_LOAD_THEME_NEWS_SUCCESS = "action_load_theme_news_success";
	public static final String ACTION_LOAD_THEME_NEWS_FAILURE = "action_load_theme_news_failure";
	public static final String ACTION_LOAD_THEME_NEWS_OFFLINE_SUCCESS = "action_load_theme_news_offline_success";
	public static final String ACTION_LOAD_THEME_NEWS_OFFLINE_FAILURE = "action_load_theme_news_offline_failure";
	public static final String ACTION_LOAD_THEME_NEWS_BEFORE_SUCCESS = "action_load_theme_news_before_success";
	public static final String ACTION_LOAD_THEME_NEWS_BEFORE_FAILURE = "action_load_theme_news_before_failure";
	public static final String ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_SUCCESS = "action_load_theme_news_before_offline_success";
    public static final String ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_FAILURE = "action_load_theme_news_before_offline_failure";
    
	//NewsActivity发�?�广播的ACTION
	public static final String ACTION_LOAD_NEWS_CONTENT_SUCCESS = "action_load_news_content_success";
	public static final String ACTION_LOAD_NEWS_CONTENT_FAILURE = "action_load_news_content_failure";
	public static final String ACTION_LOAD_NEWS_CONTENT_OFFLINE_SUCCESS = "action_load_news_content_offline_success";
	public static final String ACTION_LOAD_NEWS_CONTENT_OFFLINE_FAILURE = "action_load_news_content_offline_failure";
	
	//ThemeNewsActivity发�?�广播的ACTION
	public static final String ACTION_LOAD_THEME_NEWS_CONTENT_SUCCESS = "action_load_theme_news_content_success";
	public static final String ACTION_LOAD_THEME_NEWS_CONTENT_FAILURE = "action_load_theme_news_content_failure";
	public static final String ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_SUCCESS = "action_load_theme_news_content_offline_success";
	public static final String ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_FAILURE = "action_load_theme_news_content_offline_failure";
	
	//点击item打开NewsActivity或ThemeNewsActivity的ID
	public static final String NEWS_ACYTIVITY_ID = "news_acytivity_id";
	public static final String THEME_NEWS_ACYTIVITY_ID = "theme_news_acytivity_id";
	
	//Cache.db数据库的表格名和字段�?
	public static final String CACHE_TABLE_NAME = "CacheList";
	public static final String CACHE_COLUMN_ID = "id";
	public static final String CACHE_COLUMN_DATE = "date";
	public static final String CACHE_COLUMN_JSON = "json";
	
	//WebCache.db数据库的表格名和字段�?
	public static final String WEB_CACHE_TABLE_NAME = "WebCache";
	public static final String WEB_CACHE_COLUMN_ID = "id";
	public static final String WEB_CACHE_COLUMN_NEWSID = "newsId";
	public static final String WEB_CACHE_COLUMN_JSON = "json";
	
	public static final String START_LOCATION = "start_location";
    public static final String CACHE = "cache";
    public static final int BASE_COLUMN = 1010;

	/**
	 * Bmob appid
	 */
	public static final String APPID = "e4029d90eb02e61fe4c4f589fcacb567";
}
