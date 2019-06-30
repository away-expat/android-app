package com.away_expat.away.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.SearchTagListViewAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTagFragment extends Fragment {

    private SearchTagListViewAdapter adapter;
    private ImageView searchIV;
    private ListView listview;
    private String token;

    public SearchTagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_tag, container, false);

        listview = (ListView) view.findViewById(R.id.list_view);
        searchIV = (ImageView) view.findViewById(R.id.search_img);

        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getAllTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    searchIV.setVisibility(View.INVISIBLE);

                    adapter = new SearchTagListViewAdapter(getActivity());
                    adapter.bind(response.body());

                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                DetailedTagSearchFragment fragment = new DetailedTagSearchFragment();
                fragment.setData(adapter.getItem(position));

                ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

        return view;
    }

    public void updateListView(String search, Activity mainActivity) {
        token = mainActivity.getIntent().getStringExtra("token");
        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).searchByText(token, search);

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    if (searchIV != null) {
                        searchIV.setVisibility(View.INVISIBLE);
                    }

                    if (adapter == null) {
                        adapter = new SearchTagListViewAdapter(mainActivity);
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
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Toast.makeText(mainActivity, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
