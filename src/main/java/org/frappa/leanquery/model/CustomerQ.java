package org.frappa.leanquery.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CustomerQ {
    private Set<Integer> ids;
    private String firstName;
    private String lastName;
    private String rentedMovieTitle;
}
