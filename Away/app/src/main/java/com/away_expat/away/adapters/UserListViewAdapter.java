package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.User;
import com.away_expat.away.views.ListViewUserView;

import java.util.ArrayList;
import java.util.List;

public class UserListViewAdapter extends BaseAdapter {

    private List<User> mModel = new ArrayList<User>();
    private Context mContext;

    public UserListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public User getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewUserView v = null;
        if (convertView == null) {
            v = new ListViewUserView(mContext);
        } else {
            v = (ListViewUserView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<User> model) {
        mModel = model;
    }
}
