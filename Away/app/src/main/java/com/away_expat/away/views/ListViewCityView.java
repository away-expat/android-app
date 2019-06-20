package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.City;

public class ListViewCityView extends ConstraintLayout {
    private TextView mTextView;
    private ImageView selectedIV;

    public ListViewCityView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewCityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewCityView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_city, this);
        mTextView = (TextView) findViewById(R.id.countrylist_name);
        selectedIV = (ImageView) findViewById(R.id.countrylist_img);

        selectedIV.setVisibility(INVISIBLE);
    }

    public void bind(City city) {
        String toDisplay = city.getName()+" - "+city.getCountry();
        mTextView.setText(toDisplay);
    }

    public void setSelected() {
        selectedIV.setVisibility(VISIBLE);
    }

    public void unselect() {
        selectedIV.setVisibility(INVISIBLE);
    }
}
