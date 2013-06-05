package com.mobica.map.googledirections;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Leg {

	@SerializedName("distance")
	private Direction distance;
	
	@SerializedName("duration")
	private Direction duration;
	
	@SerializedName("end_address")
	private String endAddress;
	
	@SerializedName("end_location")
	private GeoPoint endLocation;
	
	@SerializedName("start_address")
	private String startAddress;
	
	@SerializedName("start_location")
	private GeoPoint startLocation;
	
	@SerializedName("steps")
	private List<Step> steps;
	
	//@SerializedName("via_waypoint")
	private List<String> viaWaypoint;

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

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public GeoPoint getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(GeoPoint endLocation) {
		this.endLocation = endLocation;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public GeoPoint getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(GeoPoint startLocation) {
		this.startLocation = startLocation;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<String> getViaWaypoint() {
		return viaWaypoint;
	}

	public void setViaWaypoint(List<String> viaWaypoint) {
		this.viaWaypoint = viaWaypoint;
	}
}
