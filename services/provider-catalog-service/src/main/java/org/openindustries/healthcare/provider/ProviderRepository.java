package org.openindustries.healthcare.provider;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ProviderRepository {
    private final ConcurrentHashMap<UUID, Provider> store = new ConcurrentHashMap<>();

    public Provider save(Provider entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<Provider> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Provider> findAll() {
        return List.copyOf(store.values());
    }
}
