package org.frappa.leanquery.repository;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.model.mapper.CustomerMapper;
import org.frappa.leanquery.plan.fetch.CustomerFetchPlan;
import org.frappa.leanquery.plan.fetch.CustomerFetchPlanHandler;
import org.frappa.leanquery.plan.filter.CustomerFilterPlan;
import org.frappa.leanquery.plan.filter.CustomerFilterPlanHandler;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectField;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.frappa.leanquery.jooq.tables.Customer.CUSTOMER;
import static org.frappa.leanquery.jooq.tables.Rental.RENTAL;

@Repository
@RequiredArgsConstructor
public class CustomerRepository implements PlanRepository<CustomerR, CustomerFilterPlan, CustomerFetchPlan>{
    private final DSLContext context;
    private final CustomerMapper customerMapper;
    private final CustomerFilterPlanHandler customerFilterPlanHandler;
    private final CustomerFetchPlanHandler customerFetchPlanHandler;


    @Override
    public List<CustomerR> findByPlan(CustomerFilterPlan filterPlan, CustomerFetchPlan fetchPlan) {
        List<SelectField<?>> selectFields = List.of(CUSTOMER.CUSTOMER_ID, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME, CUSTOMER.EMAIL);
        Table<?> table = CUSTOMER;
        Condition planCondition = this.customerFilterPlanHandler.handle(filterPlan);

        // handle join fetches if any
        Pair<List<SelectField<?>>, Table<?>> join = this.customerFetchPlanHandler.handleJoinFetches(selectFields, table, fetchPlan);
        selectFields = join.a;
        table = join.b;

        // fetch the root (main query)
        Result<?> rawResult= context
                .select(selectFields)
                .from(table)
                .where(planCondition)
                .orderBy(CUSTOMER.LAST_NAME.asc())
                .fetch();
        List<CustomerR> customers = this.applyMapping(rawResult, fetchPlan);

        // handle repository fetches if any
        this.customerFetchPlanHandler.handleRepositoryFetches(customers, fetchPlan);


        // fetch rental.films rental.1..1.film via inventory
//        Result<?> filmsResult= context
//                .select(FILM.FILM_ID, FILM.RELEASE_YEAR, FILM.TITLE, FILM.DESCRIPTION, RENTAL.RENTAL_ID)
//                .from(FILM)
//                .leftJoin(INVENTORY).on(INVENTORY.FILM_ID.eq(FILM.FILM_ID))
//                .leftJoin(RENTAL).on(RENTAL.INVENTORY_ID.eq(INVENTORY.INVENTORY_ID))
//                .where(RENTAL.RENTAL_ID.in(rentalsIndex.keySet()))
//                .orderBy(FILM.TITLE.asc())
//                .fetch();
//        filmsResult.forEach(f->{
//            CustomerR.Rental.Film film = this.customerMapper.mapFilm(f);
//            CustomerR.Rental rental = rentalsIndex.get(f.get(RENTAL.RENTAL_ID));
//            rental.setFilm(film);
//        });

        return customers;
    }

    private List<CustomerR> applyMapping(Result<?> rawResult, CustomerFetchPlan fetchPlan){
        List<CustomerR> customers = new ArrayList<>();
        rawResult.forEach(r->{
            CustomerR customer = this.customerMapper.mapCustomer(r);
            customers.add(customer);
        });
        return customers;
    }


}
