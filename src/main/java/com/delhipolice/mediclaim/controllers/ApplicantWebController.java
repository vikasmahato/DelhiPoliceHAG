package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.Applicant;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.services.ApplicantService;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import com.delhipolice.mediclaim.vo.HospitalVO;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ApplicantWebController {

    private final ApplicantService applicantService;
    DiaryEntryService diaryEntryService;

    @Autowired
    public ApplicantWebController(ApplicantService applicantService, DiaryEntryService diaryEntryService) {
        this.applicantService = applicantService;
        this.diaryEntryService = diaryEntryService;
    }

    @GetMapping("/applicants")
    public String applicants(Model model) {
        List<ApplicantVO> applicants =  applicantService.findAll();
        model.addAttribute("applicants", applicants);
        return "applicants_home";
    }


    @GetMapping("/applicantDetails/{id}")
    public String applicantDetails(Model model, @PathVariable Long id) {
        Applicant applicant =  applicantService.find(id);

        List<DiaryEntryVO> diaryEntries = diaryEntryService.findAllByApplicant(applicant);

        model.addAttribute("applicant", applicant);
        model.addAttribute("diaryEntries", diaryEntries);


        return "applicant_details";
    }




}