package com.galleryapp.gid.views;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.views.ImageSaveActivityFragments.ImageInfoFragmentPage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class MyLocationActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    //UIVariable
    private EditText etLattitude;
    private EditText etLongitude;
    private EditText etAccuracy;
    private Button btnSaveLocation;
    //


    private double currentLongitude;
    private double currentLatitude;
    private float currentAccuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        this.InitializeVariable();

        //Creating google api client initialization begin
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        //Creating google api client initialization end

    }

    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        // Disconnecting the client invalidates it.
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        // only stop if it's connected, otherwise we crash
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    // Google API Clients Methods Begin
    @Override
    public void onConnected(Bundle bundle) {
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {

            this.currentLatitude = mCurrentLocation.getLatitude();
            this.currentLongitude = mCurrentLocation.getLongitude();
            this.currentAccuracy = mCurrentLocation.getAccuracy();
        }
        this.startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.currentLongitude = location.getLongitude();
        this.currentLatitude = location.getLatitude();
        this.currentAccuracy = location.getAccuracy();
        this.UpdateLattitudeAndLongitudeValue();
    }

    private void InitializeVariable(){
        this.etLattitude = (EditText)this.findViewById(R.id.tbLattitudeValue);
        this.etLongitude = (EditText)this.findViewById(R.id.tbLongitudeValue);
        this.etAccuracy = (EditText)this.findViewById(R.id.tbAccuracyValue);
        this.btnSaveLocation = (Button)this.findViewById(R.id.btnChooseLocation);

        this.btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Geocoder geocoder = new Geocoder(MyLocationActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(currentLatitude,currentLongitude, 1);

                    String strAddress = addresses.get(0).getAddressLine(0);
                    String strCity = addresses.get(0).getAddressLine(1);
                    String strCountry = addresses.get(0).getCountryName();
                    String strAddressResult= strAddress+","+strCity+","+strCountry;
                    setResult(Activity.RESULT_OK
                            ,new Intent()
                                    .putExtra("LattitudeValue",currentLatitude)
                                    .putExtra("LongitudeValue",currentLongitude)
                                    .putExtra("AccuracyValue",currentAccuracy)
                                    .putExtra("locationAddress",strAddressResult)
                    );
                    finish();
                }catch(Exception e){
                    Log.d("MyLocationActivity","btnSaveLocationOnClick error "+e.getMessage());
                }

            }
        });
    }

    private void UpdateLattitudeAndLongitudeValue(){
        this.etLattitude.setText(Double.toString(this.currentLatitude));
        this.etLongitude.setText(Double.toString(this.currentLongitude));
        this.etAccuracy.setText(Float.toString(this.currentAccuracy));
    }
}
