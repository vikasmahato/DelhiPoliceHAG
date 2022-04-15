package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Applicant;
import com.delhipolice.mediclaim.vo.ApplicantVO;

import java.util.List;

public interface ApplicantService {

    Applicant find(Long id);

    Applicant findByPisNumber(String pisNumber);

    List<ApplicantVO> findByPISNumber(String pisNumber);

    Applicant save(ApplicantVO applicantVO);

    Applicant update(Applicant applicant);

}
