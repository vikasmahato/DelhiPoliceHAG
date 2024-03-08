package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.HealthCheckupDiaryEntry;
import com.delhipolice.mediclaim.model.User;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.utils.UserHelper;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import com.delhipolice.mediclaim.vo.HealthCheckupDiaryEntryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class DiaryEntryWebController {

    @Autowired
    DiaryEntryService diaryEntryService;

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

 /*   @GetMapping("/diaryEntryCreateForm")
    public String diaryEntryCreate(Model model) {
        model.addAttribute("diaryEntry", new DiaryEntryVO());
        return "create_diary_entry";
    }*/

/*    @GetMapping("/permissionEntryCreateForm")
    public String permissionEntryCreate(Model model) {
        DiaryEntryVO diaryEntryVO = new DiaryEntryVO();
        diaryEntryVO.setClaimType(ClaimType.PERMISSION);
        model.addAttribute("diaryEntry", diaryEntryVO);
        return "create_permission_entry";
    }

    @GetMapping("/creditEntryCreateForm")
    public String creditEntryCreateForm(Model model) {
        DiaryEntryVO diaryEntryVO = new DiaryEntryVO();
        diaryEntryVO.setClaimType(ClaimType.CREDIT);
        model.addAttribute("diaryEntry", diaryEntryVO);
        return "create_credit_entry";
    }

    @GetMapping("/viewclaim")
    public String viewclaim(@RequestParam(value = "id") UUID id, Model model) {
        DiaryEntryVO diaryEntryVO = diaryEntryService.find(id).get();
        diaryEntryVO.setViewMode(Boolean.TRUE);
        model.addAttribute("diaryEntry", diaryEntryVO);
        return "claim_view";
    }*/

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

    @PostMapping("/diaryEntryUpdate")
    public RedirectView update(@ModelAttribute DiaryEntryVO diaryEntryVO, Model model) {
        log.error(diaryEntryVO.toString());
        DiaryEntry diaryEntry = diaryEntryService.update(diaryEntryVO);
        return new RedirectView("/viewclaim?id=" + diaryEntry.getId());
    }



    @GetMapping("/createCalculationSheet/{id}")
    public String createCalculationSheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryId", id);
        return "create_calculation_sheet";
    }
    @GetMapping("/printCalculationSheet/{id}")
    public String printCalculationSheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_calculation_sheet";
    }

    @GetMapping("/printReferralNotesheet/{id}")
    public String printReferralNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_referral_notesheet";
    }

    @GetMapping("/printHealthNotesheet/{id}")
    public String printHealthNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find1(id).get());
        return "prints/health/notesheet";
    }

    @GetMapping("/printHealthOrder/{id}")
    public String printHealthOrder(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find1(id).get());
        return "prints/health/order";
    }

    @GetMapping("/printReferralOrder/{id}/{renderSignature}")
    public String printReferralOrder(@PathVariable UUID id, @PathVariable Optional<String> renderSignature, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());

        DiaryEntryVO diaryEntry = diaryEntryService.find(id).get();
        model.addAttribute("diaryEntry", diaryEntry);
        model.addAttribute("renderSignature", renderSignature.orElse("false"));

        return "prints/" + diaryEntry.getDiaryType().getEnumValue().toLowerCase(Locale.ROOT) + "/" + diaryEntry.getClaimType().getEnumValue().toLowerCase(Locale.ROOT) + "_order";
    }

    @GetMapping("/printOpEmergencyForwardingLetter/{id}")
    public String printOpEmergencyForwardingLetter(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_op_emg_forwarding_letter";
    }

    @GetMapping("/printIpEmergencyForwardingLetter/{id}")
    public String printIpEmergencyForwardingLetter(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_ip_emg_forwarding_letter";
    }

    @GetMapping("/printCreditForwardingLetter/{id}")
    public String printCreditForwardingLetter(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_credit_fl";
    }

    @GetMapping("/printNotesheet/{id}")
    public String printNotesheet(@PathVariable UUID id, Model model) {
        DiaryEntryVO diaryEntry = diaryEntryService.find(id).get();
        model.addAttribute("diaryEntry", diaryEntry);

        return "prints/" + diaryEntry.getDiaryType().getEnumValue().toLowerCase(Locale.ROOT) + "/" + diaryEntry.getClaimType().getEnumValue().toLowerCase(Locale.ROOT) + "_notesheet";
    }

    @GetMapping("/printPermission/{id}")
    public String printCreditPermission(@PathVariable UUID id, Model model) {
        DiaryEntryVO diaryEntry = diaryEntryService.find(id).get();
        model.addAttribute("diaryEntry", diaryEntry);

        switch (diaryEntry.getClaimType()) {
            case REFERRAL:
                return "prints/print_referral_notesheet";
            case PERMISSION:
                return "prints/print_permission_permission";
            case CREDIT:
                return "prints/print_credit_permission";
            default:
                return "prints/print_treatment_notesheet";
        }
    }

    @GetMapping("/printTreatmentNotesheet/{id}")
    public String printTreatmentNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_treatment_notesheet";
    }

    @GetMapping("/printTreatmentPermission/{id}")
    public String printTreatmentPermission(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id).get());
        return "prints/print_treatment_permission";
    }




}