package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.constants.*;
import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.ClaimDetails;
import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import com.delhipolice.mediclaim.vo.ClaimDetailsVO;
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
        //seedHospitals();
        //seedMedicalRates();
      //  seedDummyData();
    }

    private void seedHospitals() throws IOException {


        File file = new ClassPathResource("data/cghs_hospital.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        HospitalVO[] list = objectMapper.readValue(file, HospitalVO[].class);

        for(HospitalVO hospitalVO : list) {
            hospitalService.save(hospitalVO);
            //log.info("Saved: " + hospitalVO.getName());

        }


    }

    private void seedMedicalRates() throws IOException {


        File file = new ClassPathResource("data/medicalRates.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        MedicalRates[] list = objectMapper.readValue(file, MedicalRates[].class);

        for(MedicalRates medicalRates : list) {
            medicalRatesService.save(medicalRates);
            //log.info("Saved: " + medicalRates.getProductName());

        }


    }

    public void seedDummyData(){



        diaryEntryService.save(buildVO("Applicant 1", "00000", "999999", TreatmentBy.SELF, "00001", DiaryType.INDIVIDUAL, ClaimType.CREDIT));
        diaryEntryService.save(buildVO("Applicant 2", "00001", "888888", TreatmentBy.SELF, "00001", DiaryType.INDIVIDUAL, ClaimType.PERMISSION));

        diaryEntryService.save(buildVO("Applicant 3", "00002", "777777", TreatmentBy.RELATIVE, "00001", DiaryType.INDIVIDUAL, ClaimType.CREDIT));
        diaryEntryService.save(buildVO("Applicant 4", "00003", "6666666", TreatmentBy.RELATIVE, "00001", DiaryType.INDIVIDUAL, ClaimType.PERMISSION));

        diaryEntryService.save(buildVO("Applicant 5", "00004", "5555555", TreatmentBy.SELF, "00001", DiaryType.HOSPITAL, ClaimType.CREDIT));
        diaryEntryService.save(buildVO("Applicant 6", "00005", "444444", TreatmentBy.RELATIVE, "00001", DiaryType.HOSPITAL, ClaimType.PERMISSION));

    }

    private DiaryEntryVO buildVO(String name, String beltNumber, String pisNumber, TreatmentBy treatmentBy, String diaryNumber, DiaryType diaryType, ClaimType claimType) {
        DiaryEntryVO diaryEntryVO = new DiaryEntryVO();

        ApplicantVO applicant = new ApplicantVO();
        applicant.setName(name);
        applicant.setBeltNumber(beltNumber);
        applicant.setPisNumber(pisNumber);
        applicant.setRank(Designation.ACP);


        Hospital hospitalVO = new Hospital();
        hospitalVO.setId(2l);

        diaryEntryVO.setHospital(hospitalVO);
        diaryEntryVO.setApplicant(applicant);
        diaryEntryVO.setTreatmentTakenBy(treatmentBy);

        diaryEntryVO.setDiaryNumber(diaryNumber);
        diaryEntryVO.setDiaryDate(new Date());
        diaryEntryVO.setDiaryType(diaryType);
        diaryEntryVO.setClaimType(claimType);


        ClaimDetails claimDetails = new ClaimDetails();
        claimDetails.setStartDate(new Date());
        claimDetails.setEndDate(new Date());
        claimDetails.setRelativeName(TreatmentBy.SELF.equals(treatmentBy) ? applicant.getName() : "Sakshi");
        claimDetails.setRelation( TreatmentBy.SELF.equals(treatmentBy) ? Relation.SELF : Relation.MOTHER);
        claimDetails.setApplicationDate(new Date());
        claimDetails.setRelativeCghsNumber( TreatmentBy.SELF.equals(treatmentBy) ? null : "123123");
        claimDetails.setRelativeCghsexpiry(TreatmentBy.SELF.equals(treatmentBy) ? null : new Date());

        diaryEntryVO.setClaimDetails(new ClaimDetailsVO(claimDetails));

        CalculationSheetEntry calculationSheetEntry = new CalculationSheetEntry();
        calculationSheetEntry.setTotal(22d);
        calculationSheetEntry.setAmountAsked(25d);
        calculationSheetEntry.setBillDate("bill date");
        calculationSheetEntry.setBillNumber("bill number");
        calculationSheetEntry.setSerialNumber("22");


        CalculationSheetEntry calculationSheetEntry2 = new CalculationSheetEntry();
        calculationSheetEntry2.setTotal(22d);
        calculationSheetEntry2.setAmountAsked(25d);
        calculationSheetEntry2.setBillDate("bill date");
        calculationSheetEntry2.setBillNumber("bill number");
        calculationSheetEntry2.setSerialNumber("22");

        List<CalculationSheetEntry> calculationSheetEntryList = new ArrayList<>();
        calculationSheetEntryList.add(calculationSheetEntry);
        calculationSheetEntryList.add(calculationSheetEntry2);

        diaryEntryVO.setCalculationSheet(calculationSheetEntryList);

        return diaryEntryVO;
    }





}
