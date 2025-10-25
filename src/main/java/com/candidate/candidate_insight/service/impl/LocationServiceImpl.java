package com.candidate.candidate_insight.service.impl;

import com.candidate.candidate_insight.model.Location;
import com.candidate.candidate_insight.repository.LocationRepository;
import com.candidate.candidate_insight.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    public LocationServiceImpl(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    @Override
    public Location getLocationByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public int createLocation(Location location) {
        return repository.save(location);
    }

    @Override
    public int updateLocation(Location location) {
        return repository.update(location);
    }

    @Override
    public int deleteLocation(String code) {
        return repository.delete(code);
    }
}