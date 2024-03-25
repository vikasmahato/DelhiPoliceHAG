package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Tenant;
import com.delhipolice.mediclaim.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService{

    @Autowired
    TenantRepository tenantRepository;

    @Override
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant find(Long id) {
        Tenant tenant =  tenantRepository.findById(id).get();
        tenant.getUsers();
        return tenant;
    }
}
