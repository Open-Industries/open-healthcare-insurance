package org.openindustries.healthcare.provider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProviderService {

    @Inject
    ProviderRepository repository;

    public Provider create(CreateProviderRequest request) {
        return repository.save(Provider.create(request.name(), request.providerType(), request.regionCode()));
    }

    public Provider get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Provider not found: " + id));
    }

    public List<Provider> list() {
        return repository.findAll();
    }

    public Provider suspend(UUID id) {
        Provider entity = get(id);
        if (!"CONTRACTED".equals(entity.status)) {
            throw new DomainException(409, "Only a CONTRACTED provider can transition via suspend");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }
}
