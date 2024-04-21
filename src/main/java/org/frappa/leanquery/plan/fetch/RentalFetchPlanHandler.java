package org.frappa.leanquery.plan.fetch;

import org.frappa.leanquery.model.RentalR;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalFetchPlanHandler implements FetchPlanHandler<RentalR, RentalFetchPlan>{
    @Override
    public List<RentalR> handle(List<RentalR> rootEntities, RentalFetchPlan rentalFetchPlan) {
        return rootEntities;
    }
}
