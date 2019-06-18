package com.away_expat.away;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.away_expat.away.classes.User;
import com.away_expat.away.dto.TokenDto;
import com.away_expat.away.fragments.UserCreationFragment;
import com.away_expat.away.fragments.TagFragment;
import com.away_expat.away.fragments.CityFragment;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends FragmentActivity {

    private Button previousBtn, nextBtn;
    private int step;
    private UserCreationFragment accModifFrag;
    private CityFragment accCountryFrag;
    private TagFragment accTagFrag;
    private User account;

    private RetrofitServiceGenerator retrofitServiceGenerator;

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

            accCountryFrag = new CityFragment();
            accCountryFrag.setArguments(getIntent().getExtras());

            accTagFrag = new TagFragment();
            accTagFrag.setArguments(getIntent().getExtras());
        }

        previousBtn = (Button)findViewById(R.id.previousBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (step) {
                    case 1:
                        finish();
                        break;

                    case 2:
                        step--;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accModifFrag).commit();
                        break;

                    case 3:
                        step--;
                        nextBtn.setText(R.string.next);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accCountryFrag).commit();
                        break;
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (step) {
                    case 1:
                        account = accModifFrag.checkAndGetAccountInfo();
                        if (account != null) {
                            step++;
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accCountryFrag).commit();
                            return;
                        } else {
                            return;
                        }

                    case 2:
                        step++;
                        nextBtn.setText(R.string.create);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accTagFrag).commit();
                        break;

                    case 3:
                        step++;
                        Call<TokenDto> call = retrofitServiceGenerator.createService(UserApiService.class).createUser(account);
                        Log.i("AWAYINFO", "Account "+account.toString());

                        call.enqueue(new Callback<TokenDto>() {
                            @Override
                            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                                Log.i("AWAYINFO", "Acc creation success : " + response.isSuccessful());
                                if (response.isSuccessful()) {
                                    Intent home = new Intent(CreateAccountActivity.this, HomeActivity.class);
                                    home.putExtra("token", response.body().getToken());

                                    home.putExtra("connected_user", account);

                                    startActivity(home);
                                    finish();
                                } else {
                                    //TODO
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenDto> call, Throwable t) {
                                //TODO
                                Log.i("error", t.getMessage());
                            }
                        });

                        break;
                }
            }
        });
    }
}
