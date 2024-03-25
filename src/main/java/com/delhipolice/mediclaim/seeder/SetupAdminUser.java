package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.constants.Roles;
import com.delhipolice.mediclaim.model.Tenant;
import com.delhipolice.mediclaim.services.TenantService;
import com.delhipolice.mediclaim.services.UserService;
import com.delhipolice.mediclaim.utils.FinancialYearGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class SetupAdminUser implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private TenantService tenantService;

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.loadUserByUsername("vikasmahato0@gmail.com");

        } catch (Exception e) {
            userService.createAdminUser("vikasmahato0@gmail.com", "adminPassword");
            Tenant tenant = Tenant.builder()
                .tenantName("Crime Branch")
                .diaryNumberFormat("Diary No. {diaryNumber} Genl. Br.(III)/Crime dated {diaryDate}")
                .branchCode("CRIME")
                .branchName("Crime Branch")
                .dateFormat("dd.MM.yyyy")
                .fundsHead("\"01-01.06 Medical treatment\" /Crime")
                .healthCheckupAdmissibleAmountMale(BigDecimal.valueOf(2000))
                .healthCheckupAdmissibleAmountFemale(BigDecimal.valueOf(2200))
                .healthCheckupFundsHead("\"255-Police, 01-01.06 Medical treatment\" /Crime")
                .healthCheckupSop("PHQ Standard Operating Procedure (SOP) No.4201-4350/HAR/PHQ dated 01.10.2021")
                .address("2ND FLOOR, OLD POLICE HEAD QUARTER, MSO BUILDING, ITO, DELHI")
                .telephone("011-20845026")
                .endorsementFormat("No. __________________/Genl/(III)/Crime Dated Delhi, the _____________________/{diaryYear}")
                .financialYear(FinancialYearGenerator.getActualFinancialYear(new Date()))
                .diaryYear(FinancialYearGenerator.getCurrentYear())
                .build();

            Tenant savedTenant = tenantService.save(tenant);

            userService.save(savedTenant.getId(), "hagcrimebranch@gmail.com", "adminPassword");
            userService.save(savedTenant.getId(), "hagcrimebranch1@gmail.com", "adminPassword");
            userService.save(savedTenant.getId(), "hagcrimebranch2@gmail.com", "adminPassword");


        }
    }
}