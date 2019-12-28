package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class MyProcedureModel {
    private String procedureId;
    private String status;
    private String display;
    private Date performedPeriodStart;
    private Date performedPeriodEnd;
}
