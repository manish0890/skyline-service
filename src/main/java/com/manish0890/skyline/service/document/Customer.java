package com.manish0890.skyline.service.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class Customer extends BaseDocument {
    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    private String city;

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = "Zip Code cannot be empty!")
    private String zipcode;

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = "Phone number cannot be empty!")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
