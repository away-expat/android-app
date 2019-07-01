package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.Info;
import com.squareup.picasso.Picasso;

public class ListViewCountryInfoView extends ConstraintLayout {

    private Info info;
    private TextView titleTV;

    public ListViewCountryInfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewCountryInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewCountryInfoView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_country_info, this);
        titleTV = (TextView) findViewById(R.id.title);
    }

    public void bind(Info info) {
        this.info = info;
        titleTV.setText(info.getTitle());
    }
}