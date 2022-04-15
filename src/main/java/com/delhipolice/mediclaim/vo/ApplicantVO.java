package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.CghsCategory;
import com.delhipolice.mediclaim.constants.Designation;
import com.delhipolice.mediclaim.model.Applicant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ApplicantVO {

    private Long id;
    private String name;
    private String beltNumber;
    private String pisNumber;
    private Designation rank;
    private String cghsNumber;

    @Temporal(TemporalType.DATE)
    private Date cghsExpiry;

    private CghsCategory cghsCategory;

    public ApplicantVO(Applicant applicant) {
        id = applicant.getId();
        name = applicant.getName();
        beltNumber = applicant.getBeltNumber();
        pisNumber = applicant.getPisNumber();
        rank = applicant.getRank();
        cghsNumber = applicant.getCghsNumber();
        cghsCategory = applicant.getCghsCategory();
        cghsExpiry = applicant.getCghsExpiry();
    }
}
