package com.interest.myapplication.biz;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.Call;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.HttpUtils;
import com.interest.myapplication.util.ParseJson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * SplashActivity的业务类
 */
public class SplashActivityBiz {
	private Context context;
	private ParseJson parse = new ParseJson();

	public SplashActivityBiz(Context context) {
		this.context = context;
	}


	/**
	 * 发送Http请求获取启动界面图像
	 * @return 
	 */
	public void loadStratImage(final File file) {
		//TODO 可重构在MyApplication检查网络是否连接
		if (HttpUtils.isNetworkConnected(context)) {
			String url = Constant.BASEURL+Constant.START;
			try {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onResponse(String response) {
						try {
							//解析JSON数据
							String imageUrl = parse.parseSplashJson(response);
							//发送Http请求下载保存图片
							saveImage(imageUrl,file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		else {
//			Toast.makeText(context, "网络无法连接", Toast.LENGTH_SHORT).show();
//		}
	}

	/**
	 * 发送Http请求保存网络图片到本地
	 * @param imgaeUrl
	 */
	public void saveImage(String imageUrl,final File file){
		if (HttpUtils.isNetworkConnected(context)) {
			try {
				OkHttpUtils.get().url(imageUrl).build().execute(new BitmapCallback() {

					@Override
					public void onResponse(Bitmap bitmap) {
						//保存图片
						saveFile(bitmap, file);
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**  
	 * 保存Bitmap格式图片文件  
	 * @param bm  
	 */    
	public void saveFile(Bitmap bm, File file){ 
		try {
			if (file.exists()) {
				file.delete();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));    
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);    
			bos.flush();    
			bos.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
