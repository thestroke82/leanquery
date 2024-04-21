package org.frappa.leanquery.repository;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.frappa.leanquery.jooq.tables.Film;
import org.frappa.leanquery.model.FilmR;
import org.frappa.leanquery.model.RentalR;
import org.frappa.leanquery.model.mapper.FilmMapper;
import org.frappa.leanquery.plan.fetch.FilmFetchPlan;
import org.frappa.leanquery.plan.fetch.FilmFetchPlanHandler;
import org.frappa.leanquery.plan.fetch.RentalFetchPlan;
import org.frappa.leanquery.plan.filter.FilmFilterPlan;
import org.frappa.leanquery.plan.filter.FilmFilterPlanHandler;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectField;
import org.jooq.Table;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.frappa.leanquery.jooq.tables.Film.*;

@Component
@RequiredArgsConstructor
public class FilmRepository implements PlanRepository<FilmR, FilmFilterPlan, FilmFetchPlan> {

    private final DSLContext context;
    private final FilmFilterPlanHandler filterPlanHandler;
    private final FilmFetchPlanHandler fetchPlanHandler;
    private final FilmMapper filmMapper;
    @Override
    public List<FilmR> findByPlan(FilmFilterPlan filterPlan, FilmFetchPlan fetchPlan) {
        List<SelectField<?>> selectFields = List.of(FILM.FILM_ID, FILM.TITLE, FILM.DESCRIPTION, FILM.RELEASE_YEAR);
        Table<?> table = FILM;
        Condition planCondition = this.filterPlanHandler.handle(filterPlan);

        // handle join fetches if any
        Pair<List<SelectField<?>>, Table<?>> join = this.fetchPlanHandler.handleJoinFetches(selectFields, table, fetchPlan);
        selectFields = join.a;
        table = join.b;

        // fetch the root (main query)
        Result<?> rawResult= context
                .select(selectFields)
                .from(table)
                .where(planCondition)
                .orderBy(FILM.TITLE)
                .fetch();
        List<FilmR> result = this.applyMapping(rawResult, fetchPlan);

        // handle repository fetches if any
        this.fetchPlanHandler.handleRepositoryFetches(result, fetchPlan);

        return result;
    }

     private List<FilmR> applyMapping(Result<?> rawResult, FilmFetchPlan fetchPlan){
        List<FilmR> result = new ArrayList<>();
        rawResult.forEach(r->{
            FilmR current = this.filmMapper.mapFilm(r);
            if(fetchPlan.isJoinLanguage()){
                current.setLanguage(this.filmMapper.mapLanguage(r));
            }
            result.add(current);
        });
        return result;
    }
}
