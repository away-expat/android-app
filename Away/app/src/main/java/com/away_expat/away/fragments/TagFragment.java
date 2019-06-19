package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.away_expat.away.R;
import com.away_expat.away.adapters.TagActivityAdapter;
import com.away_expat.away.adapters.TagSelectionGridViewAdapter;
import com.away_expat.away.adapters.TagUserAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagFragment extends Fragment implements TagUserAdapter.OnAddedTagClickListener {

    private TagSelectionGridViewAdapter tagsAdapter;
    private TagUserAdapter userTagAdapter;
    private EditText searchET;
    private Button selectBtn;
    private GridView gridView;
    private RecyclerView recyclerView;
    private String token;

    private List<Tag> addedTag = new ArrayList<>();
    private List<Tag> allTag = new ArrayList<>();

    public TagFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_tag, container, false);
        token = getActivity().getIntent().getStringExtra("token");

        gridView = (GridView) view.findViewById(R.id.grid_view);
        searchET = (EditText) view.findViewById(R.id.search_tag_input);
        selectBtn = (Button) view.findViewById(R.id.tag_selection_btn);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        setupSearch();
        setupTagGridView();
        setupUserTag();

        selectBtn.setOnClickListener(v -> {
            //TODO
        });

        return view;
    }

    public void setupUserTag() {
        if (token != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            TagFragment $this = this;

            Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getUserTags(token);

            call.enqueue(new Callback<List<Tag>>() {
                @Override
                public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                    addedTag = response.body();

                    userTagAdapter = new TagUserAdapter(addedTag, getContext(), $this);
                    recyclerView.setAdapter(userTagAdapter);
                }

                @Override
                public void onFailure(Call<List<Tag>> call, Throwable t) {
                    Log.i("error", t.getMessage());
                }
            });
        }
    }

    private void setupSearch() {
        searchET.addTextChangedListener(new TextWatcher() {
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
    }


    private void setupTagGridView() {
        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getAllTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                allTag = response.body();

                tagsAdapter = new TagSelectionGridViewAdapter(getContext());
                tagsAdapter.bind(allTag);

                gridView.setAdapter(tagsAdapter);
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

        gridView.setOnItemClickListener((a, v, position, id) -> {
            Log.i("AWAYINFO", "----------------------------------------−−> "+addedTag.size()+" // "+tagsAdapter.getItem(position).getName());
            addedTag.add(tagsAdapter.getItem(position));
            userTagAdapter.notifyDataSetChanged();

            allTag.remove(position);
            tagsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(int position) {
        Log.i("AWAYINFO", "----------------------------------------> "+addedTag.get(position).getName());
        allTag.add(addedTag.get(position));
        tagsAdapter.notifyDataSetChanged();

        addedTag.remove(position);
        userTagAdapter.notifyDataSetChanged();
    }
}
