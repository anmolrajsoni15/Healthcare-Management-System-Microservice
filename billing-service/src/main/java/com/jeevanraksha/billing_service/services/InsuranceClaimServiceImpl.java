package com.jeevanraksha.billing_service.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeevanraksha.billing_service.entity.InsuranceClaim;
import com.jeevanraksha.billing_service.repository.InsuranceClaimRepository;

@Service
public class InsuranceClaimServiceImpl implements InsuranceClaimService {

    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;

    @Override
    public List<InsuranceClaim> getAllClaims() {
        return insuranceClaimRepository.findAll();
    }

    @Override
    public Optional<InsuranceClaim> getClaimById(UUID claimId) {
        return insuranceClaimRepository.findById(claimId);
    }

    @Override
    public InsuranceClaim createClaim(InsuranceClaim claim) {
        claim.setClaimDate(LocalDate.now());
        claim.setStatus("PENDING");
        return insuranceClaimRepository.save(claim);
    }

    @Override
    public Optional<InsuranceClaim> updateClaim(UUID claimId, InsuranceClaim updatedClaim) {
        return insuranceClaimRepository.findById(claimId)
            .map(existingClaim -> {
                existingClaim.setInsuranceProvider(updatedClaim.getInsuranceProvider());
                existingClaim.setClaimAmount(updatedClaim.getClaimAmount());
                existingClaim.setStatus(updatedClaim.getStatus());
                return insuranceClaimRepository.save(existingClaim);
            });
    }

    @Override
    public void deleteClaim(UUID claimId) {
        insuranceClaimRepository.deleteById(claimId);
    }
}

