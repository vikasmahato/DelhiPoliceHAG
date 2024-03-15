package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.IDiaryEntry;
import com.delhipolice.mediclaim.model.ReferralDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReferralDiaryEntryRepository extends JpaRepository<ReferralDiaryEntry, UUID> {

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.isDeleted = false")
    List<IDiaryEntry> findAllEntries();

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.isDeleted = false")
    List<ReferralDiaryEntry> findAll();

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.id = :id AND d.isDeleted = false")
    Optional<ReferralDiaryEntry> find(UUID id);
}
