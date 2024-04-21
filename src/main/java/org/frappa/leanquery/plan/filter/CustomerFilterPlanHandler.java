package org.frappa.leanquery.plan.filter;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.plan.filter.base.FilterPlanHandler;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import static org.frappa.leanquery.jooq.tables.Customer.CUSTOMER;
import static org.frappa.leanquery.jooq.tables.Film.FILM;
import static org.frappa.leanquery.jooq.tables.Inventory.INVENTORY;
import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;

@Component
@RequiredArgsConstructor
public class CustomerFilterPlanHandler implements FilterPlanHandler<CustomerFilterPlan> {
    private final DSLContext context;
    public Condition handle(CustomerFilterPlan filterPlan) {
        Condition condition = DSL.noCondition();
        if(filterPlan==null){
            return condition;
        }
        if(filterPlan.getIdIn()!=null && !filterPlan.getIdIn().isEmpty()){
            condition = condition.and(CUSTOMER.CUSTOMER_ID.in(filterPlan.getIdIn()));
        }
        if(filterPlan.getFirstNameLike()!=null){
            condition = condition.and(CUSTOMER.FIRST_NAME.containsIgnoreCase(filterPlan.getFirstNameLike()));
        }
        if(filterPlan.getLastNameLike()!=null){
            condition = condition.and(CUSTOMER.LAST_NAME.containsIgnoreCase(filterPlan.getLastNameLike()));
        }
        if(filterPlan.getRentedMovieTitleLike()!=null){
            condition = condition.and(CUSTOMER.CUSTOMER_ID.in(
                    context.select(RENTAL.CUSTOMER_ID)
                            .from(RENTAL)
                            .join(INVENTORY).on(RENTAL.INVENTORY_ID.eq(INVENTORY.INVENTORY_ID))
                            .join(FILM).on(INVENTORY.FILM_ID.eq(FILM.FILM_ID))
                            .where(FILM.TITLE.containsIgnoreCase(filterPlan.getRentedMovieTitleLike()))
                    )
            );
        }
        return condition;
    }
}
