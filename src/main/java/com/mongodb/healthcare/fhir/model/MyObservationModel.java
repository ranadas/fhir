package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
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

}
