package com.away_expat.away.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.away_expat.away.MapsActivity;
import com.away_expat.away.R;
import com.away_expat.away.TagActivity;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.User;

import static android.app.Activity.RESULT_OK;

public class ActivityCreationFragment extends Fragment {

    private User connectedUser;
    private Button placePickerBtn, tagPickerBtn, saveBtn;
    private TextView selectedPlaceTV;
    private EditText title;
    private Activity toCreate;
    private CreationFragment previousFragment;

    public ActivityCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_creation, container, false);
        toCreate = new Activity();

        title = (EditText) view.findViewById(R.id.activity_crea_titleET);
        selectedPlaceTV = (TextView) view.findViewById(R.id.selected_placeTV);

        placePickerBtn = (Button) view.findViewById(R.id.place_picker);
        placePickerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra("connected_user", connectedUser);
            startActivityForResult(intent, 1);
        });

        tagPickerBtn = (Button) view.findViewById(R.id.event_crea_dateBtn);
        tagPickerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TagActivity.class);
            intent.putExtra("connected_user", connectedUser);
            startActivityForResult(intent, 2);
        });

        saveBtn = (Button) view.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(v -> {
            checkAndReturnNewActivity();
        });

        return view;
    }

    public void setData(User user, CreationFragment previousFragment) {
        this.connectedUser = user;
        this.previousFragment = previousFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            Log.i("INFO", "------------------------->"+ data.getExtras().get("position").toString());
            selectedPlaceTV.setText("Coordonnée : "+data.getExtras().get("position").toString());
        }

        if (resultCode == RESULT_OK && requestCode == 2) {
            //Log.i("INFO","------------------------->"+ data.getExtras().get("tag").toString());
        }
    }

    public void checkAndReturnNewActivity() {


        previousFragment.setNewActivity(null);
        getActivity().getFragmentManager().popBackStack();
    }
}
