package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.views.ListViewAccountView;

import java.util.ArrayList;
import java.util.List;

public class AccountListViewAdapter extends BaseAdapter {

    private List<Integer> mModel = new ArrayList<Integer>();
    private Context mContext;

    public AccountListViewAdapter(Context context) {
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
        ListViewAccountView v = null;
        if (convertView == null) {
            v = new ListViewAccountView(mContext);
        } else {
            v = (ListViewAccountView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Integer> model) {
        mModel = model;
    }
}
