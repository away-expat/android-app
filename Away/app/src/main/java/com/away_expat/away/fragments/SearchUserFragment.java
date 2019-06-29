package com.away_expat.away.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.SearchTagListViewAdapter;
import com.away_expat.away.adapters.SearchUserListViewAdapter;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserFragment extends Fragment {

    private SearchUserListViewAdapter adapter;
    private ListView listview;
    private TextView searchTV;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);

        listview = (ListView) view.findViewById(R.id.list_view);
        searchTV = (TextView) view.findViewById(R.id.search_text);

        if (adapter == null) {
            adapter = new SearchUserListViewAdapter(getActivity());
            adapter.bind(new ArrayList<>());
            listview.setAdapter(adapter);
        } else {
            searchTV.setVisibility(View.INVISIBLE);
            listview.setAdapter(adapter);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AccountFragment fragment = new AccountFragment();
                fragment.setUser(adapter.getItem(i), false);
                ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

        return view;
    }

    public void updateListView(String search, Activity mainActivity) {
        token = mainActivity.getIntent().getStringExtra("token");
        Call<List<User>> call = RetrofitServiceGenerator.createService(UserApiService.class).searchByText(token, search);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (searchTV != null) {
                        searchTV.setVisibility(View.INVISIBLE);
                    }

                    if (adapter == null) {
                        adapter = new SearchUserListViewAdapter(mainActivity);
                    }

                    adapter.bind(response.body());
                    adapter.notifyDataSetChanged();

                    if (listview != null) {
                        listview.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(mainActivity, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(mainActivity, getActivity().getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
