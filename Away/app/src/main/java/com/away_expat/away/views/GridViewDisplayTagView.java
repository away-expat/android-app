package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;

public class GridViewDisplayTagView extends ConstraintLayout {
    private TextView mTextView;
    private ImageView mImageView;
    private Tag tag;

    public GridViewDisplayTagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GridViewDisplayTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridViewDisplayTagView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.gridview_tag_selection, this);
        mTextView = (TextView) findViewById(R.id.tag_text);
        mImageView = (ImageView) findViewById(R.id.remove_img);
        mImageView.setVisibility(GONE);
    }

    public void bind(Tag tag) {
        this.tag = tag;
        mTextView.setText(tag.getName());
    }
}