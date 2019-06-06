package com.away_expat.away;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.away_expat.away.classes.User;
import com.away_expat.away.fragments.AccountFragment;
import com.away_expat.away.fragments.CountryFragment;
import com.away_expat.away.fragments.CountryInformationFragment;
import com.away_expat.away.fragments.CreationFragment;
import com.away_expat.away.fragments.HomeFragment;
import com.away_expat.away.fragments.SearchFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {

    private User connectedUser;
    private BottomNavigationViewEx bottom_bar;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private CreationFragment creationFragment;
    private AccountFragment accountFragment;
    private CountryInformationFragment countryInfoFragment;
    private CountryFragment countryFragment;

    private ImageView countryImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View customBar = getSupportActionBar().getCustomView();

        countryImageView = (ImageView) customBar.findViewById(R.id.country);
        countryImageView.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, countryFragment).commit());

        bottom_bar = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);

        connectedUser = (User) getIntent().getSerializableExtra("connected_user");

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

        //country Info
        countryInfoFragment = new CountryInformationFragment();
        countryInfoFragment.setArguments(getIntent().getExtras());

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
            case R.id.searchFragmentMenu:
                searchFragment.setUser(connectedUser);
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, searchFragment).commit();
                break;
            case R.id.createFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, creationFragment).commit();
                break;
            case R.id.accountFragmentMenu:
                accountFragment.setUser(connectedUser, true);
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, accountFragment).commit();
                break;
            case R.id.countryInfoFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, countryInfoFragment).commit();
                break;
        }
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).commit();
    }
}
