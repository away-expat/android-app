package com.away_expat.away.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.RetrofitServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UserCreationFragment extends Fragment {

    private EditText emailET, passwordET, firstnameET, lastnameET;
    private Spinner countrySpin;
    private Button birthdayBtn, disconnectBtn;
    private User user = null;
    private String selectedDate = null;
    private boolean connected = false;

    final Calendar myCalendar = Calendar.getInstance();

    public UserCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);
        emailET = (EditText) view.findViewById(R.id.account_input_email);
        passwordET = (EditText) view.findViewById(R.id.account_input_password);
        firstnameET = (EditText) view.findViewById(R.id.account_input_firstname);
        lastnameET = (EditText) view.findViewById(R.id.account_input_lastname);
        countrySpin = (Spinner) view.findViewById(R.id.account_input_country);

        setCountrySpinner();

        disconnectBtn = (Button) view.findViewById(R.id.disconnectionBtn);
        birthdayBtn = (Button) view.findViewById(R.id.account_birthdate_btn);

        if (user != null) {
            emailET.setText(user.getEmail());
            firstnameET.setText(user.getFirstname());
            lastnameET.setText(user.getLastname());
        } else {
            user = new User();
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                selectedDate = sdf.format(myCalendar.getTime());
                birthdayBtn.setText(sdf.format(myCalendar.getTime()));
            }
        };
        birthdayBtn.setOnClickListener(v -> new DatePickerDialog(getContext(), R.style.CustomDialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

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

        countrySpin.setAdapter(adapter);
    }

    public User checkAndGetAccountInfo () {
        if (!emailET.getText().toString().equals("")) {
            if (!passwordET.getText().toString().equals("")) {
                user = new User(emailET.getText().toString(), passwordET.getText().toString());
            } else {
                passwordET.setError(getActivity().getResources().getString(R.string.required));
                return null;
            }
        } else {
            emailET.setError(getActivity().getResources().getString(R.string.required));
            return null;
        }

        if (!firstnameET.getText().toString().equals("")) {
            user.setFirstname(firstnameET.getText().toString());
        }

        if (!lastnameET.getText().toString().equals("")) {
            user.setLastname(lastnameET.getText().toString());
        }

        if (selectedDate != null) {
            user.setBirthday(selectedDate);
        }

        user.setCountry(countrySpin.getSelectedItem().toString());

        return user;
    }

    public void setUser(User user, boolean connected) {
        this.user = user;
        this.connected = connected;
    }
}
