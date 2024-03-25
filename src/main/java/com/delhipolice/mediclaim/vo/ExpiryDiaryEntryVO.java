package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.*;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.utils.CustomDateDeserializer;
import com.delhipolice.mediclaim.utils.EnglishNumberToWords;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.delhipolice.mediclaim.constants.Gender.MALE;
import static com.delhipolice.mediclaim.constants.Relation.*;

@Getter
@Setter
@NoArgsConstructor
public class ExpiryDiaryEntryVO implements Serializable, IDiaryEntryVO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer tenantId;
    private String diaryNumber;
    private String serialNo;
    private String displayDiaryNumber;
    private String displayName;
    private String patientApplicantDisplay;
    private DiaryType diaryType;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date diaryDate;
    private ApplicantVO applicant;
    private Relation treatmentTakenBy;
    private Hospital hospital;
    private BigDecimal amountClaimed;
    private BigDecimal admissibleAmount;
    private String amountGrantedInWords;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private List<CalculationSheetEntry> calculationSheet;
    private Relation applicationSubmittedBy;
    private ClaimDetailsVO claimDetails;
    private ClaimType claimType;
    private String amountAsked1;
    private String amountGranted1;
    private String notesheetSalutation;
    private Relation inverseRelationToApplicant;
    private Boolean viewMode = Boolean.FALSE;
    private String financialYear;
    private String diaryYear;
    private Boolean isNewClaim = Boolean.FALSE;
    private String patient;
    private String fundsHead;
    private String branchCode;
    private String patientCghs;
    private String dateFormat;
    private String branchAddress;
    private String branchPhoneNo;
    private String displayEndorsement;
    private Gender applicantGender;
    private Gender patientGender;
    private Double calculationSheetAdjustmentFactor;

    public ExpiryDiaryEntryVO(ExpiryDiaryEntry diaryEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        id = diaryEntry.getId();
        serialNo = diaryEntry.getSerialNo();
        diaryNumber = diaryEntry.getDiaryNumber();
        diaryType = diaryEntry.getDiaryType();
        diaryDate = diaryEntry.getDiaryDate();
        applicant = new ApplicantVO(diaryEntry.getApplicant());
        treatmentTakenBy = diaryEntry.getTreatmentTakenBy();
        applicationSubmittedBy = diaryEntry.getApplicationSubmittedBy();
        inverseRelationToApplicant = getInverseRelationToApplicant(diaryEntry.getTreatmentTakenBy());
        hospital = diaryEntry.getHospital();
        claimType = diaryEntry.getClaimType();
        amountClaimed = diaryEntry.getAmountClaimed();
        admissibleAmount = diaryEntry.getAdmissibleAmount();
        displayDiaryNumber = buildDiaryNumber(user);
        displayName = buildDisplayName();
        claimDetails = new ClaimDetailsVO(diaryEntry.getClaimDetails());
        calculationSheet = diaryEntry.getCalculationSheet() == null ? new ArrayList<>() : diaryEntry.getCalculationSheet();
        amountAsked1 = diaryEntry.getAmountClaimed().toString();
        amountGranted1 = diaryEntry.getAdmissibleAmount().toString();
        notesheetSalutation = buildNotesheetSalutation();
        amountGrantedInWords = EnglishNumberToWords.convert(diaryEntry.getAdmissibleAmount().doubleValue());
        financialYear = user.getFinancialYear();
        displayEndorsement = buildDisplayEndorsement(user);
        diaryYear = user.getDiaryYear();
        isNewClaim = claimDetails.getIsNewClaim();
        patient = (Relation.SELF.equals(treatmentTakenBy) ? applicant.getName() : claimDetails.getRelativeName() +  " " + claimDetails.getRelation().getRelation() + " of " + applicant.getName());
        patientCghs = Relation.SELF.equals(treatmentTakenBy) ? applicant.getCghsNumber() : claimDetails.getRelativeCghsNumber();
        patientApplicantDisplay = buildPatientApplicantDisplay();
        fundsHead = user.getFundsHead();
        branchCode = user.getBranchCode();
        dateFormat = user.getDateFormat();
        branchPhoneNo = user.getTelephone();
        branchAddress = user.getAddress();
        applicantGender = Relation.SELF.equals(treatmentTakenBy) ? diaryEntry.getApplicationSubmittedBy().getGender() : diaryEntry.getApplicant().getGender();
        patientGender = Relation.SELF.equals(treatmentTakenBy) ? diaryEntry.getApplicant().getGender() : diaryEntry.getTreatmentTakenBy().getGender();
        calculationSheetAdjustmentFactor = diaryEntry.getCalculationSheetAdjustmentFactor();
    }

    private Relation getInverseRelationToApplicant(Relation treatmentTakenBy) {
        if(!SELF.equals(treatmentTakenBy)) {
            return treatmentTakenBy;
        } else {
            if(WIFE.equals(applicationSubmittedBy)) {
                return HUSBAND;
            }
            if (HUSBAND.equals(applicationSubmittedBy)) {
                return WIFE;
            }
            if(MOTHER.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return SON;
                } else {
                    return DAUGHTER;
                }
            }
            if(FATHER.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return SON;
                } else {
                    return DAUGHTER;
                }
            }
            if(MOTHER_IN_LAW.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return SON;
                } else {
                    return DAUGHTER;
                }
            }
            if(FATHER_IN_LAW.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return SON;
                } else {
                    return DAUGHTER;
                }
            }
            if(BROTHER.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return BROTHER;
                } else {
                    return SISTER;
                }
            }
            if(SISTER.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return BROTHER;
                } else {
                    return SISTER;
                }
            }
            if(SON.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return FATHER;
                } else {
                    return MOTHER;
                }
            }
            if(DAUGHTER.equals(applicationSubmittedBy)) {
                if(MALE.equals(applicant.getGender())) {
                    return FATHER;
                } else {
                    return MOTHER;
                }
            }

        }
        return null;
    }

    private String buildPatientApplicantDisplay() {
        if(SELF.equals(treatmentTakenBy)) {
            // Self expired. So application submitted by relative
            return claimDetails.getRelativeName() +  " " + applicationSubmittedBy.getRelation() + " Late " + buildDisplayName();
        } else {
            return "Late " + claimDetails.getRelativeName() +  " " + treatmentTakenBy.getRelation() + " " + buildDisplayName();
        }
    }

    private String buildNotesheetSalutation() {
        if(TreatmentBy.SELF.equals(treatmentTakenBy)) {
            return "The claimant is a CGHS beneficiary having a valid CGHS card ID No. <b>"+applicant.getCghsNumber()+ "</b>";
        } else {
            return "The claimant and patient are CGHS beneficiary having valid CGHS cards with ID Nos.<b> "+applicant.getCghsNumber()+ "</b> &<b>"+claimDetails.getRelativeCghsNumber()+"</b> respectively";
        }
    }

    private String buildDiaryNumber(User user) {
        String diaryNumberFormat = user.getDiaryNumberFormat();

        SimpleDateFormat formatter = new SimpleDateFormat(user.getDateFormat());
        String formattedDate = formatter.format(diaryDate);


        return diaryNumberFormat.replace("{diaryNumber}", diaryNumber)
                .replace("{diaryDate}", formattedDate);
    }

    private String buildDisplayEndorsement(User user) {
        String diaryYear = user.getDiaryYear();
        String endorsementFormat = user.getEndorsementFormat();
        return endorsementFormat
                .replace("{diaryYear}", diaryYear);
    }

    private String buildDisplayName() {
        return applicant.getRank().getEnumValue() + " " + applicant.getName() + " No. " + applicant.getBeltNumber() + " (PIS No. " + applicant.getPisNumber() + ")";
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Diary Number: " + diaryNumber +
                ", Diary Type: " + diaryType +
                ", Diary Date: " + diaryDate +
                ", Applicant: " + applicant.getName() +
                ", Hospital ID: " + hospital.getId() +
                ", Treatment Taken By: " + treatmentTakenBy;
    }
}
