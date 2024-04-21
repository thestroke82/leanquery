package org.frappa.leanquery.repository;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.RentalMapper;
import org.frappa.leanquery.plan.fetch.RentalFetchPlan;
import org.frappa.leanquery.plan.fetch.RentalFetchPlanHandler;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.frappa.leanquery.plan.filter.RentalFilterPlanHandler;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;

@Repository
@RequiredArgsConstructor
public class RentalRepository implements PlanRepository<RentalR, RentalFilterPlan, RentalFetchPlan>{

    private final DSLContext context;
    private final RentalMapper rentalMapper;
    private final RentalFilterPlanHandler rentalFilterPlanHandler;
    private final RentalFetchPlanHandler rentalFetchPlanHandler;

    @Override
    public List<RentalR> findByPlan(RentalFilterPlan filterPlan, RentalFetchPlan fetchPlan) {

        // fetch the root
        Result<?> rawResult= context
                .select(RENTAL.RENTAL_ID, RENTAL.RENTAL_DATE, RENTAL.RETURN_DATE, RENTAL.CUSTOMER_ID)
                .from(RENTAL)
                .where(this.rentalFilterPlanHandler.handle(filterPlan))
                .orderBy(RENTAL.RENTAL_DATE.desc())
                .fetch();
        List<RentalR> rentals = rawResult.stream()
                .map(this.rentalMapper::mapRental)
                .collect(Collectors.toList());

        this.rentalFetchPlanHandler.handle(rentals, fetchPlan);

        return rentals;
    }
}
