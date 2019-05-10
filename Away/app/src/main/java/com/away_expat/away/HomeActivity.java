package com.away_expat.away;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.away_expat.away.classes.Account;
import com.away_expat.away.fragment.AccountFragment;
import com.away_expat.away.fragment.CountryFragment;
import com.away_expat.away.fragment.CreationFragment;
import com.away_expat.away.fragment.HomeFragment;
import com.away_expat.away.fragment.SearchFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {

    private Account connectedUser;
    private BottomNavigationViewEx bottom_bar;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private CreationFragment creationFragment;
    private AccountFragment accountFragment;
    private CountryFragment countryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottom_bar = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);

        connectedUser = (Account) getIntent().getSerializableExtra("connected_user");

        //home
        homeFragment = new HomeFragment();
        homeFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container, homeFragment).commit();

        //search
        searchFragment = new SearchFragment();
        searchFragment.setArguments(getIntent().getExtras());

        //create
        creationFragment = new CreationFragment();
        creationFragment.setArguments(getIntent().getExtras());

        //account
        accountFragment = new AccountFragment();
        accountFragment.setArguments(getIntent().getExtras());

        //country
        countryFragment = new CountryFragment();
        countryFragment.setArguments(getIntent().getExtras());

        initBottomBar();
    }

    private void initBottomBar() {
        bottom_bar.enableItemShiftingMode(false);
        bottom_bar.enableAnimation(false);

        bottom_bar.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.homeFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homeFragment).commit();
                break;
            case R.id.eventsFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, searchFragment).commit();
                break;
            case R.id.createFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, creationFragment).commit();
                break;
            case R.id.accountFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, accountFragment).commit();
                break;
            case R.id.countryFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, countryFragment).commit();
                break;
        }
        return true;
    }
}
