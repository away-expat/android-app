package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.ActivityListViewAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.ActivityListDto;
import com.away_expat.away.services.ActivityApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedTagSearchFragment extends Fragment {

    private TextView detailedSearchTV;
    private ProgressBar progressBar;
    private ListView listview;
    private Tag tag;
    private ActivityListViewAdapter adapter;
    private User connectedUser;
    private String token, toDisplay;
    private boolean flagLoading = false;
    private ActivityListDto result;

    public DetailedTagSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_search, container, false);

        listview = (ListView) view.findViewById(R.id.list_view);

        token = getActivity().getIntent().getStringExtra("token");
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");
        toDisplay = getActivity().getResources().getString(R.string.tag_detailed_search_text);

        detailedSearchTV = (TextView) view.findViewById(R.id.detailed_tag_seach);
        progressBar = (ProgressBar) view.findViewById(R.id.search_detailed_progress);

        toDisplay += " "+tag.getName();
        detailedSearchTV.setText(toDisplay);

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Call<ActivityListDto> call = RetrofitServiceGenerator.createService(ActivityApiService.class).getActivitiesByTag(token,connectedUser.getCity().getName()+" "+connectedUser.getCity().getCountry(), tag.getName());

        call.enqueue(new Callback<ActivityListDto>() {
            @Override
            public void onResponse(Call<ActivityListDto> call, Response<ActivityListDto> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    adapter = new ActivityListViewAdapter(getActivity());
                    adapter.bind(result.getResults());
                    progressBar.setVisibility(View.GONE);
                    listview.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActivityListDto> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setData(Tag tag) {
        this.tag = tag;
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
}
