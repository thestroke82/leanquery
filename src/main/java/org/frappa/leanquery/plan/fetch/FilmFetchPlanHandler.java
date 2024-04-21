package org.frappa.leanquery.plan.fetch;

import org.antlr.v4.runtime.misc.Pair;
import org.frappa.leanquery.model.FilmR;
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

import static org.frappa.leanquery.jooq.tables.Language.LANGUAGE;
import static org.frappa.leanquery.jooq.tables.Film.FILM;


@Component
public class FilmFetchPlanHandler implements JoinFetchPlanHandler<FilmFetchPlan>, RepositoryFetchPlanHandler<FilmR, FilmFetchPlan> {
    private Logger log = LoggerFactory.getLogger(FilmFetchPlanHandler.class);
    @Override
    public Pair<List<SelectField<?>>, Table<?>> handleJoinFetches(List<SelectField<?>> rootFields, Table<?> rootTable, FilmFetchPlan fetchPlan) {
        if(rootFields==null || rootTable==null || fetchPlan==null || fetchPlan.getSelectedFetches().isEmpty()){
            return new Pair<>(rootFields, rootTable);
        }
        List<FilmFetchPlan.AllowedFetch> joinFetches = fetchPlan.getSelectedFetches().entrySet().stream()
                .filter(f->f.getValue().equals(FetchStrategy.JOIN))
                .map(f->f.getKey())
                .collect(Collectors.toList());

        if(joinFetches.isEmpty()){
            log.warn("No join fetches to handle");
            return new Pair<>(rootFields, rootTable);
        }

        List<SelectField<?>> resultFields = new ArrayList<>(rootFields);
        Table<?> resultTable = rootTable;

        if(joinFetches.contains(FilmFetchPlan.AllowedFetch.LANGUAGE)){
           resultFields.addAll(
               List.of(LANGUAGE.LANGUAGE_ID, LANGUAGE.NAME)
           );
           resultTable = resultTable
                   .leftJoin(LANGUAGE).on(FILM.LANGUAGE_ID.eq(LANGUAGE.LANGUAGE_ID));
        }


        return new Pair<>(new ArrayList<>(resultFields), resultTable);
    }

    @Override
    public List<FilmR> handleRepositoryFetches(List<FilmR> rootEntities, FilmFetchPlan fetchPlan) {
        return rootEntities;
    }
}
