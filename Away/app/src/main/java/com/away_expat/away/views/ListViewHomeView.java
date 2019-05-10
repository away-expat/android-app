package com.away_expat.away.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.away_expat.away.R;

public class ListViewHomeView extends LinearLayout {
    private TextView mTextView;

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
        mTextView = (TextView) findViewById(R.id.textView);
    }

    public void bind(int text) {
        mTextView.setText(getResources().getString(text));
    }
}
