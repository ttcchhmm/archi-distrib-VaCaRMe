package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadDTO {
    private String phone;
    private String firstName;
    private String lastName;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String company;
    private String state;
    private Integer annualRevenue;
    private String creationDate;
    private String source;
}
