package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.constants.HospitalType;
import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
    public List<MedicalRateVO> searchMedicalRate(@RequestParam(value = "searchTerm") String searchTerm, @RequestParam(value = "hospitalType") String hospitalType) {
        return medicalRatesService.findByNameContaining(searchTerm, HospitalType.valueOf(hospitalType));
    }

    @PostMapping("/persisthospital")
    public @ResponseBody HospitalVO updateHospital(HospitalVO hospitalVO) {
        if(hospitalVO.getId() != null) {
            return hospitalService.update(hospitalVO);
        } else {
            return new HospitalVO(hospitalService.save(hospitalVO));
        }
    }

    @PostMapping("/persistmedicalrates")
    public @ResponseBody MedicalRateVO persistMedicalRates(MedicalRateVO medicalRateVO) {

        if(medicalRateVO.getId() != null) {
            return medicalRatesService.update(medicalRateVO);
        } else {
            MedicalRates medicalRate = new MedicalRates(medicalRateVO);
            return new MedicalRateVO(medicalRatesService.save(medicalRate));
        }
    }
}