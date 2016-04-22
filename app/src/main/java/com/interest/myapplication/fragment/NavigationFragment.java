package com.interest.myapplication.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.interest.myapplication.R;
import com.interest.myapplication.activity.MainActivity;
import com.interest.myapplication.adapter.NavigationFragmentAdapter;
import com.interest.myapplication.biz.NavigationFragmentBiz;
import com.interest.myapplication.entity.ThemesListItem;
import com.interest.myapplication.util.Constant;

public class NavigationFragment extends BaseFragment implements OnClickListener{

	private TextView tv_main;
	private ListView lv_item;
	private NavigationFragmentAdapter adapter;
	private List<ThemesListItem> items = new ArrayList<ThemesListItem>();
	private NavigationFragmentBiz biz;
	public String urlId;
	public String title;
	private NFBroadcastReceiver receiver;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		biz = new NavigationFragmentBiz(mActivity);
		//ע��㲥������
		LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mActivity);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constant.ACTION_LOAD_NAVIGATION_THEMES_SUCCESS);
		intentFilter.addAction(Constant.ACTION_LOAD_NAVIGATION_THEMES_OFFLINE_SUCCESS);
		receiver = new NFBroadcastReceiver();
		manager.registerReceiver(receiver,intentFilter);

		View view = inflater.inflate(R.layout.navigation_menu, container, false);
		tv_main = (TextView) view.findViewById(R.id.tv_main);
		tv_main.setOnClickListener(this);
		lv_item = (ListView) view.findViewById(R.id.lv_item);
		lv_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((MainActivity)mActivity).showFragment(items.get(position).getId(),items.get(position).getTitle());
				//                getFragmentManager()
				//                        .beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
				//                        .replace(
				//                                R.id.fl_content,
				//                                new ThemeFragment(items.get(position)
				//                                        .getId()), "news").commit();
				//                ((MainActivity) mActivity).setCurId(items.get(position).getId());
				//                ((MainActivity) mActivity).closeMenu();
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_main:
			((MainActivity)mActivity).showFragment("mainFragment",mActivity.getString(R.string.main));
			break;
		}
	}

	@Override
	protected void initData() {
		//��ȡ���
		biz.loadThemes();
	}

	/**
	 * ˢ�����¼���Fragment���
	 */
	public void refresh(){
		adapter.refresh();//ˢ��RecycleView
		biz.loadThemes();//���¼������
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
	 * �Զ���㲥�����������ڽ���NavigationFragmentBiz���͵����
	 */
	class NFBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constant.ACTION_LOAD_NAVIGATION_THEMES_SUCCESS.equals(action)
					||Constant.ACTION_LOAD_NAVIGATION_THEMES_OFFLINE_SUCCESS.equals(action)) {
				items = (List<ThemesListItem>) intent.getSerializableExtra("themes");
				//����������Ϣ�����UI
				if (items != null) {
					adapter = new NavigationFragmentAdapter(mActivity, items);
					lv_item.setAdapter(adapter);
				}
			}
		}
	}
}
