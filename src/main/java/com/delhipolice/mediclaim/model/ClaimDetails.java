package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.*;
import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CLAIM_DETAILS")
public class ClaimDetails implements Serializable, Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private Integer tenantId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column
    private String relation;

    @Column
    private String relativeName;

    @Column
    private String pincode;

    @Column
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column
    private String refHospitalName;

    @Column
    private String disease;

    @Column
    @Temporal(TemporalType.DATE)
    private Date applicationDate;

    @Column
    private BigDecimal amountAvailable;

    @Column
    private String period;

    @Column
    private String policeStationNumber;

    @Column
    private String siNumber;

    @Column
    private ClaimStatus claimStatus;

    @Column
    private HospitalType hospitalType;

    @Column
    private String relativeCghsNumber;

    @Column
    @Temporal(TemporalType.DATE)
    private Date relativeCghsexpiry;

    @Column
    private BigDecimal amountDue;

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }
}
