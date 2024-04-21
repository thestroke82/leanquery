package org.frappa.leanquery.plan.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class RentalFilterPlan implements FilterPlan{
    private Set<Integer> customerIdIn;
}
