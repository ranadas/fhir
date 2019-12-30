package com.mongodb.healthcare.fhir.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyPractitionerModel {

    private String practitionerId;
    private String prefix;
    private String firstName;
    private String lastName;
}
