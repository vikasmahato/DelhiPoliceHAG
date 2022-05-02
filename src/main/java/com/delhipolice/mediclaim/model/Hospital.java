package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.constants.HospitalType;
import com.delhipolice.mediclaim.model.audit.AuditSection;
import com.delhipolice.mediclaim.model.audit.Auditable;
import com.delhipolice.mediclaim.vo.HospitalVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "HOSPITALS")
public class Hospital implements Serializable, Auditable {

    private static final long serialVersionUID = 1L;

    public Hospital(HospitalVO hospitalVO) {
        this.hospitalName = hospitalVO.getName();
        this.hospitalType = hospitalVO.getType();
        Address address = new Address(hospitalVO.getAddress(), hospitalVO.getCity(), hospitalVO.getState(), hospitalVO.getPincode());
        this.hospitalAddress = address;
    }

    public Hospital(String hospitalName, Address hospitalAddress) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String hospitalName;

    @Embedded
    private Address hospitalAddress;

    @Column
    private HospitalType hospitalType;

    @Column String description;

    @Column
    private Integer tenantId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }
}
