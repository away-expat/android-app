package com.away_expat.away.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.away_expat.away.CreateAccountActivity;
import com.away_expat.away.HomeActivity;
import com.away_expat.away.LoginActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.AccountListViewAdapter;
import com.away_expat.away.adapters.HomeListViewAdapter;
import com.away_expat.away.classes.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends ListFragment {

    private TextView nameTextview;
    private Button actionBtn;

    private Account user;
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

        actionBtn.setOnClickListener(v -> {
            if (isUserAccount) {
                actionBtn.setText(R.string.update);
                updateAccount();
            } else {
                actionBtn.setText("Follow");
                followUser();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final List<Integer> items = new ArrayList<>();
        items.add(R.string.little_lorem);
        items.add(R.string.little_lorem);
        items.add(R.string.little_lorem);
        items.add(R.string.little_lorem);
        final AccountListViewAdapter adapter = new AccountListViewAdapter(getActivity());
        adapter.bind(items);

        setListAdapter(adapter);
    }

    private void updateAccount() {
        //TODO
    }

    private void followUser() {

    }

    public void setUser(Account user, boolean isUserAcc) {
        this.user = user;
        this.isUserAccount = isUserAcc;
    }
}
