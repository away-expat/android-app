package com.away_expat.away.fragments;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.UserListViewAdapter;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;

public class EventFragment extends ListFragment {

    private TextView eventNameTextview, usernameTextview, eventDescriptionTextView, activityNameTextview, eventDateTextview;
    private UserListViewAdapter adapter;
    private Event event;
    private User connectedUser;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connected_user");

        eventNameTextview = (TextView) view.findViewById(R.id.event_name);
        eventNameTextview.setText(event.getName());

        eventDescriptionTextView = (TextView) view.findViewById(R.id.event_info);
        eventDescriptionTextView.setText(event.getDescription());

        activityNameTextview = (TextView) view.findViewById(R.id.event_activity);
        activityNameTextview.setText(event.getActivity().getName());
        activityNameTextview.setPaintFlags(activityNameTextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        activityNameTextview.setTextColor(ContextCompat.getColor(getContext(), R.color.colorMenu));
        activityNameTextview.setOnClickListener(v -> openActivityFragment());

        eventDateTextview = (TextView) view.findViewById(R.id.event_date);
        eventDateTextview.setText(event.getDate().toString());

        usernameTextview = (TextView) view.findViewById(R.id.event_username);
        usernameTextview.setText(getContext().getString(R.string.by)+" "+event.getCreator().getFirstname());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new UserListViewAdapter(getActivity());
        adapter.bind(event.getParticipant());

        setListAdapter(adapter);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        User userClicked = adapter.getItem(pos);
        AccountFragment fragment = new AccountFragment();
        fragment.setUser(userClicked, userClicked.getId().equals(connectedUser.getId()));

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

    private void openActivityFragment() {
        ActivityFragment fragment = new ActivityFragment();
        fragment.setActivity(event.getActivity());

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }
}
