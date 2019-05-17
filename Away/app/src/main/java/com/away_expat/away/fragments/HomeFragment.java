package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.HomeListViewAdapter;
import com.away_expat.away.classes.User;
import com.away_expat.away.classes.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends ListFragment {

    private HomeListViewAdapter adapter;
    private User connectedUser;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connected_user");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<User> participants = new ArrayList<>();
        //participants.add(new User("fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France"));
        participants.add(new User("001","testtest@test.com", "******", "test", "test", "01/01/1111", "USA"));
        participants.add(new User("002","helloworld@yahou.com", "*****", "Hello", "World", "00/00/0000", "Espana"));

        final List<Event> items = new ArrayList<>();
        items.add(new Event("Super Cool", "Ptite aprem chill au vre-lou. On va faire le tour du baille, mater la Joconde et manger un pti domac des mifas. Si tu kiff la vibes rejoint nous rouilla.", new Date(), new User("000","fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France"), participants));
        items.add(new Event("C'est Cool", getContext().getString(R.string.little_lorem), new Date(), new User("00", "testtest@test.com", "******", "test", "test", "01/01/1111", "USA"), participants));

        List<User> p = new ArrayList<>();
        p.add(connectedUser);
        p.add(new User("001","testtest@test.com", "******", "test", "test", "01/01/1111", "USA"));
        items.add(new Event("Ahwa Cool", getContext().getString(R.string.little_lorem), new Date(), new User("01","helloworld@yahou.com", "*****", "Hello", "World", "00/00/0000", "Espana"), p));

        adapter = new HomeListViewAdapter(getActivity());
        adapter.bind(connectedUser, items);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        EventFragment fragment = new EventFragment();
        fragment.setEvent(adapter.getItem(pos));

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

}
