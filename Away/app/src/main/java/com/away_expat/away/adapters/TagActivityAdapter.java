package com.away_expat.away.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.views.RecyclerViewTagActivityView;

import java.util.ArrayList;
import java.util.List;

public class TagActivityAdapter extends RecyclerView.Adapter<RecyclerViewTagActivityView> {

    private Context context;
    private List<String> horizontalList;

    public TagActivityAdapter(List<String> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }

    @Override
    public RecyclerViewTagActivityView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag_activity, parent, false);
        return new RecyclerViewTagActivityView(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewTagActivityView holder, int position) {
        String mItem = horizontalList.get(position);
        holder.textView.setText(mItem);
    }

    @Override
    public int getItemCount() {
        return horizontalList == null ? 0 :horizontalList.size();
    }
}
