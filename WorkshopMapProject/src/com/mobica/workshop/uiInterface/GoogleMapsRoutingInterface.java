package com.mobica.workshop.uiInterface;

import com.mobica.workshop.googledirections.GoogleDirectionsResponse;

public interface GoogleMapsRoutingInterface {

	void googleDirectionsResponse(GoogleDirectionsResponse rout);
    void googleDirectionsError(String error);
}
