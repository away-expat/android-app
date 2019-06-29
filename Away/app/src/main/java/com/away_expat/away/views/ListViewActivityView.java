package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Activity;
import com.squareup.picasso.Picasso;

public class ListViewActivityView extends ConstraintLayout {

    private TextView mTextView;
    private ImageView imageView;

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
        imageView = (ImageView) findViewById(R.id.activity_image_layout);
    }

    public void bind(Activity activity) {
        mTextView.setText(activity.getName());
        Picasso.get().load(activity.getPhotos()).into(imageView);
    }
}
