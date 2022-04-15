package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.vo.HospitalVO;
import com.delhipolice.mediclaim.vo.MedicalRateSearchCriteriaVO;
import com.delhipolice.mediclaim.vo.MedicalRateVO;

import java.util.List;

public interface MedicalRatesService {

    MedicalRates find(Long id);

    List<MedicalRateVO> findByNameContaining(MedicalRateSearchCriteriaVO medicalRateSearchCriteriaVO);

    MedicalRates save(MedicalRates medicalRates);

    MedicalRates update(MedicalRates medicalRates);

}
