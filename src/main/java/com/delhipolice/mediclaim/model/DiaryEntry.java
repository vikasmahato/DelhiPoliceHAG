package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.constants.TreatmentBy;
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
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "DIARY_ENTRY")
public class DiaryEntry implements Serializable, Auditable, IDiaryEntry {
    private static final long serialVersionUID = 1L;

    public DiaryEntry(DiaryEntryVO diaryEntryVO) {
        this.id = diaryEntryVO.getId();
        this.tenantId = diaryEntryVO.getTenantId();
        this.diaryNumber = diaryEntryVO.getDiaryNumber();
        this.serialNo = diaryEntryVO.getSerialNo();
        this.diaryType = diaryEntryVO.getDiaryType();
        this.diaryDate = diaryEntryVO.getDiaryDate();
        this.treatmentTakenBy = diaryEntryVO.getTreatmentTakenBy();
        this.hospital = diaryEntryVO.getHospital();
        this.referHospital = diaryEntryVO.getReferHospital();
        this.amountClaimed = diaryEntryVO.getAmountClaimed();
        this.admissibleAmount = diaryEntryVO.getAdmissibleAmount();
        this.calculationSheet = diaryEntryVO.getCalculationSheet();
        this.claimDetails = new ClaimDetails(diaryEntryVO.getClaimDetails());
        this.claimType = diaryEntryVO.getClaimType();
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
    private DiaryType diaryType;

    @Column
    @Temporal(TemporalType.DATE)
    private Date diaryDate;

    @OneToOne(cascade=CascadeType.ALL)
    private Applicant applicant;

    @Column
    private TreatmentBy treatmentTakenBy = TreatmentBy.SELF;

    @OneToOne(cascade = CascadeType.MERGE)
    private Hospital hospital;

    @OneToOne(cascade = CascadeType.MERGE)
    private Hospital referHospital;

    @OneToMany(cascade=CascadeType.ALL)
    private List<CalculationSheetEntry> calculationSheet;

    @Embedded
    private ClaimDetails claimDetails;

    @Column
    private ClaimType claimType = ClaimType.REFERRAL;

    @Column
    private BigDecimal amountClaimed;

    @Column
    private  BigDecimal admissibleAmount;

    @Column
    private Double calculationSheetAdjustmentFactor;

    @Column
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
