package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Event;

public class EventFragment extends Fragment {

    private TextView position;
    private Event event;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        position = (TextView) v.findViewById(R.id.position);
        position.setText(event.getActivityName());

        return v;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
