package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.constants.ClaimType;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class DiaryEntryRestController {

    private DiaryEntryService diaryEntryService;

    @Autowired
    public DiaryEntryRestController(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }

    @PostMapping("/diaryentries")
    public Page<DiaryEntryVO> listDiaryEntries(@RequestBody PagingRequest pagingRequest) {
        List<ClaimType> claimTypes = new ArrayList<>(Arrays.asList(ClaimType.CREDIT, ClaimType.PERMISSION));
        return diaryEntryService.getDiaryEntries(pagingRequest, claimTypes);
    }

    @PostMapping("/permissions")
    public Page<DiaryEntryVO> listPermissions(@RequestBody PagingRequest pagingRequest) {
        List<ClaimType> claimTypes = new ArrayList<>(Arrays.asList(ClaimType.CREDIT, ClaimType.PERMISSION));
        return diaryEntryService.getDiaryEntries(pagingRequest, claimTypes);
    }


    @GetMapping("/getCalSheetentries/{id}")
    public List<CalculationSheetEntry> getCalSheetentries(@PathVariable UUID id) {
        DiaryEntryVO diaryEntryVO = diaryEntryService.find(id);
        return diaryEntryVO.getCalculationSheet();
    }

    @PostMapping("/saveCalcSheet")
    public @ResponseBody  Boolean saveCalcSheet(CalcSheetVO calcSheetVO) {
        diaryEntryService.saveCalSheet(calcSheetVO);
        return true;

    }
}