package com.delhipolice.mediclaim.repositories;

import com.delhipolice.mediclaim.model.HealthCheckupApplicants;
import com.delhipolice.mediclaim.model.ReferralApplicants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ReferralApplicantRepository extends JpaRepository<ReferralApplicants, Long> {

}
