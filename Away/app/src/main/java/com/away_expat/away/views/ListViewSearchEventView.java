package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.squareup.picasso.Picasso;

public class ListViewSearchEventView extends ConstraintLayout {

    private TextView titleTV, subtitleTV;
    private ImageView imageIV;
    private Event event;

    public ListViewSearchEventView(Context context) {
        super(context);
        init();
    }

    public ListViewSearchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewSearchEventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_search, this);
        titleTV = (TextView) findViewById(R.id.search_list_title);
        subtitleTV = (TextView) findViewById(R.id.search_list_subtitle);
        imageIV = (ImageView) findViewById(R.id.search_list_image);
    }

    public void bind(Event event) {
        this.event = event;

        titleTV.setText(event.getTitle());
        String dateDisplay = event.getDate()+" "+event.getHour();
        subtitleTV.setText(dateDisplay);
        Picasso.get().load(event.getPhoto()).into(imageIV);
    }
}
