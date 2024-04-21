package org.frappa.leanquery.plan.fetch;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.CustomerMapper;
import org.frappa.leanquery.plan.fetch.base.FetchStrategy;
import org.frappa.leanquery.plan.fetch.base.JoinFetchPlanHandler;
import org.frappa.leanquery.plan.fetch.base.RepositoryFetchPlanHandler;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.frappa.leanquery.repository.RentalRepository;
import org.jooq.DSLContext;
import org.jooq.SelectField;
import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerFetchPlanHandler implements JoinFetchPlanHandler<CustomerFetchPlan>, RepositoryFetchPlanHandler<CustomerR, CustomerFetchPlan> {
    private final Logger log = LoggerFactory.getLogger(CustomerFetchPlanHandler.class);
    private final DSLContext context;
    private final CustomerMapper customerMapper;

    private final RentalRepository rentalRepository;


    @Override
    public Pair<List<SelectField<?>>, Table<?>> handleJoinFetches(List<SelectField<?>> rootFields, Table<?> rootTable, CustomerFetchPlan plan) {
        return new Pair<>(rootFields, rootTable);
    }
    @Override
    public List<CustomerR> handleRepositoryFetches(List<CustomerR> rootEntities, CustomerFetchPlan  customerFetchPlan) {
        if(rootEntities==null || rootEntities.isEmpty() || customerFetchPlan==null || customerFetchPlan.getSelectedFetches()== null){
            return rootEntities;
        }

        // first, get all repository fetches from the plan
        List<CustomerFetchPlan.AllowedFetch> repositoryFetches = customerFetchPlan.getSelectedFetches().entrySet().stream()
                .filter(f->f.getValue().equals(FetchStrategy.REPOSITORY))
                .map(f->f.getKey())
                .collect(Collectors.toList());
        if(repositoryFetches.isEmpty()){
            log.warn("No repository fetches to handle");
            return rootEntities;
        }

        // build an index for the root entities, so we can efficiently attach the fetched entities(see below)
        Map<Integer, CustomerR> customersIndex = rootEntities.stream().collect(Collectors.toMap(CustomerR::getId, Function.identity()));

        // if the plan contains a fetch for rentals, fetch rentals using the respective repository
        // the query is executed in a single batch, meaning that we select all the rentals that have a customer_id
        // in the set of the root result and then we attach each rental to the right customer
        if(customerFetchPlan.getSelectedFetches().containsKey(CustomerFetchPlan.AllowedFetch.RENTALS)){
            RentalFetchPlan rentalFetchPlan = (RentalFetchPlan) customerFetchPlan.getNestedFetchPlans().get(CustomerFetchPlan.AllowedFetch.RENTALS);

            // if no nested plan is provided, create a default one
            if(rentalFetchPlan==null){
                rentalFetchPlan = RentalFetchPlan.builder().build();
            }

            RentalFilterPlan rentalFilterPlan = RentalFilterPlan.builder()
                    .customerIdIn(customersIndex.keySet())
                    .build();
            List<RentalR> rentals = this.rentalRepository.findByPlan(rentalFilterPlan, rentalFetchPlan);
            rentals.forEach(r->{
                CustomerR customer = customersIndex.get(r.getCustomerId());
                customer.getRentals().add(r);
            });
        }


        return rootEntities;
    }


}
