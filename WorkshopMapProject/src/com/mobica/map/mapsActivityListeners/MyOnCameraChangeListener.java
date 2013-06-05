package com.mobica.map.mapsActivityListeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.mobica.map.MapsActivity;

/**
 *
 * Created by blsu on 04.06.13.
 */
public class MyOnCameraChangeListener implements GoogleMap.OnCameraChangeListener {
    private final GoogleMap mGoogleMap;

    public MyOnCameraChangeListener(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (cameraPosition.zoom != MapsActivity.getInstance().oldZoom) {
            MapsActivity.getInstance().mClusteringMarkers.checkMarkers(mGoogleMap.getProjection(), MapsActivity.getInstance().mMarkers);
        }
        MapsActivity.getInstance().oldZoom = cameraPosition.zoom;
    }
}
