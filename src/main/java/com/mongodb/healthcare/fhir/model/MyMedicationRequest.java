package com.mongodb.healthcare.fhir.model;


import java.util.Date;

public class MyMedicationRequest {

    private String medicationRequestId;
    private String status;
    private String intent;
    private String display;
    private java.util.Date authoredOn;


    public String getMedicationRequestId() {
        return medicationRequestId;
    }

    public void setMedicationRequestId(String medicationRequestId) {
        this.medicationRequestId = medicationRequestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Date getAuthoredOn() {
        return authoredOn;
    }

    public void setAuthoredOn(Date authoredOn) {
        this.authoredOn = authoredOn;
    }

    @Override
    public String toString() {
        return "MyMedicationRequest{" +
                "medicationRequestId='" + medicationRequestId + '\'' +
                ", status='" + status + '\'' +
                ", intent='" + intent + '\'' +
                ", display='" + display + '\'' +
                ", authoredOn=" + authoredOn +
                '}';
    }
}
