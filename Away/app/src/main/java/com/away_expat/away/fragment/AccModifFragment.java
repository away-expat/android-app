package com.away_expat.away.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.away_expat.away.R;
import com.away_expat.away.classes.Account;

import java.util.ArrayList;
import java.util.List;

public class AccModifFragment extends Fragment {

    private EditText emailET, passwordET, firstnameET, lastnameET, birthdateET;
    private Spinner countryET;

    public AccModifFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc_modif, container, false);
        emailET = (EditText) view.findViewById(R.id.account_input_email);
        passwordET = (EditText) view.findViewById(R.id.account_input_password);
        firstnameET = (EditText) view.findViewById(R.id.account_input_firstname);
        lastnameET = (EditText) view.findViewById(R.id.account_input_lastname);
        birthdateET = (EditText) view.findViewById(R.id.account_input_birthdate);
        countryET = (Spinner) view.findViewById(R.id.account_input_country);

        setCountrySpinner();

        // Inflate the layout for this fragment
        return view;
    }

    private void setCountrySpinner() {
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

    public Account checkAndGetAccountInfo () {
        Account acc = null;

        if (!emailET.getText().toString().equals("")) {
            if (!passwordET.getText().toString().equals("")) {
                acc = new Account(emailET.getText().toString(), passwordET.getText().toString());
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
}
