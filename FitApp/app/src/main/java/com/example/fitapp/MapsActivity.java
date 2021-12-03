package com.example.fitapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.fitapp.databinding.ActivityMapsBinding;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager locationManager;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String Permissions[] = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Location CurrentLocation = location;

            LatLng loc = new LatLng(CurrentLocation.getLatitude(), CurrentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,15f));

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

       checkPermissions();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @SuppressLint("MissingPermission")
    public void SetCurrentLocation(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5, mLocationListener);

        Criteria criteria = new Criteria();

        try {
            Location CurrentLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

            LatLng loc = new LatLng(CurrentLocation.getLatitude(), CurrentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,15f));

            Uri gmmIntentUri = Uri.parse("geo:"+CurrentLocation.getLatitude()+","+CurrentLocation.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(mapIntent);
                finish();
            }
            catch(Exception e) {

            }

        }
        catch (Exception e){

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Marker in current location
        checkPermissions();


    }

    public void checkPermissions(){
        try {
            if (canGetLocation()){

            }
            else{
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

            if (ActivityCompat.checkSelfPermission(this, Permissions[0]) != 1) {
                ActivityCompat.requestPermissions(this, Permissions, REQUEST_CODE_PERMISSION);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_PERMISSION)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                SetCurrentLocation();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please allow location permissions to show your current position.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean canGetLocation() {
        LocationManager lm;
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            networkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gpsEnabled && networkEnabled;
    }



}