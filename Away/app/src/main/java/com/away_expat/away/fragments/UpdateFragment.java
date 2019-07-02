package com.away_expat.away.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.away_expat.away.LoginActivity;
import com.away_expat.away.R;
import com.away_expat.away.classes.User;
import com.away_expat.away.services.RetrofitServiceGenerator;
import com.away_expat.away.services.UserApiService;
import com.away_expat.away.tools.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFragment extends Fragment {

    private TextView deleteLink;
    private Button disconnectBtn;
    private User user;

    public UpdateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_options, container, false);

        UserCreationFragment userCreationFragment = new UserCreationFragment();
        userCreationFragment.setUser(user, true);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, userCreationFragment).commit();

        DialogInterface.OnClickListener dialogDeleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SaveSharedPreference.setToken(getActivity().getApplicationContext(), null);
                        String token = getActivity().getIntent().getStringExtra("token");
                        Call<User> call = RetrofitServiceGenerator.createService(UserApiService.class).deleteUser(token);

                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    SaveSharedPreference.setToken(getActivity().getApplicationContext(), null);
                                    Intent home = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(home);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), getResources().getString(R.string.error_retry), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.error_reload), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        deleteLink = (TextView) view.findViewById(R.id.delete_link);
        deleteLink.setPaintFlags(deleteLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        deleteLink.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme);
            builder.setMessage(getContext().getResources().getString(R.string.delete_text)).setPositiveButton(getContext().getResources().getString(R.string.yes), dialogDeleteListener).setNegativeButton(getContext().getResources().getString(R.string.no), dialogDeleteListener).show();
        });

        DialogInterface.OnClickListener dialogDisconnectListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SaveSharedPreference.setToken(getActivity().getApplicationContext(), null);
                        Intent home = new Intent(getActivity(), LoginActivity.class);
                        startActivity(home);
                        getActivity().finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        disconnectBtn = (Button) view.findViewById(R.id.disconnectBtn);
        disconnectBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomDialogTheme);
            builder.setMessage(getContext().getResources().getString(R.string.disconnect_text)).setPositiveButton(getContext().getResources().getString(R.string.yes), dialogDisconnectListener).setNegativeButton(getContext().getResources().getString(R.string.no), dialogDisconnectListener).show();
        });

        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
