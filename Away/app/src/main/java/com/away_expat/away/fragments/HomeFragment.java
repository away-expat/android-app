package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.away_expat.away.R;
import com.away_expat.away.adapters.HomeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends ListFragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Integer> items = new ArrayList<>();
        items.add(R.string.default_lorem);
        items.add(R.string.default_lorem);
        items.add(R.string.default_lorem);
        final HomeListViewAdapter adapter = new HomeListViewAdapter(getActivity());
        adapter.bind(items);

        setListAdapter(adapter);
    }

}
