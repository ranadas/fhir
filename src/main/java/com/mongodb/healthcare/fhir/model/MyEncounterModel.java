package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class MyEncounterModel {


    private String encounterId;
    private String type;
    private java.util.Date startDate;
    private Date endDate;
}
