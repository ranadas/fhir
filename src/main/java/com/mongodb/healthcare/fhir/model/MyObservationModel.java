package com.mongodb.healthcare.fhir.model;


import java.util.Date;
import java.util.List;

public class MyObservationModel {

    private String observationId;
    private String status;
    private String categoryCode;
    private String categoryDisplay;
    private String codeSystem;
    private String code;
    private String codeDisplay;
    private Date effectiveDate;
    private Date issuedDate;
    private double valueQuantity;
    private String unitOfMeasure;
    private List<MyBloodPressureObservation> bloodPressure;

    public String getObservationId() {
        return observationId;
    }

    public void setObservationId(String observationId) {
        this.observationId = observationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryDisplay() {
        return categoryDisplay;
    }

    public void setCategoryDisplay(String categoryDisplay) {
        this.categoryDisplay = categoryDisplay;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDisplay() {
        return codeDisplay;
    }

    public void setCodeDisplay(String codeDisplay) {
        this.codeDisplay = codeDisplay;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public double getValueQuantity() {
        return valueQuantity;
    }

    public void setValueQuantity(double valueQuantity) {
        this.valueQuantity = valueQuantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }


    public List<MyBloodPressureObservation> getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(List<MyBloodPressureObservation> bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
}
