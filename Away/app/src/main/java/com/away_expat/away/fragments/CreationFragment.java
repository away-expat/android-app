package com.away_expat.away.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreationFragment extends Fragment {

    private EditText titleET, descriptionET;
    private TextView dateTV;
    private Button selectDateBtn, selectTimeBtn, selectActivityBtn, createActivityBtn, saveBtn;
    private User connectedUser;
    private Activity activity;

    private String selectedDate;
    private String selectedTime;

    final Calendar myCalendar = Calendar.getInstance();

    public CreationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation, container, false);

        selectActivityBtn = (Button) view.findViewById(R.id.select_activity_btn);
        selectActivityBtn.setOnClickListener(v -> {
            //TODO
        });

        createActivityBtn = (Button) view.findViewById(R.id.create_activity_btn);
        createActivityBtn.setOnClickListener(v -> {
            ActivityCreationFragment fragment = new ActivityCreationFragment();
            fragment.setData(connectedUser, this);

            ((HomeActivity) getActivity()).replaceFragment(fragment);
        });

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                updateTime(hourOfDay, minute);
            }
        };
        selectTimeBtn = (Button) view.findViewById(R.id.event_crea_timeBtn);
        selectTimeBtn.setOnClickListener(v -> new TimePickerDialog(getContext(), R.style.CustomDialogTheme, time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        selectDateBtn = (Button) view.findViewById(R.id.event_crea_dateBtn);
        selectDateBtn.setOnClickListener(v -> new DatePickerDialog(getContext(), R.style.CustomDialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        return view;
    }

    public void setUser() {
        this.connectedUser = new User();
    }

    public void setNewActivity(Activity newActivity) {
        this.activity = newActivity;
    }

    private void updateDate() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        this.selectedDate = sdf.format(myCalendar.getTime());

        selectDateBtn.setText(selectedDate);
    }

    private void updateTime(int hourOfDay, int minute) {
        this.selectedTime = hourOfDay+":"+minute;

        selectTimeBtn.setText(selectedTime);
    }

}
