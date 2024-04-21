package org.frappa.leanquery.plan.fetch.base;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public abstract class FetchPlan {
    @Getter
    protected Map<Fetch, FetchStrategy> selectedFetches = new HashMap<>();
}
