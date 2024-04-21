package org.frappa.leanquery.plan.fetch;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FetchPlan {
    @Getter
    protected Set<Fetch> selectedFetches = new HashSet<>();
}
