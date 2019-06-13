package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.classes.User;

public class CreationFragment extends Fragment {

    private EditText titleET, descriptionET, dateET;
    private Button selectActivityBtn, createActivityBtn, saveBtn;
    private User connectedUser;

    public CreationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation, container, false);

        selectActivityBtn = (Button) view.findViewById(R.id.select_activity_btn);
        selectActivityBtn.setOnClickListener(v -> {
            EventCreationFragment fragment = new EventCreationFragment();
            fragment.setUser(connectedUser);

            ((HomeActivity) getActivity()).replaceFragment(fragment);
        });

        createActivityBtn = (Button) view.findViewById(R.id.create_activity_btn);
        createActivityBtn.setOnClickListener(v -> {
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
