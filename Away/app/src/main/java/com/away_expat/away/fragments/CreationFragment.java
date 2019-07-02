package com.away_expat.away.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Event;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.services.EventApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreationFragment extends Fragment {

    private TextView activityTV, errorTV;
    private EditText titleET, descriptionET;
    private Button selectDateBtn, selectTimeBtn, selectActivityBtn, saveBtn;

    private Event toCreate = new Event();
    private String selectedDate;
    private String selectedTime;
    private Activity selectedActivity;

    final Calendar myCalendar = Calendar.getInstance();

    public CreationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creation, container, false);

        selectActivityBtn = (Button) view.findViewById(R.id.select_activity_btn);
        selectTimeBtn = (Button) view.findViewById(R.id.event_crea_timeBtn);
        selectDateBtn = (Button) view.findViewById(R.id.event_crea_dateBtn);
        saveBtn = (Button) view.findViewById(R.id.save_btn);

        titleET = (EditText) view.findViewById(R.id.event_crea_titleET);
        descriptionET = (EditText) view.findViewById(R.id.event_crea_descET);

        activityTV = (TextView) view.findViewById(R.id.selected_activity);
        activityTV.setVisibility(View.INVISIBLE);
        errorTV = (TextView) view.findViewById(R.id.error_text);
        errorTV.setVisibility(View.INVISIBLE);

        setupBtn();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedActivity != null) {
            toCreate.setIdActivity(selectedActivity.getId());
            String toDisplay = getActivity().getString(R.string.selected_activity_text)+" "+selectedActivity.getName();
            activityTV.setText(toDisplay);
            activityTV.setVisibility(View.VISIBLE);
        }

        if (selectedDate != null) {
            selectDateBtn.setText(selectedDate);
        }

        if (selectedTime != null) {
            selectTimeBtn.setText(selectedTime);
        }
    }

    private void setupBtn() {
        selectActivityBtn.setOnClickListener(v -> {
            SelectActivityFragment saf = new SelectActivityFragment();
            saf.setPreviousFrag(this, (HomeActivity) getActivity());
            ((HomeActivity) getActivity()).replaceFragment(saf);
        });

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                updateTime(hourOfDay, minute);
            }
        };
        selectTimeBtn.setOnClickListener(v -> new TimePickerDialog(getContext(), R.style.CustomDialogTheme, time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show());

        DatePickerDialog.OnDateSetListener date = (v, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        };
        selectDateBtn.setOnClickListener(v -> new DatePickerDialog(getContext(), R.style.CustomDialogTheme, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        saveBtn.setOnClickListener(v -> {
            toCreate.setTitle(titleET.getText().toString());
            toCreate.setDescription(descriptionET.getText().toString());
            List<String> needed = toCreate.isComplete();
            if (needed.size() == 0) {
                Date toTest = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/HH:mm", Locale.getDefault());
                try {
                    toTest = sdf.parse(toCreate.getDate()+"/"+toCreate.getHour());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (toTest.getTime() < new Date().getTime()) {
                    errorTV.setVisibility(View.VISIBLE);
                    errorTV.setText(R.string.error_date);
                    errorTV.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    String token = getActivity().getIntent().getStringExtra("token");
                    Call<DetailedEventDto> call = RetrofitServiceGenerator.createService(EventApiService.class).createEvent(token, toCreate);

                    call.enqueue(new Callback<DetailedEventDto>() {
                        @Override
                        public void onResponse(Call<DetailedEventDto> call, Response<DetailedEventDto> response) {
                            if (response.isSuccessful()) {
                                EventFragment fragment = new EventFragment();
                                fragment.setEvent(response.body());
                                ((HomeActivity) getActivity()).replaceFragment(fragment);
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DetailedEventDto> call, Throwable t) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                String todisplay = "";
                for (String s : needed) {
                    switch (s) {
                        case "title":
                            todisplay += " "+getActivity().getString(R.string.title);
                            break;
                        case "description":
                            todisplay += " "+getActivity().getString(R.string.description);
                            break;
                        case "hour":
                            todisplay += " "+getActivity().getString(R.string.time);
                            break;
                        case "date":
                            todisplay += " "+getActivity().getString(R.string.date);
                            break;
                        case "activity":
                            todisplay += " "+getActivity().getString(R.string.activity);
                            break;
                    }
                }

                todisplay += " "+getActivity().getString(R.string.missing);
                errorTV.setVisibility(View.VISIBLE);
                errorTV.setText(todisplay);
                errorTV.setTextColor(Color.parseColor("#ff0000"));
            }
        });
    }

    private void updateDate() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        this.selectedDate = sdf.format(myCalendar.getTime());

        selectDateBtn.setText(selectedDate);
        toCreate.setDate(selectedDate);
    }

    private void updateTime(int hourOfDay, int minute) {
        if (minute < 10) {
            this.selectedTime = hourOfDay+":0"+minute;
        } else {
            this.selectedTime = hourOfDay+":"+minute;
        }

        selectTimeBtn.setText(selectedTime);
        toCreate.setHour(selectedTime);
    }

    public void updateActivity(Activity activity) {
        this.selectedActivity = activity;
    }

}
