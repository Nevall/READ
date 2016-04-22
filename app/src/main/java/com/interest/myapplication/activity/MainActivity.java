package com.interest.myapplication.activity;


import com.interest.myapplication.R;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.fragment.CollectionFragment;
import com.interest.myapplication.fragment.MainFragment;
import com.interest.myapplication.fragment.NavigationFragment;
import com.interest.myapplication.fragment.ThemeFragment;
import com.interest.myapplication.util.CacheUtil;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.PatternUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import c.b.BP;
import c.b.PListener;
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
	private ProgressDialog pgDialog;
	private AlertDialog dialog;
	private AlertDialog dialog1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BP.init(this, Constant.APPID);
		setDialogView();
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


	private void setDialogView() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		View view = View.inflate(this,R.layout.dialog_about,null);
		dialog = builder.setCancelable(false).setView(view).create();
		View view1 = View.inflate(this,R.layout.dialog_money,null);
		TextView tvPay = (TextView) view1.findViewById(R.id.tvDialogPay);
		TextView tvCancle = (TextView) view1.findViewById(R.id.tvDialogCancle);
		final TextInputLayout tilMoney = (TextInputLayout) view1.findViewById(R.id.tilMoney);
		dialog1 = builder.setCancelable(false).setView(view1).create();
		view.findViewById(R.id.tvAboutSubmit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {dialog.dismiss();
			}
		});
		view.findViewById(R.id.tvAboutMoney).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				dialog1.show();
			}
		});
		tvPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String price =tilMoney.getEditText().getText().toString();
				if (PatternUtil.check(price)) {
					payByAli(Double.parseDouble(price));
					dialog1.dismiss();
				}else{
					tilMoney.getEditText().setText("");
					tilMoney.setError(getString(R.string.right_number));
//                    Toast.makeText(MainActivity.this, "请输入正确金额，谢谢", Toast.LENGTH_SHORT).show();
				}
			}
		});
		tvCancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog1.dismiss();
			}
		});
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
				Snackbar.make(fl_content, R.string.again, Snackbar.LENGTH_SHORT).show();
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
				showFragment("collectionFragment", getString(R.string.collection));
				break;
			case R.id.action_about:
				dialog.show();
				break;
			case R.id.action_delete_cache:
				CacheUtil.cleanCache(MainActivity.this);
				final ProgressDialog pbDialog = new ProgressDialog(this);
				pbDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pbDialog.setMessage(getString(R.string.deleting_cache));
				pbDialog.show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, R.string.clead_cache_success,Toast.LENGTH_SHORT).show();
						pbDialog.dismiss();
					}
				},1000);
				break;
			case R.id.action_quite:
				ProgressDialog.show(MainActivity.this,getString(R.string.closeing),getString(R.string.closeing),true,false);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						android.os.Process.killProcess(android.os.Process.myPid());
						System.exit(0);
					}
				},500);
				break;
		}
		return true;
	}


	private void showDialog(String message) {
		try {
			if (pgDialog == null) {
				pgDialog = new ProgressDialog(this);
				pgDialog.setCancelable(true);
			}
			pgDialog.setMessage(message);
			pgDialog.show();
		} catch (Exception e) {
			// 在其他线程调用dialog会报错
		}
	}
	private void hideDialog() {
		if (pgDialog != null && pgDialog.isShowing())
			try {
				pgDialog.dismiss();
			} catch (Exception e) {
			}
	}


	private void payByAli(double price) {
		double p = price<0.02?0.02:price;
		showDialog(getString(R.string.load_data));
		BP.pay(this, getString(R.string.name), getString(R.string.description),p, true, new PListener() {

			// 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
			@Override
			public void unknow() {
				Toast.makeText(MainActivity.this, R.string.result_unknow,
						Toast.LENGTH_SHORT).show();
				hideDialog();
			}

			// 支付成功,如果金额较大请手动查询确认
			@Override
			public void succeed() {
				Toast.makeText(MainActivity.this, R.string.pay_success, Toast.LENGTH_SHORT)
						.show();
				hideDialog();
			}

			// 无论成功与否,返回订单号
			@Override
			public void orderId(String orderId) {
				// 此处应该保存订单号,比如保存进数据库等,以便以后查询
				showDialog(getString(R.string.wait));
			}

			// 支付失败,原因可能是用户中断支付操作,也可能是网络原因
			@Override
			public void fail(int code, String reason) {
				Toast.makeText(MainActivity.this, R.string.pay_break_off, Toast.LENGTH_SHORT)
						.show();
				hideDialog();
			}
		});
	}

}
