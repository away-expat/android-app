package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private CityListViewAdapter adapter;
    private String token;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = getActivity().getIntent().getStringExtra("token");

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
