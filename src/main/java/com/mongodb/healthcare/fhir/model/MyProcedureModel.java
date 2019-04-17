package com.mongodb.healthcare.fhir.model;


import java.util.Date;

public class MyProcedureModel {

    private String procedureId;
    private String status;
    private String display;
    private java.util.Date performedPeriodStart;
    private java.util.Date performedPeriodEnd;

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Date getPerformedPeriodStart() {
        return performedPeriodStart;
    }

    public void setPerformedPeriodStart(Date performedPeriodStart) {
        this.performedPeriodStart = performedPeriodStart;
    }

    public Date getPerformedPeriodEnd() {
        return performedPeriodEnd;
    }

    public void setPerformedPeriodEnd(Date performedPeriodEnd) {
        this.performedPeriodEnd = performedPeriodEnd;
    }
}
