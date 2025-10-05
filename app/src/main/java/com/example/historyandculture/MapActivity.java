package com.example.historyandculture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQ_LOCATION = 1001;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;

    private final LatLng[] sites = {
            new LatLng(27.1751, 78.0421), // Taj Mahal
            new LatLng(28.6562, 77.2410), // Red Fort
            new LatLng(26.9124, 75.7873),  // Jaipur
            new LatLng(25.38,83.02),
    };
    private final String[] siteNames = {"Taj Mahal", "Red Fort", "Jaipur","Sarnath"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
            return;
        }

        mMap.setMyLocationEnabled(true);
        loadUserLocationAndMarkers();
    }

    private void loadUserLocationAndMarkers() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, loc -> {
                    if (loc != null) {
                        userLocation = loc;
                        addMarkersWithDistanceAndZoom();
                    } else {
                        Toast.makeText(this, "Unable to get current location. Showing all sample sites.", Toast.LENGTH_LONG).show();
                        userLocation = null;
                        addMarkersWithDistanceAndZoom();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Location error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    userLocation = null;
                    addMarkersWithDistanceAndZoom();
                });
    }

    private void addMarkersWithDistanceAndZoom() {
        int nearbyCount = 0;
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        if (userLocation != null) {
            LatLng userLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            boundsBuilder.include(userLatLng);
        }

        for (int i = 0; i < sites.length; i++) {
            float distanceKm = -1f;
            if (userLocation != null) {
                Location siteLoc = new Location("");
                siteLoc.setLatitude(sites[i].latitude);
                siteLoc.setLongitude(sites[i].longitude);
                float distMeters = userLocation.distanceTo(siteLoc);
                distanceKm = distMeters / 1000f;
            }

            String title = siteNames[i];
            if (distanceKm >= 0) {
                title += " — " + String.format("%.1f km", distanceKm);
            } else {
                title += " — distance unknown";
            }
            boolean isNearby = (distanceKm >= 0 && distanceKm <= 100.0f);
            if (isNearby) nearbyCount++;

            float hue = isNearby ? BitmapDescriptorFactory.HUE_YELLOW : BitmapDescriptorFactory.HUE_AZURE;
            mMap.addMarker(new MarkerOptions()
                    .position(sites[i])
                    .title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(hue)));

            boundsBuilder.include(sites[i]);
            Log.d("MapActivity", "Added marker: " + title + " nearby=" + isNearby);
        }
        try {
            LatLngBounds bounds = boundsBuilder.build();
            int padding = 150;
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } catch (Exception e) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sites[0], 6f));
        }

        // Inform user
        if (nearbyCount == 0) {
            Toast.makeText(this, "No sites within 100 km. Showing all sample sites for demo.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, nearbyCount + " site(s) within 100 km", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                        loadUserLocationAndMarkers();
                    }
                } else {
                }
            } else {
                Toast.makeText(this, "Location permission is needed to show nearby sites.", Toast.LENGTH_LONG).show();
                addMarkersWithDistanceAndZoom();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}