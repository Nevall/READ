package com.interest.myapplication.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hero.itemtouchhelper.helper.SimpleItemTouchHelperCallback;
import com.interest.myapplication.R;
import com.interest.myapplication.activity.MainActivity;
import com.interest.myapplication.adapter.ThemeFragmentAdapter;
import com.interest.myapplication.biz.ThemeFragmentBiz;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.entity.Theme;
import com.interest.myapplication.libs.RecyclerViewHeader;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.DateUtils;
import com.interest.myapplication.util.PreUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * �����ձ�Fragment
 */
public class ThemeFragment extends BaseFragment{

	public String urlId;
	private Theme themeNews;
	private RecyclerView themesRecyclerView;
	private RecyclerViewHeader header;
	private TextView headerTvTitle;
	private ImageView headerIvTitle;
	private LinearLayoutManager mLinearLayoutManager;
	private ThemeFragmentBiz biz;
	private BroadcastReceiver receiver;
	private ThemeFragmentAdapter adapter;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private List<StoriesEntity> storiesEntities;
	private View view;
	private String currentId;
	private int lastNewsId;


	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		biz = new ThemeFragmentBiz(mActivity);
		//ע��㲥������
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_FAILURE);

		receiver = new TFBroadcastReceiver();
		LocalBroadcastManager.getInstance(mActivity).registerReceiver(receiver,intentFilter);

		//��ʼ���ؼ�
		setViews(inflater,container);
		//���ü�����
		setListeners();
		return view;
	}

	/**
	 * ��ʼ���ؼ�
	 * @param inflater
	 * @param container
	 */
	private void setViews(LayoutInflater inflater, ViewGroup container) {
		//��ʼ���ؼ�
		view = inflater.inflate(R.layout.themes_fragment, container, false);
		themesRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_themes);
		header = RecyclerViewHeader.fromXml(getActivity(), R.layout.themes_news_header);
		headerTvTitle = (TextView)header.findViewById(R.id.tv_title);
		headerIvTitle = (ImageView)header.findViewById(R.id.iv_title);
		//ΪRecyclerView����������
		mLinearLayoutManager = new LinearLayoutManager(themesRecyclerView.getContext());
		themesRecyclerView.setLayoutManager(mLinearLayoutManager);
		// ΪRecycleView����LinearLayoutManager������
		themesRecyclerView.setItemAnimator(new DefaultItemAnimator());
		themesRecyclerView.setHasFixedSize(true);
		header.attachTo(themesRecyclerView);//��RecycleView�Ϸ���ӿؼ�
		//ΪRecyclerView����������
		adapter = new ThemeFragmentAdapter(getActivity(),themesRecyclerView);
		themesRecyclerView.setAdapter(adapter);
		//����RecycleView�����ƶ��Ͳ໬ɾ����
		ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
		ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
		mItemTouchHelper.attachToRecyclerView(themesRecyclerView);
	}


	/**
	 * ���ü�����
	 */
	private void setListeners() {
		//ΪRecycleView��ӻ��������¼����������ײ�ʵ���Զ����������
		themesRecyclerView.addOnScrollListener(new OnScrollListener() {
			//			private int visibleItemCount;
			//			private int totalItemCount;
			private int lastVisiableItem;
			//			private int previousTotal = 0;
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				((MainActivity)mActivity).sr.setEnabled(mLinearLayoutManager  
						.findFirstCompletelyVisibleItemPosition() == 0); 
				lastVisiableItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
				// ����ʱִ��
				//				visibleItemCount = recyclerView.getChildCount();
				//				totalItemCount = mLinearLayoutManager.getItemCount();
				//				if (isLoading) {
				//					if (totalItemCount > previousTotal) {
				//						isLoading = false;
				//					}
				//				}
				//				if (!isLoading && (visibleItemCount > 0)
				//						&& (lastVisiableItem >= totalItemCount - 1)) {
				//					//���ظ�����
				//					biz.loadMore(date);
				//				}
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE  
						&& lastVisiableItem + 1 == adapter.getItemCount()) { 
					//���ظ�����
					biz.loadMore(""+currentId,""+lastNewsId);
				}  
			}
		});
		//ΪRecycleView���õ�������¼�(�Զ���)
		adapter.setOnItemClickListener(new ThemeFragmentAdapter.MyItemClickListener() {

			@Override
			public void onItemClick(View view, StoriesEntity storiesEntity) {
				//��Ǹ������Ķ�״̬
				//��ȡSharedPreferences��
				String readSequence = PreUtil.getStringFromDefault(mActivity, "read", "");
				String [] splits = readSequence.split(",");
				StringBuffer sb = new StringBuffer();
				if (splits.length >= 200) {
					for (int i = 100; i < splits.length; i++) {
						sb.append(splits[i]+",");
					}
					readSequence = sb.toString();
				}
				if (!readSequence.contains(storiesEntity.getId()+"")) {
					readSequence = readSequence+storiesEntity.getId();
				}
				//���Ϊ�Ѷ�������������ɫ
				PreUtil.putStringToDefault(mActivity, "read", readSequence);
				TextView tvTitle = ((TextView)view.findViewById(R.id.tv_title));
				tvTitle.setTextColor(getResources().getColor(R.color.clicked_tv_textcolor));
				//��Activity
				((MainActivity)mActivity).startActivity(Constant.THEME_NEWS_ACYTIVITY_ID,storiesEntity);
			}
		});
	}

	/**
	 * ���¼���ThemeFragemnt���
	 * @param urlId
	 */
	public void refresh(String urlId){
		currentId = urlId;
		adapter.refresh();
		biz.loadThemeNews(urlId);
	}

	/**
	 * ��ע������replace�����л�Fragmentʱ����һ��Fragment�������������е�onDestroyView
	 * Ȼ�����к�һ��Fragment��onCreate-->>onResume,�ŵ���һ��Fragment��OnDestroy����
	 * ������ˢ�¹�����ע��㲥������������onDestroyViewע��
	 */
	@Override
	public void onDestroyView() {
		//ע��㲥������
		if (receiver!=null) {
			LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(receiver);
		}
		super.onDestroyView();
	}

	/**
	 * ���������ձ���ݺ����UI
	 */
	public void updateThemeNews() {
		if (themeNews == null) {
			return;
		}
		//��RecycleView������
		storiesEntities = themeNews.getStories();
		//��ȡstoriesEntities�����һ�����ID,���ڼ��ع������������б�
		lastNewsId = storiesEntities.get(storiesEntities.size()-1).getId(); 
		StoriesEntity topic = new StoriesEntity();
		topic.setType(Constant.TOPIC);
		topic.setTitle(mActivity.getString(R.string.editor));
		storiesEntities.add(0, topic);
		adapter.addList(themeNews.getStories());
		//����ͷ���������
		//����ImageLoader����
		DisplayImageOptions options = new DisplayImageOptions.Builder()  
		.cacheInMemory(true)  
		.cacheOnDisc(true)  
		.build();
		headerTvTitle.setText(themeNews.getDescription());
		imageLoader.displayImage(themeNews.getImage(), headerIvTitle,options);
	}


	/**
	 * ����������Ϣ�����UI
	 */
	private void updateBefore() {
		if (themeNews == null) {
			return;
		}
		storiesEntities = themeNews.getStories();
		//��ȡstoriesEntities�����һ�����ID,���ڼ��ع������������б�
		lastNewsId = storiesEntities.get(storiesEntities.size()-1).getId(); 
		adapter.addList(storiesEntities);
	}

	/**
	 * �Զ���㲥�����������ڽ���ThemeFragmentBiz���͵����
	 */
	class TFBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			((MainActivity)mActivity).sr.setRefreshing(false); 
			String action = intent.getAction();
			if (Constant.ACTION_LOAD_THEME_NEWS_SUCCESS.equals(action)) {
				themeNews = (Theme) intent.getSerializableExtra("themeNews");
				if (themeNews != null) {
					//����������Ϣ�����UI
					updateThemeNews();
				}else {
					return;
				}			
			}else if (Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_SUCCESS.equals(action)) {
				themeNews = (Theme) intent.getSerializableExtra("themeNews");
				if (themeNews != null) {
					//����������Ϣ�����UI
					updateThemeNews();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_THEME_NEWS_BEFORE_SUCCESS.equals(action)
					||Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_SUCCESS.equals(action)) {
				themeNews = (Theme) intent.getSerializableExtra("themeNews");
				if (themeNews != null) {
					//����������Ϣ�����UI
					updateBefore();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_THEME_NEWS_OFFLINE_FAILURE.equals(action)
					||Constant.ACTION_LOAD_THEME_NEWS_BEFORE_OFFLINE_FAILURE.equals(action)) {
				Snackbar.make(themesRecyclerView, R.string.network_is_not_connected_and_load, Snackbar.LENGTH_SHORT).show();
			}
		}
	}

}
