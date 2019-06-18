package com.away_expat.away;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.away_expat.away.classes.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private User connectedUser;
    private Button selectBtn;
    private LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectedUser = (User) getIntent().getSerializableExtra("connected_user");
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        selectBtn = (Button) findViewById(R.id.place_btn);
        selectBtn.setOnClickListener(v -> {

            /*if () {

            } else {
                setResult(RESULT_OK, getIntent());
                finish();
            }*/
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng userPos = new LatLng(48.866667, 2.333333);
        getIntent().putExtra("position", userPos);
        mMap.addMarker(new MarkerOptions().position(userPos));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userPos));
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(18.0f);

        mMap.setOnMapClickListener(point -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(point));
            getIntent().putExtra("position", point);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
