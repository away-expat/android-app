package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.ActivityListViewAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.ActivityByTagListDto;
import com.away_expat.away.services.ActivityApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedTagSearchFragment extends ListFragment {

    private TextView detailedSearchTV;
    private ProgressBar progressBar;
    private Tag tag;
    private ActivityListViewAdapter adapter;
    private User connectedUser;
    private String token, toDisplay;

    public DetailedTagSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_search, container, false);

        token = getActivity().getIntent().getStringExtra("token");
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");
        toDisplay = getActivity().getResources().getString(R.string.tag_detailed_search_text);

        detailedSearchTV = (TextView) view.findViewById(R.id.detailed_tag_seach);
        progressBar = (ProgressBar) view.findViewById(R.id.search_detailed_progress);

        toDisplay += " "+tag.getName();
        detailedSearchTV.setText(toDisplay);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Call<ActivityByTagListDto> call = RetrofitServiceGenerator.createService(ActivityApiService.class).getActivitiesByTag(token,connectedUser.getCity().getName()+" "+connectedUser.getCity().getCountry(), tag.getName());

        call.enqueue(new Callback<ActivityByTagListDto>() {
            @Override
            public void onResponse(Call<ActivityByTagListDto> call, Response<ActivityByTagListDto> response) {
                if (response.isSuccessful()) {
                    adapter = new ActivityListViewAdapter(getActivity());
                    adapter.bind(response.body().getResults());
                    progressBar.setVisibility(View.GONE);
                    setListAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActivityByTagListDto> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        ActivityFragment fragment = new ActivityFragment();
        fragment.setActivity(adapter.getItem(pos));

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

    public void setData(Tag tag) {
        this.tag = tag;
    }
}
