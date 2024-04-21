package org.frappa.leanquery.plan.fetch;

import org.antlr.v4.runtime.misc.Pair;
import static org.frappa.leanquery.jooq.tables.Staff.*;
import static org.frappa.leanquery.jooq.tables.Payment.*;
import static org.frappa.leanquery.jooq.tables.Rental.*;
import static org.frappa.leanquery.jooq.tables.Film.*;
import static org.frappa.leanquery.jooq.tables.Inventory.*;



import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;
import org.frappa.leanquery.plan.fetch.base.JoinFetchPlanHandler;
import org.frappa.leanquery.plan.fetch.base.RepositoryFetchPlanHandler;
import org.jooq.SelectField;
import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalFetchPlanHandler implements JoinFetchPlanHandler<RentalFetchPlan>, RepositoryFetchPlanHandler<RentalR, RentalFetchPlan> {

    private final Logger log = LoggerFactory.getLogger(RentalFetchPlanHandler.class);

    @Override
    public Pair<List<SelectField<?>>, Table<?>> handleJoinFetches(List<SelectField<?>> rootFields, Table<?> rootTable, RentalFetchPlan fetchPlan) {
        if(rootFields==null || rootTable==null || fetchPlan==null || fetchPlan.getSelectedFetches().isEmpty()){
            return new Pair<>(rootFields, rootTable);
        }
        List<RentalFetchPlan.AllowedFetch> joinFetches = fetchPlan.getSelectedFetches().entrySet().stream()
                .filter(f->f.getValue().equals(FetchStrategy.JOIN))
                .map(f->f.getKey())
                .collect(Collectors.toList());

        if(joinFetches.isEmpty()){
            log.warn("No join fetches to handle");
            return new Pair<>(rootFields, rootTable);
        }

        List<SelectField<?>> resultFields = new ArrayList<>(rootFields);
        Table<?> resultTable = rootTable;

        if(joinFetches.contains(RentalFetchPlan.AllowedFetch.STAFF)){
           resultFields.addAll(
               List.of(STAFF.STAFF_ID, STAFF.FIRST_NAME, STAFF.LAST_NAME)
           );
           resultTable = resultTable
                   .leftJoin(STAFF).on(STAFF.STAFF_ID.eq(RENTAL.STAFF_ID));
        }

        if(joinFetches.contains(RentalFetchPlan.AllowedFetch.PAYMENT)){
           resultFields.addAll(
               List.of(PAYMENT.PAYMENT_ID, PAYMENT.AMOUNT, PAYMENT.PAYMENT_DATE)
           );
           resultTable = resultTable
                   .leftJoin(PAYMENT).on(PAYMENT.RENTAL_ID.eq(RENTAL.RENTAL_ID));
        }

        if(joinFetches.contains(RentalFetchPlan.AllowedFetch.FILM)){
           resultFields.addAll(
               List.of(FILM.FILM_ID, FILM.TITLE, FILM.DESCRIPTION, FILM.RELEASE_YEAR)
           );
           resultTable = resultTable
                   .leftJoin(INVENTORY).on(RENTAL.INVENTORY_ID.eq(INVENTORY.INVENTORY_ID))
                   .leftJoin(FILM).on(INVENTORY.FILM_ID.eq(FILM.FILM_ID));
        }

        return new Pair<>(new ArrayList<>(resultFields), resultTable);
    }

    @Override
    public List<RentalR> handleRepositoryFetches(List<RentalR> rootEntities, RentalFetchPlan fetchPlan) {
        return rootEntities;
    }
}
