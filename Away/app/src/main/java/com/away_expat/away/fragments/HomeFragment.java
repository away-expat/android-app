package com.away_expat.away.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.HomeListViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.User;
import com.away_expat.away.classes.Event;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.services.ActivityApiService;
import com.away_expat.away.services.EventApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private HomeListViewAdapter adapter;
    private User connectedUser;
    private String token;
    private ListView listview;

    private List<Event> items = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        token = getActivity().getIntent().getStringExtra("token");
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");

        listview = (ListView) view.findViewById(R.id.list_view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Call<List<Event>> callOne = RetrofitServiceGenerator.createService(ActivityApiService.class).getHome(token);

        callOne.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    adapter = new HomeListViewAdapter(getActivity());
                    adapter.bind(items);

                    listview.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });

        listview.setOnItemClickListener((adapterView, view, position, l) -> {
            Call<DetailedEventDto> call = RetrofitServiceGenerator.createService(EventApiService.class).getById(token, adapter.getItem(position).getId());

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
        });

    }


}
