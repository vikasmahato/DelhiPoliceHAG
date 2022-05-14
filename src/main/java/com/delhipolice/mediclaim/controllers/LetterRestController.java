package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.services.LetterService;
import com.delhipolice.mediclaim.utils.Page;
import com.delhipolice.mediclaim.vo.CandidateDiaryEntryVO;
import com.delhipolice.mediclaim.vo.LetterVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class LetterRestController {

    private LetterService letterService;

    @Autowired
    public LetterRestController(LetterService letterService) {
        this.letterService = letterService;
    }

    @PostMapping("/createNotesheet")
    public @ResponseBody String printNotesheet(@RequestBody String payload) throws UnsupportedEncodingException, JsonProcessingException {
        String data = java.net.URLDecoder.decode(payload, StandardCharsets.UTF_8.name());
        ObjectMapper mapper = new ObjectMapper();
        CandidateDiaryEntryVO candidateDiaryEntryVO = mapper.readValue(data, CandidateDiaryEntryVO.class);

        if(candidateDiaryEntryVO.data.isEmpty())
            return "success";

        letterService.save(candidateDiaryEntryVO.data);
        return "success";
    }

    @GetMapping("/getAllNotesheets")
    public @ResponseBody Page<LetterVO> getAllNotesheets() {
        return  new Page<>(letterService.findAll());
    }



}