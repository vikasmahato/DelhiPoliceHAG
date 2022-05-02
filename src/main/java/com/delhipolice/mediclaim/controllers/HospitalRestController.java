package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public List<MedicalRateVO> searchMedicalRate(@RequestParam(value = "searchTerm") String searchTerm,@RequestParam(value = "diaryId") UUID diaryId) {
        return medicalRatesService.findByNameContaining(searchTerm, diaryId);
    }

    @PostMapping("/updatehospital")
    public @ResponseBody HospitalVO updateHospital(HospitalVO hospitalVO) {
        return hospitalService.update(hospitalVO);
    }

    @PostMapping("/updatemedicalrates")
    public @ResponseBody MedicalRateVO updateMedicalRates(MedicalRateVO medicalRateVO) {
        return medicalRatesService.update(medicalRateVO);
    }
}