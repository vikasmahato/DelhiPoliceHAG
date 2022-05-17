package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.services.LetterService;
import com.delhipolice.mediclaim.vo.LetterVO;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
public class LetterWebController {

    private LetterService letterService;

    @Autowired
    public LetterWebController(LetterService letterService) {
        this.letterService = letterService;
    }

    @GetMapping("/notesheets")
    public String notesheetsHome(Model model) {
        List<LetterVO> letters =  letterService.findAll();
        model.addAttribute("letters", letters);
        return "forwarding_home";
    }

    @GetMapping("/printNotesheet/{id}")
    public String printNotesheet(@PathVariable UUID id, Model model) {
        LetterVO letterVO = new LetterVO(letterService.find(id));
        model.addAttribute("notesheet", letterVO);
        model.addAttribute("diaryEntry", letterVO.getDiaryEntryVOS().get(0));
        return "print_notesheet";
    }

}