package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.utils.CurrencyFormatUtil;
import com.delhipolice.mediclaim.utils.UserHelper;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import com.delhipolice.mediclaim.vo.ExpiryDiaryEntryVO;
import com.delhipolice.mediclaim.vo.HealthCheckupDiaryEntryVo;
import com.delhipolice.mediclaim.vo.ReferralDiaryEntryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class DiaryEntryWebController {

    @Autowired
    DiaryEntryService diaryEntryService;

    @Autowired
    private CurrencyFormatUtil currencyFormatUtil;

    @GetMapping("/individual")
    public String individualDiaryEntry(Model model) {
        model.addAttribute("type", DiaryType.INDIVIDUAL.name());
        model.addAttribute("branchCode", UserHelper.getBranchCode());
        return "diary_entry_home";
    }

    @GetMapping("/hospital")
    public String hospitalDiaryEntry(Model model) {
        model.addAttribute("type", DiaryType.HOSPITAL.name());
        model.addAttribute("branchCode", UserHelper.getBranchCode());
        return "diary_entry_home";
    }

    @GetMapping("/referral")
    public String referralDiaryEntry(Model model) {
        model.addAttribute("type", DiaryType.HOSPITAL.name());
        model.addAttribute("claimType", ClaimType.REFERRAL.name());
        model.addAttribute("branchCode", UserHelper.getBranchCode());
        return "referral_entry_home";
    }

    @GetMapping("/permission")
    public String permissionEntry(Model model) {
        model.addAttribute("branchCode", UserHelper.getBranchCode());
        return "permission_entry_home";
    }

    @GetMapping("/health")
    public String healthCheckupEntry(Model model) {
        model.addAttribute("branchCode", UserHelper.getBranchCode());
        return "health_checkup_home";
    }

    @GetMapping("/expiry")
    public String expiryEntry(Model model) {
        model.addAttribute("branchCode", UserHelper.getBranchCode());
        return "expiry_entry_home";
    }

    @PostMapping("/diaryEntryCreate")
    public ResponseEntity<DiaryEntry> submit(@ModelAttribute DiaryEntryVO diaryEntryVO, Model model) {
        log.info(diaryEntryVO.toString());
        DiaryEntry diaryEntry = diaryEntryService.save(diaryEntryVO);
        return ResponseEntity.ok(diaryEntry);
    }

    @PostMapping("/healthDiaryEntryCreate")
    public ResponseEntity<HealthCheckupDiaryEntry> submit(@ModelAttribute HealthCheckupDiaryEntryVo diaryEntryVO, Model model) {
        HealthCheckupDiaryEntry diaryEntry = diaryEntryService.save(diaryEntryVO);
        return ResponseEntity.ok(diaryEntry);
    }

    @PostMapping("/indRefDiaryEntryCreate")
    public ResponseEntity<ReferralDiaryEntry> submit(@ModelAttribute ReferralDiaryEntryVO diaryEntryVO, Model model) {
        ReferralDiaryEntry diaryEntry = diaryEntryService.save(diaryEntryVO);
        return ResponseEntity.ok(diaryEntry);
    }

    @PostMapping("/expiryDiaryEntryCreate")
    public ResponseEntity<ExpiryDiaryEntry> submit(@ModelAttribute ExpiryDiaryEntryVO diaryEntryVO, Model model) {
        ExpiryDiaryEntry diaryEntry = diaryEntryService.save(diaryEntryVO);
        return ResponseEntity.ok(diaryEntry);
    }



    @GetMapping("/createCalculationSheet/{id}/{hospitalType}/{diaryClass}")
    public String createCalculationSheet(@PathVariable UUID id, @PathVariable String hospitalType, @PathVariable String diaryClass, Model model) {
        model.addAttribute("diaryId", id);
        model.addAttribute("diaryClass", diaryClass);
        model.addAttribute("hospitalType", hospitalType);
        return "create_calculation_sheet";
    }
    @GetMapping("/printCalculationSheet/{id}/{diaryClass}")
    public String printCalculationSheet(@PathVariable UUID id, @PathVariable String diaryClass, Model model) {

        if(diaryClass.equals("DiaryEntry")) {
            model.addAttribute("diaryEntry", diaryEntryService.findDiaryEntry(id).get());
        } else {
            model.addAttribute("diaryEntry", diaryEntryService.findExpiryDiaryEntry(id).get());
        }

        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/print_calculation_sheet";
    }

    @GetMapping("/printHealthNotesheet/{id}")
    public String printHealthNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.findHealthCheckupDiaryEntry(id).get());
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/health/notesheet";
    }

    @GetMapping("/printHealthOrder/{id}")
    public String printHealthOrder(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.findHealthCheckupDiaryEntry(id).get());
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/health/order";
    }

    @GetMapping("/printReferralNotesheet/{id}")
    public String printReferralNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.findReferralDiaryEntry(id).get());
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/referral/notesheet";
    }

    @GetMapping("/printReferralOrder/{id}")
    public String printReferralOrder(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.findReferralDiaryEntry(id).get());
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/referral/order";
    }

    @GetMapping("/printOrder/{id}/{renderSignature}")
    public String printOrder(@PathVariable UUID id, @PathVariable Optional<String> renderSignature, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.findDiaryEntry(id).get());

        DiaryEntryVO diaryEntry = diaryEntryService.findDiaryEntry(id).get();
        model.addAttribute("diaryEntry", diaryEntry);
        model.addAttribute("renderSignature", renderSignature.orElse("false"));
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);

        return "prints/" + diaryEntry.getDiaryType().getEnumValue().toLowerCase(Locale.ROOT) + "/" + diaryEntry.getClaimType().getEnumValue().toLowerCase(Locale.ROOT) + "_order";
    }

    @GetMapping("/printNotesheet/{id}")
    public String printNotesheet(@PathVariable UUID id, Model model) {
        DiaryEntryVO diaryEntry = diaryEntryService.findDiaryEntry(id).get();
        model.addAttribute("diaryEntry", diaryEntry);
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/" + diaryEntry.getDiaryType().getEnumValue().toLowerCase(Locale.ROOT) + "/" + diaryEntry.getClaimType().getEnumValue().toLowerCase(Locale.ROOT) + "_notesheet";
    }

    @GetMapping("/printExpiryNotesheet/{id}")
    public String printExpiryNotesheet(@PathVariable UUID id, Model model) {
        ExpiryDiaryEntryVO diaryEntry = diaryEntryService.findExpiryDiaryEntry(id).get();
        model.addAttribute("diaryEntry", diaryEntry);
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/expiry/" + diaryEntry.getDiaryType().getEnumValue().toLowerCase(Locale.ROOT) + "_notesheet";
    }


    @GetMapping("/printExpiryOrder/{id}/{renderSignature}")
    public String printExpiryOrder(@PathVariable UUID id, @PathVariable Optional<String> renderSignature, Model model) {
        ExpiryDiaryEntryVO diaryEntry = diaryEntryService.findExpiryDiaryEntry(id).get();
        model.addAttribute("diaryEntry", diaryEntry);
        model.addAttribute("renderSignature", renderSignature.orElse("false"));
        model.addAttribute("currencyFormatUtil", currencyFormatUtil);
        return "prints/expiry/" + diaryEntry.getDiaryType().getEnumValue().toLowerCase(Locale.ROOT) + "_order";
    }



}