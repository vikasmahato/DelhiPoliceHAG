package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PageArray;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.CalcSheetVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;

import java.util.List;
import java.util.UUID;

public interface DiaryEntryService {

    DiaryEntryVO find(UUID id);

    DiaryEntry save(DiaryEntryVO diaryEntryVO);

    DiaryEntry update(DiaryEntry diaryEntry);

    List<DiaryEntry> findAll();

    Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest);

    void saveCalSheet(CalcSheetVO calcSheetVO);


    // PageArray getDiaryEntriesArray(PagingRequest pagingRequest);
}
