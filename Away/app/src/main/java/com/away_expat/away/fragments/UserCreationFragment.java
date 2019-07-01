package com.away_expat.away.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCreationFragment extends Fragment {

    private TextView passwordTV;
    private EditText emailET, passwordET, firstnameET, lastnameET;
    private Spinner countrySpin;
    private Button birthdayBtn, saveBtn;
    private User user = null;
    private String selectedDate = null;
    private boolean connected = false;

    ArrayAdapter<CharSequence> countryAdapter;

    final Calendar myCalendar = Calendar.getInstance();

    public UserCreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);
        passwordTV = (TextView) view.findViewById(R.id.password_text);
        emailET = (EditText) view.findViewById(R.id.account_input_email);
        passwordET = (EditText) view.findViewById(R.id.account_input_password);
        firstnameET = (EditText) view.findViewById(R.id.account_input_firstname);
        lastnameET = (EditText) view.findViewById(R.id.account_input_lastname);
        countrySpin = (Spinner) view.findViewById(R.id.account_input_country);

        setCountrySpinner();

        saveBtn = (Button) view.findViewById(R.id.save_button);
        birthdayBtn = (Button) view.findViewById(R.id.account_birthdate_btn);

        if (user != null) {
            emailET.setText(user.getEmail());
            firstnameET.setText(user.getFirstname());
            lastnameET.setText(user.getLastname());
            birthdayBtn.setText(user.getBirthday());
            selectedDate = user.getBirthday();

            countrySpin.setSelection(countryAdapter.getPosition(user.getCountry()));
        } else {
            user = new User();
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                selectedDate = sdf.format(myCalendar.getTime());
                birthdayBtn.setText(sdf.format(myCalendar.getTime()));
            }
        };
        birthdayBtn.setOnClickListener(v -> new DatePickerDialog(getContext(), R.style.CustomDialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        if (connected) {
            saveBtn.setVisibility(View.VISIBLE);
            saveBtn.setOnClickListener(v -> {
                if (checkForm()) {
                    String token = getActivity().getIntent().getStringExtra("token");
                    User toUpdate = new User(emailET.getText().toString(), passwordET.getText().toString(),
                            firstnameET.getText().toString(), lastnameET.getText().toString(),
                            selectedDate, countrySpin.getSelectedItem().toString());
                    Call<User> call = RetrofitServiceGenerator.createService(UserApiService.class).updateUser(token, toUpdate);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.i("AWAYINFO", "Update success : " + response.message());
                            if (response.isSuccessful()) {

                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.i("AWAYINFO", "-----------------> " + t.getMessage());
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            saveBtn.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private boolean checkForm() {
        //TODO
        return true;
    }

    private void setCountrySpinner() {
        countryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.countries_array, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpin.setAdapter(countryAdapter);
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
