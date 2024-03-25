package com.delhipolice.mediclaim.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<GrantedAuthority> authorities;

    private String diaryNumberFormat;

    private String endorsementFormat;

    private String branchCode;

    private String branchName;

    private String dateFormat;

    private String fundsHead;

    private String address;

    private String telephone;

    private BigDecimal healthCheckupAdmissibleAmountMale;
    private BigDecimal healthCheckupAdmissibleAmountFemale;

    private String healthCheckupFundsHead;

    private String healthCheckupSop;

    private String financialYear;

    private String diaryYear;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tenant tenant;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return (User) userDetails;
    }
}