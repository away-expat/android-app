package com.away_expat.away.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.adapters.TagSuggestionAdapter;

public class RecyclerViewTagSuggestionView extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textView;
    TagSuggestionAdapter.OnAddedTagClickListener onAddedTagClickListener;

    public RecyclerViewTagSuggestionView(View view, TagSuggestionAdapter.OnAddedTagClickListener onAddedTagClickListener) {
        super(view);
        textView = (TextView) view.findViewById(R.id.tag_text);
        this.onAddedTagClickListener = onAddedTagClickListener;

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onAddedTagClickListener.onClick(getAdapterPosition());
    }
}