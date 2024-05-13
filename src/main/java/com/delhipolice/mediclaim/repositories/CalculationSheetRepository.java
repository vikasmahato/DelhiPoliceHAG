package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.IDiaryEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationSheetRepository extends JpaRepository<CalculationSheetEntry, Long> {

}
