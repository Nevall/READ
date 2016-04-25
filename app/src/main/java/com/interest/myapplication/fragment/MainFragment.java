package com.interest.myapplication.fragment;

import java.util.List;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hero.itemtouchhelper.helper.SimpleItemTouchHelperCallback;
import com.interest.myapplication.R;
import com.interest.myapplication.activity.MainActivity;
import com.interest.myapplication.adapter.MainFragmentAdapter;
import com.interest.myapplication.adapter.MainFragmentAdapter.MyItemClickListener;
import com.interest.myapplication.biz.MainFragmentBiz;
import com.interest.myapplication.entity.Before;
import com.interest.myapplication.entity.Latest;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.entity.TopStoriesEntity;
import com.interest.myapplication.libs.RecyclerViewHeader;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.DateUtils;
import com.interest.myapplication.util.PreUtil;
import com.interest.myapplication.view.Kanner;
import com.interest.myapplication.view.Kanner.OnItemClickListener;


public class MainFragment extends BaseFragment {
	private Latest latest;
	private List<StoriesEntity> storiesEntities;
	private RecyclerView recyclerView;
	private  MainFragmentAdapter adapter;
	private LinearLayoutManager mLinearLayoutManager;
	private Before before;
	private String date;
	private Kanner kanner;
	private RecyclerViewHeader header;
	private	MainFragmentBiz biz;
	private BroadcastReceiver receiver;
	private View view;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		biz = new MainFragmentBiz(mActivity);
		//ע��㲥������
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_FAILURE);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE);
		receiver = new MFBroadcastReceiver();
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
		view = inflater.inflate(R.layout.main_fragment, container, false);
		recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_main);

		header = RecyclerViewHeader.fromXml(getActivity(), R.layout.kanner);
		kanner = (Kanner) header.findViewById(R.id.kanner);

		mLinearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
		recyclerView.setLayoutManager(mLinearLayoutManager);
		recyclerView.setHasFixedSize(true);

		// ΪRecycleView����LinearLayoutManager������
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		header.attachTo(recyclerView);//��RecycleView�Ϸ���ӿؼ�
		//ΪRecyclerView����������
		adapter = new MainFragmentAdapter(getActivity(),recyclerView);
		recyclerView.setAdapter(adapter);
		//����RecycleView�����ƶ��Ͳ໬ɾ����
		//由于滑动删除功能有冲突，取消该功能
//		ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
//		ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
//		mItemTouchHelper.attachToRecyclerView(recyclerView);

	}

	/**
	 * ���ü�����
	 */
	private void setListeners() {
		//ΪRecycleView��ӻ��������¼����������ײ�ʵ���Զ����������
		recyclerView.addOnScrollListener(new OnScrollListener() {
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
					biz.loadMore(date);
				}  
			}
		});
		//ΪRecycleView���õ�������¼�(�Զ���)
		adapter.setOnItemClickListener(new MyItemClickListener() {

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
				//��NewsActivity
				((MainActivity)mActivity).startActivity(Constant.NEWS_ACYTIVITY_ID,storiesEntity);
			}
		});
		
		kanner.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void click(View v, TopStoriesEntity entity) {
                StoriesEntity storiesEntity = new StoriesEntity();
                storiesEntity.setId(entity.getId());
                storiesEntity.setTitle(entity.getTitle());
				//��NewsActivity
				((MainActivity)mActivity).startActivity(Constant.NEWS_ACYTIVITY_ID,storiesEntity);
			}
		});
	}

	@Override
	protected void initData(){
		biz.loadFirst();
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
	 * ����������Ϣ�����UI
	 */
	private void updateFirst() {
		date = latest.getDate();
		storiesEntities = latest.getStories();
		kanner.setTopEntities(latest.getTop_stories());
		StoriesEntity topic = new StoriesEntity();
		topic.setType(Constant.TOPIC);
		topic.setTitle(mActivity.getString(R.string.topic));
		storiesEntities.add(0, topic);
		adapter.addList(storiesEntities);
	}

	/**
	 * ����������Ϣ�����UI
	 */
	private void updateBefore() {
		if (before == null) {
			return;
		}
		date = before.getDate();
		StoriesEntity topic = new StoriesEntity();
		topic.setType(Constant.TOPIC);
		topic.setTitle(DateUtils.convertDate(mActivity,before.getDate()));
		storiesEntities = before.getStories();
		storiesEntities.add(0, topic);
		adapter.addList(storiesEntities);
		
	}

	/**
	 * ˢ�����¼���Fragment���
	 */
	public void refresh(){
		adapter.refresh();//ˢ��RecycleView
		kanner.refresh();//ˢ��Kanner
		biz.loadFirst();//���¼������
	}

	/**
	 * �Զ���㲥�����������ڽ���MainFragmentBiz���͵����
	 */
	class MFBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			((MainActivity)mActivity).sr.setRefreshing(false); 
			if (Constant.ACTION_LOAD_FIRST_SUCCESS.equals(action)) {
				latest = (Latest) intent.getSerializableExtra("latest");
				//����������Ϣ�����UI
				if (latest != null) {
					updateFirst();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_BEFORE_SUCCESS.equals(action)
					||Constant.ACTION_LOAD_BEFORE_OFFLINE_SUCCESS.equals(action)) {
				before = (Before) intent.getSerializableExtra("before");
				//����������Ϣ�����UI
				if (latest != null) {
					updateBefore();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_FIRST_OFFLINE_SUCCESS.equals(action)) {
				Snackbar.make(recyclerView, R.string.network_is_not_connected, Snackbar.LENGTH_SHORT).show();
				latest = (Latest) intent.getSerializableExtra("latest");
				//����������Ϣ�����UI
				if (latest != null) {
					updateFirst();
				}else {
					return;
				}
			}else if (Constant.ACTION_LOAD_FIRST_OFFLINE_FAILURE.equals(action) 
					||Constant.ACTION_LOAD_BEFORE_OFFLINE_FAILURE.equals(action)) {
				Snackbar.make(recyclerView, R.string.network_is_not_connected_and_load, Snackbar.LENGTH_SHORT).show();
			}
		}
	}
}
