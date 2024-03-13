package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.constants.TreatmentBy;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.utils.CustomDateDeserializer;
import com.delhipolice.mediclaim.utils.EnglishNumberToWords;
import com.delhipolice.mediclaim.utils.FinancialYearGenerator;
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
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DiaryEntryVO implements Serializable, IDiaryEntryVO {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Integer tenantId;
    private String diaryNumber;
    private String displayDiaryNumber;
    private String displayName;
    private String patientApplicantDisplay;
    private DiaryType diaryType;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date diaryDate;
    private ApplicantVO applicant;
    private TreatmentBy treatmentTakenBy;
    private Hospital hospital;
    private Hospital referHospital;
    private BigDecimal amountClaimed;
    private BigDecimal admissibleAmount;
    private String amountGrantedInWords;
    private String phqNumber;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date phqDate;
    private String sanctionNumber;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date sanctionDate;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date patientDOB;
    private BigDecimal sanctionAmount;
    private Boolean isObjection;
    private List<CalculationSheetEntry> calculationSheet;
    private ClaimDetailsVO claimDetails;
    private ClaimType claimType;
    private String amountAsked1;
    private String amountGranted1;
    private String notesheetSalutation;
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
    private String relationSimple;
    private Boolean isLetterGenerated = Boolean.FALSE;
    private String displayEndorsement;

    public DiaryEntryVO(DiaryEntry diaryEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        id = diaryEntry.getId();
        diaryNumber = diaryEntry.getDiaryNumber();
        diaryType = diaryEntry.getDiaryType();
        diaryDate = diaryEntry.getDiaryDate();
        applicant = new ApplicantVO(diaryEntry.getApplicant());
        treatmentTakenBy = diaryEntry.getTreatmentTakenBy();
        hospital = diaryEntry.getHospital();
        referHospital = diaryEntry.getReferHospital();
        claimType = diaryEntry.getClaimType();
        amountClaimed = diaryEntry.getAmountClaimed();
        admissibleAmount = diaryEntry.getAdmissibleAmount();
        phqNumber = diaryEntry.getPhqNumber();
        phqDate = diaryEntry.getPhqDate();
        sanctionNumber = diaryEntry.getSanctionNumber();
        sanctionDate = diaryEntry.getSanctionDate();
        displayDiaryNumber = buildDiaryNumber(user);
        displayName = buildDisplayName();
        isObjection = diaryEntry.getIsObjection();
        claimDetails = new ClaimDetailsVO(diaryEntry.getClaimDetails());
        calculationSheet = diaryEntry.getCalculationSheet() == null ? new ArrayList<>() : diaryEntry.getCalculationSheet();
        amountAsked1 = getAmountAsked().toString();
        amountGranted1 = getAmountGranted().toString();
        notesheetSalutation = buildNotesheetSalutation();
        sanctionAmount = diaryEntry.getSanctionAmount();
        amountGrantedInWords = EnglishNumberToWords.convert(getAmountGranted());
        financialYear = user.getFinancialYear();
        displayEndorsement = buildDisplayEndorsement(user);
        diaryYear = user.getDiaryYear();
        isNewClaim = claimDetails.getIsNewClaim();
        patient = (claimDetails.getIsExpired() ? "Late " : "") + (TreatmentBy.SELF.equals(treatmentTakenBy) ? applicant.getName() : claimDetails.getRelativeName() +  " " + claimDetails.getRelation().getRelation() + " of " + applicant.getName());
        patientCghs = TreatmentBy.SELF.equals(treatmentTakenBy) ? applicant.getCghsNumber() : claimDetails.getRelativeCghsNumber();
        isLetterGenerated = diaryEntry.getIsLetterGenerated();
        patientApplicantDisplay = buildPatientApplicantDisplay();
        fundsHead = user.getFundsHead();
        branchCode = user.getBranchCode();
        dateFormat = user.getDateFormat();
        branchPhoneNo = user.getTelephone();
        branchAddress = user.getAddress();
        patientDOB = diaryEntry.getPatientDOB();
        relationSimple = TreatmentBy.SELF.equals(treatmentTakenBy) ? "Self" : claimDetails.getRelation().getEnumValue();

    }

    private Double getAmountAsked() {
        return calculationSheet.stream().map(CalculationSheetEntry::getAmountAsked).reduce(0d, Double::sum);
    }

    private Double getAmountGranted() {
        return calculationSheet.stream().map(CalculationSheetEntry::getTotal).reduce(0d, Double::sum);
    }



    private String buildPatientApplicantDisplay() {
        if(TreatmentBy.RELATIVE.equals(treatmentTakenBy)) {
            return (claimDetails.getIsExpired() ? "Late " : "") + claimDetails.getRelativeName() +  " " + claimDetails.getRelation().getRelation() + " " + buildDisplayName();
        } else {
            return (claimDetails.getIsExpired() ? "Late " : "") + buildDisplayName();
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
