package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PageArray;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.CalcSheetVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryEntryRestController {

    private DiaryEntryService diaryEntryService;

    @Autowired
    public DiaryEntryRestController(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }

    @PostMapping("/diaryentries")
    public Page<DiaryEntryVO> list(@RequestBody PagingRequest pagingRequest) {
        return diaryEntryService.getDiaryEntries(pagingRequest);
    }


    @GetMapping("/getCalSheetentries/{id}")
    public List<CalculationSheetEntry> getCalSheetentries(@PathVariable Long id) {
        DiaryEntryVO diaryEntryVO = diaryEntryService.find(id);
        return diaryEntryVO.getCalculationSheet();
    }

    @PostMapping("/saveCalcSheet")
    public @ResponseBody  Boolean saveCalcSheet(CalcSheetVO calcSheetVO) {
        diaryEntryService.saveCalSheet(calcSheetVO);
        return true;

    }
}