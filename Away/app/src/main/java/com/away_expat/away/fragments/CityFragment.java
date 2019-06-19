package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.away_expat.away.R;
import com.away_expat.away.adapters.CityListViewAdapter;
import com.away_expat.away.adapters.SearchGridViewAdapter;
import com.away_expat.away.classes.City;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.services.CityApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityFragment extends ListFragment {

    private EditText searchET;
    private CityListViewAdapter adapter;
    private String token;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        searchET = (EditText) view.findViewById(R.id.search_ET);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = getActivity().getIntent().getStringExtra("token");

        getCities();
        setupSearch();
    }

    private void setupSearch() {
        searchET.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                Log.i("AWAYINFO", "--------------------------------> "+c.toString());
                searchCities(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void searchCities(String search) {
        Call<List<City>> call = RetrofitServiceGenerator.createService(CityApiService.class).searchCities(token, search);

        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                adapter = new CityListViewAdapter(getActivity());

                if (response.isSuccessful()) {
                    adapter.bind(response.body());
                } else {
                    //TODO
                }

                setListAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }

    private void getCities() {
        Call<List<City>> call = RetrofitServiceGenerator.createService(CityApiService.class).getCities(token);

        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                adapter = new CityListViewAdapter(getActivity());

                if (response.isSuccessful()) {
                    adapter.bind(response.body());
                } else {
                    //TODO
                }

                setListAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }


}
