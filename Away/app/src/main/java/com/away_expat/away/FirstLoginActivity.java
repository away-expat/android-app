package com.away_expat.away;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Toast;

import com.away_expat.away.fragments.TagFragment;

public class FirstLoginActivity extends FragmentActivity {

    private Button closeBtn;
    private TagFragment accTagFrag;
    private boolean isTagSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        getIntent().putExtra("isFirstLogin", true);

        closeBtn = (Button)findViewById(R.id.closeBtn);

        if (findViewById(R.id.fragment_container) != null) {
            accTagFrag = new TagFragment();
            accTagFrag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, accTagFrag).commit();
        }

        closeBtn.setOnClickListener(v -> {
             if (isTagSelected) {
                 Intent home = new Intent(FirstLoginActivity.this, HomeActivity.class);
                 home.putExtra("token", getIntent().getStringExtra("token"));
                 home.putExtra("connectedUser", getIntent().getSerializableExtra("connectedUser"));
                 startActivity(home);
                 finish();
             } else {
                 Toast.makeText(this, getResources().getString(R.string.tag_three_warning), Toast.LENGTH_SHORT).show();
             }
        });
    }

    public void setIsTagSelected() {
        this.isTagSelected = true;
    }
}
