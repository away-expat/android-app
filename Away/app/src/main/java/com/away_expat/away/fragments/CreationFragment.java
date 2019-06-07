package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.classes.User;

public class CreationFragment extends Fragment {

    private ConstraintLayout eventTile, activityTile;
    private User connectedUser;

    public CreationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation, container, false);

        eventTile = (ConstraintLayout) view.findViewById(R.id.create_event_layout);
        eventTile.setOnClickListener(v -> {
            EventCreationFragment fragment = new EventCreationFragment();
            fragment.setUser(connectedUser);

            ((HomeActivity) getActivity()).replaceFragment(fragment);
        });

        activityTile = (ConstraintLayout) view.findViewById(R.id.create_activity_layout);
        activityTile.setOnClickListener(v -> {
            ActivityCreationFragment fragment = new ActivityCreationFragment();
            fragment.setUser(connectedUser);

            ((HomeActivity) getActivity()).replaceFragment(fragment);
        });

        return view;
    }

    public void setUser(User user) {
        this.connectedUser = user;
    }

}
