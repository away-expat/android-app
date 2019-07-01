package com.away_expat.away;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.away_expat.away.classes.User;
import com.away_expat.away.fragments.AccountFragment;
import com.away_expat.away.fragments.CityFragment;
import com.away_expat.away.fragments.CountryInformationFragment;
import com.away_expat.away.fragments.CreationFragment;
import com.away_expat.away.fragments.HomeFragment;
import com.away_expat.away.fragments.SearchFragment;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private String token;
    private BottomNavigationViewEx bottom_bar;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private CreationFragment creationFragment;
    private AccountFragment accountFragment;
    private CountryInformationFragment countryInfoFragment;
    private CityFragment cityFragment;

    int menuIconBaseColor;
    int menuIconFocusColor;

    private ImageView changeCountryImageView, currentCountryIV;
    private TextView currentCityTV;
    private View customBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        token = getIntent().getStringExtra("token");

        menuIconBaseColor = ContextCompat.getColor(this.getApplicationContext(), R.color.colorPrimaryDark);
        menuIconFocusColor = ContextCompat.getColor(this.getApplicationContext(), R.color.colorFocusMenu);

        loadViews();
        setupUserCity();
    }

    private void initBottomBar() {
        bottom_bar.enableItemShiftingMode(false);

        bottom_bar.setOnNavigationItemSelectedListener(item -> {
            updateMenuColor(bottom_bar.getMenuItemPosition(item));
            return updateMainFragment(item.getItemId());
        });

    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.homeFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homeFragment).commit();
                break;
            case R.id.searchFragmentMenu:
                searchFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, searchFragment).commit();
                break;
            case R.id.createFragmentMenu:
                creationFragment = new CreationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, creationFragment).commit();
                break;
            case R.id.accountFragmentMenu:
                accountFragment.setUser((User) getIntent().getSerializableExtra("connectedUser"),true);
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, accountFragment).commit();
                break;
            case R.id.countryInfoFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, countryInfoFragment).commit();
                break;
        }
        return true;
    }

    private void loadViews() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        customBar = getSupportActionBar().getCustomView();

        currentCityTV = (TextView) customBar.findViewById(R.id.user_location);
        currentCountryIV = (ImageView) customBar.findViewById(R.id.user_location_country);

        changeCountryImageView = (ImageView) customBar.findViewById(R.id.country);
        changeCountryImageView.setOnClickListener(v -> {
            updateMenuColor(-1);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, cityFragment).commit();
        });

        bottom_bar = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);
        updateMenuColor(0);

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
        cityFragment = new CityFragment();
        cityFragment.setArguments(getIntent().getExtras());

        initBottomBar();
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            int currentId = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getId();
            int previousId = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getId();
            if (currentId != previousId) {
                fm.popBackStack();
            }
        } else {
            fm.popBackStack();
        }
    }

    public void updateMenuColor(int position) {
        if (position == -1) {
            changeCountryImageView.setColorFilter(menuIconFocusColor);
        }

        for (int i = 0; i < bottom_bar.getItemCount(); i++) {
            if (position == i) {
                bottom_bar.getIconAt(i).setColorFilter(menuIconFocusColor, PorterDuff.Mode.SRC_IN);
                changeCountryImageView.setColorFilter(menuIconBaseColor);
            } else {
                bottom_bar.getIconAt(i).setColorFilter(menuIconBaseColor, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public void setupUserCity() {
        User connectedUser = (User) getIntent().getSerializableExtra("connectedUser");
        currentCityTV.setText(connectedUser.getCity().getName());
        Picasso.get().load(connectedUser.getCity().getCountryCode()).into(currentCountryIV);
    }
}
