package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;

import java.util.List;

public class ListViewHomeView extends ConstraintLayout {

    private Event event;
    private TextView activityNameTV;
    private ImageView coverIV;

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
        activityNameTV = (TextView) findViewById(R.id.activity_name);
        coverIV = (ImageView) findViewById(R.id.activity_image_layout);
    }

    public void bind(Event event) {
        this.event = event;

        activityNameTV.setText(event.getTitle());
    }
}
