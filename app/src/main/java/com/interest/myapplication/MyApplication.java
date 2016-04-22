package com.interest.myapplication;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.interest.myapplication.db.CacheDbHelper;
import com.interest.myapplication.db.WebCacheDbHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;


public class MyApplication extends Application{
	private static CacheDbHelper cacheOpenhelper;
	private static WebCacheDbHelper webCacheOpenhelper;

	@Override
	public void onCreate() {
		// 创建默认的ImageLoader配置参数
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)  
		.memoryCacheExtraOptions(480, 800) // default = device screen dimensions  
		.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)  
		.threadPoolSize(2) // default  
		.threadPriority(Thread.NORM_PRIORITY - 2) // default  
		.tasksProcessingOrder(QueueProcessingType.FIFO) // default  
		.denyCacheImageMultipleSizesInMemory()  
		.memoryCache(new WeakMemoryCache())  
		.memoryCacheSize(2 * 1024 * 1024)  
		.memoryCacheSizePercentage(13) // default  
		.discCache(new UnlimitedDiscCache(getCacheDir())) // default  
		.discCacheSize(50 * 1024 * 1024)  
		.discCacheFileCount(100)  
		.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default  
		.imageDownloader(new BaseImageDownloader(this)) // default  
		.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default  
		.writeDebugLogs()  
		.build(); 
		ImageLoader.getInstance().init(configuration);
		cacheOpenhelper = new CacheDbHelper(this, "cache.db", null, 1);
		webCacheOpenhelper = new WebCacheDbHelper(this, "WebCache.db", null, 1);
	}

	public static CacheDbHelper getCacheDBHelper(){
		return cacheOpenhelper;
	}

	public static WebCacheDbHelper getWebCacheDBHelper(){
		return webCacheOpenhelper;
	}
}
