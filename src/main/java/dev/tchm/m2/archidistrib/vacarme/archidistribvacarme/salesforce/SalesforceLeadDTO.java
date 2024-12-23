package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesforceLeadDTO {
    @JsonProperty("Phone")
    private String phone;
    
    @JsonProperty("FirstName")
    private String firstName;
    
    @JsonProperty("LastName")
    private String lastName;
    
    @JsonProperty("Street")
    private String street;
    
    @JsonProperty("PostalCode")
    private String postalCode;
    
    @JsonProperty("City")
    private String city;
    
    @JsonProperty("Country")
    private String country;
    
    @JsonProperty("Company")
    private String company;
    
    @JsonProperty("State")
    private String state;
    
    @JsonProperty("AnnualRevenue")
    private Integer annualRevenue;
    
    @JsonProperty("CreationDate")
    private String creationDate;
}
