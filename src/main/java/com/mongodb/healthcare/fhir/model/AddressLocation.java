package com.mongodb.healthcare.fhir.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressLocation {

    private String type;
    private double[] coordinates;
}
