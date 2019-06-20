package com.away_expat.away;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.classes.User;
import com.away_expat.away.fragments.AccountFragment;
import com.away_expat.away.fragments.CityFragment;
import com.away_expat.away.fragments.CountryInformationFragment;
import com.away_expat.away.fragments.CreationFragment;
import com.away_expat.away.fragments.HomeFragment;
import com.away_expat.away.fragments.SearchFragment;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private String token;
    private BottomNavigationViewEx bottom_bar;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private CreationFragment creationFragment;
    private AccountFragment accountFragment;
    private CountryInformationFragment countryInfoFragment;
    private CityFragment cityFragment;

    private ImageView changeCountryImageView, currentCountryIV;
    private TextView currentCityTV;
    private View customBar;

    private RetrofitServiceGenerator retrofitServiceGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        token = getIntent().getStringExtra("token");

        getConnectedUser();
    }

    private void initBottomBar() {
        bottom_bar.enableItemShiftingMode(false);

        bottom_bar.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.homeFragmentMenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homeFragment).commit();
                break;
            case R.id.searchFragmentMenu:
                //TODO
                searchFragment.setUser();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, searchFragment).commit();
                break;
            case R.id.createFragmentMenu:
                //TODO
                creationFragment.setUser();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, creationFragment).commit();
                break;
            case R.id.accountFragmentMenu:
                //TODO
                accountFragment.setUser((User) getIntent().getSerializableExtra("connectedUser"),true);
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, accountFragment).commit();
                break;
            case R.id.countryInfoFragmentMenu:
                //TODO
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, countryInfoFragment).commit();
                break;
        }
        return true;
    }

    private  void getConnectedUser() {
        HomeActivity $this = this;
        Call<User> call = retrofitServiceGenerator.createService(UserApiService.class).getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    getIntent().putExtra("connectedUser", response.body());
                    loadViews();
                    setupUserCity();
                } else {
                    Toast.makeText($this, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText($this, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadViews() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        customBar = getSupportActionBar().getCustomView();

        currentCityTV = (TextView) customBar.findViewById(R.id.user_location);
        currentCountryIV = (ImageView) customBar.findViewById(R.id.user_location_country);
        //Picasso.get().load("https://www.countryflags.io/fr/flat/16.png").into(currentCountryIV);

        changeCountryImageView = (ImageView) customBar.findViewById(R.id.country);
        changeCountryImageView.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, cityFragment).commit());

        bottom_bar = (BottomNavigationViewEx) findViewById(R.id.bottom_bar);

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
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {

    }

    public void setupUserCity() {
        User connectedUser = (User) getIntent().getSerializableExtra("connectedUser");
        currentCityTV.setText(connectedUser.getCity().getName());
    }
}
