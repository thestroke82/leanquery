package org.frappa.leanquery.controller;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.model.CustomerQ;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.model.mapper.CustomerMapper;
import org.frappa.leanquery.plan.fetch.CustomerFetchPlan;
import org.frappa.leanquery.plan.filter.CustomerFilterPlan;
import org.frappa.leanquery.repository.CustomerRepository;
import org.frappa.leanquery.util.LogExecutionPerformance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @GetMapping
    @LogExecutionPerformance
    public List<CustomerR> findCustomers(
            CustomerQ query
    ) {
        CustomerFilterPlan filterPlan = this.customerMapper.mapCustomerFilterPlan(query);
        CustomerFetchPlan fetchPlan = CustomerFetchPlan.builder()
                .withRentals()
                .withPayments()
                .build();
        return customerRepository.findByPlan(filterPlan, fetchPlan);
    }
}
