package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.HomeListViewAdapter;
import com.away_expat.away.classes.Account;
import com.away_expat.away.classes.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends ListFragment {

    private HomeListViewAdapter adapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Event> items = new ArrayList<>();
        items.add(new Event("Super Cool", getContext().getString(R.string.little_lorem), new Date(), new Account("fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France")));
        items.add(new Event("c'est Cool", getContext().getString(R.string.little_lorem), new Date(), new Account("testtest@test.com", "******", "test", "test", "01/01/1111", "USA")));
        items.add(new Event("ahwa Cool", getContext().getString(R.string.little_lorem), new Date(), new Account("helloworld@yahou.com", "*****", "Hello", "World", "00/00/0000", "Espana")));

        adapter = new HomeListViewAdapter(getActivity());
        adapter.bind(items);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        Toast.makeText(getActivity(), "Item " + pos + " was clicked", Toast.LENGTH_SHORT).show();

        EventFragment fragment = new EventFragment();
        fragment.setEvent(adapter.getItem(pos));

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

}
