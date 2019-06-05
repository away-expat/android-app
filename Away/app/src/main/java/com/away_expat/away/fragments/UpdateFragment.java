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
        View view = inflater.inflate(R.layout.fragment_acc_update, container, false);

        AccUpdateFragment accUpdateFragment = new AccUpdateFragment();
        accUpdateFragment.setUser(user);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, accUpdateFragment).commit();

        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
