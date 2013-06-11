package com.mobica.workshop.googledirections;

import com.google.gson.annotations.SerializedName;

public class Bounds {

	@SerializedName("northeast")
	private GeoPoint northeast;
	
	@SerializedName("southwest")
	private GeoPoint southwest;

	public GeoPoint getNortheast() {
		return northeast;
	}

	public void setNortheast(GeoPoint northeast) {
		this.northeast = northeast;
	}

	public GeoPoint getSouthwest() {
		return southwest;
	}

	public void setSouthwest(GeoPoint southwest) {
		this.southwest = southwest;
	}
}
