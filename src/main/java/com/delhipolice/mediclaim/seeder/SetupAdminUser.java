package com.delhipolice.mediclaim.seeder;

import com.delhipolice.mediclaim.constants.Roles;
import com.delhipolice.mediclaim.model.Role;
import com.delhipolice.mediclaim.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            createRoleIfNotFound(Roles.USER.name());
            createRoleIfNotFound(Roles.ADMIN.name());
            userService.loadUserByUsername("vikasmahato1@gmail.com");

        } catch (Exception e) {
            String encodedPassword = passwordEncoder.encode("adminPassword");
            Role role = roleRepository.findByName(Roles.ADMIN.name());
            userService.createAdminUser("vikasmahato0@gmail.com", encodedPassword, role);
        }
    }
}