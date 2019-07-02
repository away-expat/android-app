package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Tag;
import com.away_expat.away.views.GridViewDisplayTagView;

import java.util.ArrayList;
import java.util.List;

public class DisplayTagGridViewAdapter extends BaseAdapter {

    private List<Tag> mModel = new ArrayList<>();
    private Context mContext;

    public DisplayTagGridViewAdapter(Context context) {
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
        GridViewDisplayTagView v = null;
        if (convertView == null) {
            v = new GridViewDisplayTagView(mContext);
        } else {
            v = (GridViewDisplayTagView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<Tag> model) {
        mModel = model;
    }

}
