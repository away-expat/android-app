package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.adapters.UserListViewAdapter;
import com.away_expat.away.classes.Account;
import com.away_expat.away.classes.Event;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends ListFragment {

    private TextView eventNameTextview, usernameTextview, eventDescriptionTextView;
    private Event event;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        eventNameTextview = (TextView) view.findViewById(R.id.event_name);
        eventNameTextview.setText(event.getActivityName());
        eventDescriptionTextView = (TextView) view.findViewById(R.id.event_info);
        eventDescriptionTextView.setText(event.getActivityDescription());

        usernameTextview = (TextView) view.findViewById(R.id.event_username);
        usernameTextview.setText(getContext().getString(R.string.by)+" "+event.getCreator().getFirstname());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Account> items = new ArrayList<>();
        items.add(new Account("fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France"));
        items.add(new Account("testtest@test.com", "******", "test", "test", "01/01/1111", "USA"));
        items.add(new Account("helloworld@yahou.com", "*****", "Hello", "World", "00/00/0000", "Espana"));
        final UserListViewAdapter adapter = new UserListViewAdapter(getActivity());
        adapter.bind(items);

        setListAdapter(adapter);
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
