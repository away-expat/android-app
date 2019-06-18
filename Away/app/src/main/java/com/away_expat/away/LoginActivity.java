package com.away_expat.away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.away_expat.away.classes.User;
import com.away_expat.away.dto.LoginDto;
import com.away_expat.away.dto.TokenDto;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;
import com.away_expat.away.tools.SaveSharedPreference;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn, googleBtn, facebookBtn;
    private TextView createAccountTV;
    private EditText emailET, passwordET;
    private User account;
    private GoogleSignInClient mGoogleSignInClient;

    private RetrofitServiceGenerator retrofitServiceGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SaveSharedPreference.getToken(getApplicationContext()) != null) {
            Log.i("AWAYINFO", SaveSharedPreference.getToken(getApplicationContext()));
            Intent home = new Intent(getApplicationContext(), HomeActivity.class);
            home.putExtra("token", SaveSharedPreference.getToken(getApplicationContext()));
            startActivity(home);
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loginBtn = (Button)findViewById(R.id.login_btn);
        googleBtn = (Button) findViewById(R.id.login_google_btn);
        facebookBtn = (Button) findViewById(R.id.login_fb_btn);

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

        googleBtn.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 1);
        });

        createAccountTV.setOnClickListener(v -> createAccount());
    }

    private void login() {
        Call<TokenDto> call = retrofitServiceGenerator.createService(UserApiService.class).login(new LoginDto(emailET.getText().toString(), passwordET.getText().toString()));

        call.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                Log.i("AWAYINFO", "Login success : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                    String token = response.body().getToken();
                    SaveSharedPreference.setToken(getApplicationContext(), token);
                    home.putExtra("token", token);
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
    }


    private void createAccount() {
        Intent CreateAcc = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(CreateAcc);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount googleAccount = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.i("INFO", "---------------------> "+googleAccount.toString());
            Intent createAcc = new Intent(LoginActivity.this, CreateAccountActivity.class);
            User account = new User(googleAccount);
            createAcc.putExtra("account", account);
            startActivity(createAcc);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
