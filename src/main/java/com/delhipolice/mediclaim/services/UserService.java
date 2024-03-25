package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.Role;
import com.delhipolice.mediclaim.model.User;
import com.delhipolice.mediclaim.repositories.RoleRepository;
import com.delhipolice.mediclaim.repositories.UserRepository;
import com.delhipolice.mediclaim.utils.FinancialYearGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public void createAdminUser(String username, String password, Role role) {

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

        adminUser.getRoles().add(role);
        User savedUser = userRepository.save(adminUser);

        role.getUsers().add(savedUser);
        roleRepository.save(role);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
