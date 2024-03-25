package com.delhipolice.mediclaim.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class CandidateDiaryEntryVO {
    @JsonProperty("data")
    public ArrayList<Long> data;
}
