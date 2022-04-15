package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.model.Hospital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HospitalVO {

    private Long id;
    private String name;
    private String address;
    private String state;
    private String city;
    private String pincode;

    public HospitalVO(Hospital hospital) {
        id = hospital.getId();
        name = hospital.getHospitalName();
        address = hospital.getHospitalAddress().getAddressLine();
        state = hospital.getHospitalAddress().getState();
        city = hospital.getHospitalAddress().getCity();
        pincode = hospital.getHospitalAddress().getPincode();
    }
}
