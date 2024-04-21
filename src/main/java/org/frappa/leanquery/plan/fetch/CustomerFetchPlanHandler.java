package org.frappa.leanquery.plan.fetch;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.CustomerMapper;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.frappa.leanquery.repository.RentalRepository;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.frappa.leanquery.jooq.tables.Payment.PAYMENT;

@Component
@RequiredArgsConstructor
public class CustomerFetchPlanHandler implements FetchPlanHandler<CustomerR, CustomerFetchPlan>{
    private final DSLContext context;
    private final CustomerMapper customerMapper;

    private final RentalRepository rentalRepository;


    @Override
    public List<CustomerR> handle(List<CustomerR> rootEntities, CustomerFetchPlan  customerFetchPlan) {
        if(rootEntities==null || rootEntities.isEmpty() || customerFetchPlan==null || customerFetchPlan.getSelectedFetches().isEmpty()){
            return rootEntities;
        }
        Map<Integer, CustomerR> customersIndex = rootEntities.stream().collect(Collectors.toMap(CustomerR::getId, Function.identity()));

        if(customerFetchPlan.getSelectedFetches().contains(Fetch.rental)){
            RentalFetchPlan rentalFetchPlan = RentalFetchPlan.builder().build();
            RentalFilterPlan rentalFilterPlan = RentalFilterPlan.builder()
                    .customerIdIn(customersIndex.keySet())
                    .build();
            List<RentalR> rentals = this.rentalRepository.findByPlan(rentalFilterPlan, rentalFetchPlan);
            rentals.forEach(r->{
                CustomerR customer = customersIndex.get(r.getCustomerId());
                customer.getRentals().add(r);
            });
        }

        if(customerFetchPlan.getSelectedFetches().contains(Fetch.payment)){
            Result<?> paymentsResult = context
                .select(PAYMENT.PAYMENT_ID, PAYMENT.AMOUNT, PAYMENT.PAYMENT_DATE, PAYMENT.CUSTOMER_ID)
                .from(PAYMENT)
                .where(PAYMENT.CUSTOMER_ID.in(customersIndex.keySet()))
                .orderBy(PAYMENT.PAYMENT_DATE.desc())
                .fetch();
            paymentsResult.forEach(p->{
                CustomerR.Payment payment = this.customerMapper.mapPayment(p);
                CustomerR customer = customersIndex.get(p.get(PAYMENT.CUSTOMER_ID));
                customer.getPayments().add(payment);
            });
        }

        return rootEntities;
    }
}
