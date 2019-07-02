package com.away_expat.away.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.HomeActivity;
import com.away_expat.away.R;
import com.away_expat.away.adapters.UserListViewAdapter;
import com.away_expat.away.classes.User;
import com.away_expat.away.dto.DetailedEventDto;
import com.away_expat.away.dto.ParticipateDto;
import com.away_expat.away.services.EventApiService;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends ListFragment {

    private TextView eventNameTextview, usernameTextview, eventDescriptionTextView, activityNameTextview, eventDateTextview;
    private ImageView userIV, activityIV;
    private Button actionBtn;
    private UserListViewAdapter adapter;
    private DetailedEventDto eventDto;
    private User connectedUser;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        connectedUser = (User) getActivity().getIntent().getSerializableExtra("connectedUser");

        userIV = (ImageView) view.findViewById(R.id.event_user_image);
        Picasso.get().load(eventDto.getCreator().getAvatar()).into(userIV);
        userIV.setOnClickListener(v -> {
            if (eventDto.getCreator().getId() != connectedUser.getId()) {
                getUserAndLoadFragment(eventDto.getCreator());
            } else {
                AccountFragment fragment = new AccountFragment();
                fragment.setUser(connectedUser, true);

                ((HomeActivity) getActivity()).replaceFragment(fragment);
            }
        });

        activityIV = (ImageView) view.findViewById(R.id.event_cover);
        Picasso.get().load(eventDto.getActivity().getPhotos()).into(activityIV);

        actionBtn = (Button) view.findViewById(R.id.event_action);
        if (connectedUser.getId() == eventDto.getCreator().getId()) {
            actionBtn.setVisibility(View.GONE);
        } else {
            if (checkIfParticipate()) {
                actionBtn.setText(getActivity().getString(R.string.quit));
                actionBtn.setVisibility(View.VISIBLE);
                actionBtn.setOnClickListener(v -> quitEvent());
            } else {
                actionBtn.setText(getActivity().getString(R.string.join));
                actionBtn.setVisibility(View.VISIBLE);
                actionBtn.setOnClickListener(v -> joinEvent());
            }
        }

        eventNameTextview = (TextView) view.findViewById(R.id.event_name);
        eventNameTextview.setText(eventDto.getEvent().getTitle());

        eventDescriptionTextView = (TextView) view.findViewById(R.id.event_info);
        eventDescriptionTextView.setText(eventDto.getEvent().getDescription());

        activityNameTextview = (TextView) view.findViewById(R.id.event_activity);
        activityNameTextview.setText(eventDto.getActivity().getName());
        activityNameTextview.setPaintFlags(activityNameTextview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        activityNameTextview.setOnClickListener(v -> openActivityFragment());

        eventDateTextview = (TextView) view.findViewById(R.id.event_date);
        String toDisplay = eventDto.getEvent().getHour()+" "+eventDto.getEvent().getDate();
        eventDateTextview.setText(toDisplay);

        usernameTextview = (TextView) view.findViewById(R.id.event_username);
        usernameTextview.setText(getContext().getString(R.string.by)+" "+eventDto.getCreator().getFirstname());

        return view;
    }

    private void getUserAndLoadFragment(User toLoad) {
        String token = getActivity().getIntent().getStringExtra("token");
        Call<User> call = RetrofitServiceGenerator.createService(UserApiService.class).getUserById(token, toLoad.getId());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    AccountFragment fragment = new AccountFragment();
                    fragment.setUser(response.body(), false);

                    ((HomeActivity) getActivity()).replaceFragment(fragment);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkIfParticipate() {
        for (User u : eventDto.getParticipant()) {
            if (u.getId() == connectedUser.getId()) {
                return true;
            }
        }

        return false;
    }


    private void removeFromParticipantList() {
        for (User u : eventDto.getParticipant()) {
            if (u.getId() == connectedUser.getId()) {
                eventDto.getParticipant().remove(u);
            }
        }
    }

    private void joinEvent() {
        String token = getActivity().getIntent().getStringExtra("token");
        Call<DetailedEventDto> call = RetrofitServiceGenerator.createService(EventApiService.class).participate(token, new ParticipateDto(eventDto.getEvent().getId()));

        call.enqueue(new Callback<DetailedEventDto>() {
            @Override
            public void onResponse(Call<DetailedEventDto> call, Response<DetailedEventDto> response) {
                if (response.isSuccessful()) {
                    actionBtn.setText(getActivity().getString(R.string.quit));
                    actionBtn.setOnClickListener(v -> quitEvent());
                    eventDto.getParticipant().add(connectedUser);
                    adapter.notifyDataSetChanged();
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

    private void quitEvent() {
        String token = getActivity().getIntent().getStringExtra("token");
        Call<DetailedEventDto> call = RetrofitServiceGenerator.createService(EventApiService.class).quit(token, new ParticipateDto(eventDto.getEvent().getId()));

        call.enqueue(new Callback<DetailedEventDto>() {
            @Override
            public void onResponse(Call<DetailedEventDto> call, Response<DetailedEventDto> response) {
                if (response.isSuccessful()) {
                    actionBtn.setText(getActivity().getString(R.string.join));
                    actionBtn.setOnClickListener(v -> joinEvent());
                    removeFromParticipantList();
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new UserListViewAdapter(getActivity());
        adapter.bind(eventDto.getParticipant());

        setListAdapter(adapter);
    }

    public void setEvent(DetailedEventDto event) {
        this.eventDto = event;
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        User userClicked = adapter.getItem(pos);
        if (userClicked.getId() != connectedUser.getId()) {
            getUserAndLoadFragment(userClicked);
        } else {
            AccountFragment fragment = new AccountFragment();
            fragment.setUser(connectedUser, true);

            ((HomeActivity) getActivity()).replaceFragment(fragment);
        }
    }

    private void openActivityFragment() {
        ActivityFragment fragment = new ActivityFragment();
        fragment.setActivity(eventDto.getActivity());

        ((HomeActivity) getActivity()).replaceFragment(fragment);
    }
}
