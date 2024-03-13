package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.Relation;
import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "REFERRAL_APPLICANTS")
public class ReferralApplicants implements Serializable, Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

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

    private Relation relation;

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
}
