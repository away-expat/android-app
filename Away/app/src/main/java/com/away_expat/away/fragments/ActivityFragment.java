package com.away_expat.away.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.EventListViewAdapter;
import com.away_expat.away.adapters.TagActivityAdapter;
import com.away_expat.away.adapters.TagActivityGridViewAdapter;
import com.away_expat.away.classes.Activity;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.Tag;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.DetailedEventDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityFragment extends ListFragment {

    private TextView activityName, activityAddress;
    private ImageView activityImage;
    private EventListViewAdapter eventAdapter;
    private TagActivityGridViewAdapter tagAdapter;
    private User connectedUser;
    private Activity activity;

    public ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connected_user");

        activityName = (TextView) view.findViewById(R.id.activity_name);
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
        Picasso.get().load(activity.getPhotos())
                .into(activityImage);

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

        List<Event> items = new ArrayList<>();
        eventAdapter = new EventListViewAdapter(getActivity());
        eventAdapter.bind(items);

        setListAdapter(eventAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        EventFragment fragment = new EventFragment();
        fragment.setEvent(new DetailedEventDto());

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
