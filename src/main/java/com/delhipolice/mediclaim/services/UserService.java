package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.Roles;
import com.delhipolice.mediclaim.model.Tenant;
import com.delhipolice.mediclaim.model.User;
import com.delhipolice.mediclaim.repositories.UserRepository;
import com.delhipolice.mediclaim.utils.FinancialYearGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TenantService tenantService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        user.getAuthorities();
        return user;
    }

    public void createAdminUser(String username, String password) {

        password = passwordEncoder.encode(password);

        User adminUser = userRepository.findByUsername(username);
        if(adminUser == null) {
            adminUser = new User();
        }
        adminUser.setUsername(username);
        adminUser.setPassword(password);
        adminUser.setDiaryNumberFormat("Diary No. {diaryNumber} Genl. Br.(III)/Crime dated {diaryDate}");
        adminUser.setBranchCode("CRIME");
        adminUser.setBranchName("Crime Branch");
        adminUser.setDateFormat("dd.MM.yyyy");
        adminUser.setFundsHead("\"01-01.06 Medical treatment\" /Crime");
        adminUser.setHealthCheckupAdmissibleAmountMale(BigDecimal.valueOf(2000));
        adminUser.setHealthCheckupAdmissibleAmountFemale(BigDecimal.valueOf(2200));
        adminUser.setHealthCheckupFundsHead("\"255-Police, 01-01.06 Medical treatment\" /Crime");
        adminUser.setHealthCheckupSop("PHQ Standard Operating Procedure (SOP) No.4201-4350/HAR/PHQ dated 01.10.2021");
        adminUser.setAddress("2ND FLOOR, OLD POLICE HEAD QUARTER, MSO BUILDING, ITO, DELHI");
        adminUser.setTelephone("011-20845026");
        adminUser.setEndorsementFormat("No. __________________/Genl/(III)/Crime Dated Delhi, the _____________________/{diaryYear}");
        adminUser.setFinancialYear(FinancialYearGenerator.getActualFinancialYear(new Date()));
        adminUser.setDiaryYear(FinancialYearGenerator.getCurrentYear());

        adminUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        User savedUser = userRepository.save(adminUser);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User save(Long tenantId, String username, String password) {
        Tenant tenant = tenantService.find(tenantId);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setDiaryNumberFormat(tenant.getDiaryNumberFormat());
        user.setBranchCode(tenant.getBranchCode());
        user.setBranchName(tenant.getBranchName());
        user.setDateFormat(tenant.getDateFormat());
        user.setFundsHead(tenant.getFundsHead());
        user.setHealthCheckupAdmissibleAmountMale(tenant.getHealthCheckupAdmissibleAmountMale());
        user.setHealthCheckupAdmissibleAmountFemale(tenant.getHealthCheckupAdmissibleAmountFemale());
        user.setHealthCheckupFundsHead(tenant.getHealthCheckupFundsHead());
        user.setHealthCheckupSop(tenant.getHealthCheckupSop());
        user.setAddress(tenant.getAddress());
        user.setTelephone(tenant.getTelephone());
        user.setEndorsementFormat(tenant.getEndorsementFormat());
        user.setFinancialYear(FinancialYearGenerator.getActualFinancialYear(new Date()));
        user.setDiaryYear(FinancialYearGenerator.getCurrentYear());

        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("USER")));

        user.setTenant(tenant);
        tenant.getUsers().add(user);

        User savedUser = userRepository.save(user);
        tenantService.save(tenant);

        return savedUser;
    }
}
