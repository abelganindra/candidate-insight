package com.candidate.candidate_insight.service;

import com.candidate.candidate_insight.model.Tier;
import java.util.List;

public interface TierService {
    List<Tier> getAllTiers();
    Tier getTierByCode(Integer code);
    int createTier(Tier tier);
    int updateTier(Tier tier);
    int deleteTier(Integer code);
}
