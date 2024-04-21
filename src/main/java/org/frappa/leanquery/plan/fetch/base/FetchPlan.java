package org.frappa.leanquery.plan.fetch.base;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public abstract class FetchPlan<T> {
    @Getter
    protected Map<T, FetchStrategy> selectedFetches = new HashMap<>();
    @Getter
    protected Map<T, FetchPlan> nestedFetchPlans = new HashMap<>();

}
