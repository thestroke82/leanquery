package org.frappa.leanquery.plan.fetch.base;

import java.util.List;

public interface RepositoryFetchPlanHandler<R, F extends FetchPlan>{
    List<R> handleRepositoryFetches(List<R> rootEntities, F fetchPlan);
}
