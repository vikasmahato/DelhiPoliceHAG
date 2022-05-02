package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.constants.TreatmentBy;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.utils.EnglishNumberToWords;
import com.delhipolice.mediclaim.utils.FinancialYearGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DiaryEntryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Integer tenantId;
    private String diaryNumber;
    private String displayDiaryNumber;
    private String displayName;
    private String displayNameSalutation;
    private DiaryType diaryType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date diaryDate;
    private ApplicantVO applicant;
    private TreatmentBy treatmentTakenBy;
    private Hospital hospital;
    private BigDecimal amountClaimed;
    private  BigDecimal admissibleAmount;
    private String amountGrantedInWords;
    private String phqNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date phqDate;
    private String sanctionNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sanctionDate;
    private Boolean isObjection;
    private List<CalculationSheetEntry> calculationSheet;
    private ClaimDetailsVO claimDetails;
    private ClaimType claimType;
    private String amountAsked1;
    private String amountGranted1;
    private String notesheetSalutation;
    private Boolean viewMode = Boolean.FALSE;
    private String financialYear;
    private Boolean isNewClaim = Boolean.FALSE;

    public DiaryEntryVO(DiaryEntry diaryEntry) {
        id = diaryEntry.getId();
        diaryNumber = diaryEntry.getDiaryNumber();
        diaryType = diaryEntry.getDiaryType();
        diaryDate = diaryEntry.getDiaryDate();
        applicant = new ApplicantVO(diaryEntry.getApplicant());
        treatmentTakenBy = diaryEntry.getTreatmentTakenBy();
        hospital = diaryEntry.getHospital();
        claimType = diaryEntry.getClaimType();
        amountClaimed = diaryEntry.getAmountClaimed();
        admissibleAmount = diaryEntry.getAdmissibleAmount();
        phqNumber = diaryEntry.getPhqNumber();
        phqDate = diaryEntry.getPhqDate();
        sanctionNumber = diaryEntry.getSanctionNumber();
        sanctionDate = diaryEntry.getSanctionDate();
        displayDiaryNumber = buildDiaryNumber();
        displayName = buildDisplayName();
        isObjection = diaryEntry.getIsObjection();
        claimDetails = new ClaimDetailsVO(diaryEntry.getClaimDetails());
        calculationSheet = diaryEntry.getCalculationSheet() == null ? new ArrayList<>() : diaryEntry.getCalculationSheet();
        displayNameSalutation = buildDispplayNameSalutation();
        amountAsked1 = getAmountAsked().toString();
        amountGranted1 = getAmountGranted().toString();
        notesheetSalutation = buildNotesheetSalutation();
        amountGrantedInWords = EnglishNumberToWords.convert(getAmountGranted());
        financialYear = FinancialYearGenerator.getActualFinancialYear(diaryEntry.getDiaryDate());
        isNewClaim = claimDetails.getIsNewClaim();
    }

    private Double getAmountAsked() {
        return calculationSheet.stream().map(CalculationSheetEntry::getAmountAsked).reduce(0d, Double::sum);
    }

    private Double getAmountGranted() {
        return calculationSheet.stream().map(CalculationSheetEntry::getTotal).reduce(0d, Double::sum);
    }

    private String buildDispplayNameSalutation() {
        if(TreatmentBy.RELATIVE.equals(treatmentTakenBy)) {
            return claimDetails.getRelativeName() +  " (Name of the patient) " + claimDetails.getRelation().getRelation() + " " + buildDisplayName() +" (Name of the police officer/men) ";
        } else {
            return buildDisplayName();
        }
    }

    private String buildNotesheetSalutation() {
        if(TreatmentBy.SELF.equals(treatmentTakenBy)) {
            return "the claimant is a CGHS beneficiary having token card No. <b>"+applicant.getCghsNumber()+ "</b>";
        } else {
            return "the claimant and patient are CGHS beneficiary having token cards No.<b> "+applicant.getCghsNumber()+ "</b> &<b>"+claimDetails.getRelativeCghsNumber()+"</b> respectively";
        }
    }

    private String buildDiaryNumber() {
        return diaryNumber + "/Gen Br./SD/Dated/" + diaryDate;
    }

    private String buildDisplayName() {
        return applicant.getRank().getEnumValue() + " " + applicant.getName() + " No. " + applicant.getBeltNumber();
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
