package com.away_expat.away;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.TokenDto;
import com.away_expat.away.fragments.UserCreationFragment;
import com.away_expat.away.fragments.CityFragment;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;
import com.away_expat.away.services.UserApiService;
import com.away_expat.away.tools.SaveSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends FragmentActivity {

    private Button previousBtn, nextBtn;
    private int step;
    private UserCreationFragment accModifFrag;
    private CityFragment accCityFrag;
    private User account;
    private CreateAccountActivity $this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        step = 1;

        account = (User) getIntent().getSerializableExtra("account");

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            accModifFrag = new UserCreationFragment();
            accModifFrag.setArguments(getIntent().getExtras());
            if (account != null) {
                accModifFrag.setUser(account, false);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, accModifFrag).commit();

            accCityFrag = new CityFragment();
            accCityFrag.setArguments(getIntent().getExtras());
        }

        previousBtn = (Button)findViewById(R.id.closeBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);

        previousBtn.setOnClickListener(v -> {
            switch (step) {
                case 1:
                    finish();
                    break;

                case 2:
                    step--;
                    nextBtn.setText(R.string.next);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accModifFrag).commit();
                    break;
            }
        });

        nextBtn.setOnClickListener(v -> {
            switch (step) {
                case 1:
                    account = accModifFrag.checkAndGetAccountInfo();
                    if (account != null) {
                        step++;
                        nextBtn.setText(R.string.create);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accCityFrag).commit();
                        return;
                    } else {
                        return;
                    }

                case 2:
                    account.setIdCity(accCityFrag.checkAndGetCity().getId());
                    if (account.getIdCity() != -1) {
                        Call<TokenDto> call = RetrofitServiceGenerator.createService(UserApiService.class).createUser(account);
                        Log.i("AWAYINFO", "Account " + account.toString());

                        call.enqueue(new Callback<TokenDto>() {
                            @Override
                            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                                Log.i("AWAYINFO", "Acc creation success : " + response.isSuccessful());
                                if (response.isSuccessful()) {
                                    String token = response.body().getToken();
                                    getIntent().putExtra("token", token);
                                    SaveSharedPreference.setToken(getApplicationContext(), token);

                                    getConnectedUser(token);
                                } else {
                                    Log.i("AWAYINFO", response.message());
                                    Toast.makeText($this, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenDto> call, Throwable t) {
                                Log.i("AWAYINFO", "-----------------> " + t.getMessage());
                                Toast.makeText($this, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText($this, getResources().getString(R.string.error_city), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        });
    }

    private  void getConnectedUser(String token) {
        Call<User> call = RetrofitServiceGenerator.createService(UserApiService.class).getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User connectedUser = response.body();
                    getIntent().putExtra("connectedUser", connectedUser);

                    Intent firstLogin = new Intent(CreateAccountActivity.this, FirstLoginActivity.class);
                    firstLogin.putExtra("token", token);
                    firstLogin.putExtra("connectedUser", connectedUser);
                    startActivity(firstLogin);
                    finish();
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
}
