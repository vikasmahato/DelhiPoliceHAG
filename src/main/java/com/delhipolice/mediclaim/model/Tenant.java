package com.delhipolice.mediclaim.model;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EnableJpaAuditing
@Table(name = "TENANTS")
public class Tenant implements Serializable {
    private static final long serialVersionUID = 1L;

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
