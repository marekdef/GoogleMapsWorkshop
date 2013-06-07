package com.mobica.map;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobica.map.Preferences.Preferences;
import com.mobica.map.cluster.ClusteringMarkers;
import com.mobica.map.googledirections.GeoPoint;
import com.mobica.map.googledirections.GoogleDirectionsResponse;
import com.mobica.map.googledirections.Leg;
import com.mobica.map.googledirections.Route;
import com.mobica.map.googledirections.Step;
import com.mobica.map.mapsActivityListeners.MyOnCameraChangeListener;
import com.mobica.map.mapsActivityListeners.MyOnMapLongClickListener;
import com.mobica.map.mapsActivityListeners.MyOnMarkerClickListener;
import com.mobica.map.mapsActivityListeners.MyOnMarkerDragListener;
import com.mobica.map.serverConnection.RoutingConnector;
import com.mobica.map.uiInterface.GoogleMapsRoutingInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends Activity implements GoogleMapsRoutingInterface {
    public static final HashMap<String, Integer> mMarkersShow;
    public ArrayList<Marker> mMarkers;
    public float oldZoom = 0;
    public ClusteringMarkers mClusteringMarkers;
    private RoutingConnector mRoutingConnector;
    private GoogleMap mGoogleMap;
    private Polyline mPolyline;

    static {
        mMarkersShow = new HashMap<String, Integer>();
    }

    private static volatile MapsActivity sMapsActivity;

    public static MapsActivity getInstance() {
        if (sMapsActivity == null)
            sMapsActivity = getInstance();
        return sMapsActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        mMarkers = new ArrayList<Marker>();
        sMapsActivity = this;
        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment
                = (MapFragment) myFragmentManager.findFragmentById(R.id.map);

        assert myMapFragment != null;
        mGoogleMap = myMapFragment.getMap();
        initMap();
        mClusteringMarkers = new ClusteringMarkers(this);
        mClusteringMarkers.checkMarkers(mGoogleMap.getProjection(), mMarkers);
        // MyLocation myLocation;
        //  myLocation = new MyLocation(this, mGoogleMap);
    }

    private void initMap() {
        //TODO włącz moją pozycję, kompas, oraz obsługe gestów
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.setMyLocationEnabled(true);

        //TODO dodaj long click ( dodawanie punktu na mapie), drag and drop, klikanie markera oraz odswiezanie mapy po zmianie zooma
        mGoogleMap.setOnCameraChangeListener(new MyOnCameraChangeListener(mGoogleMap));
        mGoogleMap.setOnMarkerClickListener(new MyOnMarkerClickListener(mGoogleMap));
        mGoogleMap.setOnMapLongClickListener(new MyOnMapLongClickListener(mGoogleMap));
        mGoogleMap.setOnMarkerDragListener(new MyOnMarkerDragListener());
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        //TODO Dodaj do settingsActivity zmianę typu mapy
        switch (Integer.parseInt(Preferences.getString(getString(R.string.pref_settings_map_type), "0"))){
            case 0:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_rout:
                searchRoute();
                break;
            case R.id.clear_map:
                if (mPolyline != null) {
                    mPolyline.remove();
                    mPolyline = null;
                }
                mGoogleMap.clear();
                initMap();
                mMarkers.clear();
                mMarkersShow.clear();
                break;
            case R.id.settings_route:
                startActivity(new Intent(getApplicationContext(), SettingsRouteActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchRoute() {
        if (mRoutingConnector == null) {
            mRoutingConnector = new RoutingConnector(this, this);
        }
        mRoutingConnector.startRouting(createRoutPoint());
    }

    private List<GeoPoint> createRoutPoint() {
        List<GeoPoint> geoPointList = new ArrayList<GeoPoint>();
        for (Marker marker : mMarkers) {
            geoPointList.add(new GeoPoint(marker.getPosition().latitude, marker.getPosition().longitude));
        }
        return geoPointList;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void googleDirectionsResponse(GoogleDirectionsResponse rout) {
        if (rout != null) {
            if (rout.getStatus().equalsIgnoreCase("OK")) {
                //TODO usuń starą trasę
                if (mPolyline != null) {
                    mPolyline.remove();
                    mPolyline = null;
                }

                //TODO odrysuj trase wykorzystaj obiekt PolylineOptions
                PolylineOptions polylineOptions = new PolylineOptions();
                for (Route route : rout.getRoutes()) {
                    for (int i = 0; i < route.getLegs().size(); i++) {
                        Leg leg = route.getLegs().get(i);
                        for (Step step : leg.getSteps()) {
                            polylineOptions.add(new LatLng(step.getStartLocation().getLat(), step.getStartLocation().getLon()));
                            LatLng latLng = new LatLng(step.getEndLocation().getLat(), step.getEndLocation().getLon());
                            polylineOptions.add(latLng);
                        }
                    }
                }
                mPolyline = mGoogleMap.addPolyline(polylineOptions);

                //TODO ustaw mapę tak aby było widać całą trasę z niewielkim marginesem po bokach
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                GeoPoint northGeoPoint = rout.getRoutes().get(0).getBounds().getNortheast();
                GeoPoint southGeoPoint = rout.getRoutes().get(0).getBounds().getSouthwest();
                builder.include(new LatLng(northGeoPoint.getLat(), northGeoPoint.getLon()));
                builder.include(new LatLng(southGeoPoint.getLat(), southGeoPoint.getLon()));
                LatLngBounds bounds = builder.build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, getResources().getDimensionPixelSize(R.dimen.map_showing_margin)));
            } else {
                Toast.makeText(getApplicationContext(), String.format(getString(R.string.route_error), rout.getStatus()), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), String.format(getString(R.string.route_error), getString(R.string.connections_problem)), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void googleDirectionsError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }
}