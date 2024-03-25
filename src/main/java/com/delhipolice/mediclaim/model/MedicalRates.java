package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.MedicalRateVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EnableJpaAuditing
@Table(name = "MEDICAL_RATES")
public class MedicalRates implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private Integer productCode;

    @Column
    private String productName;

    @Column
    private Float nonNabhNablRate;

    @Column
    private Float nabhNablRate;

    @Column
    private String rule;

    @Column
    private String state;

    @Column
    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column
    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public MedicalRates(MedicalRateVO medicalRateVO) {
        productCode = medicalRateVO.getProductCode();
        productName = medicalRateVO.getProductName();
        nonNabhNablRate = medicalRateVO.getNonNabhNablRate();
        nabhNablRate = medicalRateVO.getNabhNablRate();
        rule = medicalRateVO.getRule();
        state = medicalRateVO.getState();
    }


}
