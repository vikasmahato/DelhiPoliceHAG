package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupAdminUser implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.loadUserByUsername("vikasmahato0@gmail.com");

        } catch (Exception e) {
            String encodedPassword = passwordEncoder.encode("adminPassword");
            userService.createAdminUser("hagcrimebranch1@gmail.com", encodedPassword);
            userService.createAdminUser("hagcrimebranch2@gmail.com", encodedPassword);
            userService.createAdminUser("hagcrimebranch3@gmail.com", encodedPassword);
            userService.createAdminUser("hagcrimebranch@gmail.com", encodedPassword);
        }
    }
}