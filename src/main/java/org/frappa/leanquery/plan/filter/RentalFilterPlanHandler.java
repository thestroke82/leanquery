package org.frappa.leanquery.plan.filter;

import org.frappa.leanquery.plan.filter.base.FilterPlanHandler;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;

@Component
public class RentalFilterPlanHandler implements FilterPlanHandler<RentalFilterPlan> {
    @Override
    public Condition handle(RentalFilterPlan filterPlan) {
        Condition condition = DSL.noCondition();

        if(filterPlan.getCustomerIdIn()!=null && !filterPlan.getCustomerIdIn().isEmpty()){
            condition = condition.and(RENTAL.CUSTOMER_ID.in(filterPlan.getCustomerIdIn()));
        }

        return condition;
    }
}
