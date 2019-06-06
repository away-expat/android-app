package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;

public class GridViewSearchView extends ConstraintLayout {

    private Tag tag;
    private TextView mTextView;

    public GridViewSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GridViewSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridViewSearchView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.gridview_search, this);
        mTextView = (TextView) findViewById(R.id.tag_name);
    }

    public void bind(Tag tag) {
        this.tag = tag;
        mTextView.setText(tag.getName());
    }

}
