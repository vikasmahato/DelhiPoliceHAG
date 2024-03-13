package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.User;
import com.delhipolice.mediclaim.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "diary_entry_home";
    }

    @GetMapping("/settings")
    public String settings(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        User user = User.getLoggedInUser();
        model.addAttribute("user", user);
        return "settings_home";
    }

    @PostMapping("/settings")
    public String settingsUpdate(@ModelAttribute User updatedUser) {
        User currentUser = User.getLoggedInUser();
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setDiaryNumberFormat(updatedUser.getDiaryNumberFormat());
        currentUser.setEndorsementFormat(updatedUser.getEndorsementFormat());
        currentUser.setBranchCode(updatedUser.getBranchCode());
        currentUser.setBranchName(updatedUser.getBranchName());
        currentUser.setDateFormat(updatedUser.getDateFormat());
        currentUser.setFundsHead(updatedUser.getFundsHead());
        currentUser.setAddress(updatedUser.getAddress());
        currentUser.setTelephone(updatedUser.getTelephone());
        currentUser.setHealthCheckupAdmissibleAmountMale(updatedUser.getHealthCheckupAdmissibleAmountMale());
        currentUser.setHealthCheckupAdmissibleAmountFemale(updatedUser.getHealthCheckupAdmissibleAmountFemale());
        currentUser.setHealthCheckupFundsHead(updatedUser.getHealthCheckupFundsHead());
        currentUser.setHealthCheckupSop(updatedUser.getHealthCheckupSop());
        currentUser.setDiaryYear(
                StringUtils.isEmpty(updatedUser.getDiaryYear()) ? "__________" : updatedUser.getDiaryYear()
        );

        currentUser.setFinancialYear(
                StringUtils.isEmpty(updatedUser.getFinancialYear()) ? "___________________" : updatedUser.getFinancialYear()
        );


        if(StringUtils.isNotEmpty(updatedUser.getPassword())){
            String encryptedPassword = passwordEncoder.encode(updatedUser.getPassword());
            currentUser.setPassword(encryptedPassword);
        }

        userService.save(currentUser);
        return "redirect:/settings";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}