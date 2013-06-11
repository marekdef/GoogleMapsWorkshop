package com.mobica.workshop.mapsActivityListeners;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mobica.workshop.MapsActivity;
import com.mobica.workshop.cluster.ClusteringMarkers;

/**
 *
 * Created by blsu on 04.06.13.
 */
public class MyOnMarkerClickListener implements GoogleMap.OnMarkerClickListener {
    private final GoogleMap mGoogleMap;

    public MyOnMarkerClickListener(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        MapsActivity mapsActivity = MapsActivity.getInstance();
        if (MapsActivity.mMarkersShow != null && marker != null) {
            Integer type = MapsActivity.mMarkersShow.get(marker.getId());
            if (type != null) {
                switch (type) {
                    case ClusteringMarkers.MARKER:
                        marker.showInfoWindow();
                        return false;
                    case ClusteringMarkers.CLUSTER:
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                        float zoom = mGoogleMap.getCameraPosition().zoom + 1;
                        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                        mapsActivity.mClusteringMarkers.checkMarkers(mGoogleMap.getProjection(), mapsActivity.mMarkers);
                        break;
                }
            }
            return true;
        }
        return false;
    }
}
