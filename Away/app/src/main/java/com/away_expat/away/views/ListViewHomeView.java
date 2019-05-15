package com.away_expat.away.views;

import android.content.Context;
import android.renderscript.RenderScript;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;

public class ListViewHomeView extends ConstraintLayout {

    private Event event;
    private TextView activityNameTextView, activityDescriptionTextView;
    private ImageView joinImageView;

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
        activityNameTextView = (TextView) findViewById(R.id.activity_name);
        activityDescriptionTextView = (TextView) findViewById(R.id.activity_description);
        joinImageView = (ImageView) findViewById(R.id.activity_add);

        joinImageView.setOnClickListener(v -> Log.d("Info", "-------------------------> "+event.getActivityName()));
    }

    public void bind(Event event) {
        this.event = event;
        activityNameTextView.setText(event.getActivityName());
        activityDescriptionTextView.setText(event.getActivityDescription());
    }
}
