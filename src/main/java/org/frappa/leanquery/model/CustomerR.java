package org.frappa.leanquery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CustomerR {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RentalR> rentals;
}
