package com.away_expat.away;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.away_expat.away.adapters.SearchGridViewAdapter;
import com.away_expat.away.adapters.TagSelectionGridViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.TagApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagActivity extends AppCompatActivity {

    private TagSelectionGridViewAdapter adapter;
    private EditText searchET;
    private Button selectBtn;
    private GridView gridView;

    private RetrofitServiceGenerator retrofitServiceGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        gridView = (GridView) findViewById(R.id.grid_view);
        searchET = (EditText) findViewById(R.id.search_tag_input);
        selectBtn = (Button) findViewById(R.id.tag_selection_btn);

        searchET.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                Log.i("INFO", c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        selectBtn.setOnClickListener(v -> {
            setResult(RESULT_OK, getIntent());
            finish();
        });

        Context $this = this;
        Call<List<Tag>> call = retrofitServiceGenerator.createService(TagApiService.class).getAllTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                adapter = new TagSelectionGridViewAdapter($this);
                adapter.bind(response.body());

                gridView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

        gridView.setOnItemClickListener((a, v, position, id) -> {
            getIntent().putExtra("tag", adapter.getItem(position).getName());
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, getIntent());
        finish();
    }
}
