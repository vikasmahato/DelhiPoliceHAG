package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.HospitalType;
import com.delhipolice.mediclaim.model.MedicalRates;
import com.delhipolice.mediclaim.repositories.MedicalRatesRepository;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.stream.Collectors;

import static com.delhipolice.mediclaim.constants.HospitalType.NABH;

@Service
@Slf4j
public class MedicalRatesServiceImpl implements MedicalRatesService{

    @Autowired
    private MedicalRatesRepository medicalRatesRepository;

    @Autowired
    private DiaryEntryService diaryEntryService;

    @Override
    public MedicalRates find(Long id) {
        return medicalRatesRepository.findById(id).orElse(null);
    }

    @Override
    public List<MedicalRateVO> findByNameContaining(String searchTerm, HospitalType hospitalType) {

        List<MedicalRates> medicalRates = medicalRatesRepository.findByNameContaining(searchTerm.toLowerCase(Locale.ROOT));

        List<MedicalRateVO> medicalRateVOS = new ArrayList<>();

        for(MedicalRates mr : medicalRates) {
            medicalRateVOS.add(new MedicalRateVO(mr.getProductCode(), mr.getProductName(), NABH.equals(hospitalType) ? mr.getNabhNablRate() : mr.getNonNabhNablRate(), mr.getRule(), mr.getState() ));
        }

        return medicalRateVOS;
    }

    @Override
    public List<MedicalRateVO> findAll() {
        return medicalRatesRepository.findAll().stream().map(MedicalRateVO::new).collect(Collectors.toList());
    }

    @Override
    public MedicalRates save(MedicalRates medicalRates) {
        return medicalRatesRepository.save(medicalRates);
    }

    @Override
    public MedicalRateVO update(MedicalRateVO medicalRateVO) {
        MedicalRates medicalRates = medicalRatesRepository.getById(medicalRateVO.getId());
        medicalRates.setProductName(medicalRateVO.getProductName());
        medicalRates.setNabhNablRate(medicalRateVO.getNabhNablRate());
        medicalRates.setNonNabhNablRate(medicalRateVO.getNonNabhNablRate());

        MedicalRates updatedMedicalRates = medicalRatesRepository.save(medicalRates);
        return new MedicalRateVO(updatedMedicalRates);
    }

    @Override
    public int count() {
        return (int) medicalRatesRepository.count();
    }

    @Override
    public void deleteAll() {
        medicalRatesRepository.deleteAll();
    }

}
