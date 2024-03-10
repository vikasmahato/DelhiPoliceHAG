package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.Applicant;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.HealthCheckupDiaryEntry;
import com.delhipolice.mediclaim.model.ReferralDiaryEntry;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.CalcSheetVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import com.delhipolice.mediclaim.vo.HealthCheckupDiaryEntryVo;
import com.delhipolice.mediclaim.vo.ReferralDiaryEntryVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryEntryService {

    Optional<DiaryEntryVO> find(UUID id);
    Optional<HealthCheckupDiaryEntryVo> find1(UUID id);
    Optional<ReferralDiaryEntryVO> find2(UUID id);

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

    List<DiaryEntryVO> findCandidateDiaryEntries();

    int count();
}
