package com.interest.myapplication.fragment;

import java.util.List;

import com.hero.itemtouchhelper.helper.SimpleItemTouchHelperCallback;
import com.interest.myapplication.R;
import com.interest.myapplication.Dao.StoriesEntityDao;
import com.interest.myapplication.activity.MainActivity;
import com.interest.myapplication.adapter.CollectionFragmentAdapter;
import com.interest.myapplication.adapter.CollectionFragmentAdapter.MyItemClickListener;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.util.Constant;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
public class CollectionFragment extends BaseFragment {
	private List<StoriesEntity> storiesEntities;
	private RecyclerView recyclerView;
	private CollectionFragmentAdapter adapter;
	private LinearLayoutManager mLinearLayoutManager;
	private View view;
	private StoriesEntityDao dao;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		mLinearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
		recyclerView.setLayoutManager(mLinearLayoutManager);
		recyclerView.setHasFixedSize(true);
		// ΪRecycleView����LinearLayoutManager������
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		//ΪRecyclerView����������
		adapter = new CollectionFragmentAdapter(getActivity(),recyclerView);
		recyclerView.setAdapter(adapter);
		//����RecycleView�����ƶ��Ͳ໬ɾ����
		ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
		ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
		mItemTouchHelper.attachToRecyclerView(recyclerView);

	}

	/**
	 * ���ü�����
	 */
	private void setListeners() {
		//ΪRecycleView���õ�������¼�(�Զ���)
		adapter.setOnItemClickListener(new MyItemClickListener() {
			@Override
			public void onItemClick(View view, StoriesEntity storiesEntity) {
				//��NewsActivity
				((MainActivity)mActivity).startActivity(Constant.NEWS_ACYTIVITY_ID,storiesEntity);
			}
		});
	}

	@Override
	protected void initData(){
		dao = new StoriesEntityDao(mActivity);
		load();
	}

	public void load(){
		storiesEntities = dao.listAll();
		if (!storiesEntities.isEmpty()) {
			adapter.addList(storiesEntities);
		} else {
			Toast.makeText(mActivity, R.string.collection_is_empty, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ��ע������replace�����л�Fragmentʱ����һ��Fragment�������������е�onDestroyView
	 * Ȼ�����к�һ��Fragment��onCreate-->>onResume,�ŵ���һ��Fragment��OnDestroy����
	 * ������ˢ�¹�����ע��㲥������������onDestroyViewע��
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
