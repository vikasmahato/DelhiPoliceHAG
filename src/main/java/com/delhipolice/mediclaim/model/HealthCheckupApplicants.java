package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EnableJpaAuditing
@Table(name = "HEALTH_CHECKUP_APPLICANTS")
public class HealthCheckupApplicants implements Serializable, Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer tenantId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column
    private Integer serialNo;

    @Column
    private String applicantDetails;

    @Column
    private String registerNumber;

    @Column
    private BigDecimal amount;

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }


    public BigDecimal getAmount() {
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
