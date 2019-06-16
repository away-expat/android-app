package com.away_expat.away.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.away_expat.away.classes.Tag;
import com.away_expat.away.views.GridViewTagActivityView;
import com.away_expat.away.views.GridViewTagSelectionView;
import com.away_expat.away.views.ListViewTagActivityView;

import java.util.ArrayList;
import java.util.List;

public class TagActivityGridViewAdapter extends BaseAdapter {

    private List<String> mModel = new ArrayList<>();
    private Context mContext;

    public TagActivityGridViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mModel.size();
    }

    @Override
    public String getItem(int position) {
        return mModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewTagActivityView v = null;
        if (convertView == null) {
            v = new ListViewTagActivityView(mContext);
        } else {
            v = (ListViewTagActivityView) convertView;
        }
        v.bind(getItem(position));
        return v;
    }

    public void bind(List<String> model) {
        mModel = model;
    }

}
