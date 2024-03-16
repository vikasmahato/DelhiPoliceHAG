package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.constants.ClaimStatus;
import com.delhipolice.mediclaim.constants.HospitalType;
import com.delhipolice.mediclaim.constants.Relation;
import com.delhipolice.mediclaim.model.ClaimDetails;
import com.delhipolice.mediclaim.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDetailsVO{

    private Relation relation = Relation.SELF;
    private String relativeName;
    private String pincode;


    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date startDate;
    
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date endDate;
    private String refHospitalName;
    private String disease;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date expiryDate;
    private BigDecimal amountAvailable;
    private String period;
    private ClaimStatus claimStatus;
    private HospitalType hospitalType;
    private String relativeCghsNumber;
    private Boolean isNewClaim = Boolean.TRUE;
    private BigDecimal amountDue;

    public ClaimDetailsVO(ClaimDetails claimDetails) {
        relation = claimDetails.getRelation();
        relativeName = claimDetails.getRelativeName();
        pincode = claimDetails.getPincode();
        startDate = claimDetails.getStartDate();
        endDate = claimDetails.getEndDate();
        refHospitalName = claimDetails.getRefHospitalName();
        disease = claimDetails.getDisease();
        expiryDate = claimDetails.getExpiryDate();
        amountAvailable = claimDetails.getAmountAvailable();
        period = claimDetails.getPeriod();
        claimStatus = claimDetails.getClaimStatus();
        hospitalType = claimDetails.getHospitalType();
        relativeCghsNumber = claimDetails.getRelativeCghsNumber();
        isNewClaim = ClaimStatus.NEW.equals(claimDetails.getClaimStatus());
        amountDue = claimDetails.getAmountDue();
    }
}
