package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.constants.Relation;
import com.delhipolice.mediclaim.constants.TreatmentBy;
import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.ExpiryDiaryEntryVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "EXPIRY_DIARY_ENTRY")
public class ExpiryDiaryEntry implements Serializable, Auditable, IDiaryEntry {
    private static final long serialVersionUID = 1L;

    public ExpiryDiaryEntry(ExpiryDiaryEntryVO diaryEntryVO) {
        this.id = diaryEntryVO.getId();
        this.tenantId = diaryEntryVO.getTenantId();
        this.diaryNumber = diaryEntryVO.getDiaryNumber();
        this.serialNo = diaryEntryVO.getSerialNo();
        this.diaryType = diaryEntryVO.getDiaryType();
        this.diaryDate = diaryEntryVO.getDiaryDate();
        this.treatmentTakenBy = diaryEntryVO.getTreatmentTakenBy();
        this.applicationSubmittedBy = diaryEntryVO.getApplicationSubmittedBy();
        this.hospital = diaryEntryVO.getHospital();
        this.calculationSheet = diaryEntryVO.getCalculationSheet();
        this.claimDetails = new ClaimDetails(diaryEntryVO.getClaimDetails());
        this.claimType = diaryEntryVO.getClaimType();
        this.amountClaimed = diaryEntryVO.getAmountClaimed();
        this.admissibleAmount = diaryEntryVO.getAdmissibleAmount();
        this.calculationSheetAdjustmentFactor = diaryEntryVO.getCalculationSheetAdjustmentFactor();
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private Integer tenantId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column
    private String diaryNumber;

    @Column
    private String serialNo;

    @Column
    private DiaryType diaryType = DiaryType.INDIVIDUAL;

    @Column
    @Temporal(TemporalType.DATE)
    private Date diaryDate;

    @OneToOne(cascade=CascadeType.ALL)
    private Applicant applicant;

    @Column
    private Relation treatmentTakenBy = Relation.SELF;

    @Column
    private Relation applicationSubmittedBy = Relation.SELF;

    @OneToOne(cascade = CascadeType.MERGE)
    private Hospital hospital;

    @OneToMany(cascade=CascadeType.ALL)
    private List<CalculationSheetEntry> calculationSheet;

    @Embedded
    private ClaimDetails claimDetails;

    @Column
    private ClaimType claimType = ClaimType.EMERGENCY;

    @Column
    private BigDecimal amountClaimed;

    @Column
    private  BigDecimal admissibleAmount;

    private Double calculationSheetAdjustmentFactor;

    private Boolean isDeleted = Boolean.FALSE;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setIsDeleted(boolean b) {
        this.isDeleted = b;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public BigDecimal getAmountClaimed() {
        return amountClaimed == null ? BigDecimal.ZERO: amountClaimed;
    }

    public BigDecimal getAdmissibleAmount() {
        return admissibleAmount == null ? BigDecimal.ZERO: admissibleAmount;
    }
}
