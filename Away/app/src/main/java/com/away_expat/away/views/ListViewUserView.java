package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;

public class ListViewUserView extends ConstraintLayout {
    private TextView mTextView;

    public ListViewUserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewUserView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_user, this);
        mTextView = (TextView) findViewById(R.id.userlist_username);
    }

    public void bind(User user) {
        mTextView.setText(user.getFirstname()+" "+user.getLastname());
    }
}
