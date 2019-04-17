package com.mongodb.healthcare.fhir.model;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "patients")
public class MyPatientModel {

    // ObjectId
    private String id;

    // Bundle Parsed Timestamp
    private Date dateBundleParsed;

    // Patient Id
    private String patientId;

    // Name
    private String prefix;
    private String firstName;
    private String lastName;

    // Address
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;
    private AddressLocation addressLocation;

    // Gender
    private String gender;

    // Birth date
    private java.util.Date birthDate;

    // Deceased
    private boolean isDeceased;
    private java.util.Date dateDeceased;

    // Marital Status
    private String maritalStatus;

    // Language
    private String language;

    // Contact Points List
    List<MyContactPointModel> contactPoints;

    // Identifiers
    List<MyIdentifierModel> identifiers;

    // Organizations
    List<MyOrganizationModel> organizations;

    // Encounters
    List<MyEncounterModel> encounters;

    // Practitioners
    List<MyPractitionerModel> practioners;

    // Conditions
    List<MyConditionModel> conditions;

    // Observations
    List<MyObservationModel> observations;

    // MedicationRequest
    List<MyMedicationRequest> medicationRequests;

    // Procedures
    List<MyProcedureModel> procedures;


    /**
     *
     */
    public MyPatientModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDateBundleParsed() {
        return dateBundleParsed;
    }

    public void setDateBundleParsed(Date dateBundleParsed) {
        this.dateBundleParsed = dateBundleParsed;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public AddressLocation getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(AddressLocation addressLocation) {
        this.addressLocation = addressLocation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isDeceased() {
        return isDeceased;
    }

    public void setDeceased(boolean deceased) {
        isDeceased = deceased;
    }

    public Date getDateDeceased() {
        return dateDeceased;
    }

    public void setDateDeceased(Date dateDeceased) {
        this.dateDeceased = dateDeceased;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public List<MyOrganizationModel> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<MyOrganizationModel> organizations) {
        this.organizations = organizations;
    }

    public List<MyEncounterModel> getEncounters() {
        return encounters;
    }

    public void setEncounters(List<MyEncounterModel> encounters) {
        this.encounters = encounters;
    }

    public List<MyContactPointModel> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(List<MyContactPointModel> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public List<MyIdentifierModel> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<MyIdentifierModel> identifiers) {
        this.identifiers = identifiers;
    }

    public List<MyPractitionerModel> getPractioners() {
        return practioners;
    }

    public void setPractioners(List<MyPractitionerModel> practioners) {
        this.practioners = practioners;
    }

    public List<MyConditionModel> getConditions() {
        return conditions;
    }

    public void setConditions(List<MyConditionModel> conditions) {
        this.conditions = conditions;
    }

    public List<MyObservationModel> getObservations() {
        return observations;
    }

    public void setObservations(List<MyObservationModel> observations) {
        this.observations = observations;
    }

    public List<MyMedicationRequest> getMedicationRequests() {
        return medicationRequests;
    }

    public void setMedicationRequests(List<MyMedicationRequest> medicationRequests) {
        this.medicationRequests = medicationRequests;
    }

    public List<MyProcedureModel> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<MyProcedureModel> procedures) {
        this.procedures = procedures;
    }
}
