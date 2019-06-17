package com.away_expat.away.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.away_expat.away.R;
import com.away_expat.away.classes.User;

public class UpdateFragment extends Fragment {

    private TextView deleteLink;
    private Button disconnectBtn;
    private User user;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_creation, container, false);

        UserCreationFragment userCreationFragment = new UserCreationFragment();
        userCreationFragment.setUser(user, true);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, userCreationFragment).commit();

        DialogInterface.OnClickListener dialogDeleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        deleteLink = (TextView) view.findViewById(R.id.delete_link);
        deleteLink.setPaintFlags(deleteLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        deleteLink.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Delete?").setPositiveButton("Yes", dialogDeleteListener).setNegativeButton("No", dialogDeleteListener).show();
        });

        DialogInterface.OnClickListener dialogDisconnectListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        disconnectBtn = (Button) view.findViewById(R.id.disconnectBtn);
        disconnectBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Disconnect?").setPositiveButton("Yes", dialogDisconnectListener).setNegativeButton("No", dialogDisconnectListener).show();
        });

        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
