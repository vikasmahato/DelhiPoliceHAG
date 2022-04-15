package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.services.ApplicantService;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicantRestController {

    private ApplicantService applicantService;

    @Autowired
    public ApplicantRestController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping("/searchApplicant")
    public List<ApplicantVO> getApplicants(@RequestParam(value = "q", required = false) String query) {

        return applicantService.findByPISNumber(query);

    }





}