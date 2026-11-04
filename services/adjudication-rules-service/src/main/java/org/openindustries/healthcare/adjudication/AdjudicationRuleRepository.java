package org.openindustries.healthcare.adjudication;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class AdjudicationRuleRepository {
    private final ConcurrentHashMap<UUID, AdjudicationRule> store = new ConcurrentHashMap<>();

    public AdjudicationRule save(AdjudicationRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<AdjudicationRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<AdjudicationRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<AdjudicationRule> activeFor(String claimType) {
        return store.values().stream()
                .filter(r -> r.claimType.equals(claimType) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
