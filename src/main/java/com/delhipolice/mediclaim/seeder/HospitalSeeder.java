package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.constants.CaseType;
import com.delhipolice.mediclaim.constants.Designation;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.constants.TreatmentBy;
import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.ClaimDetails;
import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import com.delhipolice.mediclaim.vo.HospitalVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HospitalSeeder {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    MedicalRatesService medicalRatesService;

    @Autowired
    DiaryEntryService diaryEntryService;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        seedHospitals();
        seedMedicalRates();
        seedDummyData();
    }

    private void seedHospitals() throws IOException {


        File file = new ClassPathResource("data/hospital.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        HospitalVO[] list = objectMapper.readValue(file, HospitalVO[].class);

        for(HospitalVO hospitalVO : list) {
            hospitalService.save(hospitalVO);
            log.info("Saved: " + hospitalVO.getName());

        }


    }

    private void seedMedicalRates() throws IOException {


        File file = new ClassPathResource("data/medicalRates.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        MedicalRates[] list = objectMapper.readValue(file, MedicalRates[].class);

        for(MedicalRates medicalRates : list) {
            medicalRatesService.save(medicalRates);
            log.info("Saved: " + medicalRates.getProductName());

        }


    }

    public void seedDummyData(){

        DiaryEntryVO diaryEntryVO = new DiaryEntryVO();

        ApplicantVO applicant = new ApplicantVO();
        applicant.setName("Vikas");
        applicant.setBeltNumber("1212");
        applicant.setPisNumber("9999");
        applicant.setRank(Designation.ACP);


        Hospital hospitalVO = new Hospital();
        hospitalVO.setId(2l);

        diaryEntryVO.setHospital(hospitalVO);
        diaryEntryVO.setApplicant(applicant);
        diaryEntryVO.setTreatmentTakenBy(TreatmentBy.SELF);

        diaryEntryVO.setDiaryNumber("111");
        diaryEntryVO.setDiaryDate(new Date());
        diaryEntryVO.setDiaryType(DiaryType.INDIVIDUAL);
        diaryEntryVO.setCaseType(CaseType.REFERRAL);


        ClaimDetails claimDetails = new ClaimDetails();
        claimDetails.setStartDate(new Date());
        claimDetails.setEndDate(new Date());
        claimDetails.setRelation("Mother");
        claimDetails.setApplicationDate(new Date());
        claimDetails.setRelativeCghsNumber("123123");
        claimDetails.setRelativeCghsexpiry(new Date());

        diaryEntryVO.setClaimDetails(claimDetails);

        CalculationSheetEntry calculationSheetEntry = new CalculationSheetEntry();
        calculationSheetEntry.setTotal(22f);
        calculationSheetEntry.setAmountAsked(25f);
        calculationSheetEntry.setBillDate("bill date");
        calculationSheetEntry.setBillNumber("bill number");
        calculationSheetEntry.setSerialNumber("22");


        CalculationSheetEntry calculationSheetEntry2 = new CalculationSheetEntry();
        calculationSheetEntry2.setTotal(22f);
        calculationSheetEntry2.setAmountAsked(25f);
        calculationSheetEntry2.setBillDate("bill date");
        calculationSheetEntry2.setBillNumber("bill number");
        calculationSheetEntry2.setSerialNumber("22");

        List<CalculationSheetEntry> calculationSheetEntryList = new ArrayList<>();
        calculationSheetEntryList.add(calculationSheetEntry);
        calculationSheetEntryList.add(calculationSheetEntry2);

        diaryEntryVO.setCalculationSheet(calculationSheetEntryList);

        diaryEntryService.save(diaryEntryVO);

    }



}
