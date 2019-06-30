package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;
import com.squareup.picasso.Picasso;

public class ListViewUserView extends ConstraintLayout {
    private TextView mTextView;
    private ImageView mImageView;

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
        mImageView = (ImageView) findViewById(R.id.userlist_image);
    }

    public void bind(User user) {
        String toDisplay = user.getFirstname()+" "+user.getLastname();
        mTextView.setText(toDisplay);
        Picasso.get().load(user.getAvatar()).into(mImageView);
    }
}
