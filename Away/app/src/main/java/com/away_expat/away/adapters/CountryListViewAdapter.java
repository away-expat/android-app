package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.Info;
import com.away_expat.away.views.ListViewCountryInfoView;
import com.away_expat.away.views.ListViewHomeView;

import java.util.ArrayList;
import java.util.List;

public class CountryListViewAdapter extends BaseAdapter {

    private List<Info> mModel = new ArrayList<>();
    private Context mContext;

    public CountryListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public Info getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewCountryInfoView v = null;
        if (convertView == null) {
            v = new ListViewCountryInfoView(mContext);
        } else {
            v = (ListViewCountryInfoView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Info> model) {
        mModel = model;
    }
}
