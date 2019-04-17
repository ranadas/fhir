package com.mongodb.healthcare.fhir.model;


import java.util.Map;

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

    public MyOrganizationModel() {

    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(Map<String, String> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
