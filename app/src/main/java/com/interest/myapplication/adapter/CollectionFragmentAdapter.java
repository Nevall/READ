package com.interest.myapplication.adapter;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hero.itemtouchhelper.helper.ItemTouchHelperAdapter;
import com.hero.itemtouchhelper.helper.ItemTouchHelperViewHolder;
import com.interest.myapplication.R;
import com.interest.myapplication.Dao.StoriesEntityDao;
import com.interest.myapplication.entity.StoriesEntity;
import com.interest.myapplication.util.Constant;
import com.interest.myapplication.util.PreUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


@SuppressLint("NewApi")
public class CollectionFragmentAdapter extends Adapter<CollectionFragmentAdapter.MyViewHolder>
		implements ItemTouchHelperAdapter{

	private Context context;
	private RecyclerView recyclerView;
	public List<StoriesEntity> mData;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private MyItemClickListener mItemClickListener;
	private StoriesEntityDao dao;
	public CollectionFragmentAdapter(final Context context,RecyclerView recyclerView) {
		this.context = context;
		this.recyclerView = recyclerView;
		this.mData = new ArrayList<StoriesEntity>();
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		dao = new StoriesEntityDao(context);
	}

	/**
	 * ����ԭ���
	 */
	public void addList(List<StoriesEntity> items) {
		this.mData.clear();
		this.mData.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}


	@Override
	public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
		StoriesEntity storiesEntity = mData.get(position);
		viewHolder.tvTitle.setTextColor(context.getResources().getColor(android.R.color.black));
		if (storiesEntity.getType() == Constant.TOPIC) {
			//			((FrameLayout) viewHolder.tvTopic.getParent()).setBackgroundColor(Color.TRANSPARENT);
			//			viewHolder.tvTitle.setVisibility(View.GONE);
			//			viewHolder.ivTitle.setVisibility(View.GONE);
			//			viewHolder.tvTopic.setVisibility(View.VISIBLE);
			//			viewHolder.tvTopic.setText(storiesEntity.getTitle());
		}else {
			try {
				((FrameLayout) viewHolder.tvTopic.getParent()).setBackgroundResource(R.drawable.item_background_selector_light);
				viewHolder.tvTitle.setVisibility(View.VISIBLE);
				viewHolder.ivTitle.setVisibility(View.VISIBLE);
				viewHolder.tvTopic.setVisibility(View.GONE);
				viewHolder.tvTitle.setText(storiesEntity.getTitle());
				imageLoader.displayImage(storiesEntity.getImages().get(0), viewHolder.ivTitle,options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.main_news_item, parent, false);
		return new MyViewHolder(view, mItemClickListener);
	}

	/**
	 * �Զ���ViewHolder
	 */
	class MyViewHolder extends ViewHolder implements ItemTouchHelperViewHolder,View.OnClickListener
	{
		private MyItemClickListener mListener;
		public TextView tvTopic;
		public TextView tvTitle;
		public ImageView ivTitle;

		public MyViewHolder(View itemView,MyItemClickListener listener) {
			super(itemView);
			tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
			tvTopic = (TextView) itemView.findViewById(R.id.tv_topic);
			ivTitle = (ImageView) itemView.findViewById(R.id.iv_title);
			//��������Viewע�����¼�
			this.mListener = listener;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onItemSelected() {
			((CardView)itemView).setCardBackgroundColor(Color.LTGRAY);
		}

		@Override
		public void onItemClear() {
			((CardView)itemView).setCardBackgroundColor(0);
		}

		@Override
		public void onClick(View v) {
			if (mListener != null) {
				//ָ���Զ����item�������
				if (Constant.TOPIC != mData.get(getAdapterPosition()).getType()) {
					mListener.onItemClick(v, mData.get(getAdapterPosition()));
				}
			}
		}
	}

	/**
	 * ʵ�������ƶ��Ͳ໬ɾ���� 
	 * TODO �����ƶ����ܺ�ˢ�¹��ܳ�ͻ��δ�������ʱ�Ƴ�ù���
	 */
	@Override
	public void onItemMove(int fromPosition, int toPosition) {
//		StoriesEntity prev = mData.remove(fromPosition);
//		mData.add(toPosition, prev);
//		//�������������ƶ�ʱ����ݲ�����󣬲��������޸ı�
//		mData.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
//		notifyItemMoved(fromPosition, toPosition);
	}

	@Override
	public void onItemDismiss(final int position) {
		//����ݴ���ݿ���ɾ��
		dao.delete(mData.get(position).getId());
		final StoriesEntity prev = mData.remove(position);
		notifyItemRemoved(position);
		Snackbar.make(recyclerView,R.string.delete_item, Snackbar.LENGTH_SHORT)
				.setAction(R.string.cancle, new OnClickListener() {

					@Override
					public void onClick(View v) {
						addData(position,prev);
					}
				}).show();
	}

	/**
	 * �����ɾ������
	 * @param position
	 */
	public void addData(int position,StoriesEntity item) {
		//������в������
		dao.add(item);
		mData.add(position, item);
		notifyItemInserted(position);
	}

	/**
	 * ����Item������� ,����������
	 * @param listener
	 */
	public void setOnItemClickListener(MyItemClickListener listener){
		this.mItemClickListener = listener;
	}

	/**
	 * �Զ���item�ĵ���ӿڣ�ʵ��item�ĵ�������¼�
	 * @author Android
	 */
	public static interface MyItemClickListener {
		void onItemClick(View view , StoriesEntity storiesEntity);
	}

}

