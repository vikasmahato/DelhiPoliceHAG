package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.CghsCategory;
import com.delhipolice.mediclaim.constants.Designation;
import com.delhipolice.mediclaim.constants.Gender;
import com.delhipolice.mediclaim.model.Applicant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private CghsCategory cghsCategory;
    private Gender gender;

    public ApplicantVO(Applicant applicant) {
        id = applicant.getId();
        name = applicant.getName();
        beltNumber = applicant.getBeltNumber();
        pisNumber = applicant.getPisNumber();
        rank = applicant.getDesignation();
        cghsNumber = applicant.getCghsNumber();
        cghsCategory = applicant.getCghsCategory();
        gender = applicant.getGender();
    }
}
