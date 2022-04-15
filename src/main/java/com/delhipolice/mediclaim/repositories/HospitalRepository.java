package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h FROM Hospital h WHERE lower(h.hospitalName) LIKE %:name%")
    List<Hospital> findByNameContaining(@Param("name")  String name);
}
