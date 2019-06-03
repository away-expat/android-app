package com.away_expat.away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.away_expat.away.classes.User;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn, createAccountBtn;
    private TextView createAccountTV;
    private EditText emailET, passwordET;
    private User account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private void login() {
        //check account & get the connected account
        //TODO
        account = new User("000", "fernandesantunesdylan@gmail.com", "*****", "Dylan", "Fernandes", "06/09/1994", "France");

        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
        home.putExtra("connected_user", account);
        startActivity(home);
        finish();
    }

    private void createAccount() {
        Intent CreateAcc = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(CreateAcc);
    }
}
