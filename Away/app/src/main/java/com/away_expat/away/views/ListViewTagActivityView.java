package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.away_expat.away.R;

public class ListViewTagActivityView extends ConstraintLayout {
    private TextView mTextView;
    private String tag;

    public ListViewTagActivityView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewTagActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewTagActivityView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_tag_activity, this);
        mTextView = (TextView) findViewById(R.id.tag_text);
    }

    public void bind(String tag) {
        this.tag = tag;
        mTextView.setText(tag);
    }
}
