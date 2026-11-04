package org.openindustries.healthcare.adjudication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AdjudicationRuleService {

    @Inject
    AdjudicationRuleRepository repository;

    public AdjudicationRule create(CreateAdjudicationRuleRequest request) {
        return repository.save(AdjudicationRule.create(request.name(), request.claimType(),
                request.maxAmountMinor(), request.maxProcessingDays(), request.priority()));
    }

    public AdjudicationRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "AdjudicationRule not found: " + id));
    }

    public List<AdjudicationRule> list() {
        return repository.findAll();
    }

    public AdjudicationRule activate(UUID id) {
        AdjudicationRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.claimType());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.claimType());
        }
        for (AdjudicationRule rule : candidates) {
            if (rule.maxProcessingDays <= request.requiredMaxProcessingDays()
                    && rule.maxAmountMinor >= request.requiredMaxAmountMinor()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.claimType() + " but none meets the requirement");
    }
}
