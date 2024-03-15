package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.*;
import com.delhipolice.mediclaim.utils.CustomDateDeserializer;
import com.delhipolice.mediclaim.vo.ClaimDetailsVO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ClaimDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private Relation relation = Relation.SELF;

    @Column
    private String relativeName;

    @Column
    private String pincode;

    @Column
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column
    private String refHospitalName;

    @Column
    private String disease;

    @Column
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Column
    private BigDecimal amountAvailable;

    @Column
    private String period;

    @Column
    private ClaimStatus claimStatus  = ClaimStatus.NEW;

    @Column
    private HospitalType hospitalType;

    @Column
    private String relativeCghsNumber;

    @Column
    private BigDecimal amountDue;

    @Column
    private Boolean isExpired = Boolean.FALSE;

    public ClaimDetails(ClaimDetailsVO claimDetailsVO) {
        relation = claimDetailsVO.getRelation();
        relativeName = claimDetailsVO.getRelativeName();
        pincode = claimDetailsVO.getPincode();
        startDate = claimDetailsVO.getStartDate();
        endDate = claimDetailsVO.getEndDate();
        refHospitalName = claimDetailsVO.getRefHospitalName();
        disease = claimDetailsVO.getDisease();
        expiryDate = claimDetailsVO.getExpiryDate();
        amountAvailable = claimDetailsVO.getAmountAvailable();
        period = claimDetailsVO.getPeriod();
        claimStatus = claimDetailsVO.getClaimStatus();
        hospitalType = claimDetailsVO.getHospitalType();
        relativeCghsNumber = claimDetailsVO.getRelativeCghsNumber();
        amountDue = claimDetailsVO.getAmountDue();
        isExpired = claimDetailsVO.getIsExpired();
    }
}
