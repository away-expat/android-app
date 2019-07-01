package com.away_expat.away.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.away_expat.away.tools.OnEventItemClickListener;
import com.squareup.picasso.Picasso;

public class RecyclerViewEventView extends RecyclerView.ViewHolder {

    public TextView titleTV, dateTV;
    public ImageView eventIV;

    public RecyclerViewEventView(View view) {
        super(view);
        titleTV = (TextView) view.findViewById(R.id.title_text);
        dateTV = (TextView) view.findViewById(R.id.date_text);
        eventIV = (ImageView) view.findViewById(R.id.event_img);
    }

    public void bind(Event mItem, final OnEventItemClickListener listener) {
        titleTV.setText(mItem.getTitle());
        String toDisplay = mItem.getDate()+" "+mItem.getHour();
        dateTV.setText(toDisplay);
        Picasso.get().load(mItem.getPhoto()).into(eventIV);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEventItemClick(mItem);
            }
        });
    }
}
