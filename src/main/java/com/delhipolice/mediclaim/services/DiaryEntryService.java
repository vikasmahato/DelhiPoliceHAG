package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PageArray;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.CalcSheetVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;

import java.util.List;

public interface DiaryEntryService {

    DiaryEntryVO find(Long id);

    DiaryEntry save(DiaryEntryVO diaryEntryVO);

    DiaryEntry update(DiaryEntry diaryEntry);

    List<DiaryEntry> findAll();

    Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest);

    void saveCalSheet(CalcSheetVO calcSheetVO);


    // PageArray getDiaryEntriesArray(PagingRequest pagingRequest);
}
