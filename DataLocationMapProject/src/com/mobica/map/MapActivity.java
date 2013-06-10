package com.mobica.map;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements LocationListener {

    private GoogleMap mGoogleMap;
    private final int MOBICA_AREA = 150;
    Location mMobicaLocation;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment
                = (MapFragment) myFragmentManager.findFragmentById(R.id.map);

        assert myMapFragment != null;
        mGoogleMap = myMapFragment.getMap();
        initMap();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        android.location.Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 100, 0, this);
        mMobicaLocation = new Location(provider);
        mMobicaLocation.setLatitude(51.754585);
        mMobicaLocation.setLongitude(19.455793);
    }

    private void initMap() {
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.setMyLocationEnabled(true);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mGoogleMap.clear();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.addCircle(new CircleOptions().center(latLng).radius(3).fillColor(Color.BLUE).strokeColor(Color.BLUE));

        if (location.distanceTo(mMobicaLocation) < MOBICA_AREA) {
            Marker mobicaMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.mobica))
                    .position(new LatLng(mMobicaLocation.getLatitude(),mMobicaLocation.getLongitude()))
                    .snippet(getString(R.string.snippet_info)));
            mobicaMarker.showInfoWindow();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
