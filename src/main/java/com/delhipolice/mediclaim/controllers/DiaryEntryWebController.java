package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@Controller
@Slf4j
public class DiaryEntryWebController {

    @Autowired
    DiaryEntryService diaryEntryService;

    @GetMapping("/diaryEntry")
    public String diaryEntry(Model model) {
        return "diary_entry_home";
    }

    @GetMapping("/permission")
    public String permissionEntry(Model model) {
        return "permission_entry_home";
    }

    @GetMapping("/diaryEntryCreateForm")
    public String diaryEntryCreate(Model model) {
        model.addAttribute("diaryEntry", new DiaryEntryVO());
        return "create_diary_entry";
    }

    @GetMapping("/permissionEntryCreateForm")
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
        DiaryEntryVO diaryEntryVO = diaryEntryService.find(id);
        diaryEntryVO.setViewMode(Boolean.TRUE);
        model.addAttribute("diaryEntry", diaryEntryVO);
        return "claim_view";
    }

    @PostMapping("/diaryEntryCreate")
    public RedirectView submit(@ModelAttribute DiaryEntryVO diaryEntryVO, Model model) {
        log.error(diaryEntryVO.toString());
        DiaryEntry diaryEntry = diaryEntryService.save(diaryEntryVO);
        return new RedirectView("/diaryEntry");
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
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_calculation_sheet";
    }

    @GetMapping("/printReferralNotesheet/{id}")
    public String printReferralNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_referral_notesheet";
    }

    @GetMapping("/printReferralOrder/{id}")
    public String printReferralOrder(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_referral_order";
    }

    @GetMapping("/printOpEmergencyForwardingLetter/{id}")
    public String printOpEmergencyForwardingLetter(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_op_emg_forwarding_letter";
    }

    @GetMapping("/printIpEmergencyForwardingLetter/{id}")
    public String printIpEmergencyForwardingLetter(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_ip_emg_forwarding_letter";
    }

    @GetMapping("/printCreditNotesheet/{id}")
    public String printCreditNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_credit_notesheet";
    }

    @GetMapping("/printCreditPermission/{id}")
    public String printCreditPermission(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_credit_permission";
    }

    @GetMapping("/printTreatmentNotesheet/{id}")
    public String printTreatmentNotesheet(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_treatment_notesheet";
    }

    @GetMapping("/printTreatmentPermission/{id}")
    public String printTreatmentPermission(@PathVariable UUID id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_treatment_permission";
    }


}