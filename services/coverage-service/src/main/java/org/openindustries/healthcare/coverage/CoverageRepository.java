package org.openindustries.healthcare.coverage;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class CoverageRepository {
    private final ConcurrentHashMap<UUID, CoverageAccount> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<ClaimPayment> records = new CopyOnWriteArrayList<>();

    public CoverageAccount saveAccount(CoverageAccount account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<CoverageAccount> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public ClaimPayment saveRecord(ClaimPayment record) {
        records.add(record);
        return record;
    }

    public List<ClaimPayment> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
