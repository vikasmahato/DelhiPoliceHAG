package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.services.ApplicantService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import com.delhipolice.mediclaim.vo.HospitalVO;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ApplicantWebController {

    private ApplicantService applicantService;

    @Autowired
    public ApplicantWebController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping("/applicants")
    public String applicants(Model model) {
        List<ApplicantVO> applicants =  applicantService.findAll();
        model.addAttribute("applicants", applicants);
        return "applicants_home";
    }




}