package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;
import com.away_expat.away.tools.CircleTransform;
import com.squareup.picasso.Picasso;

public class ListViewSearchUserView extends ConstraintLayout {

    private TextView titleTV, subtitleTV;
    private ImageView imageIV;
    private User user;

    public ListViewSearchUserView(Context context) {
        super(context);
        init();
    }

    public ListViewSearchUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewSearchUserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_search, this);
        titleTV = (TextView) findViewById(R.id.search_list_title);
        subtitleTV = (TextView) findViewById(R.id.search_list_subtitle);
        imageIV = (ImageView) findViewById(R.id.search_list_image);
    }

    public void bind(User user) {
        this.user = user;

        String diplayName = user.getFirstname()+" "+user.getLastname();
        titleTV.setText(diplayName);
        subtitleTV.setText(user.getCountry());
        Picasso.get().load(user.getAvatar()).into(imageIV);
    }
}
