package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.away_expat.away.dto.ActivityListDto;
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
    private boolean flagLoading = false;
    private ActivityListDto result;

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

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
                    if(!flagLoading) {
                        flagLoading = true;
                        addItems();
                    }
                }
            }
        });

        return view;
    }

    private void addItems() {
        if (result.getToken() != null) {
            token = getActivity().getIntent().getStringExtra("token");
            Call<ActivityListDto> call = RetrofitServiceGenerator.createService(ActivityApiService.class).loadNextResults(token, result.getToken());

            call.enqueue(new Callback<ActivityListDto>() {
                @Override
                public void onResponse(Call<ActivityListDto> call, Response<ActivityListDto> response) {
                    if (response.isSuccessful()) {
                        result = response.body();
                        if (result.getResults().size() == 0 && notFoundTV != null) {
                            notFoundTV.setVisibility(View.VISIBLE);
                        }

                        if (adapter == null) {
                            adapter = new SearchActivityListViewAdapter(getActivity());
                        }

                        adapter.addItems(result.getResults());
                        adapter.notifyDataSetChanged();
                        flagLoading = false;

                        if (listview != null) {
                            listview.setAdapter(adapter);
                        }
                    } else {
                        flagLoading = false;
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ActivityListDto> call, Throwable t) {
                    flagLoading = false;
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                }
            });
        }
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
        Call<ActivityListDto> call = RetrofitServiceGenerator.createService(ActivityApiService.class).searchByText(token, search);

        call.enqueue(new Callback<ActivityListDto>() {
            @Override
            public void onResponse(Call<ActivityListDto> call, Response<ActivityListDto> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    if (result.getResults().size() == 0 && notFoundTV != null) {
                        notFoundTV.setVisibility(View.VISIBLE);
                    }

                    if (adapter == null) {
                        adapter = new SearchActivityListViewAdapter(mainActivity);
                    }

                    adapter.bind(response.body().getResults());
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
            public void onFailure(Call<ActivityListDto> call, Throwable t) {
                Toast.makeText(mainActivity, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
