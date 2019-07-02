package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.away_expat.away.R;
import com.away_expat.away.adapters.DisplayTagGridViewAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayTagFragment extends Fragment {

    private GridView gridView;
    private DisplayTagGridViewAdapter tagsAdapter;
    private List<Tag> userTag = new ArrayList<>();
    private String token;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_user_tag, container, false);
        token = getActivity().getIntent().getStringExtra("token");

        gridView = (GridView) view.findViewById(R.id.grid_view);
        setupTagGridView();

        return view;
    }

    private void setupTagGridView() {
        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getUserTags(token, user.getId());

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    userTag = response.body();
                    tagsAdapter = new DisplayTagGridViewAdapter(getContext());
                    tagsAdapter.bind(userTag);

                    gridView.setAdapter(tagsAdapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
    }
}
