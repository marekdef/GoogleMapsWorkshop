package com.mobica.workshop.directionsUrl;

import android.content.Context;

import com.mobica.workshop.Preferences.Preferences;
import com.mobica.workshop.R;
import com.mobica.workshop.googledirections.GeoPoint;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

/**
 *
 * Created by blsu on 06.06.13.
 */
public class DirectionsUrl {

    private static String getSeparator() throws UnsupportedEncodingException {
        return URLEncoder.encode("|", "UTF-8");
    }

    public static String getUrl(Context context, List<GeoPoint> geoPointst) throws UnsupportedEncodingException {
        String googleDirectionsUrl = "http://maps.googleapis.com/maps/api/directions/json?&origin={0},{1}&destination={2},{3}{4}&sensor=false{5}{6}";
        return MessageFormat.format(googleDirectionsUrl, Double.toString(geoPointst.get(0).getLat()),
                Double.toString(geoPointst.get(0).getLon()),
                Double.toString(geoPointst.get(geoPointst.size() - 1).getLat()),
                Double.toString(geoPointst.get(geoPointst.size() - 1).getLon()),
                DirectionsUrl.getWaypoints(context, geoPointst),
                DirectionsUrl.getAvoid(context),
                DirectionsUrl.getTravelMode(context));
    }

    private static String getWaypoints(Context context, List<com.mobica.workshop.googledirections.GeoPoint> geoPointst) throws UnsupportedEncodingException {
        String urlParam = context.getString(R.string.empty_string);
        String latLngFormat = "{0}{1},{2}";
        if (geoPointst.size() > 2) {
            for (int i = 1; i < (geoPointst.size() - 1); i++) {
                String separator = context.getString(R.string.empty_string);
                if (i > 1) {
                    separator = DirectionsUrl.getSeparator();
                }
                urlParam += MessageFormat.format(latLngFormat, separator,
                        Double.toString(geoPointst.get(i).getLat()),
                        Double.toString(geoPointst.get(i).getLon()));
            }
            String googleDirectionsWayPointsParam = "&waypoints={0}";
            urlParam = MessageFormat.format(googleDirectionsWayPointsParam, urlParam);
        }
        return urlParam;
    }

    private static String getAvoid(Context context) throws UnsupportedEncodingException {
        String urlParam = context.getString(R.string.empty_string);
        String paramTag = "&avoid=";
        if (Preferences.getBoolean(context.getString(R.string.pref_settings_avoid_highways))) {
            urlParam += paramTag + "highways";
        }
        if (Preferences.getBoolean(context.getString(R.string.pref_settings_avoid_tolls))) {
            if (urlParam.length() > 0) {
                urlParam += DirectionsUrl.getSeparator() + "tolls";
            } else {
                urlParam += paramTag + "tolls";
            }
        }
        return urlParam;
    }

    private static String getTravelMode(Context context) {
        String urlParam = context.getString(R.string.empty_string);
        String paramTag = "&mode=";
        if (!Preferences.getString(context.getString(R.string.pref_settings_travel_modes), context.getString(R.string.none)).
                equalsIgnoreCase(context.getString(R.string.none))) {
            urlParam = paramTag + Preferences.getString(context.getString(R.string.pref_settings_travel_modes), context.getString(R.string.none));
        }
        return urlParam;
    }
}
