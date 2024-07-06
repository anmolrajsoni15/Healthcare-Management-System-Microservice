package com.jeevanraksha.billing_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeevanraksha.billing_service.entity.InsuranceClaim;

public interface InsuranceClaimService {
  List<InsuranceClaim> getAllClaims();
    Optional<InsuranceClaim> getClaimById(UUID claimId);
    InsuranceClaim createClaim(InsuranceClaim claim);
    Optional<InsuranceClaim> updateClaim(UUID claimId, InsuranceClaim claim);
    void deleteClaim(UUID claimId);
}
