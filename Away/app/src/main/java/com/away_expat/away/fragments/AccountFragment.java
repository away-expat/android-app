package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.EventListViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountFragment extends ListFragment {

    private TextView nameTextview;
    private Button actionBtn, account_btn_tag;
    private EventListViewAdapter adapter;

    private User user;
    private boolean isUserAccount;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        actionBtn = (Button) view.findViewById(R.id.account_btn_action);
        nameTextview = (TextView) view.findViewById(R.id.account_textview_username);

        nameTextview.setText(user.getFirstname()+" "+user.getLastname());

        if (isUserAccount) {
            actionBtn.setText(R.string.update);
            actionBtn.setOnClickListener(v -> {
                updateAccount();
            });
        } else {
            actionBtn.setText("Follow");
            actionBtn.setOnClickListener(v -> {
                followUser();
            });
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity louvre = new Activity("Louvre", "Musée du Louvre. 75058 Paris");

        List<User> participants = new ArrayList<>();
        //participants.create(new User("fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France"));
        participants.add(new User(1,"testtest@input.com", "******", "input", "input", "01/01/1111", "USA"));
        participants.add(new User(2,"helloworld@yahou.com", "*****", "Hello", "World", "00/00/0000", "Espana"));

        final List<Event> items = new ArrayList<>();
        items.add(new Event("Super Cool", "Ptite aprem chill au vre-lou. On va faire le tour du baille, mater la Joconde et manger un pti domac des mifas. Si tu kiff la vibes rejoint nous rouilla.", new Date(), new User(0,"fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France"), louvre, participants));
        items.add(new Event("C'est Cool", getContext().getString(R.string.little_lorem), new Date(), new User(0, "testtest@input.com", "******", "input", "input", "01/01/1111", "USA"), louvre, participants));

        adapter = new EventListViewAdapter(getActivity());
        adapter.bind(items);

        setListAdapter(adapter);
    }

    private void updateAccount() {
        UpdateFragment fragment = new UpdateFragment();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
        fragment.setUser(user);
    }

    private void followUser() {
        //TODO
    }

    public void setUser(User user, boolean isUserAcc) {
        this.user = user;
        this.isUserAccount = isUserAcc;
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        EventFragment fragment = new EventFragment();
        fragment.setEvent(adapter.getItem(pos));

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }
}
