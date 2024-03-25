package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.HospitalType;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.vo.MedicalRateVO;

import java.util.List;


public interface MedicalRatesService {

    MedicalRates find(Long id);

    List<MedicalRateVO> findByNameContaining(String searchTerm, HospitalType hospitalType);

    List<MedicalRateVO> findAll();

    MedicalRates save(MedicalRates medicalRates);

    MedicalRateVO update(MedicalRateVO medicalRateVO);

    int count();

    void deleteAll();
}
