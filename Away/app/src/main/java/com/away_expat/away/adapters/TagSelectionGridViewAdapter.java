package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Tag;
import com.away_expat.away.views.GridViewTagSelectionView;

import java.util.ArrayList;
import java.util.List;

public class TagSelectionGridViewAdapter extends BaseAdapter {

    private List<Tag> mModel = new ArrayList<>();
    private Context mContext;
    private Tag tag;

    public TagSelectionGridViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public Tag getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewTagSelectionView v = null;
        if (convertView == null) {
            v = new GridViewTagSelectionView(mContext);
        } else {
            v = (GridViewTagSelectionView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Tag> model) {
        mModel = model;
    }

}