package org.frappa.leanquery.controller;

import lombok.RequiredArgsConstructor;
import org.frappa.leanquery.model.FilmQ;
import org.frappa.leanquery.model.FilmR;
import org.frappa.leanquery.model.RentalQ;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.FilmMapper;
import org.frappa.leanquery.model.mapper.RentalMapper;
import org.frappa.leanquery.plan.fetch.FilmFetchPlan;
import org.frappa.leanquery.plan.fetch.RentalFetchPlan;
import org.frappa.leanquery.plan.filter.FilmFilterPlan;
import org.frappa.leanquery.plan.filter.RentalFilterPlan;
import org.frappa.leanquery.repository.FilmRepository;
import org.frappa.leanquery.repository.RentalRepository;
import org.frappa.leanquery.util.LogExecutionPerformance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("film")
@RequiredArgsConstructor
public class FilmController {

    private final FilmRepository repository;
    private final FilmMapper mapper;

    @GetMapping
    @LogExecutionPerformance
    public List<FilmR> find(
            FilmQ query
    ) {
        FilmFilterPlan filterPlan = this.mapper.mapFilterPlan(query);
        FilmFetchPlan fetchPlan = FilmFetchPlan.builder()
                .withLanguage()
                .build();
        return repository.findByPlan(filterPlan, fetchPlan);
    }
}
