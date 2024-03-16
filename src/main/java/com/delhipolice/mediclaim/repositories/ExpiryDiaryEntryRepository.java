package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.ExpiryDiaryEntry;
import com.delhipolice.mediclaim.model.IDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpiryDiaryEntryRepository extends JpaRepository<ExpiryDiaryEntry, UUID> {

    @Query("SELECT d FROM ExpiryDiaryEntry d WHERE d.deletedAt is null and d.claimType = :claimType")
    List<IDiaryEntry> findAll(@Param("claimType")  ClaimType claimTypes);

    @Query("SELECT d FROM ExpiryDiaryEntry d WHERE d.applicant.id in :applicantId and d.deletedAt is null")
    List<ExpiryDiaryEntry> findAll(@Param("applicantId") Long applicantId);

    @Query("SELECT d FROM ExpiryDiaryEntry d where d.deletedAt is null")
    List<ExpiryDiaryEntry> findAll();

    @Query("SELECT d FROM ExpiryDiaryEntry d where d.id = :id and d.deletedAt is null")
    List<DiaryEntry> find(UUID id);

}
