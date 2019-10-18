package com.mongodb.healthcare.fhir.parser;


import ca.uhn.fhir.model.dstu2.valueset.IdentifierTypeCodesEnum;
import com.mongodb.healthcare.fhir.model.*;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class R4BundleProcessor {

    // Logger component
    private static final Logger logger = LoggerFactory.getLogger(R4BundleProcessor.class);

    private Bundle bundle;

    private MyPatientModel myPatientModel;
    private List<MyContactPointModel> myContactPoints;
    private List<MyIdentifierModel> myIdentifiers;
    private List<MyOrganizationModel> myOrganizations;
    private List<MyEncounterModel> myEncounters;
    private List<MyPractitionerModel> myPractitioners;
    private List<MyConditionModel> myConditions;
    private List<MyObservationModel> myObservations;
    private List<MyMedicationRequest> myMedicationRequests;
    private List<MyProcedureModel> myProcedures;

    /**
     * @param bundle
     */
    public R4BundleProcessor(Bundle bundle) {
        logger.debug("Processing bundle.");

        this.bundle = bundle;

        this.myPatientModel = new MyPatientModel();
        this.myPatientModel.setDateBundleParsed(new java.util.Date());

        this.myContactPoints = new ArrayList<MyContactPointModel>();
        this.myIdentifiers = new ArrayList<MyIdentifierModel>();
        this.myOrganizations = new ArrayList<MyOrganizationModel>();
        this.myEncounters = new ArrayList<MyEncounterModel>();
        this.myPractitioners = new ArrayList<MyPractitionerModel>();
        this.myConditions = new ArrayList<MyConditionModel>();
        this.myObservations = new ArrayList<MyObservationModel>();
        this.myMedicationRequests = new ArrayList<MyMedicationRequest>();
        this.myProcedures = new ArrayList<MyProcedureModel>();

        this.parseBundle();
    }

    /**
     *
     */
    private void parseBundle() {
        for (Bundle.BundleEntryComponent bundleEntryComponent : bundle.getEntry()) {
            Resource resource = bundleEntryComponent.getResource();

            logger.debug("Found Resource Type: " + resource.fhirType());

            // Patient
            if (resource instanceof Patient) {
                Patient patient = (Patient) resource;
                if (patient != null) {
                    this.parsePatient(patient);
                }
            }

            // Organization
            if (resource instanceof Organization) {
                Organization organization = (Organization) resource;
                if (organization != null) {
                    this.parseOrganization(organization);
                }
            }

            // Practitioner
            if (resource instanceof Practitioner) {
                Practitioner practitioner = (Practitioner) resource;
                if (practitioner != null) {
                    this.parsePractitioner(practitioner);
                }
            }

            // Encounter
            if (resource instanceof Encounter) {
                Encounter encounter = (Encounter) resource;
                if (encounter != null) {
                    this.parseEncounter(encounter);
                }
            }

            // Condition
            if (resource instanceof Condition) {
                Condition condition = (Condition) resource;
                if (condition != null) {
                    this.parseCondition(condition);
                }
            }

            // Observations
            if (resource instanceof Observation) {
                Observation observation = (Observation)resource;
                if (observation != null) {
                    this.parseObservation(observation);
                }
            }

            // Medication Requests
            if (resource instanceof MedicationRequest) {
                MedicationRequest medicationRequest = (MedicationRequest) resource;
                if (medicationRequest != null) {
                    this.parseMedicationRequest(medicationRequest);
                }
            }

            // Procedures
            if (resource instanceof Procedure) {
                Procedure procedure = (Procedure) resource;
                if (procedure != null) {
                    this.parseProcedure(procedure);
                }
            }

            // TODO - Complete following FHIR Resource Types
            // Goal
            // Careplan
            // Claim
            // Explanation of Benefits
            // Immunization
            // Diagnostic Report

        }

        this.myPatientModel.setContactPoints(this.myContactPoints);
        this.myPatientModel.setIdentifiers(this.myIdentifiers);
        this.myPatientModel.setOrganizations(this.myOrganizations);
        this.myPatientModel.setEncounters(this.myEncounters);
        this.myPatientModel.setPractioners(this.myPractitioners);
        this.myPatientModel.setConditions(this.myConditions);
        this.myPatientModel.setObservations(this.myObservations);
        this.myPatientModel.setMedicationRequests(this.myMedicationRequests);
        this.myPatientModel.setProcedures(this.myProcedures);
    }

    /**
     * @param patient
     */
    private void parsePatient(Patient patient) {
        logger.debug("Parse patient.");

        // Patient Id
        // TODO - Fix id string
        this.myPatientModel.setPatientId(patient.getId().replace("Patient/", ""));

        // Patient Names
        HumanName name = patient.getNameFirstRep();
        this.myPatientModel.setPrefix(name.getPrefixAsSingleString());
        this.myPatientModel.setFirstName(name.getGivenAsSingleString());
        this.myPatientModel.setLastName(name.getFamily());

        // Address
        Address address = patient.getAddressFirstRep();
        this.myPatientModel.setAddress(address.getLine().get(0).toString());
        this.myPatientModel.setCity(address.getCity());
        this.myPatientModel.setState(address.getState());
        this.myPatientModel.setPostalCode(address.getPostalCode());
        this.myPatientModel.setCountryCode(address.getCountry());

        Extension extension = address.getExtensionFirstRep();
        List geoLocation = extension.getExtension();
        AddressLocation addressLocation = new AddressLocation();
        addressLocation.setType("Point");
        double[] coordinatesArray = new double[2];

        for(Object geo : geoLocation){
            Extension location = (Extension)geo;
            if(location.getUrl().equalsIgnoreCase("longitude")) {
                DecimalType decimalType = (DecimalType)location.getValue();

                double longitude = 0.0;

                try {
                    longitude = Double.valueOf(decimalType.getValue().toString());
                }
                catch(NumberFormatException nfe) {
                    logger.error("Longitude parse exception.", nfe);
                }

                coordinatesArray[0] = longitude;
            }

            if(location.getUrl().equalsIgnoreCase("latitude")){
                DecimalType decimalType = (DecimalType)location.getValue();

                double latitude = 0.0;

                try {
                    latitude = Double.valueOf(decimalType.getValue().toString());
                }
                catch(NumberFormatException nfe) {
                    logger.error("Latitude parse exception.", nfe);
                }

                coordinatesArray[1] = latitude;
            }
        }
        addressLocation.setCoordinates(coordinatesArray);
        this.myPatientModel.setAddressLocation(addressLocation);

        // Patient Gender
        this.myPatientModel.setGender(patient.getGender().getDisplay());

        // Patient Birth date
        this.myPatientModel.setBirthDate(patient.getBirthDate());

        // Is patient deceased?
        if(patient.hasDeceased()) {
            myPatientModel.setDeceased(true);
            try {
                myPatientModel.setDateDeceased(patient.getDeceasedDateTimeType().getValue());
            } catch (FHIRException fe) {
                logger.error("Error parsing data deceased: " + fe);
            }
        }

        // Marital Status
        this.myPatientModel.setMaritalStatus(patient.getMaritalStatus().getText());

        // Language
        this.myPatientModel.setLanguage(patient.getCommunicationFirstRep().getLanguage().getText());

        // Patient ContactPoints
        List contactPoints = patient.getTelecom();

        for (Object contactPoint : contactPoints) {
            if (contactPoint instanceof ContactPoint) {
                if (((ContactPoint) contactPoint).getUse().equals(ContactPoint.ContactPointUse.HOME)) {

                    MyContactPointModel myContactPoint = new MyContactPointModel();
                    myContactPoint.setType("home");
                    myContactPoint.setValue(((ContactPoint) contactPoint).getValue());

                    this.myContactPoints.add(myContactPoint);
                }

                if (((ContactPoint) contactPoint).getUse().equals(ContactPoint.ContactPointUse.MOBILE)) {
                    MyContactPointModel myContactPoint = new MyContactPointModel();
                    myContactPoint.setType("mobile");
                    myContactPoint.setValue(((ContactPoint) contactPoint).getValue());

                    this.myContactPoints.add(myContactPoint);
                }

                if (((ContactPoint) contactPoint).getUse().equals(ContactPoint.ContactPointUse.WORK)) {
                    MyContactPointModel myContactPoint = new MyContactPointModel();
                    myContactPoint.setType("work");
                    myContactPoint.setValue(((ContactPoint) contactPoint).getValue());

                    this.myContactPoints.add(myContactPoint);
                }
            }
        }


        // Patient Identifier
        List identifiers = patient.getIdentifier();

        for (Object identifier : identifiers) {
            if (identifier instanceof Identifier) {
                String code = ((Identifier) identifier).getType().getCodingFirstRep().getCode();
                if (code != null) {
                    // Medical Record
                    if (code.equalsIgnoreCase(IdentifierTypeCodesEnum.MR.toString())) {
                        MyIdentifierModel myIdentifier = new MyIdentifierModel();
                        myIdentifier.setType("MR");
                        myIdentifier.setValue(((Identifier) identifier).getValue());
                        myIdentifier.setDisplay(((Identifier) identifier).getType().getText());

                        this.myIdentifiers.add(myIdentifier);
                    }

                    // SSN
                    if (code.equalsIgnoreCase(IdentifierTypeCodesEnum.SOCIAL_BENEFICIARY_IDENTIFIER.getCode())) {
                        MyIdentifierModel myIdentifier = new MyIdentifierModel();
                        myIdentifier.setType("SSB");
                        myIdentifier.setValue(((Identifier) identifier).getValue());
                        myIdentifier.setDisplay(((Identifier) identifier).getType().getText());

                        this.myIdentifiers.add(myIdentifier);
                    }

                    // Driver's License
                    if (code.equalsIgnoreCase(IdentifierTypeCodesEnum.DL.getCode())) {
                        MyIdentifierModel myIdentifier = new MyIdentifierModel();
                        myIdentifier.setType("DL");
                        myIdentifier.setValue(((Identifier) identifier).getValue());
                        myIdentifier.setDisplay(((Identifier) identifier).getType().getText());

                        this.myIdentifiers.add(myIdentifier);
                    }

                    // Passport Number
                    if (code.equalsIgnoreCase(IdentifierTypeCodesEnum.PPN.getCode())) {
                        MyIdentifierModel myIdentifier = new MyIdentifierModel();
                        myIdentifier.setType("PPN");
                        myIdentifier.setValue(((Identifier) identifier).getValue());
                        myIdentifier.setDisplay(((Identifier) identifier).getType().getText());

                        this.myIdentifiers.add(myIdentifier);
                    }
                }
            }
        }
    }

    /**
     * @param organization
     */
    private void parseOrganization(Organization organization) {
        logger.debug("Parse Organization.");

        MyOrganizationModel myOrganizationModel = new MyOrganizationModel();

        myOrganizationModel.setOrganization(organization.getTypeFirstRep().getText());
        myOrganizationModel.setOrgId(organization.getIdentifierFirstRep().getValue());
        myOrganizationModel.setCode(organization.getTypeFirstRep().getCodingFirstRep().getCode());
        myOrganizationModel.setName(organization.getName());

        // org address
        Address address = organization.getAddressFirstRep();
        myOrganizationModel.setAddress(address.getLine().get(0).toString());
        myOrganizationModel.setCity(address.getCity());
        myOrganizationModel.setState(address.getState());
        myOrganizationModel.setPostalCode(address.getPostalCode());
        myOrganizationModel.setCountry(address.getCountry());

        // org telecom
        // TODO - Add a loop to get all contact points
        ContactPoint contactPoint = organization.getTelecomFirstRep();
        Map<String,String> contactPoints = new HashMap<>();
        contactPoints.put(contactPoint.getSystem().toCode(),contactPoint.getValue());
        myOrganizationModel.setContactPoints(contactPoints);

        // add org to list
        this.myOrganizations.add(myOrganizationModel);
    }

    /**
     * @param practitioner
     */
    private void parsePractitioner(Practitioner practitioner) {
        logger.debug("Parse Practitioner.");

        MyPractitionerModel myPractitionerModel = new MyPractitionerModel();

        // TODO - had to replace the Practitioner/ string in the ID - no clue why!!
        myPractitionerModel.setPractitionerId(practitioner.getId().replace("Practitioner/", ""));
        myPractitionerModel.setPrefix(practitioner.getNameFirstRep().getPrefixAsSingleString());
        myPractitionerModel.setFirstName(practitioner.getNameFirstRep().getGivenAsSingleString());
        myPractitionerModel.setLastName(practitioner.getNameFirstRep().getFamily());

        this.myPractitioners.add(myPractitionerModel);
    }

    /**
     * @param encounter
     */
    private void parseEncounter(Encounter encounter) {
        logger.debug("Parse Encounter.");

        MyEncounterModel myEncounterModel = new MyEncounterModel();

        // TODO - had to replace the Encounter/ string in the ID - no clue why!!
        myEncounterModel.setEncounterId(encounter.getId().replace("Encounter/", ""));
        myEncounterModel.setType(encounter.getTypeFirstRep().getText());
        myEncounterModel.setStartDate(encounter.getPeriod().getStart());
        myEncounterModel.setEndDate(encounter.getPeriod().getEnd());

        this.myEncounters.add(myEncounterModel);
    }

    /**
     * @param condition
     */
    private void parseCondition(Condition condition) {
        logger.debug("Parse Condition.");

        MyConditionModel myConditionModel = new MyConditionModel();

        // TODO
        myConditionModel.setConditionId(condition.getId().replace("Condition/", ""));
        myConditionModel.setConditionText(condition.getCode().getText());
        myConditionModel.setRecordedDate(condition.getRecordedDate());

        this.myConditions.add(myConditionModel);
    }

    /**
     *
     * @param medicationRequest
     */
    private void parseMedicationRequest(MedicationRequest medicationRequest) {
        logger.debug("Parse Medication Request.");

        MyMedicationRequest myMedicationRequest = new MyMedicationRequest();

        // TODO
        myMedicationRequest.setMedicationRequestId(medicationRequest.getId().replace("MedicationRequest/", ""));
        myMedicationRequest.setStatus(medicationRequest.getStatus().getDisplay());
        myMedicationRequest.setIntent(medicationRequest.getIntent().getDisplay());
        try {
            myMedicationRequest.setDisplay(medicationRequest.getMedicationCodeableConcept().getText());
        } catch (FHIRException fe) {
            logger.error("Error parsing medicationRequest.display: " + fe);
        }
        myMedicationRequest.setAuthoredOn(medicationRequest.getAuthoredOn());

        this.myMedicationRequests.add(myMedicationRequest);
    }

    /**
     *
     * @param procedure
     */
    private void parseProcedure(Procedure procedure) {
        logger.debug("Parse Procedure.");

        MyProcedureModel myProcedureModel = new MyProcedureModel();

        // TODO
        myProcedureModel.setProcedureId(procedure.getId().replace("Procedure/", ""));
        myProcedureModel.setStatus(procedure.getStatus().getDisplay());
        myProcedureModel.setDisplay(procedure.getCode().getText());

        try {
            myProcedureModel.setPerformedPeriodStart(procedure.getPerformedPeriod().getStart());
            myProcedureModel.setPerformedPeriodEnd(procedure.getPerformedPeriod().getEnd());
        } catch (FHIRException fe) {
            logger.error("Error parsing procedure start and/or end period: " + fe);
        }

        this.myProcedures.add(myProcedureModel);
    }

    /**
     *
     * @param observation
     */
    private void parseObservation(Observation observation) {
        logger.debug("Parse Observation.");

        MyObservationModel myObservationModel = new MyObservationModel();

        // TODO
        myObservationModel.setObservationId(observation.getId().replace("Observation/", ""));
        myObservationModel.setStatus(observation.getStatus().getDisplay());
        myObservationModel.setCategoryCode(observation.getCategoryFirstRep().getCodingFirstRep().getCode());
        myObservationModel.setCategoryDisplay(observation.getCategoryFirstRep().getCodingFirstRep().getDisplay());
        myObservationModel.setCodeSystem(observation.getCode().getCodingFirstRep().getSystem());
        myObservationModel.setCode(observation.getCode().getCodingFirstRep().getCode());
        myObservationModel.setCodeDisplay(observation.getCode().getCodingFirstRep().getDisplay());

        try {
            myObservationModel.setEffectiveDate(observation.getEffectiveDateTimeType().getValue());
        } catch (FHIRException fe) {
            logger.error("Error parsing observation.effectivedate: " + fe);
        }
        myObservationModel.setIssuedDate(observation.getIssued());

        // various measurements
        try {
            if (observation.hasValueQuantity()) {
                Quantity quantity = observation.getValueQuantity();
                if (quantity != null) {
                    myObservationModel.setValueQuantity(quantity.getValue().doubleValue());
                    myObservationModel.setUnitOfMeasure(quantity.getUnit());
                }
            }
        } catch (FHIRException fe) {
            logger.error("Error parsing observation.valuequantity: " + fe);
        }

        // multi value, for example blood pressure with diastolic and systolic values
        if (observation.hasComponent()) {
            List<Observation.ObservationComponentComponent> components = observation.getComponent();

            List<MyBloodPressureObservation> myBloodPressureObservations = new ArrayList<>();

            for (Object component : components) {
                if (component instanceof Observation.ObservationComponentComponent) {
                    Observation.ObservationComponentComponent myComponent =
                            (Observation.ObservationComponentComponent) component;

                    String code = myComponent.getCode().getCodingFirstRep().getCodeElement().getCode();

                    // Diastolic pressure - 8462-4
                    // Systolic pressure - 8480-6
                    if (code.equalsIgnoreCase("8462-4") || code.equalsIgnoreCase("8480-6")) {
                        MyBloodPressureObservation myBloodPressureObservation = new MyBloodPressureObservation();

                        myBloodPressureObservation.setSystem(myComponent.getCode().getCodingFirstRep().getSystem());
                        myBloodPressureObservation.setCode(myComponent.getCode().getCodingFirstRep().getCode());
                        myBloodPressureObservation.setDisplay(myComponent.getCode().getCodingFirstRep().getDisplay());

                        try {
                            if (myComponent.hasValueQuantity()) {
                                Quantity quantity = myComponent.getValueQuantity();
                                if (quantity != null) {
                                    myBloodPressureObservation.setValue(quantity.getValue().doubleValue());
                                    myBloodPressureObservation.setUnit(quantity.getUnit());

                                    myBloodPressureObservations.add(myBloodPressureObservation);
                                }
                            }
                        } catch (FHIRException fe) {
                            logger.error("Error parsing observation.bloodpressure quantity or value: " + fe);
                        }
                    }
                }
            }

            // add blood pressure if there are values in the list
            if (!myBloodPressureObservations.isEmpty()) {
                logger.debug("Adding Blood Pressures to Observation.");
                myObservationModel.setBloodPressure(myBloodPressureObservations);
            }
        }

        this.myObservations.add(myObservationModel);
    }

    /**
     * @return
     */
    public Bundle getBundle() {
        return this.bundle;
    }

    /**
     *
     * @return
     */
    public MyPatientModel getMyPatientModel(){
        return this.myPatientModel;
    }
}
