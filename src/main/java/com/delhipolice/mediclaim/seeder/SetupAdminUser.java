package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.constants.Roles;
import com.delhipolice.mediclaim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupAdminUser implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.loadUserByUsername("vikasmahato1@gmail.com");

        } catch (Exception e) {
            userService.createAdminUser("vikasmahato0@gmail.com", "adminPassword");
        }
    }
}