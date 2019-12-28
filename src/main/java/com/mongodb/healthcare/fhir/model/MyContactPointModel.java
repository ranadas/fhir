package com.mongodb.healthcare.fhir.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MyContactPointModel {
    private String type;
    private String value;
}
