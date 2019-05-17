package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.away_expat.away.R;
import com.away_expat.away.adapters.CountryListViewAdapter;
import com.away_expat.away.classes.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends ListFragment {

    private CountryListViewAdapter adapter;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get the countries
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("France"));
        countries.add(new Country("USA"));
        countries.add(new Country("Espagne"));

        adapter = new CountryListViewAdapter(getActivity());
        adapter.bind(countries);

        setListAdapter(adapter);
    }

}
