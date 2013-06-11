package com.mobica.workshop.mapsActivityListeners;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mobica.workshop.MapsActivity;

/**
 *
 * Created by BLSU on 05.06.13.
 */
public class MyOnMarkerDragListener implements GoogleMap.OnMarkerDragListener {
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        MapsActivity.getInstance().searchRoute();
    }
}
