package com.away_expat.away.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.tools.OnTagItemClickListener;

public class RecyclerViewTagSuggestionView extends RecyclerView.ViewHolder {

    public TextView textView;

    public RecyclerViewTagSuggestionView(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.tag_text);
    }

    public void bind(Tag mItem, final OnTagItemClickListener listener) {
        textView.setText(mItem.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTagItemClick(mItem);
            }
        });
    }
}