package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Country;

public class ListViewCountryView extends ConstraintLayout {
    private TextView mTextView;

    public ListViewCountryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewCountryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewCountryView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_country, this);
        mTextView = (TextView) findViewById(R.id.countrylist_name);
    }

    public void bind(Country country) {
        mTextView.setText(country.getCountryName());
    }
}
