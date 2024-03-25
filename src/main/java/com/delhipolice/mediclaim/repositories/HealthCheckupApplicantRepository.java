package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.HealthCheckupApplicants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HealthCheckupApplicantRepository extends JpaRepository<HealthCheckupApplicants, Long> {

}
