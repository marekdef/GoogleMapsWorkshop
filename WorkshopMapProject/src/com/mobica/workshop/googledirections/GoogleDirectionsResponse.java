package com.mobica.workshop.googledirections;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GoogleDirectionsResponse {

	@SerializedName("routes")
	private List<Route> routes;
	
	@SerializedName("status")
	private String status;

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
