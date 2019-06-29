package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;
import com.away_expat.away.views.ListViewSearchEventView;
import com.away_expat.away.views.ListViewUserView;

import java.util.ArrayList;
import java.util.List;

public class SearchEventListViewAdapter extends BaseAdapter {
    private List<Event> mModel = new ArrayList<Event>();
    private Context mContext;

    public SearchEventListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public Event getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewSearchEventView v = null;
        if (convertView == null) {
            v = new ListViewSearchEventView(mContext);
        } else {
            v = (ListViewSearchEventView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Event> model) {
        mModel = model;
    }
}
