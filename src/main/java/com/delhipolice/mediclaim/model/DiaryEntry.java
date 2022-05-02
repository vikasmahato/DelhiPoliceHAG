package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.CaseType;
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
public class DiaryEntry implements Serializable, Auditable {
    private static final long serialVersionUID = 1L;

    public DiaryEntry(DiaryEntryVO diaryEntryVO) {
        this.id = diaryEntryVO.getId();
        this.tenantId = diaryEntryVO.getTenantId();
        this.diaryNumber = diaryEntryVO.getDiaryNumber();
        this.diaryType = diaryEntryVO.getDiaryType();
        this.diaryDate = diaryEntryVO.getDiaryDate();
        this.treatmentTakenBy = diaryEntryVO.getTreatmentTakenBy();
        this.hospital = diaryEntryVO.getHospital();
        this.amountClaimed = diaryEntryVO.getAmountClaimed();
        this.admissibleAmount = diaryEntryVO.getAdmissibleAmount();
        this.phqNumber = diaryEntryVO.getPhqNumber();
        this.phqDate = diaryEntryVO.getPhqDate();
        this.sanctionNumber = diaryEntryVO.getSanctionNumber();
        this.sanctionDate = diaryEntryVO.getSanctionDate();
        this.isObjection = diaryEntryVO.getIsObjection();
        this.calculationSheet = diaryEntryVO.getCalculationSheet();
        this.claimDetails = new ClaimDetails(diaryEntryVO.getClaimDetails());
        this.claimType = diaryEntryVO.getClaimType();
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

    @OneToMany(cascade=CascadeType.ALL)
    private List<CalculationSheetEntry> calculationSheet;

    @Embedded
    private ClaimDetails claimDetails;

    @Column
    private ClaimType claimType = ClaimType.OP_REFERRAL;

    @Column
    private BigDecimal amountClaimed;

    @Column
    private  BigDecimal admissibleAmount;

    @Column
    private String phqNumber;

    @Column
    @Temporal(TemporalType.DATE)
    private Date phqDate;

    @Column
    private String sanctionNumber;

    @Column
    @Temporal(TemporalType.DATE)
    private Date sanctionDate;

    @Column
    private Boolean isObjection;

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }
}
