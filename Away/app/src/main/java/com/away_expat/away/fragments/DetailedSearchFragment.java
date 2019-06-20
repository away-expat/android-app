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

public class DetailedSearchFragment extends ListFragment {

    private TextView tagTV, addTileTV;
    private ConstraintLayout tagBadge, addTile;
    private ImageView addTileIV;
    private EditText searchET;
    private ProgressBar progressBar;
    private Tag tag;
    private ActivityListViewAdapter adapter;

    private User connectedUser;
    private String token;

    private RetrofitServiceGenerator retrofitServiceGenerator;

    public DetailedSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_search, container, false);

        token = getActivity().getIntent().getStringExtra("token");
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");

        tagTV = (TextView) view.findViewById(R.id.tag_text);
        addTileTV = (TextView) view.findViewById(R.id.add_tag_text);
        addTileIV = (ImageView) view.findViewById(R.id.add_tag_img);
        searchET = (EditText) view.findViewById(R.id.search_detailed_input);
        progressBar = (ProgressBar) view.findViewById(R.id.search_detailed_progress);

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

        tagBadge = (ConstraintLayout) view.findViewById(R.id.tag_badge);
        tagBadge.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setUser();
            ((HomeActivity) getActivity()).replaceFragment(searchFragment);
        });

        addTile = (ConstraintLayout) view.findViewById(R.id.add_tag);
        addTile.setOnClickListener(v -> {
            /*if (connectedUser.getTags().contains(tag)) {
                //TODO remove the tag to user
                connectedUser.removeTag(tag);
                Log.i("INFO", "-------------> REMOVE");

                addTileTV.setText(getResources().getString(R.string.add_tag));
                addTileIV.setImageResource(R.drawable.add_black);
            } else {
                //TODO add the tag to user
                connectedUser.addTag(tag);
                Log.i("INFO", "-------------> ADD");

                addTileTV.setText(getResources().getString(R.string.remove_tag));
                addTileIV.setImageResource(R.drawable.cross);
            }*/
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tagTV.setText(tag.getName());

        /*
        if (connectedUser.getTags().contains(tag)) {
            addTileTV.setText(getResources().getString(R.string.remove_tag));
            addTileIV.setImageResource(R.drawable.cross);
        } else {
            addTileTV.setText(getResources().getString(R.string.add_tag));
            addTileIV.setImageResource(R.drawable.add_black);
        }*/

        Call<ActivityByTagListDto> call = retrofitServiceGenerator.createService(ActivityApiService.class).getActivitiesByTag(token,connectedUser.getCity().getName()+" "+connectedUser.getCity().getCountry(), tag.getName());

        call.enqueue(new Callback<ActivityByTagListDto>() {
            @Override
            public void onResponse(Call<ActivityByTagListDto> call, Response<ActivityByTagListDto> response) {
                if (response.isSuccessful()) {
                    adapter = new ActivityListViewAdapter(getActivity());
                    adapter.bind(response.body().getResults());
                    progressBar.setVisibility(View.GONE);
                    setListAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActivityByTagListDto> call, Throwable t) {
                Log.i("AWAYINFO", "-----------------> "+t.getMessage());
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

    public void setData(Tag tag, User connectedUser) {
        this.tag = tag;
        this.connectedUser = connectedUser;
    }
}
