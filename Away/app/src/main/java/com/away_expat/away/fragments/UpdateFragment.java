package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;

public class UpdateFragment extends Fragment {

    private User user;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_creation, container, false);

        UserCreationFragment userCreationFragment = new UserCreationFragment();
        userCreationFragment.setUser(user, true);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, userCreationFragment).commit();

        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
