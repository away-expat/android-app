package com.away_expat.away.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.away_expat.away.CreateAccountActivity;
import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.HomeListViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.User;
import com.away_expat.away.classes.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeListViewAdapter adapter;
    private User connectedUser;
    private String token;
    private ListView listview;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        token = getActivity().getIntent().getStringExtra("token");
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");

        listview = (ListView) view.findViewById(R.id.list_view);
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
        items.add(new Event("C'est plutot Cool", getContext().getString(R.string.little_lorem), new Date(), new User(0, "testtest@input.com", "******", "input", "input", "01/01/1111", "USA"), louvre, participants));

        List<User> p = new ArrayList<>();
        p.add(connectedUser);
        p.add(new User(1,"testtest@input.com", "******", "input", "input", "01/01/1111", "USA"));
        items.add(new Event("Ahwa Cool", getContext().getString(R.string.little_lorem), new Date(), new User(1,"helloworld@yahou.com", "*****", "Hello", "World", "00/00/0000", "Espana"), louvre, p));

        adapter = new HomeListViewAdapter(getActivity());
        adapter.bind(connectedUser, items);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EventFragment fragment = new EventFragment();
                fragment.setEvent((Event) adapterView.getItemAtPosition(position));

                ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

    }


}
