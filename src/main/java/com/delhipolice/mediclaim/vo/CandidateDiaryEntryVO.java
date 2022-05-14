package com.delhipolice.mediclaim.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.UUID;

public class CandidateDiaryEntryVO {
    @JsonProperty("data")
    public ArrayList<UUID> data;
}
