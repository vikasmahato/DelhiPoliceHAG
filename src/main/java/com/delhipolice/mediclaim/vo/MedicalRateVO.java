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
    private Float rate;
    private Float nonNabhNablRate;
    private Float nabhNablRate;



    public MedicalRateVO(Integer productCode, String productName, Float rate) {
        this.productCode = productCode;
        this.productName = productName;
        this.rate = rate;
    }

    public MedicalRateVO(MedicalRates medicalRates) {
        id = medicalRates.getId();
        productCode = medicalRates.getProductCode();
        productName = medicalRates.getProductName();
        nonNabhNablRate = medicalRates.getNonNabhNablRate();
        nabhNablRate = medicalRates.getNabhNablRate();
    }
}
