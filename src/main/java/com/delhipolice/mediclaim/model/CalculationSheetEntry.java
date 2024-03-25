package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EnableJpaAuditing
@Table(name = "CALCULATION_SHEET")
public class CalculationSheetEntry implements Serializable, Auditable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private Integer tenantId;

    @Column
    private String serialNumber;

    @Column
    private String treatment;

    @Column
    private Double total;

    @Column
    private Integer displaySerialNumber;

    @Column
    private String billNumber;

    @Column
    private String billDate;

    @Column
    private Boolean isAdjustment = Boolean.FALSE;

    @Column
    private Double amountAsked;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }


    public Double getTotal() {
        return total == null? 0.0: total;
    }

    public Double getAmountAsked() {
        return amountAsked == null ? 0.0: amountAsked;
    }

    public Boolean getAdjustment() {
        return isAdjustment == null ? Boolean.FALSE: isAdjustment;
    }
}
