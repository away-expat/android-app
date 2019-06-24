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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.FirstLoginActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.AutoCompleteTagArrayAdapter;
import com.away_expat.away.adapters.TagSelectionGridViewAdapter;
import com.away_expat.away.adapters.TagSuggestionAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagFragment extends Fragment implements TagSuggestionAdapter.OnAddedTagClickListener {

    private TagSelectionGridViewAdapter tagsAdapter;
    private TagSuggestionAdapter tagSuggestionAdapter;
    private AutoCompleteTagArrayAdapter autoCompleteAdapter;

    private AutoCompleteTextView searchATV;
    private GridView gridView;
    private RecyclerView recyclerView;
    private TextView welcomeTV;
    private String token;

    private List<Tag> suggestionTag = new ArrayList<>();
    private List<Tag> userTag = new ArrayList<>();
    private List<Tag> searchedTag = new ArrayList<>();

    public TagFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_tag, container, false);
        token = getActivity().getIntent().getStringExtra("token");

        Log.i("AWAYINFO", "-----------------> "+token);

        gridView = (GridView) view.findViewById(R.id.grid_view);
        searchATV = (AutoCompleteTextView) view.findViewById(R.id.search_tag_input);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        welcomeTV = (TextView) view.findViewById(R.id.welcomeTV);
        welcomeTV.setVisibility(View.INVISIBLE);

        setupTagGridView();

        return view;
    }

    public void setupTagSuggestion() {
        if (token != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            TagFragment $this = this;

            recyclerView.setAdapter(tagSuggestionAdapter);

            //TODO
        }
    }

    private void setupSearch() {
        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getAllTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    autoCompleteAdapter = new AutoCompleteTagArrayAdapter(getContext(), R.layout.view_auto_complete, response.body());

                    searchATV.setAdapter(autoCompleteAdapter);
                    searchATV.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                        Tag selectedTag = (Tag) adapterView.getItemAtPosition(i);
                        addToUserTags(selectedTag);
                    });
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTagGridView() {
        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getUserTags(token);

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    userTag = response.body();

                    if (userTag.size() == 0) {
                        welcomeTV.setVisibility(View.VISIBLE);
                    }

                    tagsAdapter = new TagSelectionGridViewAdapter(getContext());
                    tagsAdapter.bind(userTag);

                    gridView.setAdapter(tagsAdapter);
                    gridView.setOnItemClickListener((a, v, position, id) -> {
                        removeFromUserTags(userTag.get(position));
                    });

                    setupSearch();
                    setupTagSuggestion();
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

    private void addToUserTags(Tag tag) {
        if (!userTag.contains(tag)) {
            welcomeTV.setVisibility(View.INVISIBLE);

            Call<Tag> call = RetrofitServiceGenerator.createService(TagApiService.class).addToUserTags(token, tag.getId());

            call.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Tag : " + tag.getName() + " " + getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                        userTag.add(tag);
                        tagsAdapter.notifyDataSetChanged();


                        if (getActivity().getIntent().getBooleanExtra("isFirstLogin", false) && tagsAdapter.getCount() >= 3) {
                            ((FirstLoginActivity) getActivity()).setIsTagSelected();
                        }
                    }else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Tag : " + tag.getName() + " " + getResources().getString(R.string.already_added), Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromUserTags(Tag tag) {
        if (userTag.size() > 1) {
            Call<Void> call = RetrofitServiceGenerator.createService(TagApiService.class).removeFromUserTags(token, tag.getId());

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        userTag.remove(tag);
                        tagsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.tag_warning), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(int position) {
        Log.i("AWAYINFO", "----------------------------------------> "+ suggestionTag.get(position).getName());
        userTag.add(suggestionTag.get(position));
        tagsAdapter.notifyDataSetChanged();

        suggestionTag.remove(position);
        tagSuggestionAdapter.notifyDataSetChanged();
    }
}
