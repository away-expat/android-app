package com.away_expat.away.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.away_expat.away.MapsActivity;
import com.away_expat.away.R;
import com.away_expat.away.classes.User;

import static android.app.Activity.RESULT_OK;

public class ActivityCreationFragment extends Fragment {

    private User connectedUser;
    private Button placePickerBtn;

    public ActivityCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_creation, container, false);

        placePickerBtn = (Button) view.findViewById(R.id.place_picker);
        placePickerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivityForResult(intent, 1);


        });

        return view;
    }

    public void setUser(User user) {
        this.connectedUser = user;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            Log.i("INFO", data.getExtras().get("position").toString());
        }

    }
}
