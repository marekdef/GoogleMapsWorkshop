package com.mobica.workshop.googledirections;

import com.google.gson.annotations.SerializedName;

public class GeoPoint {

	@SerializedName("lat")
	private double lat;
	
	@SerializedName("lng")
	private double lon;

    public GeoPoint(){

    }

    public GeoPoint(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
}
