package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.model.MedicalRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRatesRepository extends JpaRepository<MedicalRates, Long> {

    @Query("SELECT h FROM MedicalRates h WHERE lower(h.productName) LIKE %:name%")
    List<MedicalRates> findByNameContaining(@Param("name")  String name);
}
