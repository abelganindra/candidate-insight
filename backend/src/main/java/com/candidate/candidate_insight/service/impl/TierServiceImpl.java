package com.candidate.candidate_insight.service.impl;

import com.candidate.candidate_insight.model.Tier;
import com.candidate.candidate_insight.repository.TierRepository;
import com.candidate.candidate_insight.service.TierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierServiceImpl implements TierService {

    private final TierRepository repository;

    public TierServiceImpl(TierRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tier> getAllTiers() {
        return repository.findAll();
    }

    @Override
    public Tier getTierByCode(Integer code) {
        return repository.findByCode(code);
    }

    @Override
    public int createTier(Tier tier) {
        return repository.save(tier);
    }

    @Override
    public int updateTier(Tier tier) {
        return repository.update(tier);
    }

    @Override
    public int deleteTier(Integer code) {
        return repository.delete(code);
    }
}
