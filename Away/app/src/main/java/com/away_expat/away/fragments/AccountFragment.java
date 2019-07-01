package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.EventListViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.tools.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountFragment extends ListFragment {

    private TextView nameTV, countryTV, birthdayTV;
    private ImageView userIV;
    private Button actionBtn, tagBtn;
    private EventListViewAdapter adapter;

    private User user;
    private boolean isUserAccount;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        userIV = (ImageView) view.findViewById(R.id.account_user_image);
        actionBtn = (Button) view.findViewById(R.id.account_btn_action);
        tagBtn = (Button) view.findViewById(R.id.account_btn_tag);
        nameTV = (TextView) view.findViewById(R.id.account_textview_username);
        countryTV = (TextView) view.findViewById(R.id.account_textview_country);
        birthdayTV = (TextView) view.findViewById(R.id.account_textview_birthdate);

        Picasso.get().load(user.getAvatar()).transform(new CircleTransform()).into(userIV);
        String toDisplay = user.getFirstname()+" "+user.getLastname();
        nameTV.setText(toDisplay);
        countryTV.setText(user.getCountry());
        birthdayTV.setText(user.getBirthday());

        if (isUserAccount) {
            actionBtn.setVisibility(View.VISIBLE);
            actionBtn.setOnClickListener(v -> updateAccount());
            
            tagBtn.setOnClickListener(v -> updateTag());
        } else {
            actionBtn.setVisibility(View.GONE);
            tagBtn.setOnClickListener(v -> showTag());
        }

        return view;
    }

    private void showTag() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final List<Event> items = new ArrayList<>();

        adapter = new EventListViewAdapter(getActivity());
        adapter.bind(items);

        setListAdapter(adapter);
    }

    private void updateAccount() {
        UpdateFragment fragment = new UpdateFragment();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
        fragment.setUser(user);
    }

    private void updateTag() {
        TagFragment fragment = new TagFragment();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

    public void setUser(User user, boolean isUserAcc) {
        this.user = user;
        this.isUserAccount = isUserAcc;
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        EventFragment fragment = new EventFragment();
        fragment.setEvent(new DetailedEventDto());

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }
}
