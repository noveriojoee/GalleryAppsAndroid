package com.galleryapp.gid.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.galleryapp.gid.Config.Config;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.views.MasterActivityFragments.CameraFragmentPage;
import com.galleryapp.gid.views.MasterActivityFragments.LocationFragmentPage;
import com.galleryapp.gid.views.MasterActivityFragments.SettingsFragmentPage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MasterAcitivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    //Google location Begin
    private static final String TAG = "MasterActivity";
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    //Google location end

    //Location Variable End
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private double currentLongitude;
    private double currentLatitude;
    private float currentAccuracy;

    public double getCurrentLongitudeValue(){
        return this.currentLongitude;
    }

    public double getCurrentLattitudeValue(){
        return this.currentLatitude;
    }

    public float getCurrentAccuracy(){
        return this.currentAccuracy;
    }


    private BottomNavigationView bottomNavigationMenu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_location:
//                    mViewPager.setCurrentItem(0);
                    item.setChecked(true);
                    return true;
                case R.id.action_camera:
                    item.setChecked(true);
                    return true;
                case R.id.action_setting:
                    mViewPager.setCurrentItem(2);
                    item.setChecked(true);
                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /*Do Nothing */
        }

        @Override
        public void onPageSelected(int position) {
            bottomNavigationMenu.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_master_acitivity);

            Config.AndroidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            //Creating google api client initialization begin
            this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            //Creating google api client initialization end


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //Configure Bottom Navigation begin
            this.bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            this.bottomNavigationMenu.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
            //Configure Bottom Navigaton end

            //Page Configuration begin
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.addOnPageChangeListener(this.mOnPageChangedListener);
            mViewPager.setAdapter(this.mSectionsPagerAdapter);
            //Page Configuration end


        }catch(Exception e){
            Log.d(TAG,e.getMessage());
        }

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
    }
    // Google API Clients Methods end


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_master_acitivity, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private AppCompatActivity activity;

        public SectionsPagerAdapter(FragmentManager fm,AppCompatActivity invokerActivity) {
            super(fm);
            this.activity = invokerActivity;
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0) {

                LocationFragmentPage locationPage = new LocationFragmentPage(this.activity);

                return locationPage;

            }else if(position == 1){
                CameraFragmentPage cameraPage = new CameraFragmentPage(this.activity);

                return cameraPage;
            }else if(position == 2){
                SettingsFragmentPage settingPage = new SettingsFragmentPage(this.activity);

                return settingPage;
            }
            else{
                return PlaceholderFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
