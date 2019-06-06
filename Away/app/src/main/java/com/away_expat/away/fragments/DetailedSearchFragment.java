package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.ActivityListViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;

import java.util.ArrayList;
import java.util.List;

public class DetailedSearchFragment extends ListFragment {

    private TextView tagTV, addTileTV;
    private ConstraintLayout tagBadge, addTile;
    private ImageView addTileIV;
    private EditText searchET;
    private User connectedUser;
    private Tag tag;
    private ActivityListViewAdapter adapter;

    public DetailedSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_search, container, false);

        tagTV = (TextView) view.findViewById(R.id.tag_text);
        addTileTV = (TextView) view.findViewById(R.id.add_tag_text);
        addTileIV = (ImageView) view.findViewById(R.id.add_tag_img);
        searchET = (EditText) view.findViewById(R.id.search_detailed_input);

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

        tagBadge = (ConstraintLayout) view.findViewById(R.id.tag_badge);
        tagBadge.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setUser(connectedUser);
            ((HomeActivity) getActivity()).replaceFragment(searchFragment);
        });

        addTile = (ConstraintLayout) view.findViewById(R.id.add_tag);
        addTile.setOnClickListener(v -> {
            if (connectedUser.getTags().contains(tag)) {
                //TODO remove the tag to user
                connectedUser.removeTag(tag);
                Log.i("INFO", "-------------> REMOVE");

                addTileTV.setText(getResources().getString(R.string.add_tag));
                addTileIV.setImageResource(R.drawable.add_black);
            } else {
                //TODO add the tag to user
                connectedUser.addTag(tag);
                Log.i("INFO", "-------------> ADD");

                addTileTV.setText(getResources().getString(R.string.remove_tag));
                addTileIV.setImageResource(R.drawable.cross);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tagTV.setText(tag.getName());

        if (connectedUser.getTags().contains(tag)) {
            addTileTV.setText(getResources().getString(R.string.remove_tag));
            addTileIV.setImageResource(R.drawable.cross);
        } else {
            addTileTV.setText(getResources().getString(R.string.add_tag));
            addTileIV.setImageResource(R.drawable.add_black);
        }

        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("Musée du Louvre", "Rue de Rivoli, 75001 Paris"));
        activities.add(new Activity("Musée du quai Branly", "37 Quai Branly, 75007 Paris"));
        activities.add(new Activity("Musee d'Orsay", "58 Quai Anatole France, 75007 Paris"));

        adapter = new ActivityListViewAdapter(getActivity());
        adapter.bind(activities);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        ActivityFragment fragment = new ActivityFragment();
        fragment.setActivity(adapter.getItem(pos));

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

    public void setData(Tag tag, User connectedUser) {
        this.tag = tag;
        this.connectedUser = connectedUser;
    }
}
