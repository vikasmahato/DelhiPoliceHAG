package com.delhipolice.mediclaim.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicalRateSearchCriteriaVO {
    String searchTerm;
    String type;
    String hospitalType;

    public MedicalRateSearchCriteriaVO(String searchTerm, String type, String hospitalType) {
        this.searchTerm = searchTerm;
        this.type = type;
        this.hospitalType = hospitalType;
    }
}
