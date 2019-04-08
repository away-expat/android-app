package com.away_expat.away;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.away_expat.away.classes.Account;

public class HomeActivity extends AppCompatActivity {

    private Account connectedUser;
    private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        testText = (TextView)findViewById(R.id.testText);

        connectedUser = (Account) getIntent().getSerializableExtra("connected_user");

        testText.setText("Hi "+connectedUser.getEmail());
    }
}
