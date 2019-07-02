package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.City;
import com.away_expat.away.views.ListViewCityView;

import java.util.ArrayList;
import java.util.List;

public class CityListViewAdapter extends BaseAdapter {

    private List<City> mModel = new ArrayList<>();
    private Context mContext;
    private ListViewCityView view;

    public CityListViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public City getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = null;
        if (convertView == null) {
            view = new ListViewCityView(mContext);
        } else {
            view = (ListViewCityView) convertView;
        }
        view.bind(getItem(position));
        return view;
    }

    public void bind(List<City> model) {
        mModel = model;
    }
}
