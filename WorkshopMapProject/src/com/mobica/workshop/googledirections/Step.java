package com.mobica.workshop.googledirections;

import com.google.gson.annotations.SerializedName;

public class Step {

	@SerializedName("distance")
	private Direction distance;
	
	@SerializedName("duration")
	private Direction duration;
	
	@SerializedName("end_location")
	private GeoPoint endLocation;
	
	@SerializedName("html_instructions")
	private String htmlInstrukctions;
	
	@SerializedName("start_location")
	private GeoPoint startLocation;
	
	@SerializedName("travel_mode")
	private String travelMode;

    @SerializedName("polyline")
    private OverviewPolyline overviewPolyline;

	public Direction getDistance() {
		return distance;
	}

	public void setDistance(Direction distance) {
		this.distance = distance;
	}

	public Direction getDuration() {
		return duration;
	}

	public void setDuration(Direction duration) {
		this.duration = duration;
	}

	public GeoPoint getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(GeoPoint endLocation) {
		this.endLocation = endLocation;
	}

	public String getHtmlInstrukctions() {
		return htmlInstrukctions;
	}

	public void setHtmlInstrukctions(String htmlInstrukctions) {
		this.htmlInstrukctions = htmlInstrukctions;
	}

	public GeoPoint getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(GeoPoint startLocation) {
		this.startLocation = startLocation;
	}

	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }
}
