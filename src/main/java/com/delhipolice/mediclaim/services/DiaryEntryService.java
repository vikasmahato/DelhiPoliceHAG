package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryEntryService {

    Optional<DiaryEntryVO> findDiaryEntry(UUID id);
    Optional<HealthCheckupDiaryEntryVo> findHealthCheckupDiaryEntry(UUID id);
    Optional<ReferralDiaryEntryVO> findReferralDiaryEntry(UUID id);

    Optional<ExpiryDiaryEntryVO> findExpiryDiaryEntry(UUID id);

    DiaryEntry save(DiaryEntryVO diaryEntryVO);
    ReferralDiaryEntry save(ReferralDiaryEntryVO diaryEntryVO);
    HealthCheckupDiaryEntry save(HealthCheckupDiaryEntryVo diaryEntryVO);

    List<DiaryEntry> findAll(List<DiaryEntryVO> diaryEntryVOS);

    List<DiaryEntry> findAllByUUIDs(List<UUID> uuids);
    List<DiaryEntryVO> findAllByApplicant(Applicant applicant);

    DiaryEntry update(DiaryEntryVO diaryEntryVO);

    DiaryEntry update(DiaryEntry diaryEntry);

    Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, List<ClaimType> claimTypes, DiaryType diaryType);
    Page<ReferralDiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, ClaimType claimType);

    Page<HealthCheckupDiaryEntryVo> getHealthCheckupDiaryEntries(PagingRequest pagingRequest);

    void saveCalSheet(CalcSheetVO calcSheetVO);

    int count();

    void deleteDiaryEntry(UUID id, String diaryEntryClass);

    ExpiryDiaryEntry save(ExpiryDiaryEntryVO diaryEntryVO);

    Page<ExpiryDiaryEntryVO> getExpiryDiaryEntries(PagingRequest pagingRequest);
}
