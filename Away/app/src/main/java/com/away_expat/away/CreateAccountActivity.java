package com.away_expat.away;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.away_expat.away.classes.User;
import com.away_expat.away.fragments.AccCountryFragment;
import com.away_expat.away.fragments.AccModifFragment;
import com.away_expat.away.fragments.AccTagFragment;

public class CreateAccountActivity extends FragmentActivity {

    private Button previousBtn, nextBtn;
    private int step;
    private AccModifFragment accModifFrag;
    private AccCountryFragment accCountryFrag;
    private AccTagFragment accTagFrag;
    private User account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        step = 1;

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            accModifFrag = new AccModifFragment();
            accModifFrag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, accModifFrag).commit();

            accCountryFrag = new AccCountryFragment();
            accCountryFrag.setArguments(getIntent().getExtras());

            accTagFrag = new AccTagFragment();
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
                        //create account
                        //TODO

                        //connect
                        //TODO

                        Intent home = new Intent(CreateAccountActivity.this, HomeActivity.class);
                        home.putExtra("connected_user", account);

                        startActivity(home);
                        finish();
                        break;
                }
            }
        });
    }
}
