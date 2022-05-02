package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, UUID> {

    @Query("SELECT d FROM DiaryEntry d WHERE d.claimType in :claimTypes")
    List<DiaryEntry> findAll(@Param("claimTypes")  List<ClaimType> claimTypes);

    @Query("SELECT d FROM DiaryEntry d")
    List<DiaryEntry> findAll();
}
