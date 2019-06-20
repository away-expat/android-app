package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.CityListViewAdapter;
import com.away_expat.away.adapters.SearchGridViewAdapter;
import com.away_expat.away.classes.City;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.CityApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;
import com.away_expat.away.services.UserApiService;
import com.away_expat.away.views.ListViewCityView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityFragment extends ListFragment {

    private EditText searchET;
    private CityListViewAdapter adapter;
    private Button selectBtn;
    private String token;
    private User connectedUser;

    private ListViewCityView oldView;
    private City selectedCity = null;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        searchET = (EditText) view.findViewById(R.id.search_ET);
        selectBtn = (Button) view.findViewById(R.id.city_select_btn);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = getActivity().getIntent().getStringExtra("token");
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");

        if (token == null && connectedUser == null) {
            selectBtn.setVisibility(View.INVISIBLE);
        } else {
            setupSelect();
        }

        getCities();
        setupSearch();
    }

    private void setupSearch() {
        searchET.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                searchCities(c.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void searchCities(String search) {
        Call<List<City>> call = RetrofitServiceGenerator.createService(CityApiService.class).searchCities(search);

        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                adapter = new CityListViewAdapter(getActivity());

                if (response.isSuccessful()) {
                    adapter.bind(response.body());
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }

                setListAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }

                setListAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSelect() {
        selectBtn.setOnClickListener(v -> {
            connectedUser.setIdCity(Integer.toString(selectedCity.getId()));

            Call<User> call = RetrofitServiceGenerator.createService(UserApiService.class).updateUser(token, connectedUser);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {
                        connectedUser = response.body();
                        getActivity().getIntent().putExtra("connectedUser", connectedUser);
                        ((HomeActivity) getActivity()).setupUserCity();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        ((ListViewCityView) v).setSelected();

        if (selectedCity != null) {
            oldView.unselect();
        }

        selectedCity = adapter.getItem(pos);
        oldView = ((ListViewCityView) v);
    }
}
