package com.mongodb.healthcare.fhir.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class MyMedicationRequest {

    private String medicationRequestId;
    private String status;
    private String intent;
    private String display;
    private Date authoredOn;

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
