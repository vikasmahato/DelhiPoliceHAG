package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.ReferralDiaryEntryVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EnableJpaAuditing
@Table(name = "REFERRAL_DIARY_ENTRY")
public class ReferralDiaryEntry implements Serializable, Auditable, IDiaryEntry {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

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

    @Column
    private Boolean isDeleted = Boolean.FALSE;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Column
    private String deletedBy;

    @Override
    public String getDiaryNumber() {
        return "";
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setIsDeleted(boolean b) {
        this.isDeleted = b;
    }

    @Override
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Override
    public void setAmountClaimed(BigDecimal totalAmountClaimed) {

    }

    @Override
    public void setCalculationSheetAdjustmentFactor(Double adjustmentFactor) {

    }

    @Override
    public void setCalculationSheet(List<CalculationSheetEntry> calculationSheetEntries) {

    }

    @Override
    public void setSerialNumberCalculationSheet(List<SerialNumberCalculationSheet> serialNumberCalculationSheets) {

    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public ReferralDiaryEntry(ReferralDiaryEntryVO vo) {
        this.id = vo.getId();
        this.tenantId = vo.getTenantId();
        this.diaryDate = vo.getDiaryDate() == null ? new Date() : vo.getDiaryDate();
        this.admissibleAmount = vo.getAdmissibleAmount();
        this.referralApplicants = vo.getReferralApplicants();
    }
}
