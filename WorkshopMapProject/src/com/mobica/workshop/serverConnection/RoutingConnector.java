package com.mobica.workshop.serverConnection;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mobica.workshop.R;
import com.mobica.workshop.directionsUrl.DirectionsUrl;
import com.mobica.workshop.googledirections.GoogleDirectionsResponse;
import com.mobica.workshop.uiInterface.GoogleMapsRoutingInterface;

import java.io.IOException;
import java.util.List;


public class RoutingConnector extends ConnectorHttp {

    private final int OK = 1;
    private final int ERROR = 0;
    private GoogleDirectionsResponse mGoogleDirectionsResponse;
    private final AlertDialog mSentDialog;
    private final GoogleMapsRoutingInterface mGoogleMapsRoutingInterface;
    private final Context mContext;

    /**
     * method create object and show alert and start thread. Response is send by interface
     *
     * @param context -application
     */
    public RoutingConnector(Context context, GoogleMapsRoutingInterface googleDirectionsInterface) {
        this.mGoogleMapsRoutingInterface = googleDirectionsInterface;
        this.mContext = context;
        mSentDialog = new AlertDialog.Builder(context).create();
        mSentDialog.setTitle(context.getResources().getString(R.string.downloading));
    }

    /**
     * @param geoPoints - list of points
     */
    public void startRouting(List<com.mobica.workshop.googledirections.GeoPoint> geoPoints) {
        mSentDialog.show();
        new RoutingThread(geoPoints).start();
    }

    @SuppressWarnings("OverlyComplexAnonymousInnerClass")
    private final Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
                    mSentDialog.dismiss();
                    mGoogleMapsRoutingInterface.googleDirectionsResponse(mGoogleDirectionsResponse);
                    break;
                case ERROR:
                    mSentDialog.dismiss();
                    mGoogleMapsRoutingInterface.googleDirectionsError((String)msg.obj);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private class RoutingThread extends Thread {
        private final List<com.mobica.workshop.googledirections.GeoPoint> mGeoPointList;

        public RoutingThread(List<com.mobica.workshop.googledirections.GeoPoint> geoPoints) {
            this.mGeoPointList = geoPoints;
        }

        @Override
        public void run() {
            try {
                String googleResponseString = sendHttpGet(DirectionsUrl.getUrl(mContext, mGeoPointList));
                mGoogleDirectionsResponse = new GoogleDirectionsResponse();
                mGoogleDirectionsResponse = (new Gson()).fromJson(googleResponseString, GoogleDirectionsResponse.class);
                uiHandler.sendEmptyMessage(OK);
            } catch (JsonSyntaxException ex) {
                Message msg = new Message();
                msg.what = ERROR;
                msg.obj = "Wrong data from server";
                uiHandler.sendMessage(msg);
            } catch (IOException e) {
                Message msg = new Message();
                msg.what = ERROR;
                msg.obj = "Problem with connection";
                uiHandler.sendMessage(msg);
                uiHandler.sendEmptyMessage(ERROR);
            }catch (Exception e) {
                Message msg = new Message();
                msg.what = ERROR;
                msg.obj = "Problem with connection";
                uiHandler.sendMessage(msg);
                uiHandler.sendEmptyMessage(ERROR);
            }
            super.run();

        }
    }
}
