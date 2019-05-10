package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.views.ListViewHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeListViewAdapter extends BaseAdapter {

    private List<Integer> mModel = new ArrayList<Integer>();
    private Context mContext;

    public HomeListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public Integer getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHomeView v = null;
        if (convertView == null) {
            v = new ListViewHomeView(mContext);
        } else {
            v = (ListViewHomeView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Integer> model) {
        mModel = model;
    }
}
