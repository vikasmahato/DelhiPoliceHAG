package com.delhipolice.mediclaim.controllers;

import com.delhipolice.mediclaim.model.Tenant;
import com.delhipolice.mediclaim.model.User;
import com.delhipolice.mediclaim.services.TenantService;
import com.delhipolice.mediclaim.services.UserService;
import com.delhipolice.mediclaim.vo.TenantVO;
import com.delhipolice.mediclaim.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TenantService tenantService;

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

    @GetMapping("/tenants")
    @PreAuthorize("hasRole('ADMIN')")
    public String tenants(Model model) {
        model.addAttribute("tenants", tenantService.findAll());
        return "tenants_home";
    }

    @GetMapping(value= "/tenants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody TenantVO  findTenant(Model model, @PathVariable Long id) {
        Tenant tenant = tenantService.find(id);
        return new TenantVO(tenant);
    }

    @PostMapping("/tenantSave")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Tenant save(Model model, Tenant tenant) {
        return tenantService.save(tenant);
    }

    @PostMapping("/userSave")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody UserVO saveUser(Model model, @RequestParam Long tenantId, @RequestParam String username, @RequestParam String password) {
        return new UserVO( userService.save(tenantId, username, password));
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
    public String showLoginPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException exception = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (exception != null) {
                model.addAttribute("error", exception.getMessage());
            }
        }
        return "login";
    }

}