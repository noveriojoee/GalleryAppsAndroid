package com.galleryapp.gid.views.MasterActivityFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.views.MasterAcitivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by noverio.joe on 3/6/17.
 */

@SuppressLint("ValidFragment")
public class LocationFragmentPage extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // variable for gps locator begin
    private View rootView;
    private AppCompatActivity invokerActivity;
    private double currentLattitude;
    private double currentLongitude;
    private float currentAccuracy;
    private Button btnDisplayLocation;
    private MapView map;

    private GoogleMap googleMap;
    // variable for gps locator end
    @SuppressLint("ValidFragment")
    public LocationFragmentPage(AppCompatActivity activity) {
        this.invokerActivity = activity;
    }

    public LocationFragmentPage getFragment() {
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_location_master_activity, container, false);
        this.btnDisplayLocation = (Button)this.rootView.findViewById(R.id.btnDisplayLocation);
        this.map = (MapView)this.rootView.findViewById(R.id.map);
        this.map.onCreate(savedInstanceState);

        this.map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap resultMap) {
                googleMap = resultMap;
                googleMap.setMyLocationEnabled(true);
            }
        });

        this.btnDisplayLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateLattitudeAndLongitudeValue();

                googleMap.clear();
                LatLng myLocation = new LatLng(currentLattitude,currentLongitude);
                googleMap.addMarker(new MarkerOptions().position(myLocation).title("Kosan Asep").snippet("Bujangan Teu Bisa Sare"));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(5).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                map.onResume();
            }
        });

        UpdateLattitudeAndLongitudeValue();
        return rootView;
    }


    private void UpdateLattitudeAndLongitudeValue(){

        EditText etLattitude = (EditText)this.rootView.findViewById(R.id.tbLattitude);
        EditText etLongitude = (EditText)this.rootView.findViewById(R.id.tbLongitude);
        EditText etAccuracy = (EditText)this.rootView.findViewById(R.id.tbAccuraccy);

        this.currentLattitude = ((MasterAcitivity)this.invokerActivity).getCurrentLattitudeValue();
        this.currentLongitude = ((MasterAcitivity)this.invokerActivity).getCurrentLongitudeValue();
        this.currentAccuracy  =((MasterAcitivity)this.invokerActivity).getCurrentAccuracy();

        etLattitude.setText(Double.toString(((MasterAcitivity)this.invokerActivity).getCurrentLattitudeValue()));
        etLongitude.setText(Double.toString(((MasterAcitivity)this.invokerActivity).getCurrentLongitudeValue()));
        etAccuracy.setText(Float.toString(((MasterAcitivity)this.invokerActivity).getCurrentAccuracy()));

        Log.d("LocationFragmentPage","Updating UI");
    }
}
