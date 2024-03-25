package com.delhipolice.mediclaim.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TENANTS")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String tenantName;

    @Column
    private String diaryNumberFormat;

    @Column
    private String endorsementFormat;

    @Column
    private String branchCode;

    @Column
    private String branchName;

    @Column
    private String dateFormat;

    @Column
    private String fundsHead;

    @Column
    private String address;

    @Column
    private String telephone;

    @Column
    private BigDecimal healthCheckupAdmissibleAmountMale;

    @Column
    private BigDecimal healthCheckupAdmissibleAmountFemale;

    @Column
    private String healthCheckupFundsHead;

    @Column
    private String healthCheckupSop;

    @Column
    private String financialYear;

    @Column
    private String diaryYear;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tenant")
    private Collection<User> users;
}
