package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.Applicant;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.IDiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, UUID> {

    @Query("SELECT d FROM DiaryEntry d WHERE d.claimType in :claimTypes and d.diaryType = :diaryType and d.deletedAt is null")
    List<IDiaryEntry> findAll(@Param("claimTypes")  List<ClaimType> claimTypes, @Param("diaryType") DiaryType diaryType);

    @Query("SELECT d FROM DiaryEntry d WHERE d.applicant.id in :applicantId and d.deletedAt is null")
    List<DiaryEntry> findAll(@Param("applicantId") Long applicantId);

    @Query("SELECT d FROM DiaryEntry d where d.deletedAt is null")
    List<DiaryEntry> findAll();

    @Query("SELECT d FROM DiaryEntry d where d.id = :id and d.deletedAt is null")
    List<DiaryEntry> find(UUID id);
}
