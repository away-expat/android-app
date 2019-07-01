package com.away_expat.away.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.away_expat.away.tools.OnEventItemClickListener;
import com.away_expat.away.views.RecyclerViewEventView;

import java.util.List;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewEventView> {

    private Context context;
    private List<Event> horizontalList;
    private final OnEventItemClickListener listener;

    public EventRecyclerViewAdapter(List<Event> horizontalList, Context context, OnEventItemClickListener listener) {
        this.horizontalList = horizontalList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerViewEventView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_event, parent, false);
        return new RecyclerViewEventView(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewEventView holder, int position) {
        Event mItem = horizontalList.get(position);
        holder.bind(mItem, listener);
    }

    @Override
    public int getItemCount() {
        return horizontalList == null ? 0 :horizontalList.size();
    }
}
