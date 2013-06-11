package com.mobica.workshop.cluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;

import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.mobica.workshop.MapsActivity;
import com.mobica.workshop.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by blsu on 04.06.13.
 */
public class ClusteringMarkers {

    public static final int CLUSTER = 1;
    public static final int MARKER = 0;
    private final Context mContext;

    public ClusteringMarkers(Context context) {
        mContext = context;
    }

    public void checkMarkers(Projection projection, ArrayList<Marker> markers) {
        HashMap<Marker, Point> points = new HashMap<Marker, Point>();
        for (Marker marker : markers) {
            points.put(marker, projection.toScreenLocation(marker.getPosition()));
            marker.setVisible(false);
        }

        CheckMarkersTask checkMarkersTask = new CheckMarkersTask();
        //noinspection unchecked
        checkMarkersTask.execute(points);
    }

    private class CheckMarkersTask extends AsyncTask<HashMap<Marker, Point>, Void, HashMap<Point, ArrayList<Marker>>> {


        private double findDistance(float x1, float y1, float x2, float y2) {
            return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
        }

        @Override
        protected HashMap<Point, ArrayList<Marker>> doInBackground(HashMap<Marker, Point>... params) {
            HashMap<Point, ArrayList<Marker>> clusters = new HashMap<Point, ArrayList<Marker>>();
            HashMap<Marker, Point> points = params[0];
            boolean wasClustered;
            for (Marker marker : points.keySet()) {
                Point point = points.get(marker);
                wasClustered = false;
                for (Point existingPoint : clusters.keySet()) {
                    if (findDistance(point.x, point.y, existingPoint.x, existingPoint.y) < 50) {
                        wasClustered = true;
                        clusters.get(existingPoint).add(marker);
                        break;
                    }
                }
                if (!wasClustered) {
                    ArrayList<Marker> markersForPoint = new ArrayList<Marker>();
                    markersForPoint.add(marker);
                    clusters.put(point, markersForPoint);
                }
            }
            return clusters;
        }

        @Override
        protected void onPostExecute(HashMap<Point, ArrayList<Marker>> clusters) {
            MapsActivity.mMarkersShow.clear();
            for (Point point : clusters.keySet()) {
                ArrayList<Marker> markersForPoint = clusters.get(point);
                Marker mainMarker = markersForPoint.get(0);
                mainMarker.setVisible(true);
                //TODO W klasie ClusteringMarkers dodaj inną ikonę dla Klastra. Ustaw klaster jako nie draggable
                mainMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                mainMarker.setDraggable(true);
                MapsActivity.mMarkersShow.put(mainMarker.getId(), MARKER);
                /*if (markersForPoint.size() > 1) {
                    mainMarker.setIcon(drawClusterIcon(Integer.toString(markersForPoint.size())));
                    mainMarker.setDraggable(false);
                    MapsActivity.mMarkersShow.put(mainMarker.getId(), CLUSTER);
                } else {
                    mainMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    mainMarker.setDraggable(true);
                    MapsActivity.mMarkersShow.put(mainMarker.getId(), MARKER);
                }*/
            }
        }

        public BitmapDescriptor drawClusterIcon(String numberOfMarkerContain) {
            Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cluster_marker);
            b = drawOwnBitmap(numberOfMarkerContain, b, getPaint());
            return BitmapDescriptorFactory.fromBitmap(b);
        }

        private Paint getPaint() {
            Paint p = new Paint();
            p.setTextSize(mContext.getResources().getDimension(R.dimen.cluster_text_size));
            p.setFakeBoldText(true);
            p.setTextAlign(Paint.Align.CENTER);
            p.setColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
            return p;
        }

        private Bitmap drawOwnBitmap(String text, Bitmap bmp, Paint paint) {
            //TODO Dla chętnych: Dodaj ile punktów zawiera kluster
            /*bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
            Canvas c = new Canvas(bmp);
            c.drawText(text, bmp.getWidth() / 2, 2 * bmp.getHeight() / 3, paint);*/
            return bmp;
        }
    }
}
