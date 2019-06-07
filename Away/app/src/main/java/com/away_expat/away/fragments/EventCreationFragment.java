package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;

public class EventCreationFragment extends Fragment {

    private User connectedUser;

    public EventCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);



        return view;
    }

    public void setUser(User user) {
        this.connectedUser = user;
    }
}
