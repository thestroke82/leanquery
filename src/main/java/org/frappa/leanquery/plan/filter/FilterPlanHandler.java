package org.frappa.leanquery.plan.filter;

import org.jooq.Condition;

public interface FilterPlanHandler<T extends FilterPlan> {
    Condition handle(T filterPlan);
}
