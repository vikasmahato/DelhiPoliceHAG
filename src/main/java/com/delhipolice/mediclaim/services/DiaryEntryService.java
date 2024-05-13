package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.*;

import java.util.List;
import java.util.Optional;


public interface DiaryEntryService {

    Optional<DiaryEntryVO> findDiaryEntry(Long id);
    Optional<HealthCheckupDiaryEntryVo> findHealthCheckupDiaryEntry(Long id);
    Optional<ReferralDiaryEntryVO> findReferralDiaryEntry(Long id);

    Optional<ExpiryDiaryEntryVO> findExpiryDiaryEntry(Long id);

    DiaryEntry save(DiaryEntryVO diaryEntryVO);
    ReferralDiaryEntry save(ReferralDiaryEntryVO diaryEntryVO);
    HealthCheckupDiaryEntry save(HealthCheckupDiaryEntryVo diaryEntryVO);

    List<DiaryEntry> findAll(List<DiaryEntryVO> diaryEntryVOS);
    List<DiaryEntry> findAll();

    List<DiaryEntry> findAllByLongs(List<Long> Longs);
    List<DiaryEntryVO> findAllByApplicant(Applicant applicant);

    DiaryEntry update(DiaryEntryVO diaryEntryVO);

    DiaryEntry update(DiaryEntry diaryEntry);

    Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, List<ClaimType> claimTypes, DiaryType diaryType);
    Page<ReferralDiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, ClaimType claimType);

    Page<HealthCheckupDiaryEntryVo> getHealthCheckupDiaryEntries(PagingRequest pagingRequest);

    void saveCalSheet(CalcSheetVO calcSheetVO);

    int count();

    void deleteDiaryEntry(Long id, String diaryEntryClass);

    ExpiryDiaryEntry save(ExpiryDiaryEntryVO diaryEntryVO);

    Page<ExpiryDiaryEntryVO> getExpiryDiaryEntries(PagingRequest pagingRequest);
}
