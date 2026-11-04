package org.openindustries.healthcare.provider;

import java.util.UUID;

/** Provider aggregate — Contracted provider network catalogue. */
public class Provider {
    public UUID id;
        public String name;
        public String providerType;
        public String regionCode;
    public String status;

    public static Provider create(String name, String providerType, String regionCode) {
        Provider e = new Provider();
        e.id = UUID.randomUUID();
        e.name = name;
        e.providerType = providerType;
        e.regionCode = regionCode;
        e.status = "CONTRACTED";
        return e;
    }
}
