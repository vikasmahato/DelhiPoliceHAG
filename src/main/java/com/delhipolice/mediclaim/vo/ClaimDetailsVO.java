package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.ClaimStatus;
import com.delhipolice.mediclaim.constants.HospitalType;
import com.delhipolice.mediclaim.constants.Relation;
import com.delhipolice.mediclaim.model.ClaimDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDetailsVO{

    private Relation relation = Relation.SELF;
    private String relativeName;
    private String pincode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String refHospitalName;
    private String disease;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applicationDate;
    private BigDecimal amountAvailable;
    private String period;
    private ClaimStatus claimStatus;
    private HospitalType hospitalType;
    private String relativeCghsNumber;
    private Boolean isNewClaim = Boolean.TRUE;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date relativeCghsexpiry;
    private BigDecimal amountDue;

    public ClaimDetailsVO(ClaimDetails claimDetails) {
        relation = claimDetails.getRelation();
        relativeName = claimDetails.getRelativeName();
        pincode = claimDetails.getPincode();
        startDate = claimDetails.getStartDate();
        endDate = claimDetails.getEndDate();
        refHospitalName = claimDetails.getRefHospitalName();
        disease = claimDetails.getDisease();
        applicationDate = claimDetails.getApplicationDate();
        amountAvailable = claimDetails.getAmountAvailable();
        period = claimDetails.getPeriod();
        claimStatus = claimDetails.getClaimStatus();
        hospitalType = claimDetails.getHospitalType();
        relativeCghsNumber = claimDetails.getRelativeCghsNumber();
        relativeCghsexpiry = claimDetails.getRelativeCghsexpiry();
        isNewClaim = ClaimStatus.NEW.equals(claimDetails.getClaimStatus());
        amountDue = claimDetails.getAmountDue();
    }
}
