package com.interest.myapplication.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.interest.myapplication.MyApplication;
import com.interest.myapplication.R;
import com.interest.myapplication.biz.SplashActivityBiz;

public class SplashActivity extends Activity{

	private ImageView ivStart;
	private SplashActivityBiz biz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		//初始化控件
		initViews();
	}

	private void initViews() {
		biz = new SplashActivityBiz(this);
		ivStart = (ImageView)findViewById(R.id.iv_start);
		//从根目录中获取图片资源
		File dir = getFilesDir();
		final File file = new File(dir,"start.jpg");
		if (file.exists()) {
			ivStart.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
		}else {
			ivStart.setImageResource(R.drawable.start);
		}
		//设置图片动画
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f , Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setFillAfter(true);//动画执行完后停留在最后一帧
		scaleAnimation.setDuration(3000);//动画时长
		//为动画设置监听器
		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				//动画开始时执行
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// 动画重复时执行
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 动画结束时执行
				//发送Http请求获取并保存启动界面图像
				biz.loadStratImage(file);
				//打开MainActivity
				startActivity();
			}
		});
		//启动动画
		ivStart.startAnimation(scaleAnimation);
	}

	/**
	 * 打开MainActivity
	 */
	protected void startActivity() {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		finish();
	}
}
