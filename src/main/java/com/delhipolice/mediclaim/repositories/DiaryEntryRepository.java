package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, UUID> {
}
