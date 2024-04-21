package org.frappa.leanquery.repository;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.model.mapper.CustomerMapper;
import org.frappa.leanquery.plan.fetch.CustomerFetchPlan;
import org.frappa.leanquery.plan.fetch.CustomerFetchPlanHandler;
import org.frappa.leanquery.plan.filter.CustomerFilterPlan;
import org.frappa.leanquery.plan.filter.CustomerFilterPlanHandler;
import org.frappa.leanquery.util.LogExecutionPerformance;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.frappa.leanquery.jooq.tables.Customer.CUSTOMER;

@Repository
@RequiredArgsConstructor
public class CustomerRepository implements PlanRepository<CustomerR, CustomerFilterPlan, CustomerFetchPlan>{
    private final DSLContext context;
    private final CustomerMapper customerMapper;
    private final CustomerFilterPlanHandler customerFilterPlanHandler;
    private final CustomerFetchPlanHandler customerFetchPlanHandler;


    @Override
    public List<CustomerR> findByPlan(CustomerFilterPlan filterPlan, CustomerFetchPlan fetchPlan) {

        // fetch the root
        Result<?> customersResult= context
                .select(CUSTOMER.CUSTOMER_ID, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME, CUSTOMER.EMAIL)
                .from(CUSTOMER)
                .where(this.customerFilterPlanHandler.handle(filterPlan))
                .orderBy(CUSTOMER.LAST_NAME.asc())
                .fetch();
        List<CustomerR> customers = customersResult.stream()
                .map(this.customerMapper::mapCustomer)
                .collect(Collectors.toList());

        this.customerFetchPlanHandler.handle(customers, fetchPlan);


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


}
