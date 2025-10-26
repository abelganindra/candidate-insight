package com.candidate.candidate_insight.service;

import com.candidate.candidate_insight.model.Location;
import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();
    Location getLocationByCode(String code);
    int createLocation(Location location);
    int updateLocation(Location location);
    int deleteLocation(String code);
}
