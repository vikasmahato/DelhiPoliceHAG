package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.vo.HospitalVO;

import java.util.List;

public interface HospitalService {

    Hospital find(Long id);

    List<HospitalVO> findAll();

    List<HospitalVO> findByNameContaining(String name);

    Hospital save(HospitalVO hospital);

    HospitalVO update(HospitalVO hospitalVO);

    int count();

    void deleteAll();

    Hospital save(Hospital hospital);
}
