package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.ActivityListViewAdapter;
import com.away_expat.away.adapters.SearchGridViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.ActivityApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchGridViewAdapter adapter;
    private TextView searchTV;
    private EditText searchET;
    private GridView gridView;
    private User connectedUser;

    private RetrofitServiceGenerator retrofitServiceGenerator;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        gridView = (GridView) view.findViewById(R.id.grid_view);
        searchTV = (TextView) view.findViewById(R.id.search_text);
        searchET = (EditText) view.findViewById(R.id.search_input);

        searchET.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                Log.i("INFO", c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        Call<List<Tag>> call = retrofitServiceGenerator.createService(TagApiService.class).getAllTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                adapter = new SearchGridViewAdapter(getActivity());
                adapter.bind(response.body());

                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                DetailedSearchFragment fragment = new DetailedSearchFragment();
                fragment.setData(adapter.getItem(position), connectedUser);

                ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

        return view;
    }

    public void setUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

}
