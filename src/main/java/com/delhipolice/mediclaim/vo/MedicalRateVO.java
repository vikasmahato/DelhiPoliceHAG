package com.delhipolice.mediclaim.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicalRateVO {
    private String productCode;
    private String productName;
    private Float rate;

    public MedicalRateVO(String productCode, String productName, Float rate) {
        this.productCode = productCode;
        this.productName = productName;
        this.rate = rate;
    }
}
