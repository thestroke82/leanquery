package org.frappa.leanquery.plan.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class CustomerFilterPlan implements FilterPlan{
    private Set<Integer> idIn;
    private String firstNameLike;
    private String lastNameLike;
    private String rentedMovieTitleLike;
}
