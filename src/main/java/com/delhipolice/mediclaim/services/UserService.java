package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.User;
import com.delhipolice.mediclaim.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void createAdminUser(String username, String password) {

        User adminUser = new User();
        adminUser.setUsername(username);
        adminUser.setPassword(password);
        adminUser.setDiaryNumberFormat("Diary No. {diaryNumber} Genl. Br.(III)/Crime dated {diaryDate}");
        adminUser.setBranchCode("CRIME");
        adminUser.setBranchName("Crime Branch");
        adminUser.setDateFormat("dd.MM.yyyy");
        adminUser.setFundsHead("\"01-01.06 Medical treatment\" /Crime");
        adminUser.setHealthCheckupAdmissibleAmount(BigDecimal.valueOf(2000));
        adminUser.setHealthCheckupFundsHead("\"255-Police, 01-01.06 Medical treatment\" /Crime");
        adminUser.setHealthCheckupSop("PHQ Standard Operating Procedure (SOP) No.4201-4350/HAR/PHQ dated 01.10.2021");
        adminUser.setAddress("2nd Floor, Old Police Head Quarter, MSO Building, ITO, Delhi");
        adminUser.setTelephone("011-20845026");
        userRepository.save(adminUser);
    }
}
