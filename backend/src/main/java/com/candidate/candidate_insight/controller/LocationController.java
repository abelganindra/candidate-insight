package com.candidate.candidate_insight.controller;

import com.candidate.candidate_insight.model.Location;
import com.candidate.candidate_insight.service.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> getAll() {
        return service.getAllLocations();
    }

    @GetMapping("/{code}")
    public Location getByCode(@PathVariable String code) {
        return service.getLocationByCode(code);
    }

    @PostMapping
    public String create(@RequestBody Location location) {
        service.createLocation(location);
        return "Location added successfully";
    }

    @PutMapping("/{code}")
    public String update(@PathVariable String code, @RequestBody Location location) {
        location.setLocationcode(code);
        service.updateLocation(location);
        return "Location updated successfully";
    }

    @DeleteMapping("/{code}")
    public String delete(@PathVariable String code) {
        service.deleteLocation(code);
        return "Location deleted successfully";
    }
}
