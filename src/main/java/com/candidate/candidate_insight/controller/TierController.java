package com.candidate.candidate_insight.controller;

import com.candidate.candidate_insight.model.Tier;
import com.candidate.candidate_insight.service.TierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
public class TierController {

    private final TierService service;

    public TierController(TierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tier> getAll() {
        return service.getAllTiers();
    }

    @GetMapping("/{code}")
    public Tier getByCode(@PathVariable Integer code) {
        return service.getTierByCode(code);
    }

    @PostMapping
    public String create(@RequestBody Tier tier) {
        service.createTier(tier);
        return "Tier added successfully";
    }

    @PutMapping("/{code}")
    public String update(@PathVariable Integer code, @RequestBody Tier tier) {
        tier.setTiercode(code);
        service.updateTier(tier);
        return "Tier updated successfully";
    }

    @DeleteMapping("/{code}")
    public String delete(@PathVariable Integer code) {
        service.deleteTier(code);
        return "Tier deleted successfully";
    }
}
