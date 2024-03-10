package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.model.MedicalRates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MedicalRateVO {
    private Long id;
    private Integer productCode;
    private String productName;
    private String rule;
    private Float rate;
    private Float nonNabhNablRate;
    private Float nabhNablRate;
    private String state;



    public MedicalRateVO(Integer productCode, String productName, Float rate, String rule, String state) {
        this.productCode = productCode;
        this.productName = productName;
        this.rate = rate;
        this.rule = rule;
        this.state = state;
    }

    public MedicalRateVO(Integer productCode, String productName, Float nonNabhNablRate, Float nabhNablRate,   String state) {
        this.productCode = productCode;
        this.productName = productName;
        this.nonNabhNablRate = nonNabhNablRate;
        this.nabhNablRate = nabhNablRate;
        this.state = state;
    }

    public MedicalRateVO(MedicalRates medicalRates) {
        id = medicalRates.getId();
        productCode = medicalRates.getProductCode();
        productName = medicalRates.getProductName();
        nonNabhNablRate = medicalRates.getNonNabhNablRate();
        nabhNablRate = medicalRates.getNabhNablRate();
        rule = medicalRates.getRule();
        state = medicalRates.getState();
    }
}
