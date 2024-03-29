package com.delhipolice.mediclaim.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    private String addressLine;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String pincode;

    public Address(String addressLine) {
        this.addressLine = addressLine;
    }

    public Address(String addressLine, String city, String state, String pincode) {
        this.addressLine = addressLine;
        this.state = state;
        this.pincode = pincode;
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(addressLine);
        if(StringUtils.isNotBlank(city))
            sb.append(", ").append(city);
        if(StringUtils.isNotBlank(state))
            sb.append(", ").append(state);
        if(StringUtils.isNotBlank(pincode))
            sb.append(" - ").append(pincode);
        return sb.toString();
    }
}
