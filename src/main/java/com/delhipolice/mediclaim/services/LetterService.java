package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Hospital;
import com.delhipolice.mediclaim.model.Letter;
import com.delhipolice.mediclaim.vo.HospitalVO;
import com.delhipolice.mediclaim.vo.LetterVO;

import java.util.List;
import java.util.UUID;

public interface LetterService {

    Letter find(UUID id);

    List<LetterVO> findAll();

    Letter save(LetterVO letterVO);

    Letter save(List<UUID> uuids);

    Letter update(LetterVO letterVO);

}
