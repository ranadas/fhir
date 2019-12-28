package com.mongodb.healthcare.fhir.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyBloodPressureObservation {
    private String system;
    private String code;
    private String display;
    private double value;
    private String unit;
}
