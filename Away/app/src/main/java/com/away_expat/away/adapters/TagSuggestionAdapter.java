package com.away_expat.away.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.tools.OnTagItemClickListener;
import com.away_expat.away.views.RecyclerViewTagSuggestionView;

import java.util.List;

public class TagSuggestionAdapter extends RecyclerView.Adapter<RecyclerViewTagSuggestionView> {

    private Context context;
    private List<Tag> horizontalList;
    private final OnTagItemClickListener listener;

    public TagSuggestionAdapter(List<Tag> horizontalList, Context context, OnTagItemClickListener listener) {
        this.horizontalList = horizontalList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerViewTagSuggestionView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag_user, parent, false);
        return new RecyclerViewTagSuggestionView(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewTagSuggestionView holder, int position) {
        holder.bind(horizontalList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return horizontalList == null ? 0 :horizontalList.size();
    }

}