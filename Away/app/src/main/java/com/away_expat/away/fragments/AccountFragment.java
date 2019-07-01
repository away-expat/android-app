package com.away_expat.away.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.EventListViewAdapter;
import com.away_expat.away.adapters.EventRecyclerViewAdapter;
import com.away_expat.away.classes.Event;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.services.EventApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.tools.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private TextView nameTV, countryTV, birthdayTV, nextTV, createdTV;
    private ImageView userIV;
    private Button actionBtn, tagBtn;
    private EventListViewAdapter adapter;
    private RecyclerView futurRecyclerView;
    private RecyclerView createdRecyclerView;

    private EventRecyclerViewAdapter futurAdapter = null;
    private List<Event> listNextEvent = new ArrayList<>();
    private EventRecyclerViewAdapter createdAdapter = null;
    private List<Event> listCreatedEvent = new ArrayList<>();

    private User user;
    private boolean isUserAccount;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        userIV = (ImageView) view.findViewById(R.id.account_user_image);
        actionBtn = (Button) view.findViewById(R.id.account_btn_action);
        tagBtn = (Button) view.findViewById(R.id.account_btn_tag);
        nameTV = (TextView) view.findViewById(R.id.account_textview_username);
        countryTV = (TextView) view.findViewById(R.id.account_textview_country);
        birthdayTV = (TextView) view.findViewById(R.id.account_textview_birthdate);
        nextTV = (TextView) view.findViewById(R.id.next_not_found_text);
        createdTV = (TextView) view.findViewById(R.id.created_not_found_text);
        futurRecyclerView = (RecyclerView) view.findViewById(R.id.futur_event_recycler_view);
        createdRecyclerView = (RecyclerView) view.findViewById(R.id.created_event_recycler_view);

        Picasso.get().load(user.getAvatar()).transform(new CircleTransform()).into(userIV);
        String toDisplay = user.getFirstname()+" "+user.getLastname();
        nameTV.setText(toDisplay);
        countryTV.setText(user.getCountry());
        birthdayTV.setText(user.getBirthday());
        setupFuturEvent();
        setupCreatedEvent();

        if (isUserAccount) {
            actionBtn.setVisibility(View.VISIBLE);
            actionBtn.setOnClickListener(v -> updateAccount());
            
            tagBtn.setOnClickListener(v -> updateTag());
        } else {
            actionBtn.setVisibility(View.GONE);
            tagBtn.setOnClickListener(v -> showTag());
        }

        return view;
    }

    private void setupFuturEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        futurRecyclerView.setLayoutManager(layoutManager);

        String token = getActivity().getIntent().getStringExtra("token");
        Call<List<Event>> call = RetrofitServiceGenerator.createService(EventApiService.class).getEventByUser(token, user.getId());

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    listNextEvent = response.body();

                    if (listNextEvent.size() == 0) {
                        nextTV.setVisibility(View.VISIBLE);
                    } else {
                        nextTV.setVisibility(View.GONE);
                    }

                    futurAdapter = new EventRecyclerViewAdapter(listNextEvent, getContext(), item -> {
                                Call<DetailedEventDto> innerCall = RetrofitServiceGenerator.createService(EventApiService.class).getById(token, item.getId());

                                innerCall.enqueue(new Callback<DetailedEventDto>() {
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
                            });
                    futurRecyclerView.setAdapter(futurAdapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.i("AWAYINFO", "-----------------> "+t.getMessage());
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCreatedEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        createdRecyclerView.setLayoutManager(layoutManager);

        String token = getActivity().getIntent().getStringExtra("token");
        Call<List<Event>> call = RetrofitServiceGenerator.createService(EventApiService.class).getCreatedEventByUser(token, user.getId());

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    listCreatedEvent = response.body();

                    if (listCreatedEvent.size() == 0) {
                        createdTV.setVisibility(View.VISIBLE);
                    } else {
                        createdTV.setVisibility(View.GONE);
                    }

                    createdAdapter = new EventRecyclerViewAdapter(listCreatedEvent, getContext(), item -> {
                        Call<DetailedEventDto> innerCall = RetrofitServiceGenerator.createService(EventApiService.class).getById(token, item.getId());

                        innerCall.enqueue(new Callback<DetailedEventDto>() {
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
                    });
                    createdRecyclerView.setAdapter(createdAdapter);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.i("AWAYINFO", "-----------------> "+t.getMessage());
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTag() {
        DisplayTagFragment fragment = new DisplayTagFragment();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
        fragment.setUser(user);
    }

    private void updateAccount() {
        UpdateFragment fragment = new UpdateFragment();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
        fragment.setUser(user);
    }

    private void updateTag() {
        TagFragment fragment = new TagFragment();
        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }

    public void setUser(User user, boolean isUserAcc) {
        this.user = user;
        this.isUserAccount = isUserAcc;
    }
}
