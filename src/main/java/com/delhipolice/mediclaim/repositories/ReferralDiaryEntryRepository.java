package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.IDiaryEntry;
import com.delhipolice.mediclaim.model.ReferralDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReferralDiaryEntryRepository extends JpaRepository<ReferralDiaryEntry, UUID> {

    @Query("SELECT d FROM ReferralDiaryEntry d")
    List<IDiaryEntry> findAllEntries();

}
