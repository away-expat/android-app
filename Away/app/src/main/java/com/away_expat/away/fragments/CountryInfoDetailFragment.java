package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.Info;

public class CountryInfoDetailFragment extends Fragment {

    private TextView titleTV, contentTV;
    private Info info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_info_detail, container, false);

        titleTV = (TextView) view.findViewById(R.id.country_info_title);
        titleTV.setText(info.getTitle());

        contentTV = (TextView) view.findViewById(R.id.country_info_content);
        contentTV.setText(info.getContent());

        return view;
    }

    public void setCountryInfo(Info info) {
        this.info = info;
    }
}
