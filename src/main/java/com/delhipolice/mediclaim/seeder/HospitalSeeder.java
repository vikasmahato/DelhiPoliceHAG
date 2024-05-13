package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.repositories.CalculationSheetRepository;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.utils.CurrencyFormatUtil;
import com.delhipolice.mediclaim.vo.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HospitalSeeder {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    MedicalRatesService medicalRatesService;

    @Autowired
    DiaryEntryService diaryEntryService;

    @Autowired
    CalculationSheetRepository calculationSheetRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        seedHospitals();
        seedMedicalRates();

        CurrencyFormatUtil currencyFormatUtil = new CurrencyFormatUtil();
        List<CalculationSheetEntry> entries = calculationSheetRepository.findAll();

        for(CalculationSheetEntry e : entries) {
            e.setDisplayAmountGranted(currencyFormatUtil.formatCurrencyInr((e.getTotal().toString())) );
            calculationSheetRepository.save(e);
        }
    }

    private void seedHospitals() throws IOException {
        if(hospitalService.count() > 0) {
            log.info(hospitalService.count() + " hospitals already exist");
            return;
        }

        InputStream is = new ClassPathResource("data/cghs_hospital.json").getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        HospitalVO[] list = objectMapper.readValue(is, HospitalVO[].class);

        for(HospitalVO hospitalVO : list) {
            hospitalService.save(hospitalVO);
            log.info("Saved: " + hospitalVO.getName());

        }


    }

    private void seedMedicalRates() throws IOException {
        if(medicalRatesService.count() > 0) {
            log.info(medicalRatesService.count() + " medical rates already exist");
            return;
        }

        InputStream is = new ClassPathResource("data/medicalRates.json").getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> list = objectMapper.readValue(is, new TypeReference<List<Map<String, String>>>() {});

        for(Map<String, String> map : list) {
            try {
                medicalRatesService.save(
                        new MedicalRates(
                                new MedicalRateVO(
                                        Integer.parseInt(map.get("productCode")),
                                        map.get("productName"),
                                        Float.parseFloat(map.get("nonNabhNablRate")),
                                        Float.parseFloat(map.get("nabhNablRate")),
                                        map.get("state")
                                )
                        )
                );
            } catch (Exception e) {
                log.error("Error saving: " + map.toString());
            }

        }
    }
}
