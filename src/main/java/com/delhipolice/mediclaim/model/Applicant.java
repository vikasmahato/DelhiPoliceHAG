package com.delhipolice.mediclaim.model;


import com.delhipolice.mediclaim.constants.CghsCategory;
import com.delhipolice.mediclaim.constants.Designation;
import com.delhipolice.mediclaim.constants.Gender;
import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.ApplicantVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EnableJpaAuditing
@Table(name = "APPLICANTS")
public class Applicant implements Serializable, Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String beltNumber;

    @Column
    private String pisNumber;

    @Column
    private Designation designation;

    @Column
    private Integer tenantId;

    @Column
    private String cghsNumber;

    @Column
    private CghsCategory cghsCategory;

    @Column
    private Gender gender;

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

    public Applicant(ApplicantVO applicantVO) {
        this.id = applicantVO.getId();
        this.name = applicantVO.getName();
        this.beltNumber = applicantVO.getBeltNumber();
        this.pisNumber = applicantVO.getPisNumber();
        this.designation = applicantVO.getRank();
        this.cghsNumber = applicantVO.getCghsNumber();
        this.cghsCategory = applicantVO.getCghsCategory();
        this.gender = applicantVO.getGender();
    }
}
