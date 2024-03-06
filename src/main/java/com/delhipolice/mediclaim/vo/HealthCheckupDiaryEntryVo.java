package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.model.HealthCheckupApplicants;
import com.delhipolice.mediclaim.model.HealthCheckupDiaryEntry;
import com.delhipolice.mediclaim.model.User;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HealthCheckupDiaryEntryVo {

    private UUID id;
    
    private Integer tenantId;
    
    private String diaryNumber;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date diaryDate;
    
    private List<HealthCheckupApplicants> healthCheckupApplicants = new ArrayList<>();
    
    private BigDecimal admissibleAmount;
    private BigDecimal allowedAdmissibleAmount;
    private String healthCheckupFundsHead;
    private String healthCheckupSop;
    private BigDecimal totalAdmissibleAmount;

    private String displayDiaryNumber;
    private String branchCode;
    private String branchName;
    private String financialYear;
    private String amountGrantedInWords;

    public HealthCheckupDiaryEntryVo(HealthCheckupDiaryEntry diaryEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        this.id = diaryEntry.getId();
        this.tenantId = diaryEntry.getTenantId();
        this.diaryNumber = diaryEntry.getDiaryNumber();
        this.diaryDate = diaryEntry.getDiaryDate();
        this.displayDiaryNumber = buildDiaryNumber(user);
        this.branchName = user.getBranchCode();
        this.branchCode = user.getBranchName();
        this.healthCheckupApplicants = diaryEntry.getHealthCheckupApplicants();
        this.admissibleAmount = diaryEntry.getAdmissibleAmount();
        this.allowedAdmissibleAmount = user.getHealthCheckupAdmissibleAmount();
        this.healthCheckupFundsHead = user.getHealthCheckupFundsHead();
        financialYear = FinancialYearGenerator.getActualFinancialYear(diaryEntry.getDiaryDate());
        this.healthCheckupSop = user.getHealthCheckupSop();
        this.totalAdmissibleAmount = diaryEntry.getHealthCheckupApplicants().stream().map(HealthCheckupApplicants::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        amountGrantedInWords = EnglishNumberToWords.convert(totalAdmissibleAmount.doubleValue());
    }

    private String buildDiaryNumber(User user) {
        String diaryNumberFormat = user.getDiaryNumberFormat();

        SimpleDateFormat formatter = new SimpleDateFormat(user.getDateFormat());
        String formattedDate = formatter.format(diaryDate);


        return diaryNumberFormat.replace("{diaryNumber}", diaryNumber)
                .replace("{diaryDate}", formattedDate);
    }
}
