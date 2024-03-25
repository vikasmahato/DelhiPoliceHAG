package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {

    private Long id;
    private String username;
    private String diaryNumberFormat;
    private String endorsementFormat;
    private String branchCode;
    private String branchName;
    private String dateFormat;
    private String fundsHead;
    private String address;
    private String telephone;
    private String healthCheckupFundsHead;
    private String healthCheckupSop;
    private String financialYear;
    private String diaryYear;
    private Long tenantId;

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.diaryNumberFormat = user.getDiaryNumberFormat();
        this.endorsementFormat = user.getEndorsementFormat();
        this.branchCode = user.getBranchCode();
        this.branchName = user.getBranchName();
        this.dateFormat = user.getDateFormat();
        this.fundsHead = user.getFundsHead();
        this.address = user.getAddress();
        this.telephone = user.getTelephone();
        this.healthCheckupFundsHead = user.getHealthCheckupFundsHead();
        this.healthCheckupSop = user.getHealthCheckupSop();
        this.financialYear = user.getFinancialYear();
        this.diaryYear = user.getDiaryYear();
        this.tenantId = user.getTenant().getId();
    }

}
