package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HospitalRestController {

    private HospitalService hospitalService;
    private MedicalRatesService medicalRatesService;
    private DiaryEntryService diaryEntryService;

    @Autowired
    public HospitalRestController(HospitalService hospitalService, MedicalRatesService medicalRatesService, DiaryEntryService diaryEntryService) {
        this.hospitalService = hospitalService;
        this.medicalRatesService = medicalRatesService;
        this.diaryEntryService = diaryEntryService;
    }

    @GetMapping("/searchHospital")
    public List<HospitalVO> stateItems(@RequestParam(value = "q", required = false) String query) {
        return hospitalService.findByNameContaining(query);
    }

    @GetMapping("/searchMedicalRate")
    public List<MedicalRateVO> searchMedicalRate(@RequestParam(value = "searchTerm") String searchTerm,@RequestParam(value = "type") String type, @RequestParam(value = "hospitalType") String hospitalType) {
        MedicalRateSearchCriteriaVO medicalRateSearchCriteriaVO = new MedicalRateSearchCriteriaVO(searchTerm, type, hospitalType);
        return medicalRatesService.findByNameContaining(medicalRateSearchCriteriaVO);

    }
}