package com.away_expat.away.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.EventListViewAdapter;
import com.away_expat.away.adapters.TagActivityAdapter;
import com.away_expat.away.adapters.TagActivityGridViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.services.EventApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFragment extends ListFragment {

    private TextView activityName, activityAddress;
    private ImageView activityImage;
    private EventListViewAdapter eventAdapter;
    private TagActivityGridViewAdapter tagAdapter;
    private User connectedUser;
    private Activity activity;

    private List<Event> items = new ArrayList<>();

    public ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");

        activityName = (TextView) view.findViewById(R.id.event_name);
        activityName.setText(activity.getName());

        activityAddress = (TextView) view.findViewById(R.id.activity_address);
        activityAddress.setText(activity.getAddress());
        activityAddress.setPaintFlags(activityAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        activityAddress.setOnClickListener(
                v -> new Handler().postDelayed(() -> {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+activity.getAddress());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }, 1000)
        );

        activityImage = (ImageView) view.findViewById(R.id.activity_image);
        Picasso.get().load(activity.getPhotos()).into(activityImage);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        List<String> listItem = activity.getType();
        TagActivityAdapter itemAdapter = new TagActivityAdapter(listItem, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String token = getActivity().getIntent().getStringExtra("token");
        Call<List<Event>> call = RetrofitServiceGenerator.createService(EventApiService.class).getEventByActivity(token, activity.getId());

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    eventAdapter = new EventListViewAdapter(getActivity());
                    eventAdapter.bind(items);

                    setListAdapter(eventAdapter);
                } else {
                    Log.i("-------------->", response.message());
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        String token = getActivity().getIntent().getStringExtra("token");
        Call<DetailedEventDto> call = RetrofitServiceGenerator.createService(EventApiService.class).getById(token, eventAdapter.getItem(pos).getId());

        call.enqueue(new Callback<DetailedEventDto>() {
            @Override
            public void onResponse(Call<DetailedEventDto> call, Response<DetailedEventDto> response) {
                if (response.isSuccessful()) {
                    EventFragment fragment = new EventFragment();

                    fragment.setEvent(response.body());
                    ((HomeActivity) getActivity()).replaceFragment(fragment);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailedEventDto> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
