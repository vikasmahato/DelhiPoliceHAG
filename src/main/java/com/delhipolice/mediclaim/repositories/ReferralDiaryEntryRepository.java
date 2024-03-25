package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.IDiaryEntry;
import com.delhipolice.mediclaim.model.ReferralDiaryEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReferralDiaryEntryRepository extends JpaRepository<ReferralDiaryEntry, Long> {

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.deletedAt is null")
    List<IDiaryEntry> findAllEntries();

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.deletedAt is null")
    List<ReferralDiaryEntry> findAll();

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.deletedAt is null")
    Page<ReferralDiaryEntry> findAll(Pageable pageable);

    @Query("SELECT d FROM ReferralDiaryEntry d WHERE d.id = :id AND d.deletedAt is null")
    Optional<ReferralDiaryEntry> find(Long id);
}
