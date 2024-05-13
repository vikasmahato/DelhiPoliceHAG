package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.CalculationSheetEntry;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.utils.PagingRequest;
import com.delhipolice.mediclaim.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class DiaryEntryRestController {

    private DiaryEntryService diaryEntryService;

    @Autowired
    public DiaryEntryRestController(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }

    @PostMapping("/diaryentries/{type}")
    public Page<DiaryEntryVO> listDiaryEntries(@RequestBody PagingRequest pagingRequest, @PathVariable String type) {

        List<ClaimType> claimTypes = Arrays.asList(ClaimType.EMERGENCY, ClaimType.REFERRAL);

        DiaryType diaryType = DiaryType.valueOf(type.toUpperCase());

        return diaryEntryService.getDiaryEntries(pagingRequest, claimTypes, diaryType);
    }

    @PostMapping("/diaryentries/delete/{id}/{type}")
    public ResponseEntity<String> deleteDiaryEntry(@PathVariable Long id, @PathVariable String type) {
        try {
            diaryEntryService.deleteDiaryEntry(id, type);
            return ResponseEntity.ok("Delete operation was successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/referraldiaryentries/")
    public Page<ReferralDiaryEntryVO> listReferralDiaryEntries(@RequestBody PagingRequest pagingRequest) {

        return diaryEntryService.getDiaryEntries(pagingRequest, ClaimType.REFERRAL);
    }

    @PostMapping("/healthdiaryentries")
    public Page<HealthCheckupDiaryEntryVo> listDiaryEntries(@RequestBody PagingRequest pagingRequest) {

        return diaryEntryService.getHealthCheckupDiaryEntries(pagingRequest);
    }

    @PostMapping("/expirydiaryentries")
    public Page<ExpiryDiaryEntryVO> listExpiryDiaryEntries(@RequestBody PagingRequest pagingRequest) {

        return diaryEntryService.getExpiryDiaryEntries(pagingRequest);
    }

    @PostMapping("/diaryEntry/{id}")
    public ResponseEntity<DiaryEntryVO> getDiaryEntry(@PathVariable Long id) {
        Optional<DiaryEntryVO> diaryEntryVO = diaryEntryService.findDiaryEntry(id);
        return diaryEntryVO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/expiryDiaryEntry/{id}")
    public ResponseEntity<ExpiryDiaryEntryVO> getExpiryDiaryEntry(@PathVariable Long id) {
        Optional<ExpiryDiaryEntryVO> diaryEntryVO = diaryEntryService.findExpiryDiaryEntry(id);
        return diaryEntryVO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/healthCheckupDiaryEntry/{id}")
    public ResponseEntity<HealthCheckupDiaryEntryVo> getHealthCheckupDiaryEntry(@PathVariable Long id) {
        Optional<HealthCheckupDiaryEntryVo> diaryEntryVO = diaryEntryService.findHealthCheckupDiaryEntry(id);
        return diaryEntryVO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/indRefDiaryEntry/{id}")
    public ResponseEntity<ReferralDiaryEntryVO> getIndRefDiaryEntry(@PathVariable Long id) {
        Optional<ReferralDiaryEntryVO> diaryEntryVO = diaryEntryService.findReferralDiaryEntry(id);
        return diaryEntryVO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/permissions")
    public Page<DiaryEntryVO> listPermissions(@RequestBody PagingRequest pagingRequest) {
        List<ClaimType> claimTypes = Arrays.asList(ClaimType.PERMISSION, ClaimType.CREDIT);
        return diaryEntryService.getDiaryEntries(pagingRequest, claimTypes, DiaryType.INDIVIDUAL);
    }


    @GetMapping("/getCalSheetentries/{id}")
    public Map<String, Object> getCalSheetentries(@PathVariable Long id) {
        DiaryEntryVO diaryEntryVO = diaryEntryService.findDiaryEntry(id).get();
        Double adjustmentFactor = diaryEntryVO.getCalculationSheetAdjustmentFactor();
        List<CalculationSheetEntry> calculationSheet = diaryEntryVO.getCalculationSheet();

        List<CalculationSheetEntry> mainEntries = calculationSheet.stream().filter(entry -> !entry.getAdjustment()).collect(Collectors.toList());
        CalculationSheetEntry adjustmentEntry = calculationSheet.stream().filter(CalculationSheetEntry::getAdjustment).findFirst().orElse(null);


        Map<String, Object> response = new HashMap<>();
        response.put("adjustmentFactor", adjustmentFactor);
        response.put("calculationSheet", mainEntries);
        response.put("serialNoCalculationSheet", diaryEntryVO.getSerialNumberCalculationSheet());
        response.put("adjustmentEntry", adjustmentEntry);

        return response;
    }

    @PostMapping("/saveCalcSheet")
    public @ResponseBody  Boolean saveCalcSheet(CalcSheetVO calcSheetVO) {
        diaryEntryService.saveCalSheet(calcSheetVO);
        return true;

    }

}