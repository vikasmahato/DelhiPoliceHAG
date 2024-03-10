package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.vo.MedicalRateVO;

import java.util.List;
import java.util.UUID;

public interface MedicalRatesService {

    MedicalRates find(Long id);

    List<MedicalRateVO> findByNameContaining(String searchTerm, UUID diaryId);

    List<MedicalRateVO> findAll();

    MedicalRates save(MedicalRates medicalRates);

    MedicalRateVO update(MedicalRateVO medicalRateVO);

    int count();

    void deleteAll();
}
