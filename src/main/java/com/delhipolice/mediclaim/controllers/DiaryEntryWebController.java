package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.services.DiaryEntryService;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        model.addAttribute("diaryEntry", new DiaryEntryVO());
        return "create_permission_entry";
    }

    @GetMapping("/creditEntryCreateForm")
    public String creditEntryCreateForm(Model model) {
        model.addAttribute("diaryEntry", new DiaryEntryVO());
        return "create_credit_entry";
    }

    @GetMapping("/viewclaim")
    public String viewclaim(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "claim_view";
    }

    @PostMapping("/diaryEntryCreate")
    public RedirectView submit(@ModelAttribute DiaryEntryVO diaryEntryVO, Model model) {
        log.error(diaryEntryVO.toString());
        DiaryEntry diaryEntry = diaryEntryService.save(diaryEntryVO);
        return new RedirectView("/diaryEntry");
    }

    @GetMapping("/createCalculationSheet/{id}")
    public String createCalculationSheet(@PathVariable Long id, Model model) {
        model.addAttribute("diaryNo", id);
        return "create_calculation_sheet";
    }
    @GetMapping("/printCalculationSheet/{id}")
    public String printCalculationSheet(@PathVariable Long id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_calculation_sheet";
    }

    @GetMapping("/printNotesheet/{id}")
    public String printNotesheet(@PathVariable Long id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_notesheet";
    }

    @GetMapping("/printForwardingLetter/{id}")
    public String printForwardingLetter(@PathVariable Long id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_forwarding_letter";
    }

    @GetMapping("/printOrder/{id}")
    public String printOrder(@PathVariable Long id, Model model) {
        model.addAttribute("diaryEntry", diaryEntryService.find(id));
        return "print_forwarding_letter";
    }

}