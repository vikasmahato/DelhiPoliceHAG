package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.model.Tenant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TenantVO {

    private Long id;
    private String tenantName;
    private String diaryNumberFormat;
    private String endorsementFormat;
    private String branchCode;
    private String branchName;
    private String dateFormat;
    private String fundsHead;
    private String address;
    private String telephone;
    private String healthCheckupFundsHead;
    private BigDecimal healthCheckupAdmissibleAmountMale;
    private BigDecimal healthCheckupAdmissibleAmountFemale;
    private String healthCheckupSop;
    private String financialYear;
    private String diaryYear;
    private Long tenantId;

    private List<UserVO> users = new ArrayList<>();

    public TenantVO(Tenant tenant) {
        this.id = tenant.getId();
        this.tenantName = tenant.getTenantName();
        this.diaryNumberFormat = tenant.getDiaryNumberFormat();
        this.endorsementFormat = tenant.getEndorsementFormat();
        this.branchCode = tenant.getBranchCode();
        this.branchName = tenant.getBranchName();
        this.dateFormat = tenant.getDateFormat();
        this.fundsHead = tenant.getFundsHead();
        this.address = tenant.getAddress();
        this.telephone = tenant.getTelephone();
        this.healthCheckupFundsHead = tenant.getHealthCheckupFundsHead();
        this.healthCheckupSop = tenant.getHealthCheckupSop();
        this.financialYear = tenant.getFinancialYear();
        this.diaryYear = tenant.getDiaryYear();
        this.healthCheckupAdmissibleAmountMale = tenant.getHealthCheckupAdmissibleAmountMale();
        this.healthCheckupAdmissibleAmountFemale = tenant.getHealthCheckupAdmissibleAmountFemale();
        this.users = tenant.getUsers().stream().map(UserVO::new).collect(Collectors.toList());
    }

}
