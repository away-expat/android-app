package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Activity;
import com.away_expat.away.views.ListViewActivityView;
import com.away_expat.away.views.ListViewSearchActivityView;
import com.away_expat.away.views.ListViewTagActivityView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityListViewAdapter extends BaseAdapter {
    private List<Activity> mModel = new ArrayList<Activity>();
    private Context mContext;

    public SearchActivityListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public Activity getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewSearchActivityView v = null;
        if (convertView == null) {
            v = new ListViewSearchActivityView(mContext);
        } else {
            v = (ListViewSearchActivityView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Activity> model) {
        mModel = model;
    }
}
