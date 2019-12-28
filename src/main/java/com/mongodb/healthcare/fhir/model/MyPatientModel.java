package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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

}
