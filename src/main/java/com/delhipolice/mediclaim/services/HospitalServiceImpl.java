package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.repositories.HospitalRepository;
import com.delhipolice.mediclaim.vo.HospitalVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    private HospitalRepository hospitalRepository;


    @Override
    public Hospital find(Long id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    @Override
    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }


    @Override
    public List<HospitalVO> findByNameContaining(String name) {

        if(StringUtils.isEmpty(name))
            return new ArrayList<>();

        List<Hospital> hospitals = hospitalRepository.findByNameContaining(name.toLowerCase(Locale.ROOT));
        return hospitals.stream().map(HospitalVO::new).limit(10).collect(Collectors.toList());
    }

    @Override
    public Hospital save(HospitalVO hospitalVO) {
        return hospitalRepository.save(new Hospital(hospitalVO));
    }

    @Override
    public Hospital update(Hospital hospital) {
        return null;
    }
}
