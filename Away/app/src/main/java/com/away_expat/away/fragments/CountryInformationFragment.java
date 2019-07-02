package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.CountryListViewAdapter;
import com.away_expat.away.classes.Info;
import com.away_expat.away.services.InfoApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryInformationFragment extends Fragment {

    private CountryListViewAdapter adapter;
    private String token;
    private ListView listview;
    private TextView notFound;

    private List<Info> items = new ArrayList<>();

    public CountryInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_information, container, false);
        token = getActivity().getIntent().getStringExtra("token");

        listview = (ListView) view.findViewById(R.id.list_view);
        notFound = (TextView) view.findViewById(R.id.not_found);
        notFound.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Call<List<Info>> callOne = RetrofitServiceGenerator.createService(InfoApiService.class).getCountryInfo(token);

        callOne.enqueue(new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() == 0) {
                        notFound.setVisibility(View.VISIBLE);
                    } else {
                        notFound.setVisibility(View.GONE);
                    }

                    items = response.body();
                    adapter = new CountryListViewAdapter(getActivity());
                    adapter.bind(items);

                    listview.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Info>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });

        listview.setOnItemClickListener((adapterView, view, position, l) -> {
            CountryInfoDetailFragment fragment = new CountryInfoDetailFragment();

            fragment.setCountryInfo(adapter.getItem(position));
            ((HomeActivity) getActivity()).replaceFragment(fragment);
        });

    }
}
