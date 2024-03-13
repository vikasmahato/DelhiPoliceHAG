package com.delhipolice.mediclaim.vo;

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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ReferralDiaryEntryVO implements IDiaryEntryVO {

    private UUID id;

    private Integer tenantId;

    private String diaryNumber;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date diaryDate;

    private List<ReferralApplicants> referralApplicants = new ArrayList<>();

    private BigDecimal admissibleAmount;
    private BigDecimal allowedAdmissibleAmountMale;
    private BigDecimal allowedAdmissibleAmountFemale;
    private BigDecimal totalAdmissibleAmount;

    private String displayDiaryNumber;
    private String displayEndorsement;
    private String branchCode;
    private String branchName;
    private String financialYear;
    private String diaryYear;
    private String amountGrantedInWords;
    private String fundsHead;

    public ReferralDiaryEntryVO(ReferralDiaryEntry diaryEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        this.id = diaryEntry.getId();
        this.tenantId = diaryEntry.getTenantId();
        this.diaryNumber = diaryEntry.getDiaryNumber();
        this.diaryDate = diaryEntry.getDiaryDate();
        this.displayDiaryNumber = buildDiaryNumber(user);
        this.branchName = user.getBranchName();
        this.branchCode = user.getBranchCode();
        this.referralApplicants = diaryEntry.getReferralApplicants();
        this.fundsHead = user.getHealthCheckupFundsHead();
        this.admissibleAmount = diaryEntry.getAdmissibleAmount();
        this.allowedAdmissibleAmountMale = user.getHealthCheckupAdmissibleAmountMale();
        this.allowedAdmissibleAmountFemale = user.getHealthCheckupAdmissibleAmountFemale();
        financialYear = user.getFinancialYear();
        diaryYear = user.getDiaryYear();
        displayEndorsement = buildDisplayEndorsement(user);
        this.totalAdmissibleAmount = diaryEntry.getReferralApplicants().stream().map(ReferralApplicants::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        amountGrantedInWords = EnglishNumberToWords.convert(totalAdmissibleAmount.doubleValue());
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
}
