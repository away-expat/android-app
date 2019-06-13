package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;

import java.util.ArrayList;
import java.util.List;

public class UserCreationFragment extends Fragment {

    private EditText emailET, passwordET, firstnameET, lastnameET, birthdateET;
    private Spinner countryET;
    private Button disconnectBtn;
    private User user = null;
    private boolean connected = false;

    public UserCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);
        emailET = (EditText) view.findViewById(R.id.account_input_email);
        passwordET = (EditText) view.findViewById(R.id.account_input_password);
        firstnameET = (EditText) view.findViewById(R.id.account_input_lastname);
        lastnameET = (EditText) view.findViewById(R.id.account_input_firstname);
        birthdateET = (EditText) view.findViewById(R.id.account_input_birthdate);
        countryET = (Spinner) view.findViewById(R.id.account_input_country);

        setCountrySpinner();

        disconnectBtn = (Button) view.findViewById(R.id.disconnectionBtn);

        if (user != null) {
            emailET.setText(user.getEmail());
            passwordET.setText(user.getPassword());
            firstnameET.setText(user.getFirstname());
            lastnameET.setText(user.getLastname());
        }

        if (connected) {
            disconnectBtn.setVisibility(View.VISIBLE);
        } else {
            disconnectBtn.setVisibility(View.INVISIBLE);
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void setCountrySpinner() {
        //raw data to remove
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("France");
        spinnerArray.add("England");
        spinnerArray.add("España");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                spinnerArray
        );

        countryET.setAdapter(adapter);
    }

    public User checkAndGetAccountInfo () {
        User acc = null;

        if (!emailET.getText().toString().equals("")) {
            if (!passwordET.getText().toString().equals("")) {
                acc = new User(emailET.getText().toString(), passwordET.getText().toString());
            } else {
                passwordET.setError(getActivity().getResources().getString(R.string.required));
                return null;
            }
        } else {
            emailET.setError(getActivity().getResources().getString(R.string.required));
            return null;
        }

        return acc;
    }

    public void setUser(User user, boolean connected) {
        this.user = user;
        this.connected = connected;
    }
}
