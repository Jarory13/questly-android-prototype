package com.questly.android.ui;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.questly.android.R;
import com.questly.android.commons.BaseActivity;
import com.questly.android.commons.CircleTransform;
import com.questly.android.util.AppHelper;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Main screen a user see if they are already logged in.s
 */
public class MainActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private CreateTaskDialogFragment createTaskDialogFragment;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private double mCurrentLatitude;

    private double mCurrentLongitude;

    private ImageView mProfileAvatar;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProfileAvatar = (ImageView) findViewById(R.id.profile_avatar);
        Picasso.with(this).load(R.drawable.stock_profile_image)
                .transform(new CircleTransform()).into(mProfileAvatar);
        setSupportActionBar(toolbar);
        mProfileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createTaskDialogFragment == null) {
                    createTaskDialogFragment = new CreateTaskDialogFragment();
                }
                createTaskDialogFragment.show(getFragmentManager(),
                        CreateTaskDialogFragment.class.getSimpleName());
            }
        });

        Button taskButton = (Button) findViewById(R.id.task_button);
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaskListActivity.class));
            }
        });

        Button mapButton = (Button) findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager()
                        .findFragmentByTag(SupportMapFragment.class.getSimpleName()) == null) {
                    SupportMapFragment mapFragment = SupportMapFragment.newInstance();
                    mapFragment.getMapAsync(MainActivity.this);
                    getSupportFragmentManager().beginTransaction()
                            .add(getContainerId(), mapFragment)
                            .commit();
                }

            }
        });

        Button profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        //Init
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(MainActivity.this);
        getSupportFragmentManager().beginTransaction()
                .add(getContainerId(), mapFragment, mapFragment.getClass().getSimpleName())
                .commit();
        getSupportActionBar().setTitle("Map");
        getGoogleApiClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    MainActivity.this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void getGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_signout:
                showSigningOutProgressDialog();
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        hideSigningOutProgressDialog();
                        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                        finish();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSigningOutProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage(getString(R.string.signing_out));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    private void hideSigningOutProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        } else {
            mCurrentLatitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            AppHelper.getInstance().setLat(mCurrentLatitude);
            AppHelper.getInstance().setLong(mCurrentLongitude);
            if (mMap != null) {
                LatLng current = new LatLng(mCurrentLatitude, mCurrentLongitude);
                mMap.addMarker(new MarkerOptions().position(current).title("current location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16f));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLatitude = location.getLatitude();
        mCurrentLongitude = location.getLongitude();
        AppHelper.getInstance().setLat(mCurrentLatitude);
        AppHelper.getInstance().setLong(mCurrentLongitude);
        if (mMap != null) {
            LatLng current = new LatLng(mCurrentLatitude, mCurrentLongitude);
            mMap.addMarker(new MarkerOptions().position(current).title("current location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16f));
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
