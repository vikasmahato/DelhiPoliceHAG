package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.HealthCheckupDiaryEntryVo;
import com.delhipolice.mediclaim.vo.ReferralDiaryEntryVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "REFERRAL_DIARY_ENTRY")
public class ReferralDiaryEntry implements Serializable, Auditable, IDiaryEntry {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private Integer tenantId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column
    @Temporal(TemporalType.DATE)
    private Date diaryDate;

    @OneToMany(cascade=CascadeType.ALL)
    private List<ReferralApplicants> referralApplicants = new ArrayList<>();

    @Column
    private  BigDecimal admissibleAmount;

    @Override
    public String getDiaryNumber() {
        return "";
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public ReferralDiaryEntry(ReferralDiaryEntryVO vo) {
        this.id = vo.getId();
        this.tenantId = vo.getTenantId();
        this.diaryDate = vo.getDiaryDate();
        this.admissibleAmount = vo.getAdmissibleAmount();
        this.referralApplicants = vo.getReferralApplicants();
    }
}
