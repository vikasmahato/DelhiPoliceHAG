package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.LetterVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "LETTER")
public class Letter implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    public Letter(LetterVO letterVO) {
        this.id = letterVO.getId();
        this.auditSection = letterVO.getAuditSection();
        this.letterDate = letterVO.getLetterDate();
    }

    public Letter(List<DiaryEntry> diaryEntries) {
        this.diaryEntries = diaryEntries;
        this.letterDate = new Date();
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private Integer tenantId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column
    @Temporal(TemporalType.DATE)
    private Date letterDate;

    @OneToMany
    private List<DiaryEntry> diaryEntries;

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }
}
