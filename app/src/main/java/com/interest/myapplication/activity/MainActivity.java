package com.interest.myapplication.activity;


import com.interest.myapplication.R;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.fragment.CollectionFragment;
import com.interest.myapplication.fragment.MainFragment;
import com.interest.myapplication.fragment.NavigationFragment;
import com.interest.myapplication.fragment.ThemeFragment;
import com.interest.myapplication.util.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends AppCompatActivity implements OnRefreshListener,OnMenuItemClickListener{

	private Toolbar toolbar;
	private FrameLayout fl_content;
	private DrawerLayout mDrawerLayout;
	public  SwipeRefreshLayout sr;
	private String curId;
	private long firstTime;
	private NavigationFragment navigationFragment;
	private ThemeFragment themeFragment;
	private MainFragment mainFragment;
	private CollectionFragment collectionFragment;
	private Fragment mContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//��ֹFragment�ص������ϴα����fragment����(��ջ���ջ)
		//û����
		//        if(savedInstanceState!=null){
		//            FragmentManager manager = getSupportFragmentManager();
		//            manager.popBackStackImmediate(null, 1);
		//        }
		ininView();//��ʼ���ؼ�
		loadFragment();//����Fragment
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//��ʱ���Fragment�ص�����
		//		super.onSaveInstanceState(outState);
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void ininView() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setOnMenuItemClickListener(this);
		//ʵ��Toolbar�Ĳ˵���ť�ļ�ͷ���ع���
		fl_content = (FrameLayout)findViewById(R.id.fl_content);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
		ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				toolbar, R.string.app_name, R.string.app_name);
		mDrawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();
		//��ʼ��ˢ�¹��ؼ�
		sr = (SwipeRefreshLayout) findViewById(R.id.sr);
		sr.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_bright);
		//Ϊˢ�¹������ü���
		sr.setOnRefreshListener(this);
	}

	/**
	 * ����Fragment
	 */
	private void loadFragment() {
		mainFragment = new MainFragment();
		navigationFragment = new NavigationFragment();
		themeFragment = new ThemeFragment();
		collectionFragment = new CollectionFragment();
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		//��Fragment���ص�����������
		if (!navigationFragment.isAdded()) {
			transaction.add(R.id.ll_navigation_fragment,navigationFragment,"navigationFragment").show(navigationFragment);
		}
		if (!themeFragment.isAdded()) {
			transaction.add(R.id.fl_content,themeFragment,"themeFragment").hide(themeFragment);
		}
		if (!collectionFragment.isAdded()) {
			transaction.add(R.id.fl_content,collectionFragment,"collectionFragment").hide(collectionFragment);
		}
		if (!mainFragment.isAdded()) {
			transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left)
			.add(R.id.fl_content,mainFragment,"mainFragment").show(mainFragment);
		}
		transaction.commit();
		curId = "mainFragment";
	}

	/**
	 * ˢ��Fragment���
	 */
	@Override
	public void onRefresh() {
		//ˢ�¼����¼�
		FragmentManager manager = getSupportFragmentManager();
		((NavigationFragment) manager.findFragmentByTag("navigationFragment")).refresh();
		if (mainFragment.isVisible() && themeFragment.isHidden()) {
			((MainFragment) manager.findFragmentByTag("mainFragment")).refresh();
		}else if (mainFragment.isHidden() && themeFragment.isVisible()){
			((ThemeFragment)manager.findFragmentByTag("themeFragment")).refresh(curId);
		}
	}

	/**
	 * �رղ�ߵ�����
	 */
	public void closeMenu() {
		mDrawerLayout.closeDrawers();
	}


	@Override
	public void onBackPressed() {
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
			closeMenu();
		} else {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {
				Snackbar.make(fl_content, "再按一次退出", Snackbar.LENGTH_SHORT).show();
				firstTime = secondTime;
			} else {
				finish();
			}
		}
	}


	/**
	 * ��ʾFragment
	 */
	public void showFragment(String urlId,String title) {
		if (curId != urlId) {
			toolbar.setTitle(title);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if ("mainFragment".equals(urlId)){
				if (mainFragment.isHidden()) {
					transaction.hide(themeFragment).hide(collectionFragment);
					transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left).show(mainFragment).commit();
				}
			}else if ("collectionFragment".equals(urlId)){
				sr.setEnabled(false);
				collectionFragment.load();
				transaction.hide(themeFragment).hide(mainFragment);
				transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left).show(collectionFragment).commit();
			}else{
				transaction.hide(collectionFragment).hide(mainFragment);
				transaction.setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left).show(themeFragment).commit();	
				themeFragment.refresh(urlId);
			}
		}
		curId = urlId;
		closeMenu();
	}

	/**
	 * ��NewsActivity��ThemeNewsAcitvity
	 */
	public void startActivity(String acytivityId,StoriesEntity storiesEntity){
		Intent intent = null;
		if (Constant.NEWS_ACYTIVITY_ID.equals(acytivityId)) {
			intent = new Intent(this,NewsActivity.class);
		}else if (Constant.THEME_NEWS_ACYTIVITY_ID.equals(acytivityId)) {
			intent = new Intent(this,ThemeNewsActivity.class);
		}
		intent.putExtra("storiesEntity", storiesEntity);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.action_collection:
			showFragment("collectionFragment", "收藏");
			break;
		case R.id.action_settings:

			break;
		}
		return true;
	}


}
