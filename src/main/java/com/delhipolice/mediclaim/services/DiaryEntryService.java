package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.model.Applicant;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.CalcSheetVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;

import java.util.List;
import java.util.UUID;

public interface DiaryEntryService {

    DiaryEntryVO find(UUID id);

    DiaryEntry save(DiaryEntryVO diaryEntryVO);

    List<DiaryEntry> findAll(List<DiaryEntryVO> diaryEntryVOS);

    List<DiaryEntry> findAllByUUIDs(List<UUID> uuids);
    List<DiaryEntryVO> findAllByApplicant(Applicant applicant);

    DiaryEntry update(DiaryEntryVO diaryEntryVO);

    DiaryEntry update(DiaryEntry diaryEntry);

    Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, List<ClaimType> claimTypes);

    void saveCalSheet(CalcSheetVO calcSheetVO);

    List<DiaryEntryVO> findCandidateDiaryEntries();

    int count();
}
