package org.frappa.leanquery.plan.filter;

import org.frappa.leanquery.plan.filter.base.FilterPlanHandler;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import static org.frappa.leanquery.jooq.tables.Film.*;

@Component
public class FilmFilterPlanHandler implements FilterPlanHandler<FilmFilterPlan> {
    @Override
    public Condition handle(FilmFilterPlan filterPlan) {
        Condition condition = DSL.noCondition();

        if(filterPlan.getTitleLike()!=null && !filterPlan.getTitleLike().isEmpty()){
            condition = condition.and(FILM.TITLE.containsIgnoreCase(filterPlan.getTitleLike()));
        }

        return condition;
    }
}
