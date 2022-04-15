package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.repositories.MedicalRatesRepository;
import com.delhipolice.mediclaim.vo.MedicalRateSearchCriteriaVO;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class MedicalRatesServiceImpl implements MedicalRatesService{

    @Autowired
    private MedicalRatesRepository medicalRatesRepository;

    @Override
    public MedicalRates find(Long id) {
        return medicalRatesRepository.findById(id).orElse(null);
    }

    @Override
    public List<MedicalRateVO> findByNameContaining(MedicalRateSearchCriteriaVO medicalRateSearchCriteriaVO) {

        if(StringUtils.isEmpty(medicalRateSearchCriteriaVO.getSearchTerm()))
            return new ArrayList<>();

        List<MedicalRates> medicalRates = medicalRatesRepository.findByNameContaining(medicalRateSearchCriteriaVO.getSearchTerm().toLowerCase(Locale.ROOT));

        List<MedicalRateVO> medicalRateVOS = new ArrayList<>();

        for(MedicalRates mr : medicalRates) {
            medicalRateVOS.add(new MedicalRateVO(mr.getId().toString(), mr.getProductName(), medicalRateSearchCriteriaVO.getHospitalType().equals("NABH") ? mr.getNabhNablRate() : mr.getNonNabhNablRate() ));
        }

        return medicalRateVOS;
    }

    @Override
    public MedicalRates save(MedicalRates medicalRates) {
        return medicalRatesRepository.save(medicalRates);
    }

    @Override
    public MedicalRates update(MedicalRates medicalRates) {
        return medicalRatesRepository.save(medicalRates);
    }

}
