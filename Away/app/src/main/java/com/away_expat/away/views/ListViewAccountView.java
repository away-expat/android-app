package com.away_expat.away.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.away_expat.away.R;

public class ListViewAccountView extends LinearLayout {
    private TextView mTextView;

    public ListViewAccountView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewAccountView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_account, this);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    public void bind(int text) {
        mTextView.setText(getResources().getString(text));
    }
}
