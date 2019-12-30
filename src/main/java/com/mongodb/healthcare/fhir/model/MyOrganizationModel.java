package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@NoArgsConstructor
@Getter
@Setter
public class MyOrganizationModel {

    // Org id
    private String organization;
    private String orgId;
    private String code;
    private String name;

    // Org Address
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Contact Points List
    Map<String,String> contactPoints;
}
