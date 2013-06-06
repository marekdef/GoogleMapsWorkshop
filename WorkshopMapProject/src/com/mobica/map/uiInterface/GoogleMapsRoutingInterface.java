package com.mobica.map.uiInterface;

import com.mobica.map.googledirections.GoogleDirectionsResponse;

public interface GoogleMapsRoutingInterface {

	void googleDirectionsResponse(GoogleDirectionsResponse rout);
    void googleDirectionsError(String error);
}
