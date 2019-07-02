package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;

public class ListViewEventView extends ConstraintLayout {
    private Event event;
    private TextView eventNameTextView, eventDateTextView;

    public ListViewEventView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewEventView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_account, this);
        eventNameTextView = (TextView) findViewById(R.id.account_event_title);
        eventDateTextView = (TextView) findViewById(R.id.account_event_date);
    }

    public void bind(Event event) {
        this.event = event;

        eventNameTextView.setText(event.getTitle());
        eventDateTextView.setText(event.getDate());
    }
}
