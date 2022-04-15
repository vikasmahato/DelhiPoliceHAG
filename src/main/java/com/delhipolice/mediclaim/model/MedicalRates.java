package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MEDICAL_RATES")
public class MedicalRates implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String productCode;

    @Column
    private String productName;

    @Column
    private Float nonNabhNablRate;

    @Column
    private Float nabhNablRate;

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
}
