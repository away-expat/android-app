package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Activity;

public class ListViewActivityView extends ConstraintLayout {

    private TextView mTextView;

    public ListViewActivityView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewActivityView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_activity, this);
        mTextView = (TextView) findViewById(R.id.activity_name);
    }

    public void bind(Activity activity) {
        mTextView.setText(activity.getName());
    }
}
