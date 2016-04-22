package com.interest.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.interest.myapplication.Dao.StoriesEntityDao;
import com.interest.myapplication.biz.ThemeNewsActivityBiz;
import com.interest.myapplication.entity.Content;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.util.Constant;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class ThemeNewsActivity extends AppCompatActivity{
	private Toolbar toolbar;
	private StoriesEntity storiesEntity;
	private WebView mWebView;
	private ThemeNewsActivityBiz biz;
	private TNABroadcastReceiver receiver;
	private Content newsContent;
	private StoriesEntityDao dao;
	private FloatingActionButton fabShare;
	private FloatingActionButton fabOther;
	private FloatingActionButton fabSave;
	private Animation goneAnimation;
	private Animation showAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_theme_news_base);
		dao = new StoriesEntityDao(this);
		biz = new ThemeNewsActivityBiz(this);
		//注册广播接收�?
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_FAILURE);
		receiver = new TNABroadcastReceiver();
		registerReceiver(receiver,intentFilter);

		//获取冲MainActivity传�?�的数据
		storiesEntity = (StoriesEntity)getIntent().getSerializableExtra("storiesEntity");
		initView();//初始化控�?
		setListener();//设置监听�?
		loadData();//发�?�HTTP请求获取数据
	}

	/**
	 * 初始化控�?
	 */
	private void initView() {
		fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
		fabOther = (FloatingActionButton) findViewById(R.id.fabOther);
		fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
		goneAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fab_gone);
		showAnimation = AnimationUtils.loadAnimation(this,R.anim.anim_fab_show);	
		//初始Toolbar并设置左上角图标可点�?
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setTitle(storiesEntity.getTitle());
		//初始化WebView
		mWebView = (WebView)findViewById(R.id.webview);
		//设置支持JavaScript
		mWebView.getSettings().setJavaScriptEnabled(true);
		//设置WebView的缓存模�?(这个方式不论如何都会从缓存中加载�?
		//除非缓存中的网页过期，出现的问题就是打开动�?�网页时，不能时时更新，会出现上次打�?过的状�?�，除非清除缓存)
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//�?启DOM storage API 实现数据缓存功能
		mWebView.getSettings().setDomStorageEnabled(true);
		//�?启Application Cache 实现页面缓存功能
		mWebView.getSettings().setAppCacheEnabled(true);
		//�?启Database storage 实现数据库数据缓存功�?
		mWebView.getSettings().setDatabaseEnabled(true);
	}


	/**
	 * 设置监听�?
	 */
	private void setListener() {
		//为Toolbar左上角图标设置监听器
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		fabSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrGoneButton();
				save();
			}
		});
		fabOther.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrGoneButton();
			}
		});
		fabShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showOrGoneButton();
				share();
			}
		});
	}


	public void save(){
		try{
			boolean result = dao.add(storiesEntity);
			if (result){
				Toast.makeText(ThemeNewsActivity.this,R.string.save_success,Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(ThemeNewsActivity.this,R.string.is_save,Toast.LENGTH_SHORT).show();
			}
		}catch (NullPointerException e){
			e.printStackTrace();
		}
	}


	/**
	 * 点击分享按钮后，进行弹出分享界面并分享
	 */
	private void share() {
		if (newsContent != null) {
			OnekeyShare oks = new OnekeyShare();
			//关闭sso授权
			oks.disableSSOWhenAuthorize();
			// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
			oks.setTitle(newsContent.getTitle());
			// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
			oks.setTitleUrl(newsContent.getShare_url().replaceAll("'\'", ""));
			// text是分享文本，所有平台都需要这个字段
			oks.setText(newsContent.getTitle());
			// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			oks.setImageUrl(newsContent.getImage().replaceAll("'\'", ""));
			// url仅在微信（包括好友和朋友圈）中使用
			oks.setUrl(newsContent.getShare_url().replaceAll("'\'", ""));
			// comment是我对这条分享的评论，仅在人人网和QQ空间使用
			oks.setComment(getString(R.string.share));
			// site是分享此内容的网站名称，仅在QQ空间使用
			oks.setSite(getString(R.string.app_name));
			// siteUrl是分享此内容的网站地址，仅在QQ空间使用
			oks.setSiteUrl(newsContent.getShare_url().replaceAll("'\'", ""));
			// 启动分享GUI
			oks.show(ThemeNewsActivity.this);
		} else {
			Toast.makeText(this, R.string.no_data_share, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 璁剧疆fabButton鐐瑰嚮鏃剁殑鍔ㄧ敾鏁堟灉鍙婃搷浣�?
	 */
	private void showOrGoneButton() {
		if (fabShare.getVisibility()==View.VISIBLE){
			fabShare.setAnimation(goneAnimation);
			fabSave.setAnimation(goneAnimation);
			fabShare.startAnimation(goneAnimation);
			fabSave.startAnimation(goneAnimation);
			fabShare.setVisibility(View.GONE);
			fabSave.setVisibility(View.GONE);
		}else if(fabShare.getVisibility()==View.GONE){
			fabShare.setAnimation(showAnimation);
			fabSave.setAnimation(showAnimation);
			fabShare.startAnimation(showAnimation);
			fabSave.startAnimation(showAnimation);
			fabShare.setVisibility(View.VISIBLE);
			fabSave.setVisibility(View.VISIBLE);
		}
	}


	/**
	 * 发�?�Http请求获取网络数据
	 */
	private void loadData() {
		biz.loadThemeNews(storiesEntity.getId());
	}

	/**
	 * 获取数据后更新UI
	 */
	private void updateNewsContent() {
		String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
		String html = "<html><head>" + css + "</head><body>" + newsContent.getBody() + "</body></html>";
		html = html.replace("<div class=\"img-place-holder\">", "");
		mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
	}

	@Override
	public void onBackPressed() {
		finish();
		//设置动画效果
		overridePendingTransition(0, R.anim.slide_out_to_right_from_left);
	}

	@Override
	protected void onDestroy() {
		//注销广播接收�?
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
		super.onDestroy();
	}


	/**
	 * 自定义广播接收器，用于接收NewsActivityBiz发�?�的数据
	 */
	class TNABroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constant.ACTION_LOAD_THEME_NEWS_CONTENT_SUCCESS.equals(action)
					||Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_SUCCESS.equals(action)) {
				newsContent = (Content) intent.getSerializableExtra("content");
				//加载�?新消息后更新UI
				if (newsContent != null) {
					updateNewsContent();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_THEME_NEWS_CONTENT_OFFLINE_FAILURE.equals(action)) {
				Snackbar.make(mWebView, "网络无法连接，没有更多离线内容了", Snackbar.LENGTH_SHORT).show();
			}
		}
	}
}
