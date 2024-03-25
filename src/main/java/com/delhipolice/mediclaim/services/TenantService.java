package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Tenant;

import java.util.List;

public interface TenantService {
    List<Tenant> findAll();

    Tenant save(Tenant tenant);

    Tenant find(Long id);
}
