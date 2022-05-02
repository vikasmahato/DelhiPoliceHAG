package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.HospitalVO;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HospitalWebController {

    private MedicalRatesService medicalRatesService;
    private HospitalService hospitalService;

    @Autowired
    public HospitalWebController(MedicalRatesService medicalRatesService, HospitalService hospitalService) {
        this.medicalRatesService = medicalRatesService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("/medicalrates")
    public String medicalRatesHome(Model model) {
        List<MedicalRateVO> medicalRates =  medicalRatesService.findAll();
        model.addAttribute("medicalRates", medicalRates);
        return "medical_rates_home";
    }

    @GetMapping("/hospitals")
    public String hospitals(Model model) {
        List<HospitalVO> hospitals =  hospitalService.findAll();
        model.addAttribute("hospitals", hospitals);
        return "hospitals_home";
    }





}