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
import android.widget.GridView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.SearchGridViewAdapter;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchGridViewAdapter adapter;
    private TextView searchTV;
    private EditText searchET;
    private GridView gridView;
    private User connectedUser;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        gridView = (GridView) view.findViewById(R.id.grid_view);
        searchTV = (TextView) view.findViewById(R.id.search_text);
        searchET = (EditText) view.findViewById(R.id.search_input);

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

        //TO REMOVE
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("","Resto"));
        tags.add(new Tag("", "Bar"));
        tags.add(new Tag("", "Musée"));
        tags.add(new Tag("", "Parc"));
        tags.add(new Tag("", "Magasin"));
        tags.add(new Tag("", "Bistro"));

        adapter = new SearchGridViewAdapter(getActivity());
        adapter.bind(tags);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                DetailedSearchFragment fragment = new DetailedSearchFragment();
                fragment.setData(adapter.getItem(position), connectedUser);

                ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

        return view;
    }

    public void setUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

}
