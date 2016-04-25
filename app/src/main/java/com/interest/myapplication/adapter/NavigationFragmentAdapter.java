package com.interest.myapplication.adapter;


import java.util.ArrayList;
import java.util.List;

import com.interest.myapplication.R;
import com.interest.myapplication.entity.ThemesListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by Android on 2016/3/6.
 * 涓婚鏃ユ姤鍒楄〃鐨勯�閰嶅�?
 */
public class NavigationFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<ThemesListItem> themes = new ArrayList<ThemesListItem>();
    private LayoutInflater inflater;

    public NavigationFragmentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public void addDatas(List<ThemesListItem> themes) {
        this.themes = themes;
        notifyDataSetChanged();
    }
    
	/**
	 * 刷新Adapter
	 */
	public void refresh(){
		themes.clear();
		notifyDataSetChanged();
	}

    @Override

    public int getCount() {
        return themes.size();
    }

    @Override
    public Object getItem(int position) {
        return themes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvThemeTitle = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.navigation_menu_item, null);
            tvThemeTitle = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(tvThemeTitle);
        } else {
            tvThemeTitle = (TextView) convertView.getTag();
        }
        tvThemeTitle.setText(themes.get(position).getTitle());
        return convertView;
    }
}
