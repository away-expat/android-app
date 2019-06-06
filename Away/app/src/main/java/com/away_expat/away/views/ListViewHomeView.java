package com.away_expat.away.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;

import java.util.List;

public class ListViewHomeView extends ConstraintLayout {

    private Event event;
    private TextView activityNameTextView;
    private ImageView joinImageView;
    private User connectedUser;
    private boolean isConnectedUserInEvent = false;

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
        joinImageView = (ImageView) findViewById(R.id.activity_add);

        joinImageView.setOnClickListener(v -> joinEvent());
    }

    public void bind(User connectedUser, Event event) {
        this.connectedUser = connectedUser;
        this.event = event;

        activityNameTextView.setText(event.getName());

        for (User u : event.getParticipant()) {
            if (u.getId().equals(connectedUser.getId())) {
                joinImageView.setImageResource(R.drawable.done);
                isConnectedUserInEvent = true;
                break;
            }
        }
    }

    private void joinEvent() {
        if (isConnectedUserInEvent) {
            isConnectedUserInEvent = false;

            //TODO
            //Join event (may create the event id)
            List<User> p = event.getParticipant();
            p.remove(connectedUser);

            //update db
            event.setParticipant(p);

            joinImageView.setImageResource(R.drawable.add);
        } else {
            isConnectedUserInEvent = true;

            //TODO
            //Join event (may create the event id)
            List<User> p = event.getParticipant();
            p.add(connectedUser);

            //update db
            event.setParticipant(p);

            joinImageView.setImageResource(R.drawable.done);
        }
    }
}
