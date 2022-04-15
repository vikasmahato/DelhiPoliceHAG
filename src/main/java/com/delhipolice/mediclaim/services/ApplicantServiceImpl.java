package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Applicant;
import com.delhipolice.mediclaim.repositories.ApplicantRepository;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicantServiceImpl implements ApplicantService{

    @Autowired
    private ApplicantRepository applicantRepository;


    @Override
    public Applicant find(Long id) {
        return applicantRepository.findById(id).orElse(null);
    }

    @Override
    public Applicant findByPisNumber(String pisNumber) {
        return applicantRepository.findByPisNumber(pisNumber);
    }

    @Override
    public List<ApplicantVO> findByPISNumber(String pisNumber) {

        if(StringUtils.isEmpty(pisNumber))
            return new ArrayList<>();

        List<Applicant> hospitals = applicantRepository.findAllByPISNumber(pisNumber.toLowerCase(Locale.ROOT));
        return hospitals.stream().map(ApplicantVO::new).limit(10).collect(Collectors.toList());
    }

    @Override
    public Applicant save(ApplicantVO applicantVO) {
        Applicant applicant = new Applicant(applicantVO);
        return applicantRepository.save(applicant);
    }

    @Override
    public Applicant update(Applicant applicant) {
        return null;
    }

}
