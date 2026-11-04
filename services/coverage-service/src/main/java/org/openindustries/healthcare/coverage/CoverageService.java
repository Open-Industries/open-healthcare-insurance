package org.openindustries.healthcare.coverage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CoverageService {

    @Inject
    CoverageRepository repository;

    public CoverageAccount open(OpenCoverageAccountRequest request) {
        return repository.saveAccount(CoverageAccount.open(request.memberId(), request.coverageLimitMinor()));
    }

    public CoverageAccount get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "CoverageAccount not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public ClaimPayment consume(UUID accountId, CreateClaimPaymentRequest request) {
        CoverageAccount account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "CoverageAccount is not ACTIVE");
        }
        if (request.amountMinor() > account.coverageLimitMinor) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.amountMinor()
                    + " exceeds available " + account.coverageLimitMinor);
        }
        account.coverageLimitMinor -= request.amountMinor();
        repository.saveAccount(account);
        return repository.saveRecord(ClaimPayment.of(accountId, request.claimType(), request.amountMinor(), account.coverageLimitMinor));
    }

    public List<ClaimPayment> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
