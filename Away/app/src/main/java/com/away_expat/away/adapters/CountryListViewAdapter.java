package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Country;
import com.away_expat.away.views.ListViewCountryView;

import java.util.ArrayList;
import java.util.List;

public class CountryListViewAdapter extends BaseAdapter {

    private List<Country> mModel = new ArrayList<>();
    private Context mContext;

    public CountryListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public Country getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewCountryView v = null;
        if (convertView == null) {
            v = new ListViewCountryView(mContext);
        } else {
            v = (ListViewCountryView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Country> model) {
        mModel = model;
    }
}
