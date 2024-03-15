package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.HealthCheckupDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HealthCheckupDiaryEntryRepository extends JpaRepository<HealthCheckupDiaryEntry, UUID> {

    @Query("SELECT d FROM HealthCheckupDiaryEntry d WHERE d.isDeleted = false")
    List<HealthCheckupDiaryEntry> findAll();

    @Query("SELECT d FROM HealthCheckupDiaryEntry d WHERE d.id = :id AND d.isDeleted = false")
    Optional<HealthCheckupDiaryEntry> find(UUID id);

}
