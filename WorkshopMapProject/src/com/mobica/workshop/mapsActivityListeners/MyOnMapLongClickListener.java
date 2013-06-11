package com.mobica.workshop.mapsActivityListeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobica.workshop.MapsActivity;
import com.mobica.workshop.R;

/**
 * 
 * Created by blsu on 04.06.13.
 */
public class MyOnMapLongClickListener implements GoogleMap.OnMapLongClickListener {
    private final GoogleMap mGoogleMap;

    public MyOnMapLongClickListener(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        String title = String.format(MapsActivity.getInstance().getString(R.string.marker_name), Integer.toString(MapsActivity.getInstance().mMarkers.size() + 1));
        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(title).draggable(true));
        MapsActivity.getInstance().mMarkers.add(marker);
        MapsActivity.getInstance().mClusteringMarkers.checkMarkers(mGoogleMap.getProjection(), MapsActivity.getInstance().mMarkers);
    }
}

