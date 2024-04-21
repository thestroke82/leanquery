package org.frappa.leanquery.repository;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.FilmMapper;
import org.frappa.leanquery.model.mapper.RentalMapper;
import org.frappa.leanquery.plan.fetch.RentalFetchPlan;
import org.frappa.leanquery.plan.fetch.RentalFetchPlanHandler;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.frappa.leanquery.plan.filter.RentalFilterPlanHandler;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectField;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;

@Repository
@RequiredArgsConstructor
public class RentalRepository implements PlanRepository<RentalR, RentalFilterPlan, RentalFetchPlan>{

    private final DSLContext context;
    private final RentalMapper rentalMapper;
    private final FilmMapper filmMapper;
    private final RentalFilterPlanHandler rentalFilterPlanHandler;
    private final RentalFetchPlanHandler rentalFetchPlanHandler;

    @Override
    public List<RentalR> findByPlan(RentalFilterPlan filterPlan, RentalFetchPlan fetchPlan) {

        List<SelectField<?>> selectFields = List.of(RENTAL.RENTAL_ID, RENTAL.RENTAL_DATE, RENTAL.RETURN_DATE, RENTAL.CUSTOMER_ID);
        Table<?> table = RENTAL;
        Condition planCondition = this.rentalFilterPlanHandler.handle(filterPlan);

        // handle join fetches if any
        Pair<List<SelectField<?>>, Table<?>> join = this.rentalFetchPlanHandler.handleJoinFetches(selectFields, table, fetchPlan);
        selectFields = join.a;
        table = join.b;

        // fetch the root (main query)
        Result<?> rawResult= context
                .select(selectFields)
                .from(table)
                .where(planCondition)
                .orderBy(RENTAL.RENTAL_DATE.desc())
                .fetch();
        List<RentalR> rentals = this.applyMapping(rawResult, fetchPlan);

        // handle repository fetches if any
        this.rentalFetchPlanHandler.handleRepositoryFetches(rentals, fetchPlan);

        return rentals;
    }

    private List<RentalR> applyMapping(Result<?> rawResult, RentalFetchPlan fetchPlan){
        List<RentalR> rentals = new ArrayList<>();
        rawResult.forEach(r->{
            RentalR rental = this.rentalMapper.mapRental(r);
            if(fetchPlan.isJoinStaff()){
                rental.setStaff(this.rentalMapper.mapStaff(r));
            }
            if(fetchPlan.isJoinPayment()){
                rental.setPayment(this.rentalMapper.mapPayment(r));
            }
            if(fetchPlan.isJoinFilm()){
                rental.setFilm(this.filmMapper.mapFilm(r));
            }
            rentals.add(rental);
        });
        return rentals;
    }
}
