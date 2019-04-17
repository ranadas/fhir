package com.mongodb.healthcare.fhir.model;


import java.util.Date;

public class MyEncounterModel {


    private String encounterId;
    private String type;
    private java.util.Date startDate;
    private java.util.Date endDate;


    public MyEncounterModel() {

    }

    public String getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(String encounterId) {
        this.encounterId = encounterId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
