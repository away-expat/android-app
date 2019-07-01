package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.SearchActivityListViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.services.ActivityApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityFragment extends Fragment {

    private SearchActivityListViewAdapter adapter;
    private ListView listview;
    private ImageView searchIV;
    private ProgressBar progressBar;
    private TextView notFoundTV;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_sub, container, false);

        if (getView() != null) {
            return getView();
        }

        token = getActivity().getIntent().getStringExtra("token");

        progressBar = (ProgressBar) view.findViewById(R.id.search_progress);
        progressBar.setVisibility(View.GONE);
        listview = (ListView) view.findViewById(R.id.list_view);
        searchIV = (ImageView) view.findViewById(R.id.search_img);
        notFoundTV = (TextView) view.findViewById(R.id.search_no_result);
        notFoundTV.setVisibility(View.GONE);

        if (adapter == null) {
            adapter = new SearchActivityListViewAdapter(getActivity());
            adapter.bind(new ArrayList<>());
            listview.setAdapter(adapter);
        } else {
            searchIV.setVisibility(View.INVISIBLE);
            listview.setAdapter(adapter);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ActivityFragment fragment = new ActivityFragment();
                    fragment.setActivity(adapter.getItem(i));
                    ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void updateListView(String search, android.app.Activity mainActivity) {
        if (progressBar != null && searchIV != null) {
            progressBar.setVisibility(View.VISIBLE);
            searchIV.setVisibility(View.INVISIBLE);
        }

        if (notFoundTV != null) {
            notFoundTV.setVisibility(View.GONE);
        }

        token = mainActivity.getIntent().getStringExtra("token");
        Call<List<Activity>> call = RetrofitServiceGenerator.createService(ActivityApiService.class).searchByText(token, search);

        call.enqueue(new Callback<List<Activity>>() {
            @Override
            public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() == 0 && notFoundTV != null) {
                        notFoundTV.setVisibility(View.VISIBLE);
                    }

                    if (adapter == null) {
                        adapter = new SearchActivityListViewAdapter(mainActivity);
                    }

                    adapter.bind(response.body());
                    adapter.notifyDataSetChanged();

                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }

                    if (listview != null) {
                        listview.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(mainActivity, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Activity>> call, Throwable t) {
                Toast.makeText(mainActivity, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
