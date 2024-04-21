package org.frappa.leanquery.plan.fetch;

import java.util.List;

public interface FetchPlanHandler <R, F extends FetchPlan>{
    List<R> handle(List<R> rootEntities, F fetchPlan);
}
