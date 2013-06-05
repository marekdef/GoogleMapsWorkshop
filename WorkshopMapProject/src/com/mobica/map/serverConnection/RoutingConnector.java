package com.mobica.map.serverConnection;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mobica.map.R;
import com.mobica.map.googledirections.GoogleDirectionsResponse;
import com.mobica.map.uiInterface.GoogleMapsRoutingInterface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class RoutingConnector extends ConnectorHttp {

    private GoogleDirectionsResponse googleDirectionsResponse;
    private final AlertDialog sentDialog;
    private final GoogleMapsRoutingInterface mGoogleMapsRoutingInterface;

    /**
     * method create object and show alert and start thread. Response is send by interface
     *
     * @param context -application
     */
    public RoutingConnector(Context context, GoogleMapsRoutingInterface googleDirectionsInterface) {
        this.mGoogleMapsRoutingInterface = googleDirectionsInterface;
        sentDialog = new AlertDialog.Builder(context).create();
        sentDialog.setTitle(context.getResources().getString(R.string.downloading));
    }

    /**
     * @param geoPoints - list of points
     */
    public void startRouting(List<com.mobica.map.googledirections.GeoPoint> geoPoints) {
        sentDialog.show();
        new RoutingThread(geoPoints).start();
    }

    private final Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    sentDialog.dismiss();
                    mGoogleMapsRoutingInterface.getGoogleDirectionsResponse(googleDirectionsResponse);
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private class RoutingThread extends Thread {
        private final List<com.mobica.map.googledirections.GeoPoint> mGeoPointList;

        public RoutingThread(List<com.mobica.map.googledirections.GeoPoint> geoPoints) {
            this.mGeoPointList = geoPoints;
        }

        @Override
        public void run() {
            String TAG = "RountingConnector";
            try {
                String googleResponseString = sendHttpGet(getUrl(mGeoPointList));
                googleDirectionsResponse = new GoogleDirectionsResponse();
                googleDirectionsResponse = (new Gson()).fromJson(googleResponseString, GoogleDirectionsResponse.class);
            } catch (JsonSyntaxException ex) {
                Log.e(TAG, "parse response google ", ex);
            } catch (Exception e) {
                Log.e(TAG, "parse response google ", e);
            }
            super.run();
            uiHandler.sendEmptyMessage(2);

        }
    }

    private String getUrl(List<com.mobica.map.googledirections.GeoPoint> geoPointst) throws UnsupportedEncodingException {
        String urlString;
        urlString = "http://maps.googleapis.com/maps/api/directions/json?";
        urlString += "&origin=";
        urlString += Double.toString(geoPointst.get(0).getLat());
        urlString += ",";
        urlString += Double.toString(geoPointst.get(0).getLon());
        urlString += "&destination=";// to
        urlString += Double.toString(geoPointst.get(geoPointst.size() - 1).getLat());
        urlString += ",";
        urlString += Double.toString(geoPointst.get(geoPointst.size() - 1).getLon());
        if(geoPointst.size() > 2){
            urlString += "&waypoints=";
            for(int i = 1; i < (geoPointst.size()-1); i++){
                urlString += URLEncoder.encode("|", "UTF-8");
                urlString += Double.toString(geoPointst.get(i).getLat());
                urlString += ",";
                urlString += Double.toString(geoPointst.get(i).getLon());
            }
        }
        urlString += "&sensor=false";
        Log.d("routingConnector", urlString);
        return urlString;
    }
}
