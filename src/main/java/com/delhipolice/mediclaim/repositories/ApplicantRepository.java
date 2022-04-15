package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    @Query("SELECT a FROM Applicant a WHERE lower(a.pisNumber) LIKE :pisNumber%")
    List<Applicant> findAllByPISNumber(@Param("pisNumber")  String pisNumber);

    @Query("SELECT a FROM Applicant a WHERE lower(a.pisNumber) = :pisNumber")
    Applicant findByPisNumber(@Param("pisNumber")  String pisNumber);
}
