package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyConditionModel {
    private String conditionId;
    private String conditionText;
    private java.util.Date recordedDate;
}
