package com.away_expat.away;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.LoginDto;
import com.away_expat.away.dto.TokenDto;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;
import com.away_expat.away.services.UserApiService;
import com.away_expat.away.tools.SaveSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView createAccountTV;
    private EditText emailET, passwordET;
    private ConstraintLayout subLayout;
    private ProgressBar progressBar;

    private User connectedUser;

    private LoginActivity $this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subLayout = (ConstraintLayout) findViewById(R.id.sub_layout);
        loginBtn = (Button)findViewById(R.id.login_btn);

        createAccountTV = (TextView)findViewById(R.id.login_create);
        emailET = (EditText) findViewById(R.id.login_input_email);
        passwordET = (EditText) findViewById(R.id.login_input_password);

        loginBtn.setOnClickListener(view -> {
            if (!emailET.getText().toString().equals("")) {
                if (!passwordET.getText().equals("")) {
                    login();
                } else {
                    passwordET.setError(getApplicationContext().getString(R.string.required));
                }
            } else {
                emailET.setError(getApplicationContext().getString(R.string.required));
            }
        });

        createAccountTV.setOnClickListener(v -> createAccount());

        progressBar = (ProgressBar) findViewById(R.id.load_progress);
        progressBar.setVisibility(View.GONE);

        if(SaveSharedPreference.getToken(getApplicationContext()) != null) {
            subLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            String token = SaveSharedPreference.getToken(getApplicationContext());
            getIntent().putExtra("token", token);
            getConnectedUser(token);
        }
    }

    private void login() {
        String email = emailET.getText().toString().replaceAll(" ","");
        String password = passwordET.getText().toString();

        Call<TokenDto> call = RetrofitServiceGenerator.createService(UserApiService.class).login(new LoginDto(email, password));

        call.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                Log.i("AWAYINFO", "Login success : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    getIntent().putExtra("token", token);
                    SaveSharedPreference.setToken(getApplicationContext(), token);

                    getConnectedUser(token);
                } else {
                    Toast.makeText($this, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {
                Toast.makeText($this, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void getConnectedUser(String token) {
        Call<User> call = RetrofitServiceGenerator.createService(UserApiService.class).getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    connectedUser = response.body();
                    getIntent().putExtra("connectedUser", connectedUser);

                    checkIfUserAsTags(token);
                } else {
                    progressBar.setVisibility(View.GONE);
                    subLayout.setVisibility(View.VISIBLE);
                    Toast.makeText($this, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                subLayout.setVisibility(View.VISIBLE);
                Toast.makeText($this, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIfUserAsTags(String token) {
        Call<List<Tag>> call = RetrofitServiceGenerator.createService(TagApiService.class).getUserTags(token, connectedUser.getId());

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    List<Tag> userTag = response.body();

                    if (userTag.size() == 0) {
                        Intent firstLogin = new Intent(LoginActivity.this, FirstLoginActivity.class);
                        firstLogin.putExtra("token", token);
                        firstLogin.putExtra("connectedUser", getIntent().getSerializableExtra("connectedUser"));
                        startActivity(firstLogin);
                        finish();
                    } else {
                        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                        home.putExtra("token", token);
                        home.putExtra("connectedUser", getIntent().getSerializableExtra("connectedUser"));
                        startActivity(home);
                        finish();
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
                    subLayout.setVisibility(View.VISIBLE);
                    Toast.makeText($this, getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                subLayout.setVisibility(View.VISIBLE);
                Toast.makeText($this, getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount() {
        Intent CreateAcc = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(CreateAcc);
    }

}
