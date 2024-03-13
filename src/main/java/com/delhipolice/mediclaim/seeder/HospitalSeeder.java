package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.constants.*;
import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.ClaimDetails;
import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.services.HospitalService;
import com.delhipolice.mediclaim.services.MedicalRatesService;
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
import java.util.ArrayList;
import java.util.Date;
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

    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        seedHospitals();
        seedMedicalRates();
        seedDummyData();
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

         medicalRatesService.deleteAll();

        if(medicalRatesService.count() > 0) {
            log.info(medicalRatesService.count() + " medical rates already exist");
            return;
        }

        InputStream is = new ClassPathResource("data/medicalRates.json").getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        //MedicalRates[] list = objectMapper.readValue(file, MedicalRates[].class);
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

            //log.info("Saved: " + medicalRates.getProductName());

        }


    }



    public void seedDummyData(){
        if(diaryEntryService.count() > 0) {
            log.info(diaryEntryService.count() + " diary entries already exist");
            return;
        }

       /* Random random = new Random();
        String[] names = {"John Doe", "Jane Smith", "Robert Johnson", "Michael Brown", "Linda Garcia"};

        for (int i = 0; i < 15; i++) {
            String name = names[random.nextInt(names.length)];
            String beltNumber = String.format("%05d", random.nextInt(100000));
            String pisNumber = String.format("%06d", random.nextInt(1000000));
            TreatmentBy treatmentBy = TreatmentBy.values()[random.nextInt(TreatmentBy.values().length)];
            String diaryNumber = String.format("%05d", random.nextInt(100000));
            DiaryType diaryType = DiaryType.values()[random.nextInt(DiaryType.values().length)];
            ClaimType claimType = ClaimType.values()[random.nextInt(ClaimType.values().length)];

            diaryEntryService.save(buildVO(name, beltNumber, pisNumber, treatmentBy, diaryNumber, DiaryType.INDIVIDUAL, ClaimType.EMERGENCY));
        }*/
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
