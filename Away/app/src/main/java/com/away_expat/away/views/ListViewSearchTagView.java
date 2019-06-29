package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.tools.CircleTransform;
import com.squareup.picasso.Picasso;

public class ListViewSearchTagView extends ConstraintLayout {

    private Tag tag;
    private TextView titleTV, subtitleTV;
    private ImageView imageIV;

    public ListViewSearchTagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListViewSearchTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewSearchTagView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.listview_search, this);
        titleTV = (TextView) findViewById(R.id.search_list_title);
        subtitleTV = (TextView) findViewById(R.id.search_list_subtitle);
        imageIV = (ImageView) findViewById(R.id.search_list_image);
    }

    public void bind(Tag tag) {
        this.tag = tag;
        titleTV.setText(tag.getName());
        Picasso.get().load(R.drawable.tag).into(imageIV);
    }

}
