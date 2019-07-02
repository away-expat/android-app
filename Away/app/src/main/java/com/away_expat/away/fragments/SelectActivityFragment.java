package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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

public class SelectActivityFragment extends Fragment {

    private EditText searchET;
    private SearchActivityListViewAdapter adapter;
    private ListView listview;
    private TextView searchTV;
    private String token, currentSearch;
    private Fragment previousFrag;
    private HomeActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_activity, container, false);

        if (getView() != null) {
            return getView();
        }

        token = getActivity().getIntent().getStringExtra("token");

        searchET = (EditText) view.findViewById(R.id.search_ET);
        listview = (ListView) view.findViewById(R.id.list_view);
        searchTV = (TextView) view.findViewById(R.id.search_text);

        if (adapter == null) {
            adapter = new SearchActivityListViewAdapter(getActivity());
            adapter.bind(new ArrayList<>());
            listview.setAdapter(adapter);
        } else {
            searchTV.setVisibility(View.INVISIBLE);
            listview.setAdapter(adapter);
        }

        searchET.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence c, int start, int before, int count) {
                Log.i("INFO", c.toString());
                if (c.toString().length() >= 3) {
                    currentSearch = c.toString();
                    token = getActivity().getIntent().getStringExtra("token");
                    Call<ActivityListDto> call = RetrofitServiceGenerator.createService(ActivityApiService.class).searchByText(token, currentSearch);

                    call.enqueue(new Callback<ActivityListDto>() {
                        @Override
                        public void onResponse(Call<ActivityListDto> call, Response<ActivityListDto> response) {
                            if (response.isSuccessful()) {
                                if (searchTV != null) {
                                    searchTV.setVisibility(View.INVISIBLE);
                                }

                                adapter.bind(response.body().getResults());
                                adapter.notifyDataSetChanged();

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

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {}

            public void afterTextChanged(Editable c) {}
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((CreationFragment) previousFrag).updateActivity(adapter.getItem(i));
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public void setPreviousFrag(Fragment frag, HomeActivity mainActivity) {
        this.previousFrag = frag;
        this.mainActivity = mainActivity;
    }
}
