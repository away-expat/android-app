package com.away_expat.away.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.away_expat.away.R;

public class RecyclerViewTagActivityView extends RecyclerView.ViewHolder {

    public TextView textView;

    public RecyclerViewTagActivityView(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.tag_text);
    }

}
