package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.ExpiryDiaryEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpiryDiaryEntryRepository extends JpaRepository<ExpiryDiaryEntry, Long> {

    @Query("SELECT d FROM ExpiryDiaryEntry d WHERE d.deletedAt is null and d.claimType = :claimType")
    Page<ExpiryDiaryEntry> findAll(@Param("claimType")  ClaimType claimType, Pageable pageable);

    @Query("SELECT d FROM ExpiryDiaryEntry d WHERE d.deletedAt is null and d.claimType = :claimType")
    List<ExpiryDiaryEntry> findAll(@Param("claimType")  ClaimType claimType);

    @Query("SELECT d FROM ExpiryDiaryEntry d WHERE d.applicant.id in :applicantId and d.deletedAt is null")
    List<ExpiryDiaryEntry> findAll(@Param("applicantId") Long applicantId);

    @Query("SELECT d FROM ExpiryDiaryEntry d where d.deletedAt is null")
    List<ExpiryDiaryEntry> findAll();

    @Query("SELECT d FROM ExpiryDiaryEntry d where d.deletedAt is null")
    Page<ExpiryDiaryEntry> findAll(Pageable pageable);

    @Query("SELECT d FROM ExpiryDiaryEntry d where d.id = :id and d.deletedAt is null")
    List<DiaryEntry> find(Long id);

}
