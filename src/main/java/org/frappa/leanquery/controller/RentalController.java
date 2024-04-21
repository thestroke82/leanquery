package org.frappa.leanquery.controller;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.model.CustomerQ;
import org.frappa.leanquery.model.CustomerR;
import org.frappa.leanquery.model.RentalQ;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.CustomerMapper;
import org.frappa.leanquery.model.mapper.RentalMapper;
import org.frappa.leanquery.plan.fetch.CustomerFetchPlan;
import org.frappa.leanquery.plan.fetch.RentalFetchPlan;
import org.frappa.leanquery.plan.filter.CustomerFilterPlan;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.frappa.leanquery.repository.CustomerRepository;
import org.frappa.leanquery.repository.RentalRepository;
import org.frappa.leanquery.util.LogExecutionPerformance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rental")
@RequiredArgsConstructor
public class RentalController {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @GetMapping
    @LogExecutionPerformance
    public List<RentalR> find(
            RentalQ query
    ) {
        RentalFilterPlan filterPlan = this.rentalMapper.mapRentalFilterPlan(query);
        RentalFetchPlan fetchPlan = RentalFetchPlan.builder()
                .withStaff()
                .withFilm()
                .withPayment()
                .build();
        return rentalRepository.findByPlan(filterPlan, fetchPlan);
    }
}
