package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.Tenant;
import com.delhipolice.mediclaim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
