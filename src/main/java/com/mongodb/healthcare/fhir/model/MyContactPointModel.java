package com.mongodb.healthcare.fhir.model;


public class MyContactPointModel {

    private String type;
    private String value;

    public MyContactPointModel() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
