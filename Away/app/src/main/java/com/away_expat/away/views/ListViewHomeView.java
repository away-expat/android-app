package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.squareup.picasso.Picasso;

public class ListViewHomeView extends ConstraintLayout {

    private Event event;
    private TextView activityNameTV, eventNameTV, dateTV;
    private ImageView coverIV, promotedIV;

    public ListViewHomeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewHomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewHomeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_home, this);
        promotedIV = (ImageView) findViewById(R.id.promoted_img);
        promotedIV.setVisibility(GONE);
        eventNameTV = (TextView) findViewById(R.id.event_name);
        activityNameTV = (TextView) findViewById(R.id.activity_name);
        dateTV = (TextView) findViewById(R.id.date_text);
        coverIV = (ImageView) findViewById(R.id.activity_image_layout);
    }

    public void bind(Event event) {
        this.event = event;
        eventNameTV.setText(event.getTitle());
        activityNameTV.setText(event.getActivityName());
        String toDisplay = event.getDate()+" "+event.getHour();
        dateTV.setText(toDisplay);

        Picasso.get().load(event.getPhoto()).into(coverIV);
        if (event.isPromoted()) {
            promotedIV.setVisibility(VISIBLE);
        }
    }
}
